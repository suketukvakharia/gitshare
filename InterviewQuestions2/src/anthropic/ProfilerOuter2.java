package anthropic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public class ProfilerOuter2 {

  /**
   * Loop 1 - round 2: coding round
   * Main Question: Given profiler samples from running code, find the slowest part in the code
   *
   * Setup:
   *
   * Code is being run and profiler samples data throughout execution
   * Have different samples showing which function was called after which
   * Don't get full samples - have to piece together from multiple samples
   * Need to figure out the stack trace and identify slowest/fastest functions
   *
   *
   * Events are in the format:
   * ("start", "A", 0)
   * ("start", "B", 2)
   * ("end", "B", 5)
   * ("end", "A", 6)
   *
   * We need to keep track of each start-end, function call time
   *
   * Re-construct stack for each entry,
   * re-construct time elapsed for each entry.
   * Keep track of fastest & slowest
   */

  private class ProfilerInput {
    private final String type; // start|end
    private final String functionName;
    private final long timestampMs;

    public ProfilerInput(String type, String functionName, long timestampMs) {
      this.type = type;
      this.functionName = functionName;
      this.timestampMs = timestampMs;
    }
  }

  private class Profiler {
    public Map<String, Long> getSlowest(List<ProfilerInput> profilerInputs, long profileFinishTime) {

      // keep track of current trace using list of profilerInpue
      List<ProfilerInput> currentTrace = new ArrayList<>();

      // final result*
      Map<String, Long> functionToTotalTime = new HashMap<>();

      // iterate through each profiled input keeping track of full results
      for (ProfilerInput profilerInput : profilerInputs) {
        if (!Set.of("start", "end").contains(profilerInput.type)) {
          continue; // unsupported type
        }

        if (profilerInput.type.equals("start")) {
          currentTrace.add(profilerInput);
          continue;
        }

        // should be end*
        if (currentTrace.isEmpty()) {
          // not expected return for now
          throw new RuntimeException("Invalid profiled events");
        }

        // Create profiled result
        ProfilerInput finishingFunction = currentTrace.remove(currentTrace.size()-1);
        long elapsedMs = profilerInput.timestampMs - finishingFunction.timestampMs;
        String finishedFunction = finishingFunction.functionName;
        functionToTotalTime.putIfAbsent(finishedFunction, 0L);
        functionToTotalTime.put(finishedFunction, functionToTotalTime.get(finishedFunction) + elapsedMs);
      }

      // add the remaining current element assuming they all finished at the end
      while(!currentTrace.isEmpty()) {
        ProfilerInput finishingFunction = currentTrace.remove(currentTrace.size()-1);
        long elapsedMs = profileFinishTime - finishingFunction.timestampMs;
        String finishedFunction = finishingFunction.functionName;
        functionToTotalTime.putIfAbsent(finishedFunction, 0L);
        functionToTotalTime.put(finishedFunction, functionToTotalTime.get(finishedFunction) + elapsedMs);
      }

      return functionToTotalTime;
    }
  }

  @Test
  public void testNestedExecution() {
    ProfilerOuter2 outer = new ProfilerOuter2();
    ProfilerOuter2.Profiler profiler = outer.new Profiler();

    // A starts at 0, B starts at 2, B ends at 5, A ends at 6
    List<ProfilerOuter2.ProfilerInput> inputs = List.of(
        outer.new ProfilerInput("start", "A", 0),
        outer.new ProfilerInput("start", "B", 2),
        outer.new ProfilerInput("end", "B", 5),
        outer.new ProfilerInput("end", "A", 6)
    );

    // profileFinishTime is 6 (time of the last event)
    Map<String, Long> results = profiler.getSlowest(inputs, 6);

    assertEquals(2, results.size());
    assertEquals(Long.valueOf(3), results.get("B")); // B ran from 2 to 5
    assertEquals(Long.valueOf(6), results.get("A")); // A ran from 0 to 6
  }

  @Test
  public void testSequentialExecution() {
    ProfilerOuter2 outer = new ProfilerOuter2();
    ProfilerOuter2.Profiler profiler = outer.new Profiler();

    // A runs entirely, then B runs entirely
    List<ProfilerOuter2.ProfilerInput> inputs = List.of(
        outer.new ProfilerInput("start", "A", 0),
        outer.new ProfilerInput("end", "A", 10),
        outer.new ProfilerInput("start", "B", 15),
        outer.new ProfilerInput("end", "B", 20)
    );

    Map<String, Long> results = profiler.getSlowest(inputs, 20);

    assertEquals(2, results.size());
    assertEquals(Long.valueOf(10), results.get("A"));
    assertEquals(Long.valueOf(5), results.get("B"));
  }

  @Test(expected = RuntimeException.class)
  public void testInvalidEvents_EmptyStackOnEnd() {
    ProfilerOuter2 outer = new ProfilerOuter2();
    ProfilerOuter2.Profiler profiler = outer.new Profiler();

    List<ProfilerOuter2.ProfilerInput> inputs = List.of(
        outer.new ProfilerInput("end", "A", 5)
    );

    profiler.getSlowest(inputs, 5); // Should still throw RuntimeException
  }

  @Test
  public void testUnsupportedTypesAreIgnored() {
    ProfilerOuter2 outer = new ProfilerOuter2();
    ProfilerOuter2.Profiler profiler = outer.new Profiler();

    List<ProfilerOuter2.ProfilerInput> inputs = List.of(
        outer.new ProfilerInput("start", "A", 0),
        outer.new ProfilerInput("pause", "A", 2), // Should be ignored
        outer.new ProfilerInput("end", "A", 5)
    );

    Map<String, Long> results = profiler.getSlowest(inputs, 5);

    assertEquals(1, results.size());
    assertEquals(Long.valueOf(5), results.get("A"));
  }

  // ==========================================
  // GOTCHA TESTS (Now passing with your new code!)
  // ==========================================

  @Test
  public void testGotcha_CyclesAndRecursion() {
    ProfilerOuter2 outer = new ProfilerOuter2();
    ProfilerOuter2.Profiler profiler = outer.new Profiler();

    // A calls B, B calls A (Recursion)
    List<ProfilerOuter2.ProfilerInput> inputs = List.of(
        outer.new ProfilerInput("start", "A", 0),
        outer.new ProfilerInput("start", "B", 2),
        outer.new ProfilerInput("start", "A", 5), // Recursive call
        outer.new ProfilerInput("end", "A", 10),  // Inner A takes 5ms
        outer.new ProfilerInput("end", "B", 15),  // B takes 13ms
        outer.new ProfilerInput("end", "A", 20)   // Outer A takes 20ms
    );

    Map<String, Long> results = profiler.getSlowest(inputs, 20);

    assertEquals(2, results.size());
    // B took 13ms total
    assertEquals(Long.valueOf(13), results.get("B"));

    // A took 5ms (inner) + 20ms (outer) = 25ms total
    // Your Map successfully aggregates these!
    assertEquals(Long.valueOf(25), results.get("A"));
  }

  @Test
  public void testGotcha_MissingEndEvent_ProgramCrash() {
    ProfilerOuter2 outer = new ProfilerOuter2();
    ProfilerOuter2.Profiler profiler = outer.new Profiler();

    List<ProfilerOuter2.ProfilerInput> inputs = List.of(
        outer.new ProfilerInput("start", "Main", 0),
        outer.new ProfilerInput("start", "DatabaseCall", 5)
        // CRASH! No end events.
    );

    // Interviewer says: "The app crashed at timestamp 15"
    Map<String, Long> results = profiler.getSlowest(inputs, 15);

    // Your new while(!currentTrace.isEmpty()) block beautifully handles this.
    assertEquals(2, results.size());

    // DB Call started at 5, crashed at 15 -> 10ms
    assertEquals(Long.valueOf(10), results.get("DatabaseCall"));
    // Main started at 0, crashed at 15 -> 15ms
    assertEquals(Long.valueOf(15), results.get("Main"));
  }

  @Test
  public void testGotcha_HighVolumeLoop_Aggregation() {
    ProfilerOuter2 outer = new ProfilerOuter2();
    ProfilerOuter2.Profiler profiler = outer.new Profiler();

    List<ProfilerOuter2.ProfilerInput> inputs = new ArrayList<>();
    long time = 0;

    // 10,000 rapid executions of a fast function
    for (int i = 0; i < 10000; i++) {
      inputs.add(outer.new ProfilerInput("start", "FastPoller", time));
      time += 1; // takes 1ms
      inputs.add(outer.new ProfilerInput("end", "FastPoller", time));
      time += 1; // wait 1ms
    }

    Map<String, Long> results = profiler.getSlowest(inputs, time);

    // Previously, this generated 10,000 objects. Now it's just 1 map entry!
    assertEquals(1, results.size());
    // 10,000 runs * 1ms each = 10,000 total ms spent in FastPoller
    assertEquals(Long.valueOf(10000), results.get("FastPoller"));
  }

}

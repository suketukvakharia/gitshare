package anthropic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringJoiner;
import java.util.TreeSet;
import org.junit.Test;

public class ProfilerOuter {

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

  private class ProfiledResult {
    private final String finishedFunction;
    private final long elapsedMs;
    private final List<String> fullTrace; // includes the funciton that just finished

    public ProfiledResult(String finishedFunction, long elapsedMs, List<String> fullTrace) {
      this.finishedFunction = finishedFunction;
      this.elapsedMs = elapsedMs;
      this.fullTrace = fullTrace;
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      ProfiledResult that = (ProfiledResult) o;
      return elapsedMs == that.elapsedMs && Objects.equals(finishedFunction,
          that.finishedFunction) && Objects.equals(fullTrace, that.fullTrace);
    }

    @Override
    public int hashCode() {
      return Objects.hash(finishedFunction, elapsedMs, fullTrace);
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", ProfiledResult.class.getSimpleName() + "[", "]")
          .add("finishedFunction='" + finishedFunction + "'")
          .add("elapsedMs=" + elapsedMs)
          .add("fullTrace=" + fullTrace)
          .toString();
    }
  }
  private class Profiler {
    public SortedSet<ProfiledResult> getSlowest(List<ProfilerInput> profilerInputs) {

      // keep track of current trace using list of profilerInpue
      List<ProfilerInput> currentTrace = new ArrayList<>();

      // final result*
      SortedSet<ProfiledResult> resultSet = new TreeSet<>((s1, s2) -> Long.compare(s1.elapsedMs, s2.elapsedMs));

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
        List<String> fullTrace = currentTrace.stream().map(c -> c.functionName).toList();
        ProfilerInput finishingFunction = currentTrace.remove(currentTrace.size()-1);
        long elapsedMs = profilerInput.timestampMs - finishingFunction.timestampMs;
        String finishedFunction = finishingFunction.functionName;
        ProfiledResult profiledResult = new ProfiledResult(finishedFunction, elapsedMs, fullTrace);
        resultSet.add(profiledResult);
      }

      // print last & first
      System.err.println(resultSet.last());

      System.err.println(resultSet.first());

      return resultSet;
    }
  }

  @Test
  public void testNestedExecution() {
    ProfilerOuter outer = new ProfilerOuter();
    ProfilerOuter.Profiler profiler = outer.new Profiler();

    // A starts at 0, B starts at 2, B ends at 5, A ends at 6
    // Expected: B took 3ms, A took 6ms
    List<ProfilerOuter.ProfilerInput> inputs = List.of(
        outer.new ProfilerInput("start", "A", 0),
        outer.new ProfilerInput("start", "B", 2),
        outer.new ProfilerInput("end", "B", 5),
        outer.new ProfilerInput("end", "A", 6)
    );

    SortedSet<ProfilerOuter.ProfiledResult> results = profiler.getSlowest(inputs);

    // Validate sizes and expected order (fastest to slowest)
    assertFalse(results.isEmpty());
    assertEquals(2, results.size());

    // First element should be B (3ms)
    assertEquals("B", results.first().finishedFunction);
    assertEquals(3L, results.first().elapsedMs);

    // Last element should be A (6ms)
    assertEquals("A", results.last().finishedFunction);
    assertEquals(6L, results.last().elapsedMs);
  }

  @Test
  public void testSequentialExecution() {
    ProfilerOuter outer = new ProfilerOuter();
    ProfilerOuter.Profiler profiler = outer.new Profiler();

    // A runs entirely, then B runs entirely
    List<ProfilerOuter.ProfilerInput> inputs = List.of(
        outer.new ProfilerInput("start", "A", 0),
        outer.new ProfilerInput("end", "A", 10),
        outer.new ProfilerInput("start", "B", 15),
        outer.new ProfilerInput("end", "B", 20)
    );

    SortedSet<ProfilerOuter.ProfiledResult> results = profiler.getSlowest(inputs);

    assertEquals(2, results.size());
    assertEquals("B", results.first().finishedFunction); // B took 5
    assertEquals("A", results.last().finishedFunction);  // A took 10
  }

  @Test(expected = RuntimeException.class)
  public void testInvalidEvents_EmptyStackOnEnd() {
    ProfilerOuter outer = new ProfilerOuter();
    ProfilerOuter.Profiler profiler = outer.new Profiler();

    // End event occurs before any start event
    List<ProfilerOuter.ProfilerInput> inputs = List.of(
        outer.new ProfilerInput("end", "A", 5)
    );

    profiler.getSlowest(inputs); // Should throw RuntimeException
  }

  @Test
  public void testUnsupportedTypesAreIgnored() {
    ProfilerOuter outer = new ProfilerOuter();
    ProfilerOuter.Profiler profiler = outer.new Profiler();

    // Contains unsupported "pause" type
    List<ProfilerOuter.ProfilerInput> inputs = List.of(
        outer.new ProfilerInput("start", "A", 0),
        outer.new ProfilerInput("pause", "A", 2),
        outer.new ProfilerInput("end", "A", 5)
    );

    SortedSet<ProfilerOuter.ProfiledResult> results = profiler.getSlowest(inputs);

    assertEquals(1, results.size());
    assertEquals(5L, results.first().elapsedMs); // Pause should be completely ignored
  }

  @Test
  public void testGotcha_CyclesAndRecursion() {
    ProfilerOuter outer = new ProfilerOuter();
    ProfilerOuter.Profiler profiler = outer.new Profiler();

    // A calls B, B calls A (Recursion)
    List<ProfilerOuter.ProfilerInput> inputs = List.of(
        outer.new ProfilerInput("start", "A", 0),
        outer.new ProfilerInput("start", "B", 2),
        outer.new ProfilerInput("start", "A", 5), // Recursive call
        outer.new ProfilerInput("end", "A", 10),  // Inner A takes 5ms
        outer.new ProfilerInput("end", "B", 15),  // B takes 13ms
        outer.new ProfilerInput("end", "A", 20)   // Outer A takes 20ms
    );

    SortedSet<ProfilerOuter.ProfiledResult> results = profiler.getSlowest(inputs);

    // Validate that we got 3 distinct execution results
    assertEquals(3, results.size());

    // The fastest should be the inner 'A'
    assertEquals("A", results.first().finishedFunction);
    assertEquals(5L, results.first().elapsedMs);
    // Notice the trace correctly captures the cycle: [A, B, A]
    assertEquals(List.of("A", "B", "A"), results.first().fullTrace);

    // The slowest should be the outer 'A'
    assertEquals("A", results.last().finishedFunction);
    assertEquals(20L, results.last().elapsedMs);
    assertEquals(List.of("A"), results.last().fullTrace);
  }


}

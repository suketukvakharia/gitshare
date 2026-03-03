package anthropic;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Profiler {


    /**
     * https://prachub.com/coding-questions/convert-stack-samples-to-trace-events
     *
     * Input: samples = [(1, ['main']), (2, ['main','A']), (3, ['main','A','B']), (4, ['main','A']), (5, ['main','C']), (6, ['main','C']), (7, ['main'])], stability = 1
     *
     * Output: [{'time': 1, 'type': 'start', 'func': 'main'}, {'time': 2, 'type': 'start', 'func': 'A'},
     * {'time': 3, 'type': 'start', 'func': 'B'}, {'time': 4, 'type': 'end', 'func': 'B'},
     * {'time': 5, 'type': 'end', 'func': 'A'}, {'time': 5, 'type': 'start', 'func': 'C'},
     * {'time': 7, 'type': 'end', 'func': 'C'}]
     *
     * Input: samples = [(1, ['main']), (2, ['main','f']), (3, ['main','f','f']), (4, ['main','f','f']), (5, ['main','f']), (6, ['main'])], stability = 2
     *
     * Output: [{'time': 2, 'type': 'start', 'func': 'main'}, {'time': 3, 'type': 'start', 'func': 'f'},
     * {'time': 4, 'type': 'start', 'func': 'f'}, {'time': 5, 'type': 'end', 'func': 'f'},
     * {'time': 6, 'type': 'end', 'func': 'f'}]
     */

    private class InputSample {
        int time;
        List<String> stack;


        public InputSample(int time, List<String> stack) {
            this.time = time;
            this.stack = stack;
        }
    }

    private class InProgressSample {
        int timeObserved;
        int depth;
        String function;

        public InProgressSample(int timeObserved, int depth, String function) {
            this.timeObserved = timeObserved;
            this.depth = depth;
            this.function = function;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            InProgressSample that = (InProgressSample) o;
            return timeObserved == that.timeObserved && depth == that.depth && Objects.equals(function, that.function);
        }

        @Override
        public int hashCode() {
            return Objects.hash(timeObserved, depth, function);
        }
    }

    private class OutputSample {
        int time;
        String type;
        String funciton;

        @Override
        public String toString() {
            return "OutputSample{" +
                    "time=" + time +
                    ", type='" + type + '\'' +
                    ", funciton='" + funciton + '\'' +
                    '}' + '\n';
        }
    }

    public List<OutputSample> getElements(List<InputSample> inputSamples) {
        // requirements ...

        // base case
        if (inputSamples == null || inputSamples.size() == 0) {
            return List.of();
        }

        // Thought process

        // For each iteration
            // Find out if any stack elements ended based on current stack. If so add them in order to Output with event ending..
            // NOTE: Compare from enclosing function and then go in

            // Find out if any stack elements started based on current stack. If so add them in order to Output with event start
            // NOTE: Compare from enclosing function and then go in

        // At the end add the remaining elements are ignored I guess, print them for debugging if needed.


        // To keep track of current stack elements
        List<String> currentStackElements = new ArrayList<>();
        List<OutputSample> outputSamples = new ArrayList<>();

        for (InputSample inputSample: inputSamples) {
            int currentTime = inputSample.time;

            // find any stack elements that have ended
            int baseChangedAtIndex = Math.min(inputSample.stack.size(), currentStackElements.size());
            for (int i = 0; i <currentStackElements.size() && i < inputSample.stack.size(); i++) {
                String currentStackElement = currentStackElements.get(i);
                String inputSampleElement = inputSample.stack.get(i);
                if (!currentStackElement.equals(inputSampleElement)) {
                    baseChangedAtIndex = i;
                    break;
                }
            }

            // add all elements as ended
            for (int i = currentStackElements.size()-1; i >= baseChangedAtIndex ; i--) {
                OutputSample outputSample = new OutputSample();
                outputSample.time = currentTime;
                outputSample.type = "end";
                outputSample.funciton = currentStackElements.removeLast();
                outputSamples.add(outputSample);
            }



            // add all elements that started and add them to output samples as well
            for (int i = baseChangedAtIndex; i < inputSample.stack.size(); i++) {
                OutputSample outputSample = new OutputSample();
                outputSample.time = currentTime;
                outputSample.type = "start";
                outputSample.funciton =  inputSample.stack.get(i);
                outputSamples.add(outputSample);
                currentStackElements.add(inputSample.stack.get(i));
            }
        }
        return outputSamples;
    }


    public List<OutputSample> getElementsStable(List<InputSample> inputSamples, int stability) {
        // requirements ...

        // base case
        if (inputSamples == null || inputSamples.size() == 0) {
            return List.of();
        }

        // base case
        if (stability <= 1) {
            return getElements(inputSamples);
        }

        // Thought process

        // For each iteration
        // Find out if any stack elements ended based on current stack. If so add them in order to Output with event ending..
        // NOTE: Compare from enclosing function and then go in

        // Find out if any stack elements started based on current stack. If so add them in order to Output with event start
        // NOTE: Compare from enclosing function and then go in

        // At the end add the remaining elements are ignored I guess, print them for debugging if needed.

        // Thought process
        // For each iteration



        // To keep track of current stack elements
        List<InProgressSample> currentStackElements = new ArrayList<>();
        List<OutputSample> outputSamples = new ArrayList<>();

        for (InputSample inputSample: inputSamples) {
            int currentTime = inputSample.time;

            // find any stack elements that have ended
            int baseChangedAtIndex = Math.min(inputSample.stack.size(), currentStackElements.size());
            for (int i = 0; i <currentStackElements.size() && i < inputSample.stack.size(); i++) {
                String currentStackElement = currentStackElements.get(i).function;
                String inputSampleElement = inputSample.stack.get(i);
                if (!currentStackElement.equals(inputSampleElement)) {
                    baseChangedAtIndex = i;
                    break;
                }
            }

            // add all elements as ended
            for (int i = currentStackElements.size()-1; i >= baseChangedAtIndex ; i--) {
                InProgressSample inProgressSample = currentStackElements.removeLast();

                if (inProgressSample.timeObserved + stability > currentTime) {
                    continue;
                }

                OutputSample outputSample = new OutputSample();
                outputSample.time = currentTime;
                outputSample.type = "end";
                outputSample.funciton = inProgressSample.function;
                outputSamples.add(outputSample);
            }

            // start event for started elements with stability
            for (InProgressSample inProgressSample: currentStackElements) {
                if (inProgressSample.timeObserved + stability -1 == currentTime) {
                    OutputSample outputSample = new OutputSample();
                    outputSample.time = currentTime;
                    outputSample.type = "start";
                    outputSample.funciton =  inProgressSample.function;
                    outputSamples.add(outputSample);
                }
            }



            // add all elements that started and add them to output samples as well
            for (int i = baseChangedAtIndex; i < inputSample.stack.size(); i++) {
                InProgressSample inProgressSample = new InProgressSample(currentTime, i, inputSample.stack.get(i));
                currentStackElements.add(inProgressSample);
            }
        }
        return outputSamples;
    }


    @Test
    public void test1() {
        List<InputSample> inputSamples = List.of(
                new InputSample(1, List.of("main"))
        );
        List<OutputSample> outputSamples = getElements(inputSamples);
        System.err.println(outputSamples);
    }

    @Test
    public void test2() {
        List<InputSample> inputSamples = List.of(
                new InputSample(1, List.of("main")),
                new InputSample(1, List.of("main", "A"))
        );
        List<OutputSample> outputSamples = getElements(inputSamples);
        System.err.println(outputSamples);
    }

    @Test
    public void test3() {
        List<InputSample> inputSamples = List.of(
                new InputSample(1, List.of("main")),
                new InputSample(2, List.of("main", "A")),
                new InputSample(3, List.of("main", "A", "B")),
                new InputSample(4, List.of("main", "A")),
                new InputSample(5, List.of("main", "C")),
                new InputSample(6, List.of("main", "C")),
                new InputSample(7, List.of("main"))

        );
        List<OutputSample> outputSamples = getElements(inputSamples);
        System.err.println(outputSamples);
    }

    @Test
    public void test4() {
        List<InputSample> inputSamples = List.of(
                new InputSample(1, List.of("main")),
                new InputSample(2, List.of("main", "f")),
                new InputSample(3, List.of("main", "f", "f")),
                new InputSample(4, List.of("main", "f", "f")),
                new InputSample(5, List.of("main", "f")),
                new InputSample(6, List.of("main"))

        );
        List<OutputSample> outputSamples = getElements(inputSamples);
        System.err.println(outputSamples);
    }

    @Test
    public void test5() {
        List<InputSample> inputSamples = List.of(
                new InputSample(1, List.of("main")),
                new InputSample(2, List.of("main", "f")),
                new InputSample(3, List.of("main", "f", "f")),
                new InputSample(4, List.of("main", "f", "f")),
                new InputSample(5, List.of("main", "f")),
                new InputSample(6, List.of("main"))

        );
        List<OutputSample> outputSamples = getElementsStable(inputSamples,2);
        System.err.println(outputSamples);
    }
}

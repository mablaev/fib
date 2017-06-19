package com.mycompany;

import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class Main {

    public static void main(String[] args) {


        try {
            Args programArgs = Args.of(args);

            if (Args.NON_VALID_ARGS.equals(programArgs)) {
                usage();
            } else {

                System.out.println("Program is started...");

                if (programArgs.threadCount > Runtime.getRuntime().availableProcessors()) {
                    System.out.println("WARN: Number of available processors is less than thread count.");
                }

                PerformanceTestResult result = doActionWith(programArgs);
                printResult(programArgs, result);
            }

        } catch (InterruptedException e) {
            System.err.println("[WARN] Program was unexpectedly interrupted " + e);
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Done.");
        }
    }


    private static PerformanceTestResult doActionWith(Args programArgs) throws InterruptedException {

        Runnable action = () -> new FibCalcImpl().fibBigInt(programArgs.fibNumber);
//        Runnable action = () -> new FibCalcImpl().fib(programArgs.fibNumber);

        PerformanceTester tester = new PerformanceTesterImpl();
        PerformanceTestResult result = tester.runPerformanceTest(action, programArgs.execCount, programArgs.threadCount);

        return result;
    }

    private static void printResult(Args programArgs, PerformanceTestResult result) {
        if (PerformanceTestResult.ERROR_RESULT.equals(result)) {
            System.err.println("Program completed with errors.");
        } else {
            System.out.println(String.format("Calculation of Fib(%s) function, %d times using %d threads, stats:" +
                            " totalTime=%s(ms), minTime=%s(ms), maxTime=%s(ms)",
                    programArgs.fibNumber, programArgs.execCount, programArgs.threadCount,
                    result.getTotalTime(), result.getMinTime(), result.getMaxTime()));
        }
    }

    private static void usage() {
        System.out.println("java -jar <jar-to-execute>.jar Fib ExecCount NumThreads");
        System.out.println("Fib        - fibonacci number to calculate.");
        System.out.println("ExecCount  - Number of times to execute.");
        System.out.println("NumThreads - How many threads to use.");
    }

    private static class Args {
        public static final Args NON_VALID_ARGS = new Args(-1, -1, -1);

        private final int fibNumber;
        private final int execCount;
        private final int threadCount;

        private Args(int fibNumber, int execCount, int threadCount) {
            this.fibNumber = fibNumber;
            this.execCount = execCount;
            this.threadCount = threadCount;
        }

        public static Args of(String[] args) {

            if (args.length < 3) {
                return NON_VALID_ARGS;
            }

            boolean allValid = Stream.of(args).allMatch(Args::valid);

            if (allValid) {
                return new Args(parseInt(args[0]), parseInt(args[1]), parseInt(args[2]));
            } else {
                return NON_VALID_ARGS;
            }
        }

        private static boolean valid(String arg) {
            try {
                parseInt(arg);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}

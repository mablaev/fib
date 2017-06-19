package com.mycompany;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PerformanceTesterImpl implements PerformanceTester {


    @Override
    public PerformanceTestResult runPerformanceTest(Runnable task, int executionCount, int threadPoolSize)
            throws InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(threadPoolSize, new NamedThreadFactory());
        CompletionService<Void> completionService = new ExecutorCompletionService<>(es);

        List<MeasurableTask> allTasks = new ArrayList<>();

        try {

            for (int execNumber = 0; execNumber < executionCount; execNumber++) {

                for (int i = 0; i < threadPoolSize; i++) {
                    MeasurableTask measurableTask = MeasurableTask.of(task);
                    allTasks.add(measurableTask);
                    completionService.submit(measurableTask, null);
                }

                if (!canWaitTillCompleted(completionService, threadPoolSize)) {
                    return PerformanceTestResult.ERROR_RESULT;
                }
            }
            return gatherStats(allTasks);
        } finally {
            shutdownExecService(es);
        }
    }

    private void shutdownExecService(ExecutorService es) throws InterruptedException {
        es.shutdownNow();
        if (!es.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("Still waiting...");
            System.exit(0);
        }
    }

    private boolean canWaitTillCompleted(CompletionService<Void> completionService, int threadCount) throws InterruptedException {
        while (threadCount > 0) {
            if (!canGetNextDone(completionService)) {
                return false;
            }
            threadCount--;
        }
        return true;
    }

    private boolean canGetNextDone(CompletionService<Void> completionService) throws InterruptedException {
        try {
            completionService.take().get();
            return true;
        } catch (CancellationException ce) {
            System.out.println("Execution was cancelled: " + ce);
        } catch (ExecutionException e) {
            System.out.println("Execution has failed: " + e);
        }
        return false;
    }

    private static PerformanceTestResult gatherStats(List<MeasurableTask> allTasks) {
        long minTime = allTasks.stream()
                .mapToLong(MeasurableTask::getElapsedTime)
                .min().getAsLong();

        long maxTime = allTasks.stream()
                .mapToLong(MeasurableTask::getElapsedTime)
                .max().getAsLong();

        long totalTime = allTasks.stream()
                .mapToLong(MeasurableTask::getElapsedTime)
                .sum();

        return new PerformanceTestResult(totalTime, minTime, maxTime);
    }
}

package certif.concurrent;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Sand {
    public static void main(String[] args) {
        locks();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        Runnable runnableTask = () -> {
            try {
                System.out.println("co");
                TimeUnit.MILLISECONDS.sleep(3000);
                System.out.println("ucou");
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        };

        Callable<String> callableTask = () -> {
            System.out.println("he");
            TimeUnit.MILLISECONDS.sleep(3000);
            System.out.println("llo");
            return "Rand value: " + Math.random() * 101;
        };

        List<Callable<String>> callableTasks = List.of(callableTask, callableTask);
        List<Callable<String>> paramTasks = List.of(new ParamTask(1), new ParamTask(2));

        Future<?> submittedRunnable = executor.submit(runnableTask);
        // submittedRunnable.get() returns null
        // cancel without interrupting if running (sleeping is not running)
        System.out.println("cancelled " + submittedRunnable.cancel(false));
        Future<String> submittedCallable = executor.submit(callableTask);
        try {
            System.out.println(submittedCallable.get(5, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // CancellationException is runtime
            throw new RuntimeException(e);
        }

        try {
            String s = executor.invokeAny(callableTasks);
            System.out.println(s);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        try {
            List<Future<String>> futures = executor.invokeAll(paramTasks);
            System.out.println(futures.stream().map(f -> {
                try {
                    return f.get(1, TimeUnit.SECONDS);
                } catch (TimeoutException | InterruptedException | ExecutionException e) {
                    return "Error";
                }
            }).collect(Collectors.joining(",")));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int period = 500;
        int delay = 500;
        scheduledExecutor.scheduleAtFixedRate(runnableTask, 100, period, TimeUnit.MILLISECONDS);
        scheduledExecutor.scheduleWithFixedDelay(runnableTask, 100, delay, TimeUnit.MILLISECONDS);
        scheduledExecutor.shutdownNow();

        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        List<Integer> numbersToAdd = List.of(1, 2, 3, 4);
        ForkJoinPool forkJoin = new ForkJoinPool(4);
        try {
            int sum = forkJoin.submit(() -> numbersToAdd.parallelStream().reduce(0, Integer::sum)).get();
            System.out.println(sum);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        forkJoin.shutdown();
    }

    static void locks() {
        Lock lock = new ReentrantLock();
        System.out.println("locking...");
        lock.lock();// wait to acquire a lock
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock.unlock();
        System.out.println("unlocked");

        if (lock.tryLock()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {} finally {
                lock.unlock();
            }
        }
    }

    static class ParamTask implements Callable<String> {
        int number;

        ParamTask(int number) {
            this.number = number;
        }

        public String call() throws InterruptedException {
            System.out.println("he");
            TimeUnit.MILLISECONDS.sleep(3000);
            System.out.println("llo");
            return number + " Rand value: " + Math.random() * 101;
        }
    }
}

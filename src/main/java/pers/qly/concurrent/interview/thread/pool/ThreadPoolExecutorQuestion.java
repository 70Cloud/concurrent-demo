package pers.qly.concurrent.interview.thread.pool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * @Author: NoNo
 * @Description: 如何获取 ThreadPoolExecutor 正在运行的线程
 * @Date: Create in 12:53 2019/4/13
 */
public class ThreadPoolExecutorQuestion {

    // Q：如何获取 ThreadPoolExecutor 正在运行的线程？
    // A：虽然 ThreadPoolExecutor 有 beforeExecute()、afterExecute() 可以实现线程池监控，参考 pers.qly.concurrent.pool.MyExecutors
    //    但是有些框架并不是直接实现了 ThreadPoolExecutor，而是实现了 AbstractExecutorService，比如说 Netty
    //    最根本的要实现好 ThreadFactory，然后就可以通过 ThreadFactory 和 ThreadGroup 做到这一点

    public static void main(String[] args) throws InterruptedException {

        // main 线程启动子线程，子线程的创建来自于 Executors.defaultThreadFactory()

        ExecutorService executorService = Executors.newCachedThreadPool();
        // 之前了 ThreadPoolExecutor beforeExecute()、afterExecute() 能够获取当前线程数量等

        Set<Thread> threadsContainer = new HashSet<>();

        setThreadFactory(executorService, threadsContainer);

        for (int i = 0; i < 9; i++) {// 开启 9 个线程
            executorService.submit(() -> {

            });
        }
        // 线程池等待执行 3ms
        executorService.awaitTermination(3, TimeUnit.MILLISECONDS);

        // 去重
        threadsContainer.stream()
                .filter(Thread::isAlive)
                .forEach(thread -> System.out.println("线程池所创造的线程 : " + thread));

        Thread mainThread = Thread.currentThread();

        ThreadGroup mainThreadGroup = mainThread.getThreadGroup();

        int count = mainThreadGroup.activeCount();
        Thread[] threads = new Thread[count];
        mainThreadGroup.enumerate(threads, true);

        Stream.of(threads)
                .filter(Thread::isAlive)
                .forEach(thread -> System.out.println("线程池所创造的线程 : " + thread));

        // 关闭线程池
        executorService.shutdown();
    }

    private static void setThreadFactory(ExecutorService executorService, Set<Thread> threadsContainer) {

        if (executorService instanceof ThreadPoolExecutor) {

            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;

            ThreadFactory oldThreadFactory = threadPoolExecutor.getThreadFactory();

            threadPoolExecutor.setThreadFactory(new DelegatingThreadFactory(oldThreadFactory, threadsContainer));
        }
    }

    private static class DelegatingThreadFactory implements ThreadFactory {

        private final ThreadFactory delegate;

        private final Set<Thread> threadsContainer;

        private DelegatingThreadFactory(ThreadFactory delegate, Set<Thread> threadsContainer) {
            this.delegate = delegate;
            this.threadsContainer = threadsContainer;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = delegate.newThread(r);
            // cache thread
            threadsContainer.add(thread);
            return thread;
        }
    }
}

package pers.qly.concurrent.interview.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 12:22 2019/4/13
 */
public class ExecutorServiceQuestion {

    // Q：请问 J.U.C 中内建了几种 ExecutorService 实现？
    // A：ThreadPoolExecutor(1.5)、ScheduledExecutorService 的 ScheduledThreadPoolExecutor(1.5) 实现、ForkJoinPool(1.7)

    // Q：请分别解释 ThreadPoolExecutor 构造器参数在运行时的作用？
    // A：newFixedThreadPool、newSingleThreadExecutor 中核心线程数和最大线程数一致
    //     corePoolSize 核心线程数 、maximumPoolSize 最大线程数、keepAliveTime 非核心线程的空余线程的存活时间、
    //     unit 存活时间单位、workQueue 当线程超过核心线程数时会把线程加到阻塞队列、 threadFactory 构建的线程工厂、
    //     handler 拒绝策略

    // Q：几种 RejectedExecutionHandler 拒绝策略有什么区别？
    // A：不一定非要用它的拒绝策略
    //    AbortPolicy 中止策略：放弃当前执行的任务，直接抛异常，提醒
    //    CallerRunsPolicy 调用 shutdown，异步变为同步执行
    //    DiscardOldestPolicy 抛弃并调用策略：在队列中找出最老的任务执行(poll)，放弃一个再加一个
    //    DiscardPolicy 抛弃策略：直接丢弃，也不抛异常，什么都不加

    public static void main(String[] args) {
        /**
         * 1.5
         *   ThreadPoolExecutor
         *   ScheduledThreadPoolExecutor :: ThreadPoolExecutor
         * 1.7
         *   ForkJoinPool
         *   在 1.8 的时候有个 java.util.concurrent.ForkJoinPool#commonPool()，跟着处理器的线程去执行
         *   在 1.8 的 CompletableFuture#screenExecutor() 就用了 ForkJoinPool#commonPool()
         */
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService = Executors.newScheduledThreadPool(2);

        // 当 executorService1 不再被引用，它会被 GC -> finalize() -> shutdown()
        // 所以需要关闭
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();

        executorService.shutdown();
        executorService1.shutdown();
    }
}

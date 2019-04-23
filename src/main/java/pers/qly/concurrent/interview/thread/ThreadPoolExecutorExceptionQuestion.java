package pers.qly.concurrent.interview.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 13:22 2019/4/12
 */
public class ThreadPoolExecutorExceptionQuestion {

    // Q：当线程遇到异常时，ThreadPoolExecutor 如何捕获异常？
    // A：通过覆盖 ThreadPoolExecutor#afterExecute(Runnable, Throwable) 达到获取异常的信息

    public static void main(String[] args) throws InterruptedException {

//        ExecutorService executorService = Executors.newFixedThreadPool(2);

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                1,
                1,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()
        ) {

            /**
             * 通过覆盖 {@link ThreadPoolExecutor#afterExecute(Runnable, Throwable)} 达到获取异常的信息
             * @param r
             * @param t
             */
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.printf("线程[%s] 遇到了异常，详细信息：%s\n",
                        Thread.currentThread().getName(),
                        t.getMessage());
            }

        };

        executorService.execute(() -> {
            throw new RuntimeException("数据达到阈值");
        });

        // 等待一秒钟，确保提交的任务完成
        executorService.awaitTermination(1, TimeUnit.SECONDS);

        // 关闭线程池
        executorService.shutdown();

    }
}

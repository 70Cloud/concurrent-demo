package pers.qly.concurrent.interview.barrier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 11:31 2019/4/13
 */
public class CountDownLatchQuestion {

    // Q：请说明 CountDownLatch 与 CyclicBarrier 的区别？
    // A：CountDownLatch 通过计数器的方式不断减 1，为 0 时调用 await()
    //    CyclicBarrier 可循环

    // Q：请说明 Semaphore 的使用场景？
    // A：Semaphore 的语义与 Lock 有点类似
    //    Semaphore#acquire()、Semaphore#realease()

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(5);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 4; i++) {
            executorService.submit(() -> {
                action();
                latch.countDown(); // -1
            });
        }
        // 等待完成
        // 当计数 > 0，会被阻塞
        latch.await();

        System.out.println("Done...");

        executorService.shutdown();
    }

    private static void action() {
        System.out.printf("线程[%s] 正在执行...\n", Thread.currentThread().getName());
    }
}

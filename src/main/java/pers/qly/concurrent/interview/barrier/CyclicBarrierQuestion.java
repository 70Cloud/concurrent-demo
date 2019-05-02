package pers.qly.concurrent.interview.barrier;

import java.util.concurrent.*;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 11:38 2019/4/13
 */
public class CyclicBarrierQuestion {

    public static void main(String[] args) throws InterruptedException {

        CyclicBarrier barrier = new CyclicBarrier(5);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                action();
                try {
                    // 当计数 > 0 时，才阻塞
                    // before: 5 - 3 = 2 > 0 就会阻塞
                    // after: 5 - 5 = 0 = 0 就不会阻塞
                    // CyclicBarrier.await() = CountDownLatch.countDown() + await()
                    // 先计数 -1，再判断当计数 > 0 时，才阻塞
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        // 尽可能不要执行完成再 reset，此处已经在 3ms 内执行完成了，所以 reset 没用了
        // 先等待 3s，再执行 CyclicBarrier#reset()，会报 BrokenBarrierException
        // 如果是 3ms，就不报错，还是阻塞，所以说尽量不要用 CyclicBarrier
        // 如果要用的话，尽量保证线程数和循环次数一致
        executorService.awaitTermination(3,TimeUnit.MILLISECONDS);

        // 当计数 > 0 时，reset() 之后还是阻塞
        // reset 此处是非操作
        barrier.reset();

        System.out.println("Done...");

        executorService.shutdown();
    }

    private static void action() {
        System.out.printf("线程[%s] 正在执行...\n", Thread.currentThread().getName());
    }
}

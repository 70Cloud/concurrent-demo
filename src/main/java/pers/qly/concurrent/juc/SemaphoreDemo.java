package pers.qly.concurrent.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 13:42 2019/2/27
 */
public class SemaphoreDemo {

    // Semaphore 信号灯 基于 AQS 来实现
    // Semaphore 可以控制同时访问的线程个数，通过 acquire 获取一个许可，如果没有就等待，通过 release 释放一个许可
    // 有点类似限流的作用
    // 比如某商场就 5 个停车位，每个停车位只能停一辆车，如果这个时候来了 10 辆车，必须要等前面有空的车位才能进入
    // 使用场景：可以实现对某些接口访问的限流

    // Q：Semaphore 与 CountDownLatch 的实现方式区别
    // A：Semaphore 的实现方式和 CountDownLatch 的差异点
    //    在于 acquireSharedInterruptibly 中的 tryAcquireShared 方法的实现，这个方法是在 Semaphore 方法中重写的
    //    在 Semaphore 中存在公平和非公平的方式，和重入锁是一样的
    //    公平和非公平取决于是否按照 FIFO 队列中的顺序去分配 Semaphore 所维护的许可

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            new Car(i, semaphore).start();
        }
    }

    static class Car extends Thread {
        private int num;
        private Semaphore semaphore;

        public Car(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire(); // 获取一个 permit(许可)
                System.out.println("第 " + num + " 辆车占用一个停车位");
                TimeUnit.SECONDS.sleep(2);
                semaphore.release();
                System.out.println("第 " + num + " 辆车走喽！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

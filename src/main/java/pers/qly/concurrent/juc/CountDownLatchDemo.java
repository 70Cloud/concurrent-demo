package pers.qly.concurrent.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 13:28 2019/2/27
 */
public class CountDownLatchDemo {

    // CountDownLatch 实际上是是使得线程阻塞了，既然涉及到阻塞，就一定涉及到 AQS 队列

    // CountDownLatch 使用场景
    // 1、通过 CountDownLatch 实现最大的并行请求，也就是可以让 N 个线程同时执行
    // 2、应用程序启动之前，需要确保相应的服务已经启动，比如 zookeeper 中的 Watcher 机制，
    //      通过原生 API 连接的地方有用到 CountDownLatch

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(3);

        new Thread(() -> countDownLatch.countDown(), "t1").start();
        new Thread(() -> countDownLatch.countDown(), "t2").start();
        new Thread(() -> countDownLatch.countDown(), "t3").start();

        countDownLatch.await(); // 使得当前线程在 CountDownLatch 倒计时到 0 之前一直等待，除非线程被中断

        System.out.println("所有线程执行完毕！");

    }
}

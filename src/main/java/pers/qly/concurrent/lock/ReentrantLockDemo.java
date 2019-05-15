package pers.qly.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:57 2019/2/26
 */
public class ReentrantLockDemo {
    // ReentrantLock 重进入锁 实现原子性
    // 如果当前线程 t1 通过调用 lock 方法获取了锁之后，再次调用 lock，是不会再阻塞去获取锁的，直接增加重试次数就行了
    // 公平锁，非公平锁
    //  锁的公平性是相对于获取锁的顺序而言的，
    //  如果是一个公平锁，那么锁的获取顺序就应该符合请求的绝对时间顺序，也就是 FIFO
    //  非公平锁在获取锁的时候，会先通过 CAS 进行抢占，而公平锁则不会

    // Q: synchronized 与 Lock(ReentrantLock) 的区别
    // A：1、从层次上：synchronized 是 JVM 层面上的关键字；ReentrantLock 是类级别的实现，也叫 JDK 层面的实现
    //    2、Lock 比较灵活(lock()、unlock())，随时可以加锁，释放锁；
    //          synchronized 什么时候获得锁，释放锁(1、同步代码块执行完 2、出现异常时)是一个被动的过程
    //    3、Lock 可以判断锁的状态；synchronized 是个关键字，无法灵活的去判断锁的状态
    //    4、基于 Lock 中的 ReentrantLock 来说，存在公平锁和非公平锁；synchronized 是一个非公平锁

    private static int count = 0;
    static Lock lock = new ReentrantLock();

    public static void incr() {
        lock.lock(); // 获得锁  _CXQ -> EntryList -> CAS -> park -> unpark
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        count++;
        lock.unlock(); // 释放锁
    }

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> ReentrantLockDemo.incr()).start();
        }

        Thread.sleep(3000);

        System.out.println("result : " + count);
    }
}

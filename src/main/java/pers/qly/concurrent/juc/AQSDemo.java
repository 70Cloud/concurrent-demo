package pers.qly.concurrent.juc;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 16:32 2019/2/26
 */
public class AQSDemo {

    // AQS：AbstractQueuedSynchronizer 同步队列，FIFO 队列，可以看做是一个用来实现锁以及其他需要同步功能的框架。

    // 从使用上来说，AQS 的功能可以分为两种：独占锁和共享锁
    // 独占锁模式下，每次只能有一个线程持有锁，比如 ReentrantLock 就是以独占方式实现的互斥锁
    // 共享锁模式下，允许多个线程同时获取锁，并发访问共享资源，比如 ReentrantReadWriteLock。

    // 很显然，独占锁是一种悲观保守的加锁策略，它限制了读/读冲突，如果某个只读线程获取锁，则其他读线程都只
    // 能等待，这种情况下就限制了不必要的并发性，因为读操作并不会影响数据的一致性。共享锁则是一种乐观锁，它
    // 放宽了加锁策略，允许多个执行读操作的线程同时访问共享资源

}

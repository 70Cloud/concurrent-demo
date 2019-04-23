package pers.qly.concurrent.interview.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 9:33 2019/4/13
 */
public class ReentrantLockQuestion {

    // Q：请说明 ReentrantLock 与 ReentrantReadWriteLock 的区别？
    // A：ReentrantReadWriteLock 在重进入的基础上增加了读锁（共享锁，共享，强调可见性）和写锁（独占锁，互斥，串行，强调数据一致性）

    // Q：请解释 ReentrantLock 为什么命名为重进入？
    // A：采用了偏向锁的设计，第一次进入有一次 CAS 操作，并把 ThreadID 保存，之后如果还是同一个线程，就不做 CAS 操作
    //    acquire() : 当获取不到锁的时候，判断有没有入队，没有的话就入队，获取到锁的时候就过去了
    //    acquireQueued() : 如果当前线程已被其他线程调用了 interrupt() 方法时，这时会返回 true，
    //       acquireQueued() 执行完时，interrupted 会清空(false)
    //       再通过 selfInterrupt() 方法将状态恢复(interrupted = true)
    /**
     * public final void acquire(int arg) {
     *         if (!tryAcquire(arg) &&
     *             acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
     *             selfInterrupt();
     *     }
     *
     * final boolean acquireQueued(final Node node, int arg) {
     *         boolean interrupted = false;
     *         try {
     *             for (;;) {
     *                 final Node p = node.predecessor();
     *                 if (p == head && tryAcquire(arg)) {
     *                     setHead(node);
     *                     p.next = null; // help GC
     *                     return interrupted;
     *                 }
     *                 if (shouldParkAfterFailedAcquire(p, node))
     *                     interrupted |= parkAndCheckInterrupt();
     *             }
     *         } catch (Throwable t) {
     *             cancelAcquire(node);
     *             if (interrupted)
     *                 selfInterrupt();
     *             throw t;
     *         }
     *     }
     */

    // Q：请说明 Lock#lock() 与 Lock#lockInterruptibly() 的区别？
    // A：简单的理解就是 Lock#lockInterruptibly() 调用的时候，如果其他线程显式地中断当前线程，就直接抛出异常。
    //    看一下实现 ReentrantLock 中的 ReentrantLock#lock() 与 ReentrantLock#lockInterruptibly()
    //    ReentrantLock#lock() 实现参考上方
    //    ReentrantLock#lockInterruptibly() 首先会判断 Thread.interrupted(),如果线程已经被中断，那么会抛出异常
    //    接下来进到 doAcquireInterruptibly()，前面的部分与 acquireQueued() 差不多，主要区别在判断获取失败后的操作，
    //    parkAndCheckInterrupt():会清掉状态，true -> false
    //    如果已经被其他线程中断(interrupted = true)的话，会抛异常

    /**
     * public final void acquireInterruptibly(int arg)
     *             throws InterruptedException {
     *         if (Thread.interrupted())
     *             throw new InterruptedException();
     *         if (!tryAcquire(arg))
     *             doAcquireInterruptibly(arg);
     *     }
     *
     * private void doAcquireInterruptibly(int arg)
     *         throws InterruptedException {
     *         final Node node = addWaiter(Node.EXCLUSIVE);
     *         try {
     *             for (;;) {
     *                 final Node p = node.predecessor();
     *                 if (p == head && tryAcquire(arg)) {
     *                     setHead(node);
     *                     p.next = null; // help GC
     *                     return;
     *                 }
     *                 if (shouldParkAfterFailedAcquire(p, node) &&
     *                     parkAndCheckInterrupt())
     *                     throw new InterruptedException();
     *             }
     *         } catch (Throwable t) {
     *             cancelAcquire(node);
     *             throw t;
     *         }
     *     }
     */

    /**
     * T1,T2,T3
     *
     * T1(lock) , T2(park) , T3(park)
     * 会有一个等待队列 Waited Queue : Head -> T2 next -> T3
     * T1(unlock) -> unpark all
     * Waited Queue : Head -> T2 next -> T3
     * T2(free) T3(free)
     * -> T2(lock) , T3(park)
     * Waited Queue : Head -> T3
     * T2(unlock) -> unpark all
     */

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        // lock      lock         lock
        // main() -> action1() -> action2() -> action3()
        synchronizedAction(ReentrantLockQuestion::action1);

        lockVsLockInterruptibly();
    }

    private static void lockVsLockInterruptibly() {

        try {
            lock.lockInterruptibly();
            action1();
        } catch (InterruptedException e) {
            // 显式地恢复中断状态
            Thread.currentThread().interrupt();
            // 当前线程并未消亡，线程池可能还在存活
        }finally {
            lock.unlock();
        }
    }

    // action1() -> action2() -> action3()
    private static void action1(){
        synchronizedAction(ReentrantLockQuestion::action2);
    }

    private static void action2() {
        synchronizedAction(ReentrantLockQuestion::action3);
    }

    private static void action3() {
        System.out.println("Hello World!");
    }

    private static void synchronizedAction(Runnable runnable){
        lock.lock();
        try {
            runnable.run();
        }finally {
            lock.unlock();
        }
    }
}

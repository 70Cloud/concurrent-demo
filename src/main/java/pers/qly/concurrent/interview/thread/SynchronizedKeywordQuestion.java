package pers.qly.concurrent.interview.thread;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 13:38 2019/4/12
 */
public class SynchronizedKeywordQuestion {

    // Q：请说明 synchronized 关键字在修饰方法与代码块中的区别？
    // A：可以通过看字节码 javap -v
    //    修饰方法： ACC_SYNCHRONIZED
    //    修饰代码块：monitorenter monitorexit

    // Q：请说明 synchronized 关键字与 ReentrantLock 之间的区别？
    // A：1、从层次上：synchronized 是 JVM 层面上的关键字；ReentrantLock 是类级别的实现，也叫 JDK 层面的实现
    //    2、Lock 比较灵活(lock()、unlock())，随时可以加锁，释放锁；
    //       synchronized 什么时候获得锁，释放锁(1、同步代码块执行完 2、出现异常时)是一个被动的过程
    //    3、Lock 可以判断锁的状态；synchronized 是个关键字，无法灵活的去判断锁的状态
    //    4、基于 Lock 中的 ReentrantLock 来说，存在公平锁和非公平锁；synchronized 是一个非公平锁

    /**
     * ReentrantLock 逻辑：通过重进入，lock(+1),unlock(-1) 确保 state = 0
     * main{
     *     lock(+1)
     *     action()
     *          lock(+1)
     *          printf()
     *              lock(+1)
     *              unlock(-1)
     *          unlock(-1)
     *     unlock(-1)
     * }
     *
     * synchronized 逻辑：通过锁住代码块
     * main{
     *     synchronized(){
     *         synchronized(){
     *             synchronized(){
     *
     *             }
     *         }
     *     }
     * }
     */

    // Q：请解释偏向锁对 synchronized 关键字与 ReentrantLock 的价值？
    // A：这个题目其实是故意坑人的，偏向锁只针对 synchronized 有用，对 ReentrantLock 没用
    //    其实 ReentrantLock 已经实现了偏向锁（第一次进入做一次 CAS 操作，并且把 threadID 存进来，
    //      重进入的时候就判断一下 threadID 是不是相同，减少 CAS 操作），
    //      在 ReentrantLock.Sync.nonfairTryAcquire() 中的 setExclusiveOwnerThread() 方法其实就是偏向锁实现
    //    JVM 中有个命令 -XX:+UseBiasedLocking 使用偏向锁（在 JDK 5.6 版本更新，默认 false，Java 6,7 默认打开偏向锁）
    //    参考 http://www.oracle.com/splash/openjdk.java.net/maintenance
    //    synchronized 其实也是可重进入的，只不过是在 JVM 层面，ReentrantLock 是在代码层面


    public static void main(String[] args) {

    }

    private static void synchronizedBlock() {
        synchronized (SynchronizedKeywordQuestion.class) {

        }
    }

    private synchronized static void synchronizedMethod() {

    }
}

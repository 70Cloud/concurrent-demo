package pers.qly.concurrent.thread;

import java.util.concurrent.locks.Condition;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:19 2019/2/26
 */
public class WaitAndNotifyTest {

    // Q：wait 或者 Notify 为什么要获取锁
    // A：wait 和 notify 是用来让线程进入等待状态以及使得线程唤醒的两个操作
    // 调用 wait 方法，首先会获取监视器锁，获得成功以后，会让当前线程进入等待状态(Waiting)进入等待队列(WaitSet)并且释放锁(monitorexit)；然后
    //  当其他线程调用 notify 或者 notifyall 以后，会选择从等待队列中唤醒任意一个线程，而执行完 notify 方法以后，并不
    //  会立马唤醒线程，原因是当前的线程仍然持有这把锁，处于等待状态的线程无法获得锁。必须要等到当前的线程执
    //  行完 monitorexit 指令以后，也就是锁被释放以后，处于等待队列中的线程就可以开始竞争锁了
    //  wait 有2个功能
    //  1、释放当前的对象锁
    //  2、使当前线程进入阻塞
    //  notify 不释放锁

    // Q：wait 和 sleep 区别
    // A：wait 属于 Object 类，sleep 属于 Thread 类
    //  sleep() 方法导致了程序暂停执行指定的时间，让出 cpu 给其他线程，但是他的监控状态依然保持着，
    //  当指定的时间到了又会自动恢复运行状态。
    //  在调用 sleep() 方法的过程中，线程不会释放对象锁
    //  而当调用 wait() 方法的时候，线程会放弃对象锁，进入等待此对象的等待锁定池，
    //  只有针对此对象调用notify()方法后本线程才进入对象锁定池准备

    public static void main(String[] args) {
        Object lock = new Object(); // 锁是传进去的，好处就是可以控制锁的范围

        ThreadWaitDemo threadWaitDemo = new ThreadWaitDemo(lock);
        threadWaitDemo.start();
        ThreadNotifyDemo threadNotifyDemo = new ThreadNotifyDemo(lock);
        threadNotifyDemo.start();

    }
}

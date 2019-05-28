package pers.qly.concurrent.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 17:21 2019/2/26
 */
public class ConditionAwaitAndSignalDemo extends Thread {

    // Condition 可以理解为一个等待队列，用来存储处于等待状态的线程
    // AQS FIFO 同步双向队列 用来存储去获得锁的线程
    // 与 JVM 的实现 wait(waitSet) 和 notify(CXQ\EntryList) 类似

    private Lock lock;
    private Condition condition;

    public ConditionAwaitAndSignalDemo(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        
        try {
            lock.lock();
            System.out.printf("[%s] 开始执行 Thread Wait\n", Thread.currentThread().getName());
            condition.await();
            System.out.printf("[%s] 执行结束 Thread Wait\n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        ConditionAwait awaitDemo = new ConditionAwait(lock,condition);
        awaitDemo.start();
        ConditionSignal signalDemo = new ConditionSignal(lock,condition);
        signalDemo.start();
    }
}

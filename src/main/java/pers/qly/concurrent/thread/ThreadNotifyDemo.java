package pers.qly.concurrent.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:15 2019/2/26
 */
public class ThreadNotifyDemo extends Thread {

    private Object lock;

    public ThreadNotifyDemo(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            System.out.printf("[%s] 开始执行 Thread notify\n", Thread.currentThread().getName());

            lock.notify(); // 阻塞 LockSupport.unpark(node.thread)

            System.out.printf("[%s] 执行结束 Thread notify\n", Thread.currentThread().getName());
        }
    }
}

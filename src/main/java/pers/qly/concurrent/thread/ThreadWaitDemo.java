package pers.qly.concurrent.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:15 2019/2/26
 */
public class ThreadWaitDemo extends Thread {

    private Object lock;

    public ThreadWaitDemo(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.printf("[%s] 开始执行 Thread wait\n", Thread.currentThread().getName());
            try {
                lock.wait(); // 会释放锁  LockSupport.park(node.thread) 阻塞
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("[%s] 执行结束 Thread wait\n", Thread.currentThread().getName());
        }

    }
}

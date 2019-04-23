package pers.qly.concurrent.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:15 2019/2/26
 */
public class ConditionSignal extends Thread {

    private Lock lock;

    private Condition condition;

    public ConditionSignal(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        try {
            lock.lock();

            System.out.printf("[%s] 开始执行 Thread signal\n", Thread.currentThread().getName());

            condition.signal(); // LockSupport.unpark(node.thread)

            System.out.printf("[%s] 执行结束 Thread signal\n", Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }

    }
}

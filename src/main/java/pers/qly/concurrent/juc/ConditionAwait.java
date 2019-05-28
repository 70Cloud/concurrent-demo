package pers.qly.concurrent.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:15 2019/2/26
 */
public class ConditionAwait extends Thread {

    private Lock lock;

    private Condition condition;

    public ConditionAwait(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        
        try {
            lock.lock();
            System.out.printf("[%s] 开始执行 Thread await\n", Thread.currentThread().getName());
            condition.await(); // 会释放锁，进入 Condition 等待队列，LockSupport.park(node.thread) 阻塞
            System.out.printf("[%s] 执行结束 Thread await\n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

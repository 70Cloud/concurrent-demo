package pers.qly.concurrent.base;

import java.util.concurrent.TimeUnit;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 20:18 2019/2/25
 */
public class ThreadStateDemo {
    // 命令行 输入 jps 查看当前线程 id ,然后输入 jstack 线程id 查看线程状态
    public static void main(String[] args) {

        // NEW

        // RUNNABLE

        // BLOCKED Object#wait()
        // log4j 1.0 文件系统出这个问题比较多，影响性能

        // WAITING Object#wait(),#join(),LockSupport#park() ,
        //  可以通过 <tt>Object.notify()</tt> or <tt>Object.notifyAll()</tt> 唤醒

        // TIMED_WAITING #sleep,Object#wait(long),#join(long),LockSupport#parkNanos,LockSupport#parkUntil
        //  可以通过 Object.notify()  Object.notifyAll() LockSupport#unpark() 唤醒

        // TERMINATED

        new Thread(()->{
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Time-Waiting").start();

        new Thread(()->{
            while (true){
                synchronized (ThreadStateDemo.class){
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"Waiting").start();

        new Thread(new BlockDemo(),"BlockDemo-0").start();
        new Thread(new BlockDemo(),"BlockDemo-1").start();
    }

    static class BlockDemo extends Thread{
        @Override
        public void run() {
            synchronized (BlockDemo.class){
                while (true){
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

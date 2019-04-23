package pers.qly.concurrent.interview.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author: NoNo
 * @Description: 如何控制线程执行顺序
 * @Date: Create in 12:33 2019/4/12
 */
public class ThreadExecutionQuestion {

    // Q：当有线程 T 1 、T 2 以及 T 3 ， 如何实现 T 1 - > T 2 - > T 3 的执行顺序？
    // A：1.可以通过 join 来控制执行顺序
    //    2.自旋 Spin 控制执行顺序
    //    3.sleep 控制执行顺序

    public static void main(String[] args) throws InterruptedException {

//        threadJoinOneByOne();

//        threadLoop();

//        threadSleep();

        threadWait();
    }

    private static void threadWait() throws InterruptedException {

        Thread t1 = new Thread(ThreadExecutionQuestion::action, "t1");
        Thread t2 = new Thread(ThreadExecutionQuestion::action, "t2");
        Thread t3 = new Thread(ThreadExecutionQuestion::action, "t3");

        threadStartAndWait(t1);
        threadStartAndWait(t2);
        threadStartAndWait(t3);
    }

    private static void threadStartAndWait(Thread thread) {

        // 如果线程是刚创建就调用 start 方法
        if (Thread.State.NEW.equals(thread.getState())) {
            thread.start();
        }

        // Java Thread 对象和实际 JVM 执行的 OS Thread 不是相同的对象
        // JVM Thread 回调 Java Thread.run() 方法，
        // 同时 Thread 提供一些 native 方法来获取 JVM Thread 中的状态
        // 当 JVM Thread 执行后，自动就 notify() 了
        while (thread.isAlive()) { // Thread 是特殊的 Object
            // 当线程 Thread isAlive() == false 时，thread.wait() 操作会被自动释放
            synchronized (thread) {
                try {
                    thread.wait(); // 到底是谁通知 Thread -> thread.notify();
//                    LockSupport.park(); // t1 线程执行完，阻塞，死锁发生
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void threadSleep() throws InterruptedException {

        Thread t1 = new Thread(ThreadExecutionQuestion::action, "t1");
        Thread t2 = new Thread(ThreadExecutionQuestion::action, "t2");
        Thread t3 = new Thread(ThreadExecutionQuestion::action, "t3");

        t1.start();

        while (t1.isAlive()) {
            // sleep
            Thread.sleep(0);
        }

        t2.start();

        while (t2.isAlive()) {
            Thread.sleep(0);
        }

        t3.start();

        while (t3.isAlive()) {
            Thread.sleep(0);
        }

    }

    private static void threadLoop() {

        Thread t1 = new Thread(ThreadExecutionQuestion::action, "t1");
        Thread t2 = new Thread(ThreadExecutionQuestion::action, "t2");
        Thread t3 = new Thread(ThreadExecutionQuestion::action, "t3");

        t1.start();

        while (t1.isAlive()) {
            // 自旋 Spin
        }

        t2.start();

        while (t2.isAlive()) {

        }

        t3.start();

        while (t3.isAlive()) {

        }

    }

    private static void threadJoinOneByOne() throws InterruptedException {
        Thread t1 = new Thread(ThreadExecutionQuestion::action, "t1");
        Thread t2 = new Thread(ThreadExecutionQuestion::action, "t2");
        Thread t3 = new Thread(ThreadExecutionQuestion::action, "t3");

        // start() 仅是通知线程启动
        t1.start();
        // join() 控制线程必须执行完成
        t1.join();

        t2.start();
        t2.join();

        t3.start();
        t3.join();
    }

    private static void action() {
        System.out.printf("线程[%s] 正在执行...\n", Thread.currentThread().getName());  // 2
    }
}

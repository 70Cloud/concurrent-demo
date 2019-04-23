package pers.qly.concurrent.interview.thread;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 12:52 2019/4/12
 */
public class HowToStopThreadQuestion {

    // Q：如何停止一个线程？
    // A：所谓的真正的停止线程是不可能的，只有停止线程执行的逻辑
    //    1.可以通过加一个开关来进行判断
    //    2.可以通过 interrupt 来中断线程
    //    3.线程发生异常也可以销毁一个线程

    // Q：为什么 Java 要放弃 Thread 的 stop() 方法？
    // A：参考 https://docs.oracle.com/javase/7/docs/technotes/guides/concurrency/threadPrimitiveDeprecation.html
    //    因为存在潜在的不安全，stop 的时候可能会导致正被锁住的对象解锁，从而导致死锁等问题的出现

    // Q：请说明 Thread interrupt() 、isInterrupted() 以及 interrupted() 的区别以及意义？
    // A：Thread interrupt():会设置状态，从而调用 JVM 的 interrupt0 方法
    //    isInterrupted():会调用 JVM 的实现，当前仅当线程状态是中断时返回 true（仅判断不清除）
    //    interrupted():即判断又清除

    public static void main(String[] args) throws InterruptedException {

        Action action = new Action();

        // 子线程
        Thread t1 = new Thread(action, "t1");

        t1.start();

        // 改变 action stopped 状态
        action.setStopped(true);

        t1.join();

        Thread t2 = new Thread(() -> {
            // 如果不加个状态的判断，线程还是会执行
//            action();

            if (!Thread.currentThread().isInterrupted()) {
                action();
            }
        }, "t2");

        t2.start();
        // 中断操作(仅仅设置状态，而并非终止线程）
        t2.interrupt();
        t2.join();
    }


    private static class Action implements Runnable {

        // 线程安全问题，确保可见性（Happens-Before)
        private volatile boolean stopped = false;

        @Override
        public void run() {
            if (!stopped) {
                // 执行动作
                action();
            }
        }

        public void setStopped(boolean stopped) {
            this.stopped = stopped;
        }
    }

    private static void action() {
        System.out.printf("线程[%s] 正在执行...\n", Thread.currentThread().getName());  // 2
    }
}

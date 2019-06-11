package pers.qly.concurrent.interview.thread;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 14:32 2019/4/12
 */
public class CompleteAllThreadsQuestion {

    // Q：如何确保在主线程退出前，所有线程执行完毕？
    // A：通过把所有的活跃线程复制到 ThreadGroup.enumerate 可以确保

    public static void main(String[] args) throws InterruptedException {

        // main 线程 -> 子线程
        Thread t1 = new Thread(CompleteAllThreadsQuestion::action, "t1");
        Thread t2 = new Thread(CompleteAllThreadsQuestion::action, "t2");
        Thread t3 = new Thread(CompleteAllThreadsQuestion::action, "t3");

        // 不确定 t1、t2、t3 是否调用 start()

        t1.start();
        t2.start();
        t3.start();

        // 创建了 N Thread

        Thread mainThread = Thread.currentThread();
        // 获取 main 线程组
        ThreadGroup threadGroup = mainThread.getThreadGroup();
        // 活跃的线程数
        int count = threadGroup.activeCount();
        Thread[] threads = new Thread[count];
        // 把所有的线程复制到 threads 数组（递归）
        threadGroup.enumerate(threads, true);

        for (Thread thread : threads) {
            System.out.printf("当前活跃线程: %s\n", thread.getName());
        }
    }

    private static void action() {
        System.out.printf("线程[%s] 正在执行...\n", Thread.currentThread().getName());  // 2
    }

}

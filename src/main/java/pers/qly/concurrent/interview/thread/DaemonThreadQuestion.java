package pers.qly.concurrent.interview.thread;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 14:19 2019/4/12
 */
public class DaemonThreadQuestion {

    // Q：当主线程退出时，守候子线程会执行完毕吗？
    // A：很多答案都说会说，主线程退出的时候，守候线程不会退出
    //    当主线程退出的时候，守候线程不一定执行，与执行时间有关系

    public static void main(String[] args) {
        // main 线程
        Thread t1 = new Thread(() -> {
            System.out.println("Hello,World");

            Thread currentThread = Thread.currentThread();
            System.out.printf("线程[name : %s, daemon:%s]: Hello,World\n",
                    currentThread.getName(),
                    currentThread.isDaemon()
            );
        }, "daemon");
        // 变成守候线程
        t1.setDaemon(true);
        t1.start();

        // 守候线程的执行依赖于执行时间（非唯一评判）
    }
}

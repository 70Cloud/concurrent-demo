package pers.qly.concurrent.interview.thread;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 13:13 2019/4/12
 */
public class ThreadExceptionQuestion {

    // Q：当线程遇到异常时，到底发生了什么？
    // A：会输出 isAlive() = false，线程终止，并抛出异常信息

    // Q：当线程遇到异常时，如何捕获？
    // A：参考 Thread.setDefaultUncaughtExceptionHandler()

    public static void main(String[] args) throws InterruptedException {

        // 这样有什么好处？比如说在高并发的情况下，如果把异常堆栈信息都输出，会打爆
        // 建议用这种方法！
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.out.printf("线程[%s] 遇到了异常，详细信息：%s\n",
                    thread.getName(),
                    throwable.getMessage());
        });

        // main 线程 -> 子线程
        Thread t1 = new Thread(() -> {
            throw new RuntimeException("数据达到阈值");
        }, "t1");

        t1.start();
        // main 线程会中止吗？
        t1.join();

        // Java Thread 是一个包装，它由 GC 做垃圾回收
        // JVM Thread 可能是一个 OS Thread，被 JVM 管理，
        // 当线程执行完毕（正常或者异常）
        System.out.println(t1.isAlive());
    }
}

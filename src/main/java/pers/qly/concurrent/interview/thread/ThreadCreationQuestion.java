package pers.qly.concurrent.interview.thread;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 11:15 2019/4/12
 */
public class ThreadCreationQuestion {

    // Q：如何创建线程？
    // A：在 Java 中，有且仅有一个，通过 new Thread() 创建

    // Runnable 之类的只能说是运行线程的方式
    // ThreadPoolExecutor 也并不能创建线程，只是调用 Thread 的 start 方法去运行一个线程
    // java.util.concurrent.ThreadPoolExecutor.addWorker -> t.start();

    // Q：如何销毁一个线程？
    // A：在 Java 中，目前还做不到，在 C++ 中可以实现

    public static void main(String[] args) {
        // main 线程 -> 子线程
        Thread thread = new Thread(() -> {
        }, "子线程-1");

    }

    /**
     * 不鼓励自定义（扩展） Thread
     */
    private static class MyThread extends Thread {

        /**
         * 多态的方式，覆盖父类实现
         */
        @Override
        public void run() {
            super.run();
        }
    }
}

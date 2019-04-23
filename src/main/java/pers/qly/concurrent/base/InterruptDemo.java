package pers.qly.concurrent.base;

import java.util.concurrent.TimeUnit;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 20:36 2019/2/25
 */
public class InterruptDemo {

    private static int index;

    public static void main(String[] args) throws InterruptedException {

        // 如何停止一个线程（中断逻辑）
        // 1、interrupt
        // 2、volatile boolean isStop = false;

        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                index++;
            }
//            System.out.printf("[%s]Thread\n",Thread.currentThread().getName());
            System.out.println(index);
        },"InterruptDemo");

        thread.start();

//        TimeUnit.SECONDS.sleep(1);

//        System.out.printf("[%s]Thread\n",Thread.currentThread().getName());

        thread.interrupt(); // 设置 isInterrupted 标识为 true

        System.out.println(thread.isInterrupted());

        // Thread.interrupted 对线程的中断标志进行复位
    }
}

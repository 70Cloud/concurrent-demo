package pers.qly.concurrent.base;

import java.util.concurrent.TimeUnit;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 20:42 2019/2/25
 */
public class ThreadInterruptDemo {

    public static void main(String[] args) throws InterruptedException {
        // 第一种复位方式
//        Thread thread = new Thread(() -> {
//            while (true) {
//                boolean isInterrupted = Thread.currentThread().isInterrupted();
//
//                if (isInterrupted) {
//                    System.out.println("before : " + isInterrupted);
//                    Thread.interrupted(); // 复位
//                    System.out.println("after : " + Thread.currentThread().isInterrupted());
//
//                }
//            }
//        });
//
//        thread.start();
//        TimeUnit.SECONDS.sleep(1);
//        thread.interrupt(); // 中断线程

        // FIXME
        // 第二种复位方式
        Thread thread = new Thread(() -> {
            while (true) { // 相当于 for (; ; )
                try {
                    Thread.sleep(1 * 10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
        System.out.println("before : " + thread.isInterrupted());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("after : " + thread.isInterrupted());
    }
}

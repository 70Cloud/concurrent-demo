package pers.qly.concurrent.juc;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    static class Thread1 extends Thread {

        private int index;
        private CyclicBarrier cyclicBarrier;

        Thread1(int index, CyclicBarrier cyclicBarrier) {
            this.index = index;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread() + "Thread1 线程 " + this.index + " 开始执行");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "Thread1 任务 " + this.index + " 执行完成");
        }
    }

    static class Thread2 extends Thread {

        private int index;
        private CyclicBarrier cyclicBarrier;

        Thread2(int index, CyclicBarrier cyclicBarrier) {
            this.index = index;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread() + "Thread2 线程 " + this.index + " 开始执行");

                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "Thread2 任务 " + this.index + " 执行完成");
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        for (int i = 0; i < 5; i++) {
            new Thread1(i, cyclicBarrier).start();
        }

        cyclicBarrier.reset();

        for (int i = 0; i < 5; i++) {
            new Thread2(i, cyclicBarrier).start();
        }
    }
}

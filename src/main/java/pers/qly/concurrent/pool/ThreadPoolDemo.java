package pers.qly.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 14:38 2019/2/27
 */
public class ThreadPoolDemo implements Runnable {

    // 什么是线程池，类似于以前的数据库连接池概念
    // 作用：
    // 1、降低创建线程和销毁线程的性能开销
    // 2、合理的设置线程池大小可以避免因为线程数超出硬件资源瓶颈带来的问题，类似起到了限流的作用；线程是稀缺资源，如果无线创建，会造成系统稳定性问题

    // newFixedThreadPool：该方法返回一个固定数量的线程池，线程数不变，当有一个任务提交时，若线程池
    //      中空闲，则立即执行，若没有，则会被暂缓在一个任务队列中，等待有空闲的线程去执行。
    // newSingleThreadExecutor: 创建一个线程的线程池，若空闲则执行，若没有空闲线程则暂缓在任务队列中。
    // newCachedThreadPool()：返回一个可根据实际情况调整线程个数的线程池，不限制最大线程数量(最大为 int 最大值)，若用空
    //      闲的线程则执行任务，若无任务则不创建线程。并且每一个空闲线程会在60秒后自动回收
    // newScheduledThreadPool: 创建一个可以指定线程的数量的线程池，但是这个线程池还带有延迟和周期性执行
    //      任务的功能，类似定时器。

    static ExecutorService service = Executors.newFixedThreadPool(3);
    static ExecutorService service1 = MyExecutors.newMyExecutors();

    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            service.execute(new ThreadPoolDemo());
            service.submit(new ThreadPoolDemo());
//            service1.execute(new ThreadPoolDemo());
        }
        service.shutdown();
    }

    // Q：execute 和 submit 的区别
    // A：execute 只能接受 Runnable 的任务，
    //    而 submit 不管 Runnable(不带返回值) 或者 Callable(带返回值) 的任务都能接受

    // Q：CPU 密集型、IO 密集型、混合型线程池个数设置大小怎么选择
    // A：CPU 密集型(主要执行计算任务，CPU 使用率较高)：线程池设置的越小越好
    //    IO 密集型(主要进行 IO 操作，执行 IO 操作的时间较长，这时 CPU 处于空闲状态，CPU 使用率较低)：按照 CPU 核心数的倍数设置(2,3,4 倍)
}

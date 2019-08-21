package pers.qly.concurrent.base;

import java.util.concurrent.TimeUnit;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 20:55 2019/2/25
 */
public class AtomicityDemo {

    private static int count = 0;

    public synchronized static void incr() {
        
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(AtomicityDemo::incr).start();
        }
        Thread.sleep(4000);
        System.out.println("运行结果：" + count); // 发现结果 <= 1000，所以这个就是原子性
        // 线程没办法在多线程环境下实现原子递增
    }
}

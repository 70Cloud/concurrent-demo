package pers.qly.concurrent.vola;

import java.util.concurrent.TimeUnit;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 20:57 2019/2/25
 */
public class VolatileDemo {

    // 停止线程的第二种方法
    private volatile static boolean stop = false;

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        stop = true;
    }
}

package pers.qly.concurrent.vola;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 21:46 2019/2/25
 */
public class VolatileDemo3 {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    // DCL 的问题

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            a = 1;
            x = b;
        });

        Thread t2 = new Thread(() -> {
            b = 1;
            y = a;
        });

        t1.start();
        t2.start();
        t1.join(); // 阻塞，底层是用 wait notify 来解决的-> 类似 Callable - Future
        t2.join();

        System.out.println("x = " + x + "; y = " + y);
        // 会出现 4 种结果 FIXME 思考下
        // x = 0 ; y = 1
        // x = 1 ; y = 0
        // x = 1 ; y = 1
        // x = 0 ; y = 0
    }
}

package pers.qly.concurrent.vola;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 21:42 2019/2/25
 */
public class VolatileDemo2 {

    private static volatile VolatileDemo2 instance = null;
    // 加了 volatile，执行的时候会加一个 lock 指令，加了一层内存屏障，解决可见性问题

    public static synchronized VolatileDemo2 getInstance() {
        if (instance == null) {
            instance = new VolatileDemo2();
        }
        return instance;
    }

    public static void main(String[] args) {
        VolatileDemo2.getInstance();
    }
}

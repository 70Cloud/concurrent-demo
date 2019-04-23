package pers.qly.concurrent.vola;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 22:27 2019/2/25
 */
public class VolatileDemo4 {

    // 只是用来查看指令 javap -c 生成字节码指令

    volatile int i = 0;

    public void incr(){
        i++;
        // getfield
        // iadd
        // putfield
    }

    public static void main(String[] args) {
        // 实际上这边是多线程，没写完
        new Thread().start();
        new VolatileDemo4().incr();
    }
}

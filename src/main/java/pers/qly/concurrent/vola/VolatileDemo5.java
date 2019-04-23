package pers.qly.concurrent.vola;

import java.util.concurrent.TimeUnit;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 11:31 2019/4/15
 */
public class VolatileDemo5 {

    // 验证 volatile 的实践性
    private static volatile boolean flag = false;

    public static void main(String[] args) {

//        loopVolatile(); // 放在这里就不行
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                flag = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        loopVolatile();// 放在这里就能读到修改之后的值
    }

    private static void loopVolatile() {
        for (; ; ) {
            if (flag) {
                System.out.println("我已经收到消息退出来了,,,,,,,,,,,,,,,,,,,,,");
                break;
            }
        }
    }
}

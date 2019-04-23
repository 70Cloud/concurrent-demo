package pers.qly.concurrent.interview.thread;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 14:26 2019/4/12
 */
public class ShutdownHookQuestion {

    // Q：请说明 ShutdownHook 线程的使用场景， 以及如何触发执行？
    // A：ShutdownHook 是在当线程退出的时候正在执行
    //    Spring 中的 AbstractApplicationContext#regiterShutdownHook 就会调用 doClose() 关闭上下文的所有 Bean

    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();

        runtime.addShutdownHook(new Thread(ShutdownHookQuestion::action, "Shutdown Hook Question"));

    }

    private static void action() {
        System.out.printf("线程[%s] 正在执行...\n", Thread.currentThread().getName());
    }
}

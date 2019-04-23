package pers.qly.concurrent.interview.future;

import java.util.concurrent.*;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 13:23 2019/4/13
 */
public class CancellableFutureQuestion {

    // Q：如何获取 Future 对象？
    // A：submit

    // Q：请举例 Future get() 以及 get(long ,TimeUnit) 方法的使用场景？
    // A：主要用于超时等待，如果不希望等待太长时间，就可以加个时间，超过这个时间会报异常

    // Q：如何利用 Future 优雅地取消一个任务的执行？
    // A：参考如下

    // Q：实现高并发需要注意什么
    // A：1.线程安全
    //    2.减少同步，线程竞争
    //    3.合理的利用状态位来进行判断
    //    4.合理的使用线程池
    //    5.要有超时的意识，只允许它执行多少秒（为什么 HTTP 请求、服务端的相应、RPC 框架都会有超时时间，
    //         就是 IO 是有限的，前面都堵住了，后边就执行不了了）


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future future = executorService.submit(() -> { // 3s 内执行完成，才算正常
            action(5);  // 如果 > 3s 就走超时撤销流程
        });

        try {
            future.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // Thread 恢复中断状态
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            // Thread 设置中断状态
            Thread.currentThread().interrupt();
            // 尝试取消：执行超时，适当地关闭
            future.cancel(true);

        }

        executorService.shutdown();
    }

    private static void action(int seconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
//            if(Thread.currentThread().isInterrupted()){// 中断状态判断（只判断不清除）
            if (Thread.interrupted()) {// 判断中断状态并且清除
                return;
            }
            action();
        } catch (InterruptedException e) {

        }
    }

    private static void action() {
        System.out.printf("线程 [%s] 正在执行...\n", Thread.currentThread().getName());
    }
}

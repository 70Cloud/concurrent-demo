package pers.qly.concurrent.future;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author: NoNo
 * @Description: Future Demo
 * @Date: Create in 14:28 2019/1/4
 */
public class FutureDemo {

    public static void main(String[] args) {

        Random random = new Random();

        ExecutorService service = Executors.newFixedThreadPool(1);

        Future<String> future = service.submit(() -> {// 正常流程
            // 如果随机时间大于等于 100ms,那么触发容错
            int value = random.nextInt(200);

            System.out.println("helloWorld() costs : " + value + " ms.");

            Thread.sleep(value);

            return "Hello World !";
        });

        try {
            future.get(100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // 超时流程
            future.cancel(true);// 解决容错后还能收到返回结果的问题
            System.out.println("超时保护！");
        }

        service.shutdown();
    }
}

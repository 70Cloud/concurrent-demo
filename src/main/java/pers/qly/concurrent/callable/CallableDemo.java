package pers.qly.concurrent.callable;

import java.util.concurrent.*;

/**
 * @Author: NoNo
 * @Description: 阻塞不阻塞看接口有没有抛出 InterruptedException {@link Future}
 * Callable,带返回值的线程
 * @Date: Create in 19:17 2019/1/11
 */
public class CallableDemo implements Callable<String> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        CallableDemo callableDemo = new CallableDemo();

        Future<String> future = executorService.submit(callableDemo);

        System.out.println(future.get()); // 阻塞

        executorService.shutdown();
    }

    @Override
    public String call() throws Exception {
        return "String" + 1;
    }
}

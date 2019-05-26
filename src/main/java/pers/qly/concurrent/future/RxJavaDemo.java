package pers.qly.concurrent.future;

import rx.Observer;
import rx.Single;
import rx.schedulers.Schedulers;

import java.util.Random;

/**
 * @Author: NoNo
 * @Description: Reactive X Java Demo
 * @Date: Create in 14:35 2019/1/4
 */
public class RxJavaDemo {

    public static void main(String[] args) {

        final Random random = new Random();

        Single.just("Hello World!") // just 发布数据
                .subscribeOn(Schedulers.immediate()) // 订阅的线程池 immediate = Thread.currentThread()
                .subscribe(new Observer<String>() {  // 消费

                    public void onCompleted() { //正常结束流程
                        System.out.println("执行结束！");
                    }

                    public void onError(Throwable throwable) { //异常结束流程
                        System.out.println("熔断保护！");
                    }

                    public void onNext(String s) {  // 数据消费 s = "Hello World!"
                        // 如果随机时间大于等于 100ms,那么触发容错
                        int value = random.nextInt(200);

                        if (value > 100) {
                            throw new RuntimeException("TimeOut!");
                        }
                        // 大于 100 这句话也不会出来，直接执行 onError()
                        System.out.println("helloWorld() costs : " + value + " ms.");
                    }
                });
    }
}

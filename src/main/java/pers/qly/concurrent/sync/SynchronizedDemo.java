package pers.qly.concurrent.sync;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 14:11 2019/2/26
 */
public class SynchronizedDemo {

    // synchronized 关键字使用(作用域不同)

    // 加了 synchronized ，在字节码指令中会增加 ACC_SYNCHRONIZED monitorenter/monitorexit
    static Object lock = new Object();

    // 1、修饰一个类,this,对象
//    public void SynchronizedDemo(){
//        synchronized (SynchronizedDemo.class){ // 全局锁(此处是对于多个 SynchronizedDemo 都是唯一占用)
//
//        }
//    }

//    public void SynchronizedDemo(){
//        synchronized (lock){ // 对象锁：如果对象带 static 就是类级别的锁，否则就是对象级别的锁
//
//        }
//    }

    public void SynchronizedDemo(){
        synchronized (this){ // 实例锁：在当前的实例中锁

        }
    }

    private static int count = 0;

    // 2、synchronized 修饰方法
    // 加了 synchronized 输出结果就是 1000，不加永远 <= 1000
    public synchronized static void incr() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
    }

    public static void main(String[] args) throws InterruptedException {

        // 如果上边是 this 的话，这 2 个对象互相不影响
        SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
        SynchronizedDemo synchronizedDemo1 = new SynchronizedDemo();
        synchronizedDemo.SynchronizedDemo();
        synchronizedDemo1.SynchronizedDemo();

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                SynchronizedDemo.incr();
            }).start();
        }
        Thread.sleep(4000);
        System.out.println("result : " + SynchronizedDemo.count);
    }

    // 总结
    // 1、无论 synchronized 关键字加在方法上还是对象上，如果它作用的对象是非静态的，则它取得的锁是对象；
    //    如果 synchronized 作用的对象是一个静态方法或一个类，则它取得的锁是对类，该类所有的对象同一把锁。 
    // 2、每个对象只有一个锁（lock）与之相关联，谁拿到这个锁谁就可以运行它所控制的那段代码。 
    // 3、实现同步是要很大的系统开销作为代价的，甚至可能造成死锁，所以尽量避免无谓的同步控制。

    // Q：为什么 Synchronized 能实现线程同步？
    // A：Java 对象头，Mark Work(存储对象的 HashCode，分代年龄和锁标志位信息)，Monitorenter\MonitorExit
    //    Synchronized 通过 Monitor 来实现线程同步，Monitor 是依赖于底层的操作系统的 Mutex Lock（互斥锁）来实现的线程同步。
}

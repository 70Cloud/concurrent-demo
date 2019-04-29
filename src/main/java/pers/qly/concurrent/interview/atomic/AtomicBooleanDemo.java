package pers.qly.concurrent.interview.atomic;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 13:49 2019/4/13
 */
public class AtomicBooleanDemo {

    // Q：在 Java 中，volatile 保证的是可见性还是原子性？
    // A：既有可见性（一定会保证），又有原子性（看情况），原生型都保证
    //    可见性：对象类型、原生类型都是可见性
    //    原子性：原生类型都是原子性
    //    volatile 底层就是基于内存屏障来实现的，内存屏障可以理解为变量锁，对一个变量的原子性的保证，对象存在中间对象
    //    锁是为什么出现，为了锁一段代码，这段代码叫临界区

    // Q：在 Java 中， volatile long 和 double 是线程安全的吗？
    // A：是线程安全的

    // Q：在 Java 中， volatile 的底层实现是基于什么机制？
    // A：内存屏障，其实就是锁，可以理解为变量锁，对一个变量的原子性的保证

    // Q：为什么 AtomicBoolean 内部变量使用 int 实现， 而非 boolean ？
    // A：当 value = 0 时为 false ，1 的时候为 true，为什么不用 short,boolean,byte 等
    //    现在的操作系统基本都是 32 位或者 64 位，在虚拟机中，boolean 其实就是用 int 来实现的

    // Q：在变量原子操作时， Atomic* CAS 操作比 synchronized 关键字那个更重？
    // A：看情况，如果没有锁竞争的时候，CAS 操作比 synchronized 重
    // CAS 操作也是相对重的实现，它也是实现 synchronized 瘦锁(thin lock) 的关键
    // 偏向锁(就是为了避免 CAS 操作) < CAS 操作 < synchronized 重锁(完全互斥)

    // Q：Atomic* CAS 的底层是如何实现的？
    // A：比较和交换 2 次操作，CAS 操作是一个更大的原子操作
    //   底层 X86 系统有一个汇编指令：cpmxchg(Compare And Exchange)，比较与交换

    private static int actualValue = 3;// 原始值

    public static void main(String[] args) {
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        AtomicInteger atomicInteger = new AtomicInteger(3);

        // 比较 if(value == 3){
        //      交换 value = 5
        // }

        // 偏向锁 < CAS 操作 < synchronized 重锁(完全互斥)
        // CAS 操作也是相对重的实现，它也是实现 synchronized 瘦锁(thin lock) 的关键
        atomicInteger.compareAndSet(3, 5);
    }

    // 此处的 synchronized 带价并不比 atomicInteger.compareAndSet() 要大
    // 如果是单一线程的话，会由偏向锁进行实现，如果是多线程的话都是把这个锁住
    private synchronized static void compareAndSet(int expectedValue, int newValue) {
        if (actualValue == expectedValue) {
            actualValue = newValue;
        }
    }
}

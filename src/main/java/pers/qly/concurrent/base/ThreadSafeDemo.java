package pers.qly.concurrent.base;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 20:54 2019/2/25
 */
public class ThreadSafeDemo {

    public static void main(String[] args) {

        // 线程的安全性问题
        // 可见性、原子性、有序性

        // 原子性：无法在多线程环境下实现原子递增
        // 有序性：在程序运行的过程中，代码执行的顺序和我们编写代码的顺序可能是不一致的，存在编译器的优化
        //      和指令的优化（CPU 运行过程中的优化，会乱序执行，优化提升 CPU 的执行效率，不影响代码的语义的情况下，进行重排序）

        // 线程是CPU调度的最小单元
        // CPU 的高速缓存，L1(L1 d 数据,L2 i 指令),L2,L3 cache，CPU的高速缓存对其他CPU是不可见的，这样就导致了缓存一致性问题
        // 所以会有总线锁，缓存锁（MESI，CPU 会加 #LOCK 指令）

        // 缓存一致性协议 MESI
        // 标记位、嗅探协议
        // M(modify)：表示当前缓存被修改过
        // E(Exclusive)：独占缓存，表示该缓存只会在该CPU中使用，并且和主内存中的数据保持一致
        // S(Shared)：多个核心CPU中的数据是一致的
        // I(Invalid)：缓存失效状态

        // 1、缓存一致性就导致了可见性的问题
        // 2、CPU 会进行指令重排，乱序执行，内存会乱序访问，
        // 总结来说，由于编译器的执行重排序，处理器的指令重排序，内存系统的重排序，所以就导致了有序性

        // CPU 层面如何解决
        // 1、引入了高速缓存的概念
        // 2、多核心 CPU 技术的出现

        // JVM 层面如何解决
        // JMM (Java Memory Model Java内存模型)
        // 为了屏蔽硬件和操作系统访问的差异，来实现 Java 程序在各个平台上达到一致性的访问效果，
        // 所以在 JVM 层面提供一个 JVM 抽象内存模型，主要解决 可见性、原子性、有序性 这三个问题

        // Q：如何解决原子性、可见性、有序性的问题
        // A：volatile/synchronized/final/j.u.c

        // 原子性解决方案：
        //      synchronized(monitorenter/monitorexit)、AtomicInteger等

        // 可见性解决方案：
        //      volatile、synchronized、final

        // 有序性解决方案
        //      volatile、synchronized

        // Q：volatile 有什么用
        // A：可以认为是一个轻量级的锁，解决了可见性（lock 指令），增加了一层内存屏障防止指令重排序

        // Q：什么是内存屏障
        // A：优化屏障和内存屏障机制
        // 从 CPU 层面了解什么是内存屏障
        // 乱序访问
        // store barrier（写屏障 storestore barrier
        //      强制所有在 storestore 内存屏障之前的所有指令先执行，发送缓存失效的信号；
        //      所有在 storestore 内存屏障之后的 store 指令，必须在 storestore 内存屏障之前的指令执行完之后再执行）
        // /load barrier（读屏障 loadload barrier
        //      强制所有在 loadload 内存屏障之前的所有指令先执行，发送缓存失效的信号；
        //      所有在 loadload 内存屏障之后的 store 指令，必须在 loadload 内存屏障之前的指令执行完之后再执行）
        ///full barrier（全屏障，上面两者相结合）
        // 屏障的作用：防止指令之间的重排序，保证数据的可见性

        // Q：CPU 层面的内存屏障有什么作用？
        // A：1、保证可见性  2、防止指令重排序

        // Q：编译器层面如何解决指令重排序问题？
        // A：通过 volatile 解决

        // JVM 层面的内存屏障
        // loadload()   require()
        // storestore   release()  定义在写之后，之前的写操作必须比之后的操作先执行
        // loadstore    require()
        // storeload    fence()    全屏障

        // Q：内存屏障的作用
        // A：1、确保不会把前面的指令排到内存屏障的后面
        //    2、强制对缓存的修改，立即写入到缓存中。如果是写操作的话，会导致其他CPU的缓存无效
        //    规则：
        //          对每个 volatile 写操作的前面会插入 storestore barrier
        //          对每个 volatile 写操作的后面会插入 storeload barrier
        //          对每个 volatile 读操作的前面会插入 loadload barrier
        //          对每个 volatile 读操作的后面会插入 loadstore barrier
    }
}

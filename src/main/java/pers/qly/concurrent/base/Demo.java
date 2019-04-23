package pers.qly.concurrent.base;

import java.util.concurrent.Callable;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 22:10 2019/2/25
 */
public class Demo {

    // 多线程如何学习
    // 1、为什么要用多线程？
    //    合理的利用 CPU 资源，提升程序性能
    // 2、如何使用
    //    Thread\Runnable\ExecutorService\J.U.C
    // 3、使用多线程带来哪些问题？
    //    原子性、可见性、有序性
    //    原子性：获取互斥锁（锁临界区的大的锁，CAS 锁变量的小锁）
    //    可见性：确保字段的改变可以被后续线程看到
    //    有序性：确保不会产生意外的结果
    // 4、怎么解决？
    //    volatile\synchronized\Lock\AtomicInteger
    // 5、这些关键字底层是如何解决多线程带来的问题？
    //    内存屏障、锁、缓存一致性
    // 6、原子性、可见性、有序性的本质？
    //    高速缓存、CPU 指令优化、内存乱序、缓存一致性


    // 这段代码无意义，只是用来看看指令
    static volatile int i;
    // 加了 volatile ,编译时加了一个 ACC_VOLATILE 指令
    // 查看 hotspot 源码
    // accessFlags.hpp
    // bytecodeIntepreter.cpp
    //   obj->release int field put(field offset,STACK_INT(-1))
    // oop.inline.cpp
    //   static void release_store(volatile jint* p,jint v)
    //   inline void OrderAccess::release_store(volatile jint* p,jint v){*p = v;}
    // 发现这边有个 volatile ，这个是 C++ 中的，是语言级别的内存屏障，禁止编译器对代码的优化

    public static void main(String[] args) {
        i = 10;
    }
}

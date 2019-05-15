package pers.qly.concurrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 16:18 2019/2/26
 */
public class ReentrantReadWriteLockDemo {

    // ReentrantReadWriteLock 重入读写锁(在读多写少的情况下，可以提高性能)
    // 以前理解的锁都是排它的，而对于 ReentrantReadWriteLock 来说，他是共享锁(意味着在同一时刻可以有多个线程获得锁)
    // 一般情况下，读写锁的性能比排他锁好，因为大部分情况下都是读多写少的

    static Map<String, Object> cacheMap = new HashMap<>(); // 模拟缓存
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock read = rwl.readLock(); // 读锁
    static Lock write = rwl.writeLock(); // 写锁

    // 缓存的更新和读取的时候
    public static final Object get(String key) {
        System.out.println("开始读取数据");
        read.lock(); // 读锁
        // (作用：当我们在执行读操作的时候，首先会获取一个读锁，在并发访问的时候，读不会被阻塞，
        // 也就是说，如果有 10 个或者 100 个线程访问读锁，不会影响后续的读操作)
        try {
            return cacheMap.get(key);
        } finally {
            read.unlock();
        }
    }

    public static final Object put(String key, Object value) {
        write.lock();// 写锁
        // 在执行写操作的时候，会获取一个写锁，意味着其他数据去读的时候会被阻塞到上边的读锁位置，当写锁被释放后，读锁就可以读取数据了
        System.out.println("开始写数据");
        try {
            return cacheMap.put(key, value);
        } finally {
            write.unlock();
        }
    }
}

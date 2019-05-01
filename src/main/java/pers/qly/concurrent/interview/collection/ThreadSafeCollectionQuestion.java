package pers.qly.concurrent.interview.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 14:48 2019/4/12
 */
public class ThreadSafeCollectionQuestion {

    // Q：请在 Java 集合框架以及 J.U.C 框架中各举出 List、Set 以及 Map 的实现？
    // A：List：
    //      Java 集合框架：ArrayList、LinkedList
    //      J.U.C 框架：CopyOnWriteArrayList、LinkedList
    //    Set：
    //      Java 集合框架：HashSet、TreeSet
    //      J.U.C 框架：CopyOnWriteArraySet、ConcurrentSkipListSet
    //    Map：
    //      Java 集合框架：HashMap、TreeMap
    //      J.U.C 框架：ConcurrentSkipListMap、ConcurrentHashMap

    // Q：如何将普通 List、Set 以及 Map 转化为线程安全对象？如何在 Java 9+ 实现以上问题？
    // A：参考如下

    // Q：请说明 HashTable、HashMap 以及 ConcurrentHashMap 的区别？
    // A：HashTable：所有方法都是线程安全，不允许 key、value 为空（报空指针），采用数组 + 链表实现
    //    HashMap：key、Value 都可以为空，Java 8 增加红黑树实现
    //    ConcurrentHashMap：不允许 key、value 为空

    // Q：请说明 ConcurrentHashMap 在不同的 J D K 中的实现？
    // A：< 7：采用分段锁实现，读的时候部分锁，写的时候需要锁
    //    = 7：读的时候不需要锁，写的时候需要锁
    //    8：读的时候不需要锁，写的时候需要锁，为了解决 Hash 冲突，采用红黑树

    // Q：请说明 ConcurrentHashMap 与 ConcurrentSkipListMap 各自的优势与不足？
    // A：ConcurrentHashMap 在写的时候都会加锁，内存占的小一点
    //    ConcurrentSkipListMap 在写的时候不加锁。内存占的大一点，通过空间换时间

    public static void main(String[] args) {

        // Java 9 的实现
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        // Java 9 + of 工厂方法，返回 Immutable 对象

        list = List.of(1, 2, 3, 4, 5);

        Set<Integer> set = Set.of(1, 2, 3, 4, 5);

        Map<Integer, String> map = Map.of(1, "A");

        // 以上实现都是不变对象，不过第一个除外

        // 通过 Collections#sychronized* 方法返回

        // Wrapper 设计模式（所有的方法都被 synchronized 同步或互斥）
        list = Collections.synchronizedList(list);

        set = Collections.synchronizedSet(set);

        map = Collections.synchronizedMap(map);

        // JUC
        list = new CopyOnWriteArrayList<>(list);
        set = new CopyOnWriteArraySet<>(set);
        map = new ConcurrentHashMap<>(map);

    }
}

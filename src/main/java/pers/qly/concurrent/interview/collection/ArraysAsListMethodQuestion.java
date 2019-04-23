package pers.qly.concurrent.interview.collection;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 14:42 2019/4/12
 */
public class ArraysAsListMethodQuestion {

    // Q：请说明 List、Vector 以及 CopyOnWriteArrayList 的相同点和不同点？
    // A：Vector 是 List 的实现，两者没有办法比较，Vector 就是 List，CopyOnWriteArrayList 也是 List 的实现
    //    应该比较 ArrayList 与 Vector 以及 CopyOnWriteArrayList
    //    Vector 所有方法都是同步
    //    CopyOnWriteArrayList 读的时候不加锁，写的时候加锁

    // Q：请说明 Collections.synchronizedList(list) 与 Vector 的相同点和不同点？
    // A：相同点：都是通过 synchronized 实现
    //    不同点：返回类型不一样,Collections.synchronizedList(list) 返回 List，Vector 返回 Vector;实现原理不同

    // Q：Arrays.asList(Object... ) 方法是线程安全的吗？如果不是的话，如何实现替代方案？
    // A：并非是线程安全，返回一个视图，但不能说是不变的，其实是可变的，比较特殊，可以替换元素
    // 替代方案：
    //  Java < 5 , Collections#synchronizedList
    //  Java 5+  , CopyOnWriteArrayList
    //  Java 9+  , List.of(...) 只读

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // 调整第三个元素为 9
        list.set(2, 9);
        // 3 -> 9
        // Arrays.asList 并非线程安全
        list.forEach(System.out::println);
        // Java < 5 , Collections#synchronizedList
        // Java 5+  , CopyOnWriteArrayList
        // Java 9+  , List.of(...) 只读
    }
}

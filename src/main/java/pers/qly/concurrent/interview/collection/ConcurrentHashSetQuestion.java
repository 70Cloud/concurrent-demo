package pers.qly.concurrent.interview.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:05 2019/4/12
 */
public class ConcurrentHashSetQuestion {

    // Q：请至少举出三种线程安全的 Set 实现？
    // A：ConcurrentSkipListSet、CopyOnWriteArraySet、Collections.synchronizedSet

    // Q：在 J.U.C 框架中，存在 HashSet 的线程安全实现吗？如果不存在的话，要如何实现？
    // A：不存在，实现参考如下，或者用 HashMap

    // Q：当 Set#iterator() 方法返回 Iterator 对象后，能否在其迭代中，给 Set 对象添加新的元素？
    // A：不一定，因为 Set 在这里并不一定是 java.util.concurrent 中的实现
    //    会报 ConcurrentModificationException，传统实现会有 fail-fast 快速失败的问题
    //    iterator 在迭代的时候会有一个 Mutex 互斥锁，创建迭代器的时候会建立 一个内存索引表，指向原来的对象
    //    当对象数量改变的时候，会出错。
    //    解决方法：定义一个 List，存储需要删除的对象，迭代完之后 removeAll

    public static void main(String[] args) {

    }

    private static class ConcurrentHashSet<E> implements Set<E> {

        private final Object OBJECT = new Object();

        private final ConcurrentHashMap<E, Object> map = new ConcurrentHashMap<>();

        private Set<E> keySet() {
            return map.keySet();
        }

        @Override
        public int size() {
            return keySet().size();
        }

        @Override
        public boolean isEmpty() {
            return keySet().isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return keySet().contains(o);
        }

        @Override
        public Iterator<E> iterator() {
            return keySet().iterator();
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(E e) {
            return map.put(e, OBJECT) == null;
        }

        @Override
        public boolean remove(Object o) {
            return map.remove(o) != null;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }
    }
}

package pers.qly.concurrent.interview.collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:05 2019/4/12
 */
public class PriorityBlockingQueueQuiz {

    public static void main(String[] args) throws Exception {
        BlockingQueue<Integer> queue = new PriorityBlockingQueue<>(2);
        // 1. PriorityBlockingQueue put(Object) 方法不阻塞
        // 2. PriorityBlockingQueue offer(Object) 方法不限制
        // 3. PriorityBlockingQueue 插入对象会做排序，默认参照元素 Comparable 实现，
        //    或者显示地传递 Comparator
        queue.put(9);
        queue.put(1);
        queue.put(8);

        /**
         * PriorityBlockingQueue#put() 方法其实调用了 offer 方法，不阻塞
         * public void put(E e) {
         *         offer(e); // never need to block
         *     }
         */

        System.out.println("queue.size() = " + queue.size()); // 不阻塞 3
        System.out.println("queue.take() = " + queue.take()); // 不限制 1
        System.out.println("queue = " + queue); // 排序 [8,9]
    }
}

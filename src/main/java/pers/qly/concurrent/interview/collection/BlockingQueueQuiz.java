package pers.qly.concurrent.interview.collection;

import java.util.concurrent.*;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:36 2019/4/12
 */
public class BlockingQueueQuiz {

    // Q：请说明 BlockingQueue 与 Queue 的区别？
    // A：BlockingQueue 继承了 Queue
    //    BlockingQueue#put() 当队列满了的时候会阻塞
    //    BlockingQueue#take() 取出队列头元素

    // Q：请说明 LinkedBlockingQueue 与 ArrayBlockingQueue 的区别？
    // A：LinkedBlockingQueue：链表结构，无边界（其实也是有界的，构造器中 Integer 的最大值）
    //    ArrayBlockingQueue：数组结构，有边界

    // Q：请说明 LinkedTransferQueue 与 LinkedBlockingQueue 的区别？
    // A：LinkedTransferQueue(1.7 提供)，性能比 LinkedBlockingQueue 好很多

    public static void main(String[] args) throws Exception {

        offer(new ArrayBlockingQueue<>(2));
        offer(new LinkedBlockingQueue<>(2));
        offer(new PriorityBlockingQueue<>(2));
        offer(new SynchronousQueue<>());
    }

    private static void offer(BlockingQueue<Integer> queue) throws Exception {
        System.out.println("queue.getClass() = " + queue.getClass().getName());
        System.out.println("queue.offer(1) = " + queue.offer(1));
        System.out.println("queue.offer(2) = " + queue.offer(2));
        System.out.println("queue.offer(3) = " + queue.offer(3));
        System.out.println("queue.size() = " + queue.size());
        System.out.println("queue.take() = " + queue.take());
        System.out.println("=================");
    }
}

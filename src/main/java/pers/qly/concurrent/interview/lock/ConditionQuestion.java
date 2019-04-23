package pers.qly.concurrent.interview.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 10:56 2019/4/13
 */
public class ConditionQuestion {

    // Q：请举例说明 Condition 使用场景？
    // A：Object#wait()、Object#notify() 与 Condition#await()、Condition#signal() 其实是异曲同工的

    // Q：请使用 Condition 实现“ 生产者- 消费者问题” ？
    // A：TODO..

    // Q：请解释 Condition await() 和 signal() 与 Object wait() 和 notify() 的相同与差异？
    // A：相同：都是阻塞与释放
    //    差异：Condition 等待队列将 Object monitor 对象监视方法分解为不同的对象，通过将他们与不同的锁进行结合，使每个对象
    //          都具有等待队列集合的效果
    //          wait,notify 只能实现一个条件的阻塞与释放，而 Condition 就可以在同一个锁的代码块中实现多个条件挂起与唤醒

    private Lock lock;
    private Condition condition;

    public static void main(String[] args) {

    }
}

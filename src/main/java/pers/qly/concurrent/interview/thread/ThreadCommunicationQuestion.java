package pers.qly.concurrent.interview.thread;

/**
 * @Author: NoNo
 * @Description: 线程通讯
 * @Date: Create in 14:06 2019/4/12
 */
public class ThreadCommunicationQuestion {

    // Q：为什么 wait() 和 notify() 以及 notifyAll() 方法属于 Object，并解释它们的作用？
    // A：在 Java 中，所有对象都是 Object，一个线程总会去 read 或者 write 某一个对象，monitor 肯定会去监视某个对象
    //    在 Java 中，缺少一个 Mutex 互斥对象（在 Java 5 中提供了，学习了 pthread_mutex_lock）
    //    Condition 中的 await 以及 signal 就是借鉴了 pthread

    /**
     * int main() {
     *     pthread_t t1;
     *     pthread_t t2;
     *
     *     pthread_create(&t1, NULL, addCounter, NULL);
     *     pthread_create(&t2, NULL, minusCounter, NULL);
     *
     *     pthread_join(t1, NULL);
     *     pthread_join(t2, NULL);
     *
     *     std::cout << "Counter : " << counter << std::endl;
     *
     *     return EXIT_SUCCESS;
     * }
     *
     * void *minusCounter(void *ptr) {
     *     for (int i = 0; i < 100; i++) {
     *
     *         // lock 加锁
     *         pthread_mutex_lock(&mutex1);
     *         std::cout << "minusCounter - Before Counter : " << counter << std::endl;
     *
     *         if (counter < 9 && counter > 1) { // 消费数据，唤起生产者线程
     *             counter--;
     *             pthread_cond_signal(&condition_var);
     *         }
     *
     *         if (counter < 1) { // 当数据不足时，阻塞当前消费者线程
     *             pthread_cond_wait(&condition_var, &mutex1);
     *         }
     *
     *         std::cout << "minusCounter - After Counter : " << counter << std::endl;
     *         // unlock 解锁
     *         pthread_mutex_unlock(&mutex1);
     *     }
     *     return NULL;
     * }
     *
     * void *addCounter(void *ptr) {
     *     for (int i = 0; i < 100; i++) {
     *         // lock 加锁
     *         pthread_mutex_lock(&mutex1);
     *         std::cout << "addCounter - Before Counter : " << counter << std::endl;
     *
     *         if (counter < 9) {  // 当数据不到阈值时，唤起当前消费者线程
     *             counter++;
     *             pthread_cond_signal(&condition_var);
     *         }
     *
     *         if (counter > 9) {  // 当数据达到阈值时，阻塞当前生产者线程
     *             pthread_cond_wait(&condition_var, &mutex1);
     *         }
     *
     *         std::cout << "addCounter - After Counter : " << counter << std::endl;
     *         // unlock 解锁
     *         pthread_mutex_unlock(&mutex1);
     *     }
     *     return NULL;
     * }
     */

    // Q：为什么 Object wait() 和 notify() 以及 notifyAll() 方法必须在 synchronized 之中执行？
    // A：wait() 和 notify() 主要作用是等待和通知，
    //    Object#wait() 首先需要获得锁才能调用，调用之后释放锁，当前线程又被阻塞
    //    Object#notify() 已经获得锁，唤起一个被阻塞的线程

    //    LockSupport#park() 与 wait 类似
    //    LockSupport#unpark()
}

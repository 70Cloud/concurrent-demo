package pers.qly.concurrent.interview.thread;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 11:25 2019/4/12
 */
public class ThreadStateQuestion {

    // 在 Java 中，只能表示线程的状态，不能表示相应的执行，销毁

    public static void main(String[] args) {

        // main 线程 -> 子线程
        Thread thread = new Thread(() -> { // new Runnable(){ public void run(){...}};
            System.out.printf("线程[%s] 正在执行...\n", Thread.currentThread().getName());  // 2
        }, "子线程-1");

        // 启动线程
        thread.start();

        // 先于 Runnable 执行
        System.out.printf("线程[%s] 是否还活着: %s\n", thread.getName(), thread.isAlive()); // 1
        // 在 Java 中，执行线程 Java 是没有办法销毁它的，
        // 但是当 Thread.isAlive() 返回 false 时，实际底层的 Thread 已经被销毁了

        // Runnable 怎么调用的 run 方法参考 thread.cpp 中 JavaThread::run() 方法，会调用 thread_main_inner() 方法
        // 执行完成之后会调用 exit 以及 delete
        //  this->exit(false);
        //  delete this;

        /**
         * void JavaThread::thread_main_inner() {
         *   assert(JavaThread::current() == this, "sanity check");
         *   assert(this->threadObj() != NULL, "just checking");
         *
         *   // Execute thread entry point unless this thread has a pending exception
         *   // or has been stopped before starting.
         *   // Note: Due to JVM_StopThread we can have pending exceptions already!
         *   if (!this->has_pending_exception() &&
         *       !java_lang_Thread::is_stillborn(this->threadObj())) {
         *     {
         *       ResourceMark rm(this);
         *       this->set_native_thread_name(this->get_thread_name());
         *     }
         *     HandleMark hm(this);
         *     this->entry_point()(this, this);
         *   }
         *
         *   DTRACE_THREAD_PROBE(stop, this);
         *
         *   this->exit(false);
         *   delete this;
         * }
         */
    }
}

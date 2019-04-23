package pers.qly.concurrent.interview.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 13:29 2019/4/12
 */
public class AllThreadStackQuestion {

    // Q：Java 线程有哪些状态，分别代表什么含义？
    // A：NEW  RUNNABLE  BLOCKED  WAITING  TIMED_WAITING  TERMINATED

    // Q：如何获取当前 JVM 所有的线程状态？
    // A：1.通过 jps,jstack 命令可以获取
    //    2.通过 ThreadMXBean 中的 ThreadMXBean.getThreadInfo 可以获取

    public static void main(String[] args) {

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMXBean.getAllThreadIds();

        // 打印出当前 JVM 所有 Java 进程
        for (long threadId : threadIds) {
            ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
            System.out.println(threadInfo.toString());
        }

    }
}

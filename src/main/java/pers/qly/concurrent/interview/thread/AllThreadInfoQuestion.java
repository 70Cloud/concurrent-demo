package pers.qly.concurrent.interview.thread;

import com.sun.management.ThreadMXBean;

import java.lang.management.ManagementFactory;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 13:35 2019/4/12
 */
public class AllThreadInfoQuestion {

    // Q：如何获取线程的资源消费情况？
    // A：com.sun.management.ThreadMXBean.getThreadAllocatedBytes 可以获取线程的分配内存，还有其他的一些信息

    public static void main(String[] args) {

        ThreadMXBean threadMXBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();

        long[] threadIds = threadMXBean.getAllThreadIds();

        for (long threadId : threadIds) {
//            ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
//            System.out.println(threadInfo.toString());
            long bytes = threadMXBean.getThreadAllocatedBytes(threadId);

            long kBytes = bytes / 1024;

            System.out.printf("线程[ID:%d] 分配内存： %s KB\n", threadId, kBytes);
        }

    }
}

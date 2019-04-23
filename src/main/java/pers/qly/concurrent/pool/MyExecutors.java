package pers.qly.concurrent.pool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: NoNo
 * @Description: 线程池监控
 * @Date: Create in 15:19 2019/2/27
 */
public class MyExecutors extends ThreadPoolExecutor {

    private Map<String, Date> startTime = new HashMap<>();

    // beforeExecutor\afterExecutor\shutdown 把这些放到 MongoDB 中
    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void shutdown() {
        System.out.println("已执行的任务数：" + this.getCompletedTaskCount() + "\n");
        System.out.println("当前活动线程数：" + this.getActiveCount() + "\n");
        System.out.println("当前排队线程数：" + this.getQueue().size() + "\n");
        // 可以上报
        super.shutdown();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        startTime.put(String.valueOf(r.hashCode()), new Date());
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Date startDate = startTime.remove(String.valueOf(r.hashCode()));
        Date finishDate = new Date();
        long dif = finishDate.getTime() - startDate.getTime(); // 执行间隔时间
        System.out.println("任务耗时：" + String.valueOf(dif));
        System.out.println("最大允许的线程数：" + this.getMaximumPoolSize());
        System.out.println("线程的空闲时间：" + this.getKeepAliveTime(TimeUnit.MILLISECONDS));
        System.out.println("任务总数：" + this.getTaskCount());
        super.afterExecute(r, t);
    }

    public static ExecutorService newMyExecutors() {
        return new MyExecutors(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS, new SynchronousQueue<>());
    }
}

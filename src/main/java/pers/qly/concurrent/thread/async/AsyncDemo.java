package pers.qly.concurrent.thread.async;

/**
 * @Author: NoNo
 * @Description: Thread 异步的应用，Zookeeper 中就是这么用的
 * @Date: Create in 19:54 2019/2/25
 */
public class AsyncDemo {

    PrintProcessor printProcessor;

    public AsyncDemo() {
        SaveProcessor saveProcessor = new SaveProcessor();
        saveProcessor.start();
        printProcessor = new PrintProcessor(saveProcessor);
        printProcessor.start();
    }

    public static void main(String[] args) {

        Request request = new Request();
        request.setName("NoNo");
        new AsyncDemo().doTest(request);

    }

    private void doTest(Request request) {

        printProcessor.processRequest(request);
    }
}


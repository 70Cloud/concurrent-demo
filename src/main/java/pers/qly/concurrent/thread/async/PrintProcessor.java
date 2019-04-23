package pers.qly.concurrent.thread.async;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 19:50 2019/2/25
 */
public class PrintProcessor extends Thread implements RequestProcessor {

    LinkedBlockingDeque<Request> linkedBlockingDeque = new LinkedBlockingDeque<>();

    private final RequestProcessor nextProcessor;

    public PrintProcessor(RequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public void run() {
        while (true) {
            Request request = null;
            try {
                request = linkedBlockingDeque.take();
                System.out.println("print data : " + request);
                nextProcessor.processRequest(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void processRequest(Request request) {
        linkedBlockingDeque.add(request);
    }
}

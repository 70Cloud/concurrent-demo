package pers.qly.concurrent.thread.async;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 19:52 2019/2/25
 */
public class SaveProcessor extends Thread implements RequestProcessor {

    LinkedBlockingDeque<Request> linkedBlockingDeque = new LinkedBlockingDeque<>();

    @Override
    public void run() {

        while (true) {
            Request request = null;
            try {
                request = linkedBlockingDeque.take();
                System.out.println("save data : " + request);
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

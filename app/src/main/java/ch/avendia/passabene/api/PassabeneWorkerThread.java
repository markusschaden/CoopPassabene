package ch.avendia.passabene.api;

import java.util.LinkedList;
import java.util.Queue;

import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Session;
import ch.avendia.passabene.shopping.ShoppingCardHolder;

/**
 * Created by Markus on 02.02.2015.
 */
public class PassabeneWorkerThread {

    private final Session session;
    private Queue<AdvancedApiCall> queue;
    private boolean stopped = false;
    private Thread thread;
    private ShoppingCardHolder shoppingCardHolder;

    public PassabeneWorkerThread(Session session) {
        this.session = session;
        queue = new LinkedList<AdvancedApiCall>();
        shoppingCardHolder = new ShoppingCardHolder();
    }

    public void start() {
        if(thread == null) {
            thread = new Thread(new Runnable() {
                public void run() {
                    while(!stopped) {
                        while (queue.size() > 0) {
                            AdvancedApiCall apiCall = queue.poll();
                            DTO dto = apiCall.execute(session);
                            apiCall.doUpdate(dto, false);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }


    }

    public void addApiCallToQueue(AdvancedApiCall apiCall) {
        queue.add(apiCall);
    }


}

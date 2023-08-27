package rx.investment;

import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@ServerEndpoint("/ws/price")
public class StockPriceMonitor {
    @Resource
    private ManagedExecutorService executor;
    @OnOpen
    public void open(Session session) throws IOException {
        session.getBasicRemote().sendText("Server Connected");
        repeatSend(session, 3);
    }
    private void repeatSend(Session session, int times) {
        executor.execute(() ->
            IntStream.rangeClosed(1, times).forEach(i -> {
                session.getAsyncRemote().sendText("the " + i + "th time send text to client");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            })
        );
    }
    @OnMessage
    public void received(Session session, String message) throws IOException {
        System.out.println("Received message: check if executor is still null: " + executor);
        session.getBasicRemote().sendText("Server received message");
        //repeatSend(session, 3);
    }
}

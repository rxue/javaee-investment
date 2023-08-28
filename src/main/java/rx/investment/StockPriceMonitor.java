package rx.investment;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@ServerEndpoint("/ws/price")
public class StockPriceMonitor {
    @OnOpen
    public void open(Session session) throws IOException {
        session.getBasicRemote().sendText("Server Connected from endpoint instance with hash: " + this.hashCode());
    }
    @OnMessage
    public void received(Session session, String message) throws IOException, InterruptedException {
        System.out.println("Received ping");
        TimeUnit.SECONDS.sleep(2);
        session.getBasicRemote().sendText("price from server");
    }
    @OnClose
    public void close() {
        System.out.println("closed connection");
    }
}

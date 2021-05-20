import com.alipay.api.java_websocket.client.WebSocketClient;
import com.alipay.api.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/19 21:00
 * @ Description：
 * @ since: JDk1.8
 */
public class TestApp {
    public static void main(String[] args) {
        try {
            // 这里用的binance的socket接口，国内调用需要VPN，使用换成你的就行
//            String url = "wss://stream.binance.com:9443/ws/ethbtc@ticker";
//            String url = "wss://stream.binance.com:9443/ws/ethbtc@depth20";
            String url = "ws://localhost:1120/websocket";
//            String url = "ws://139.186.169.185:1120/websocket";
//            String url = "ws://139.186.169.185:1120/websocket";
            URI uri = new URI(url);
            WebSocketClient mWs = new WebSocketClient(uri){
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("连接成功");
                }

                @Override
                public void onMessage(String s) {
                    System.out.println(s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("连接关闭");
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("连接出错");
                }
            };
            mWs.connect();
            System.out.println("haha");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

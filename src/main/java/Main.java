import com.sun.net.httpserver.HttpServer;
import controller.AdminController;
import controller.EndUserController;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/admin", new AdminController());
        server.createContext("/enduser", new EndUserController());

        server.setExecutor(null);
        server.start();
    }
}
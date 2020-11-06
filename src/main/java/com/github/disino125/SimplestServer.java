package com.github.disino125;

import org.eclipse.jetty.server.Server;

public class SimplestServer {
    public static Server createServer(int port)
    {
        Server server = new Server(port);
        return server;
    }

    public static void main(String[] args) throws Exception {
        Server server = createServer(8080);
        server.setHandler(new HelloHandler());
        server.start();
        server.join();
    }
}

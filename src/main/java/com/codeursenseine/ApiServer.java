package com.codeursenseine;


import net.codestory.http.Configuration;
import net.codestory.http.WebServer;
import net.codestory.http.routes.Routes;

import java.io.IOException;


/**
 * Main class.
 */
public class ApiServer {

    private static WebServer webServer;


    public ApiServer() throws IOException {
        Config.loadConfig();
        initServer();
        startServer();
    }

    public static class ServerConfiguration implements Configuration {
        @Override
        public void configure(Routes routes) {
            routes.add(VoteResource.class);
        }
    }

    private static int getPort() {
        // Heroku
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }

        // Default port;
        return 9999;
    }

    public static void initServer() {
        webServer=new WebServer().configure(new ServerConfiguration());
    }

    public static void startServer() {
        if (webServer==null) {
            initServer();
        }
        webServer.start(getPort());
    }


    public static void stopServer() {
        webServer.stop();
    }

    public static int getServerPort() {
        return webServer.port();
    }

    public static void main(String[] args) {
        initServer();
        startServer();
    }
}

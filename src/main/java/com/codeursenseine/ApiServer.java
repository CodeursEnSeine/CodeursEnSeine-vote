package com.codeursenseine;


import net.codestory.http.Configuration;
import net.codestory.http.WebServer;
import net.codestory.http.routes.Routes;



/**
 * Main class.
 */
public class ApiServer {

    private static WebServer webServer;


    public ApiServer() {
        initServer();
        startServer();
    }

    public static class ServerConfiguration implements Configuration {
        @Override
        public void configure(Routes routes) {
            routes.add(VoteResource.class);
        }
    }

    /**
     * Get the port.
     * <ul>
     *     <li>Heroku : System.getenv("PORT")</li>
     *     <li>Cloudbees : System.getProperty("app.port")</li>
     *     <li>default : 9999</li>
     * </ul>
     * @return port to use
     */
    private static int getPort() {
        // Heroku
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }

        // Cloudbees
        String cloudbeesPort = System.getProperty("app.port");
        if (cloudbeesPort != null) {
            return Integer.parseInt(cloudbeesPort);
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

package com.codeursenseine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by youen on 23/11/2014.
 */
public class Config {

    private static Properties configProps=new Properties();

    public static void loadConfig() throws IOException {
        InputStream inStream = null;
        try {

            String configPath = System.getenv("CONFIGPATH");
            if (configPath==null) {
                configPath="conf/ces-vote.properties";
            }
            inStream =new FileInputStream( configPath );
            configProps.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    public static Properties getConfigProps() {
        if (configProps.size()==0) {
            try {
                loadConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return configProps;
    }
}

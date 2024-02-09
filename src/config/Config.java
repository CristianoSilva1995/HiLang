package config;
import java.io.*;
import java.util.Properties;

/**
 * @author Arkadiusz Nowak
 * @reference: https://www.codejava.net/coding/reading-and-writing-configuration-for-java-application-using-properties-class
 *
 * Wrapper around Properties class allowing for usage of application wide config
 * passed by the main app controller to respective page controllers
 */

public class Config extends Properties {
    private static final String CONFIG_FILE = "src/config/config.properties";

    public Config()  {
        // create main Properties object
        super();
        // Load properties from a .properties file using InputStream
        File configFile = new File(CONFIG_FILE);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(configFile);
            super.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
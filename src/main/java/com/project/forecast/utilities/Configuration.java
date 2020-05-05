package com.project.forecast.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private static final String CONFIG_FILE = System.getProperty("user.dir")+"/src/main/resources/config.properties";
    private static Properties properties;
    public static String API_KEY = getVal("apikey");
    public static String BASE_URL = getVal("baseurl");

    private static String getVal(String key){
        properties = new Properties();
        FileInputStream fis = null;
        try{
             fis= new FileInputStream(CONFIG_FILE);
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}

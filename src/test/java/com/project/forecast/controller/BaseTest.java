package com.project.forecast.controller;

import com.project.forecast.utilities.NoRainyException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This is the common class which will be shared among all the test cases. It contains generic methods which applies at test level.
 */

public class BaseTest {
    public static Logger logger = getLogData(BaseTest.class.getName());
    public String reportPath = new File("").getAbsoluteFile().toString().trim()+"/TestReport/";
    public static ExtentReports reports;
    public static ExtentTest test;
    public String path;

    public boolean weatherReporter(ExtentTest test, String weather, String city) throws Exception {
        boolean flag = false;
        if (weather.equalsIgnoreCase("Rain") || weather.equalsIgnoreCase("Drizzle")){
            logger.info("Rain forecasted for the city: "+city);
            reportPass(test, "Validate whether Rainy forecasted for the city: "+city,
                    "Rain will happen in next 5 days for  the city: "+city);
            flag=true;
        }else{
            logger.error("Rain not forecasted for the city: "+city);
            reportFail(test, "Validate whether Rainy forecasted for the city: "+city,
                    "Rain will not happen in next 5 days for  the city: "+city+" weather would be- "+weather);
            flag =false;
            throw new NoRainyException("Rain will not happen for the city: "+city);
        }
        return flag;
    }

    public void createSearchQueryPath(String city, String appId) {
        path = "/data/2.5/weather?q=" +city + "&APPID=" + appId;
    }

    public ExtentTest generateTest(ExtentReports report, String testName, String desc){
        ExtentTest test = report.startTest(testName, desc);
        return test;
    }

    public void reportFail(ExtentTest test, String methodName, String details){
       test.log(LogStatus.FAIL, methodName, details);
    }

    public void reportPass(ExtentTest test, String methodName, String details){
        test.log(LogStatus.PASS, methodName, details);
    }

    public void reportInfo(ExtentTest test, String methodName, String details){
        test.log(LogStatus.INFO, methodName, details);
    }

    public void tearDown(ExtentTest test, ExtentReports report){
        report.endTest(test);
        report.flush();
        report.close();
    }


    public String storeJsonDataInFile(String body, String arg){
        String random = RandomStringUtils.randomAlphabetic(5);
        String   path = System.getProperty("user.dir")+"/src/test/resources/JsonBody/jsonbody_"+arg+"_"+random+".json";
        File file = new File(path);
        try{
            file.createNewFile();
            FileWriter fw = new FileWriter(path);
            fw.write(body);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public String readObjectFromRestBody(String body, String key, String arg){
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        String value="";
        try{
            String path = storeJsonDataInFile(body, arg);
            FileReader fileReader = new FileReader(path);
            JSONArray jsonarray = (JSONArray)parser.parse(fileReader);
            if(jsonarray!=null && jsonarray.size()>0) {
                for (Object obj : jsonarray) {
                    JSONObject jo = (JSONObject) obj;
                    value = String.valueOf(jo.get(key));
                    break;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public String getWeatherData(String path, String subJson, String subJsonKey){
        Map<String, String> dataMap = new HashMap<>();
        JSONParser parser = new JSONParser();
        String value="";
        try{
            Reader fis = new FileReader(path);
            JSONObject object = (JSONObject) parser.parse(fis);
            Set<Map.Entry<String, JSONObject>> es = object.entrySet();
            for(Map.Entry<String, JSONObject> en : es){
                dataMap.put(en.getKey(),String.valueOf(en.getValue()));
            }
            value = readObjectFromRestBody(dataMap.get(subJson), subJsonKey, subJson);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Logger getLogData(String className){
        DOMConfigurator.configure("log4j.xml");
        return Logger.getLogger(className);
    }

}

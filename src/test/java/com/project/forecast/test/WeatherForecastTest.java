package com.project.forecast.test;

import com.project.forecast.controller.APITestBed;
import com.project.forecast.controller.MyListeners;
import com.project.forecast.controller.RetryListener;
import com.project.forecast.utilities.RESTUtility;
import com.relevantcodes.extentreports.ExtentReports;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.project.forecast.utilities.Configuration;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * This test class is to test the api call of https://openweathermap.org by using RestAssured. It inherits APITestBes class where
 * @BeforeSuite & @AfterSuite are implemented.
 * @BeforeTest annotation sets up reporting and RequestSpecification.
 * @AfterTest annotation tears down everything
 * @Test annotation contains the real tests
 * It implements ITestListener as well
 */


//@Listeners({MyListeners.class, RetryListener.class})
public class WeatherForecastTest extends APITestBed {

    RequestSpecification request;


  @BeforeMethod
    public void setUp(){
      request= RESTUtility.requestSpecification();
      Response response = RESTUtility.getDefaultResponse(request);
      int responseCodeBaseUrl = RESTUtility.getStatusCode(response);
      Assert.assertEquals(responseCodeBaseUrl,200);
  }


@DataProvider(name="dataprovider")
  public Object[][] testData(){
      return new Object[][] {
              { "1", "Taipei" },
              { "2", "Juneau" },
              { "3", "Manchester" },
              { "4", "Kochi" },
              { "5", "Mumbai" },
              { "6", "Patna" },
              { "7", "Dubai" },
              {"8", "Quibdo"},
              { "9", "Monrovia" },
              { "10", "Hilo" },
              { "11", "Buenaventura" },
              { "12", "Belem" },
              {"13", "Kuala Terengganu"}
      };
  }

  @Test(dataProvider="dataprovider")
  public void validateRainyDayWeather(String sno, String city) throws Exception {
      System.out.println("Validation Started For: "+city);
      createSearchQueryPath(city, Configuration.API_KEY);
      Response response = RESTUtility.restGetCall(request, path);
      String file = storeJsonDataInFile(response.asString(), city);
      String weatherIs = getWeatherData(file, "weather", "main");
      Assert.assertTrue(weatherReporter(test, weatherIs, city));
  }

}

package com.project.forecast.controller;

import com.project.forecast.utilities.Configuration;
import com.project.forecast.utilities.RESTUtility;
import com.relevantcodes.extentreports.ExtentReports;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * This class is extented by all the API tests as it initially set ups the base url and after execution of all the tests within
 * the suite it rests the base url.
 */

public class APITestBed extends BaseTest {

    @BeforeSuite
    public void apiTestBedsetUp(){
        RESTUtility.setBaseURI(Configuration.BASE_URL);
        reports = new ExtentReports(reportPath+this.getClass().getSimpleName()+".html",false);
        test= generateTest(reports, this.getClass().getSimpleName(), "This test is to validate Rainy season for the selected cities");
    }

    @AfterSuite
    public void flushTestBed(){
        RESTUtility.resetBaseURI(Configuration.BASE_URL);
        super.tearDown(test, reports);
    }


}

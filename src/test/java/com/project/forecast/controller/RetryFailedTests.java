package com.project.forecast.controller;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTests implements IRetryAnalyzer {
    public static Logger logger = BaseTest.getLogData(RetryFailedTests.class.getName());
    private int retryCnt = 0;
    private int maxRetryCnt = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCnt < maxRetryCnt) {
            logger.info("Retrying " + result.getName() + " again and the count is " + (retryCnt + 1));
            new BaseTest().reportInfo(BaseTest.test,result.getMethod().getMethodName(),"Retrying " + result.getName() + " again and the count is " + (retryCnt + 1));
            retryCnt++;
            return true;
        }

        return false;
    }
}

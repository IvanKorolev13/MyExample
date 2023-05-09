package org.marshal;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

class Retry implements IRetryAnalyzer {
    private int actualRetry = 0;
    private static final int MAX_RETRY = 3;
    @Override
    public boolean retry (ITestResult result){
        if(actualRetry < MAX_RETRY){
            actualRetry++;
            return true;
        }else{
            return false;
        }
    }
}

package testComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RerunOnTestFailure implements IRetryAnalyzer {

    //giving retry method in the @test of invalidProduct method which is going to fAIL-> place order test
    int count=0;
    int maxTry=1;
    @Override
    public boolean retry(ITestResult result) {
        while (count<maxTry){
            count++;
            return true;
        }
        return false;
    }
}

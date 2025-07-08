package testComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static reportManager.ExtentReportTestNG.getReportsObject;


public class Listeners extends BaseTest implements ITestListener {
    ExtentReports extent=getReportsObject();
    ExtentTest test;
    //to make Thread safe and support parallel execution
    ThreadLocal<ExtentTest> extentTest=new ThreadLocal<ExtentTest>();
    @Override
    public void onTestStart(ITestResult result){
        test=extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result){
        extentTest.get().log(Status.PASS,"Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result){
        extentTest.get().fail(result.getThrowable());
        String filepath=null;
        try {
            driver=(WebDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
            filepath=getScreenshotPathForTest(result.getMethod().getMethodName(),driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        extentTest.get().addScreenCaptureFromPath(filepath,result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context){
        extent.flush();
    }
}

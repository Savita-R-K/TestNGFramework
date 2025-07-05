package testComponents;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LandingPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    Properties prop;
    public LandingPage landingPage ;

    public WebDriver initDriver() {

        prop = new Properties();
        try {
            prop.load(Files.newInputStream(Paths.get(System.getProperty("user.dir") + "/src/main/resources/GlobalData.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");
        if (browserName.contains("chrome")) {
            ChromeOptions options=new ChromeOptions();
            if(browserName.contains("headless")){
                options.addArguments("--headless=new");
            }
            driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public void launchApplication() {
        driver = initDriver();
        landingPage=new LandingPage(driver);
        landingPage.goTo(prop);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.close();
    }

    public String getScreenshotPathForTest(String testCaseName,WebDriver driver) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
        FileUtils.copyFile(src, dest);
        return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
    }
}

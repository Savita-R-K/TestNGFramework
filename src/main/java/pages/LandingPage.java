package pages;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LandingPage extends AbstractComponent {

    @FindBy(id="userEmail")
    WebElement usernameField;

    @FindBy(id="userPassword")
    WebElement passwordField;

    @FindBy(css="input[type='submit']")
    WebElement loginBtn;

    WebDriver driver;
    public LandingPage(WebDriver driver){
        super(driver);
        this.driver=driver;
    }

    public ProductPage login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginBtn.click();
        return new ProductPage(driver);
    }

    public void goTo() {
        driver.get(System.getProperty("url"));
    }
}
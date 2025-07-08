package pages;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfirmationPage extends AbstractComponent {

    @FindBy(css = ".hero-primary")
    WebElement confirmationMsg;

    WebDriver driver;
    public ConfirmationPage(WebDriver driver){
        super(driver);
        this.driver=driver;
    }

    public String assertMsg() {
        return confirmationMsg.getText();
    }
}
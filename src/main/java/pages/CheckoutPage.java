package pages;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends AbstractComponent {

    @FindBy(css = "input[placeholder='Select Country']")
    WebElement countryInputBox;
    @FindBy(xpath = "//button[contains(@class,'ta-item')][2]")
    WebElement selectCountry;
    @FindBy(xpath = "//a[contains(@class,'action__submit')]")
    WebElement placeOrderBtn;


    WebDriver driver;
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }

    public void selectCountry(String countryName) {
        countryInputBox.sendKeys(countryName);
        jsClick(selectCountry);
    }

    public ConfirmationPage placeOrder() {
        jsClick(placeOrderBtn);
        return new ConfirmationPage(driver);
    }

}
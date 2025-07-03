package pages;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyOrdersPage extends AbstractComponent {
    WebDriver driver;

    @FindBy(css="tbody tr:nth-child(1) td:nth-child(3)")
    WebElement recentOrder;

    public MyOrdersPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }

    public boolean assertOrderHistory(String productName) {
        return recentOrder.getText().equals(productName);
    }
}

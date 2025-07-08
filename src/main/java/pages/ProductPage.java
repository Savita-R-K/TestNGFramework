package pages;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductPage extends AbstractComponent {

    @FindBy(css = "div[role='alert']")
    WebElement alert;

    @FindBy(id = "toast-container")
    WebElement loginMsg;

    @FindBy(css = "button[routerlink*='cart']")
    WebElement cartBtn;

    @FindBy(css = "button[routerlink='/dashboard/myorders']")
    WebElement ordersBtn;

    WebDriver driver;
    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public List<WebElement> getProductList() {
        List<WebElement> productList=driver.findElements(By.cssSelector("div[class='card']"));
        return productList;
    }

    public WebElement getProduct(String prodName) {
        return getProductList().stream().filter(ele -> ele.findElement(By.cssSelector("h5 b")).getText().equals(prodName)).findFirst().orElse(null);
    }

    public String addProductToCart(String prodName) {
        getProduct(prodName).findElement(By.cssSelector("div[class='card-body'] button:last-of-type")).click();
        waitForTheElementToAppear(alert);
        String alertText=alert.getText();
        waitForTheElementToDisappear(alert);
        return alertText;

    }

    public CartPage navigateToCartPage() {
        cartBtn.click();
        return new CartPage(driver);
    }

    public String getToastMessage() {
        waitForTheElementToAppear(alert);
        return alert.getText();
    }

    public String getLoginMessage() {
        waitForTheElementToAppear(loginMsg);
        return loginMsg.getText();
    }


    public MyOrdersPage navigateToMyOrdersPage() {
        jsClick(ordersBtn);
        return new MyOrdersPage(driver);
    }
}

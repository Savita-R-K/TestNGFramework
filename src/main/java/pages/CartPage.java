package pages;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends AbstractComponent {

    @FindBy(css = ".cart h3")
    List<WebElement> cartItems;

    @FindBy(css = ".totalRow button")
    WebElement checkoutBtn;


    WebDriver driver;
    public CartPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }

    public List<WebElement> getCartItems(){
        return cartItems;
    }

    public boolean assertCartItem(String prodName){
        return getCartItems().stream().anyMatch(webElement -> webElement.getText().equals(prodName));
    }

    public CheckoutPage navigateToCheckoutPage() {
        jsClick(checkoutBtn);
        return  new CheckoutPage(driver);
    }
}

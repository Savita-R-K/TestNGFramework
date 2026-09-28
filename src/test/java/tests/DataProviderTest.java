package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import testComponents.BaseTest;

public class DataProviderTest extends BaseTest {

    @Test(dataProvider = "getData", groups = {"demo","dataProvider"})
    public void placeOrderAndVerifyHistory(String username, String password, String productName) throws InterruptedException {
        String countryName = "India";

        //login using username and password and navigate to product page
        ProductPage productPage = landingPage.login(username, password);

        //add product to cart, verify alert
        Thread.sleep(3000);
        Assert.assertEquals(productPage.addProductToCart(productName), "Product Added To Cart");

        //navigate to cart page
        CartPage cartPage = productPage.navigateToCartPage();

        //verify cart item
        Assert.assertTrue(cartPage.assertCartItem(productName));

        //navigate to checkout page
        CheckoutPage checkoutPage = cartPage.navigateToCheckoutPage();

        //selectCountry
        checkoutPage.selectCountry(countryName);

        //placeOrder and navigate to ConfirmationPage
        ConfirmationPage confirmationPage = checkoutPage.placeOrder();

        //navigate to orders page
        Thread.sleep(3000);
        MyOrdersPage myOrdersPage = productPage.navigateToMyOrdersPage();

        //check for recent order
        Assert.assertTrue(myOrdersPage.assertOrderHistory(productName));
    }

    @DataProvider
    public Object[][] getData() {
        return new Object[][]{
                {"savitaravindra57@gmail.com", "Pass@123", "ZARA COAT 3"},
                {"savitaravindra57@gmail.com", "Pass@123", "IPHONE 13 PRO"}
        };
    }

}
package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import testComponents.BaseTest;

public class DependencyTest extends BaseTest{

    String username = "savitaravindra57@gmail.com";
    String password = "Pass@123";
    String productName = "ZARA COAT 3";

    @Test
    public void placeOrder() throws InterruptedException {
        String countryName = "India";
        String confirmationMsg = "THANKYOU FOR THE ORDER.";

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

        //msg confirmation
        Assert.assertEquals(confirmationPage.assertMsg(), confirmationMsg);
    }

    @Test(dependsOnMethods = {"placeOrder"})
    public void orderHistory(){

        //login using username and password and navigate to product page
        ProductPage productPage = landingPage.login(username, password);

        //navigate to orders page
        MyOrdersPage myOrdersPage=productPage.navigateToMyOrdersPage();

        //check for recent order
        Assert.assertTrue(myOrdersPage.assertOrderHistory(productName));
    }
}

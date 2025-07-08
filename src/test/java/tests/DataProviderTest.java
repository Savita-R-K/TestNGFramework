package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import testComponents.BaseTest;

public class DataProviderTest extends BaseTest{

    @Test(dataProvider = "getData",groups = "dataProvider")
    public void placeOrderAndVerifyHistory(String username,String password,String productName) throws InterruptedException {
        String countryName = "India";

        //login using username and password and navigate to product page
        ProductPage productPage =landingPage.login(username, password);

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
        MyOrdersPage myOrdersPage=productPage.navigateToMyOrdersPage();

        //check for recent order
        Assert.assertTrue(myOrdersPage.assertOrderHistory(productName));
    }

    @DataProvider
    public Object[][] getData(){
        return new Object[][] {
                {"savitaravindra57@gmail.com","Pass@123","ZARA COAT 3"},
                {"savitaravindra57@gmail.com","Pass@123","IPHONE 13 PRO"}
        };
    }

//    ❌ Why dependsOnMethods fails in your case:
//    TestNG will do this:
//
//    Run placeOrder(data1)
//
//    Run placeOrder(data2)
//
//    Then run orderHistory(data1)
//
//    Then run orderHistory(data2)
//
//    So when orderHistory(data1) runs, both orders already exist. That’s why the first row is no longer guaranteed to be the one from data1.
//
//    @Test(dataProvider = "getData")
//    public void placeOrder(String username,String password,String productName) throws InterruptedException {
//        String countryName = "India";
//        String confirmationMsg = "THANKYOU FOR THE ORDER.";
//
//        //login using username and password and navigate to product page
//        ProductPage productPage = landingPage.login(username, password);
//
//        //add product to cart, verify alert
//        Thread.sleep(3000);
//        Assert.assertEquals(productPage.addProductToCart(productName), "Product Added To Cart");
//
//        //navigate to cart page
//        CartPage cartPage = productPage.navigateToCartPage();
//
//        //verify cart item
//        Assert.assertTrue(cartPage.assertCartItem(productName));
//
//        //navigate to checkout page
//        CheckoutPage checkoutPage = cartPage.navigateToCheckoutPage();
//
//        //selectCountry
//        checkoutPage.selectCountry(countryName);
//
//        //placeOrder and navigate to ConfirmationPage
//        ConfirmationPage confirmationPage = checkoutPage.placeOrder();
//
//        //msg confirmation
//        Assert.assertEquals(confirmationPage.assertMsg(), confirmationMsg);
//    }
//
//    @Test(dependsOnMethods = {"placeOrder"}, dataProvider = "getData")
//    public void orderHistory(String username,String password,String productName){
//
//
//
//        //login using username and password and navigate to product page
//        ProductPage productPage = landingPage.login(username, password);
//
//        //navigate to orders page
//        MyOrdersPage myOrdersPage=productPage.navigateToMyOrdersPage();
//
//        //check for recent order
//        Assert.assertTrue(myOrdersPage.assertOrderHistory(productName));
//    }
}

package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import testComponents.BaseTest;

import java.util.HashMap;

public class HashMapAsDataProviderTest extends BaseTest{

    @Test(dataProvider = "getData",groups = "dataProvider")
    public void placeOrderAndVerifyHistory(HashMap<String,String> inputMap) throws InterruptedException {
        String countryName = "India";

        //login using username and password and navigate to product page
        ProductPage productPage = landingPage.login(inputMap.get("username"), inputMap.get("password"));

        //add product to cart, verify alert
        Thread.sleep(3000);
        Assert.assertEquals(productPage.addProductToCart(inputMap.get("productName")), "Product Added To Cart");

        //navigate to cart page
        CartPage cartPage = productPage.navigateToCartPage();

        //verify cart item
        Assert.assertTrue(cartPage.assertCartItem(inputMap.get("productName")));

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
        Assert.assertTrue(myOrdersPage.assertOrderHistory(inputMap.get("productName")));
    }

    @DataProvider
    public Object[][] getData(){
        HashMap<String,String> map=new HashMap<>();
        map.put("username","savitaravindra57@gmail.com");
        map.put("password","Pass@123");
        map.put("productName","ZARA COAT 3");
        HashMap<String,String> map1=new HashMap<>();
        map1.put("username","savitaravindra57@gmail.com");
        map1.put("password","Pass@123");
        map1.put("productName","ADIDAS ORIGINAL");
        return new Object[][] {{map},{map1}};
    }
}

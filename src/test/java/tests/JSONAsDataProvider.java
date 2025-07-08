package tests;

import data.DataReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import testComponents.BaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class JSONAsDataProvider extends BaseTest {

    @Test(dataProvider = "getData", groups = "dataProvider")
    public void placeOrderAndVerifyHistory(HashMap<String, String> inputMap) throws InterruptedException {
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
        MyOrdersPage myOrdersPage = productPage.navigateToMyOrdersPage();

        //check for recent order
        Assert.assertTrue(myOrdersPage.assertOrderHistory(inputMap.get("productName")));
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> dataReader = DataReader.getJsonDataToMap(System.getProperty("user.dir") + "/src/test/java/data/PurchaseOrder.json");
        return new Object[][]{{dataReader.get(0)}, {dataReader.get(1)}};
    }
}

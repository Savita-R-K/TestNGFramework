package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import testComponents.BaseTest;

public class LoginTest extends BaseTest {

    @Test(groups = {"Smoke"})
    public void invalidLogin(){
        String username = "savitaravindra57@gmail.com";
        String password = "Pass123";


        //login using username and password and navigate to product page
        ProductPage productPage = landingPage.login(username, password);

        Assert.assertEquals(productPage.getToastMessage(),"Incorrect email or password.");

    }
    @Test
    public void validLogin() {
        String username = "savitaravindra57@gmail.com";
        String password = "Pass@123";

        //login using username and password and navigate to product page
        ProductPage productPage = landingPage.login(username, password);

        //add product to cart, verify alert
        Assert.assertEquals(productPage.getLoginMessage(),"Login Successfully");

    }

}
package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import testComponents.BaseTest;

public class LoginTest extends BaseTest {

    @Test
    public void invalidLogin() {

        //login using username and password and navigate to product page
        ProductPage productPage = landingPage.login(getProp("username"), "IncorrectPassword");

        Assert.assertEquals(productPage.getToastMessage(),"Incorrect email or password.");

    }
    @Test
    public void validLogin() {

        //login using username and password and navigate to product page
        ProductPage productPage = landingPage.login(getProp("username"), getProp("password"));
        //add product to cart, verify alert
        Assert.assertEquals(productPage.getLoginMessage(),"Login Successfully");

    }

}
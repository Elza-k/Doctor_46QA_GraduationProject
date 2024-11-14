package com.phonebook;

import com.phonebook.pages.User;
import com.phonebook.utils.RetryAnalyser;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class LoginTests extends TestBase {

    @BeforeMethod
    public void preCondition() {
        if (app.getUserHelper().isSignOutButtonPresent()) {
            app.getUserHelper().logout();
        }
        app.driver.get("https://telranedu.web.app/login");
    }

    @Test
    public void loginExistedUserPositiveTest1() {
        app.getUserHelper().clickLoginLink();
        //fillInRegistrationForm(new User("admin_admin_20242@gmail.com", "Password1@"));
        app.getUserHelper().fillInRegistrationForm(new User().setEmail("admin_admin_2024@gmail.com").setPassword("Password1@2"));
        app.getUserHelper().clickOnLoginButton();
        Assert.assertTrue(app.getUserHelper().isSignOutButtonPresent());
    }

    @Test(retryAnalyzer = RetryAnalyser.class)
    public void loginExistedUserPositiveTest2(ITestContext context) {
        String email = "Wadmin_admin_20242@gmail.com";
        String password = "Password1@";
        context.setAttribute("email", email);
        context.setAttribute("password", password);
        app.getUserHelper().login(email, password);
        assert app.getUserHelper().isSignOutButtonPresent();
    }

    @Test
    public void loginNegativeWOEmailTest() {
        app.getUserHelper().clickLoginLink();
        app.getUserHelper().fillInRegistrationForm(new User()
                //  .setEmail("admin_admin_20242@gmail.com")
                .setPassword("Password1@"));
        app.getUserHelper().clickOnLoginButton();
        Assert.assertEquals(app.getUserHelper().alertTextPresent(), "Wrong email or password", "Messages are not equals");
        Assert.assertTrue(app.getContactHelper().isAlertPresent());
    }

    @AfterMethod(enabled = false)
    public void postConditions() {
        try {
            app.getUserHelper().logout();
        } catch (Exception e) {
            // throw new RuntimeException(e);
        }
    }
}

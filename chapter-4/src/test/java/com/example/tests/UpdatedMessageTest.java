package com.example.tests;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.example.pageobjects.LoginFormPage;
import com.example.requests.MessagePayload;
import com.example.requests.MessageRequest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class UpdatedMessageTest {

    private static WebDriver driver;
    private static Eyes eyes;

    // Use WebDriverManager to download the driver binaries
    // and start the browser server for us.
    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        eyes = new Eyes();

        eyes.setApiKey("kfEbhYJuA3o108n4I1mQb9PuKrz4H6W2EghQigyRVMg5M110");
        eyes.setForceFullPageScreenshot(true);
        eyes.open(driver, "RBP","Message View Test", new RectangleSize(1400, 700));
    }

    @AfterAll
    public static void teardown() {
        eyes.close();

        driver.quit();
    }

    @Test
    public void testMessageIsCreated() {
        MessagePayload messagePayload = new MessagePayload();
        messagePayload.setName("Test User");
        messagePayload.setEmail("test@email.com");
        messagePayload.setPhone("074123456789");
        messagePayload.setSubject("A totally different subject");
        messagePayload.setDescription("Test Description that is a little larger");

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.sendRequest(messagePayload);

        driver.get("https://automationintesting.online/#/admin/");

        LoginFormPage loginFormPage = new LoginFormPage(driver);
        loginFormPage.enterUsername("admin");
        loginFormPage.enterPassword("password");
        loginFormPage.clickLoginButton();

        driver.get("https://automationintesting.online/#/admin/messages");

        eyes.checkWindow("Message Page");
    }

}

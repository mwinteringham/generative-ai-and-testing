package com.example.tests;

import com.example.pageobjects.ContactFormPage;
import com.example.pageobjects.LoginFormPage;
import com.example.pageobjects.MessagePage;
import com.example.requests.MessagePayload;
import com.example.requests.MessageRequest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InitialMessageTest {

    private static WebDriver driver;

    // Use WebDriverManager to download the driver binaries
    // and start the browser server for us.
    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
    }

    @AfterAll
    public static void teardown() {
        driver.quit();
    }

    @Test
    public void testMessageIsCreated() {
        driver.get("https://automationintesting.online/#/");

        ContactFormPage contactFormPage = new ContactFormPage(driver);
        contactFormPage.enterName("John Smith");
        contactFormPage.enterEmail("test@email.com");
        contactFormPage.enterPhone("07123456789");
        contactFormPage.enterSubject("Testing");
        contactFormPage.enterDescription("This is a test message");
        contactFormPage.submitForm();

        driver.get("https://automationintesting.online/#/admin/");

        LoginFormPage loginFormPage = new LoginFormPage(driver);
        loginFormPage.enterUsername("admin");
        loginFormPage.enterPassword("password");
        loginFormPage.clickLoginButton();

        driver.get("https://automationintesting.online/#/admin/messages");

        MessagePage messagePage = new MessagePage(driver);
        assertEquals(2, messagePage.getMessageCount());
    }
}

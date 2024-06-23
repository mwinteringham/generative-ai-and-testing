package com.example.tests;

import com.example.pageobjects.ContactFormPage;
import com.example.pageobjects.LoginPage;
import com.example.pageobjects.MessagePage;
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
        contactFormPage.clickSubmitButton();

        driver.get("https://automationintesting.online/#/admin/");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("admin");
        loginPage.enterPassword("password");
        loginPage.clickLoginButton();

        driver.get("https://automationintesting.online/#/admin/messages");

        MessagePage messagePage = new MessagePage(driver);
        assertEquals(2, messagePage.getMessageCount());
    }
}

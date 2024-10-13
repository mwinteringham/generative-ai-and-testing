package com.example.tests;

// Importing the necessary classes and libraries for the test
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

    // Declare the WebDriver instance that will be used to control the browser
    private static WebDriver driver;

    // This method is run once before all the test methods in this class.
    // It sets up the WebDriver (ChromeDriver in this case) using WebDriverManager.
    @BeforeAll
    public static void setupClass() {
        // WebDriverManager takes care of automatically downloading the correct driver binary
        // and configuring it for the desired browser (Chrome in this case).
        WebDriverManager.chromedriver().setup();

        // Initialize the WebDriver object with ChromeDriver (which launches a Chrome browser).
        driver = new ChromeDriver();
    }

    // This method is run after all test methods have been executed.
    // It ensures that the browser is properly closed and cleaned up.
    @AfterAll
    public static void teardown() {
        // Quit the browser after the test execution is done to free up resources.
        driver.quit();
    }

    // The main test method that checks if a message is successfully created and displayed.
    @Test
    public void testMessageIsCreated() {
        // Navigate to the target website where the test will be executed.
        driver.get("https://automationintesting.online/#/");

        // Instantiate the ContactFormPage object to interact with the contact form.
        ContactFormPage contactFormPage = new ContactFormPage(driver);
        
        // Fill out the contact form by entering the required fields.
        contactFormPage.enterName("John Smith");
        contactFormPage.enterEmail("test@email.com");
        contactFormPage.enterPhone("07123456789");
        contactFormPage.enterSubject("Testing");
        contactFormPage.enterDescription("This is a test message");
        
        // Submit the contact form.
        contactFormPage.clickSubmitButton();

        // Navigate to the admin login page.
        driver.get("https://automationintesting.online/#/admin/");

        // Instantiate the LoginPage object and enter the login credentials.
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("admin");
        loginPage.enterPassword("password");
        
        // Log into the admin panel.
        loginPage.clickLoginButton();

        // Navigate to the admin messages page to check for the newly created message.
        driver.get("https://automationintesting.online/#/admin/messages");

        // Instantiate the MessagePage object to interact with the messages section.
        MessagePage messagePage = new MessagePage(driver);

        // Assert that there are two messages in the message list (this could mean that
        // there was already 1 message present before, and after submitting the new one,
        // the count becomes 2).
        assertEquals(2, messagePage.getMessageCount());
    }
}

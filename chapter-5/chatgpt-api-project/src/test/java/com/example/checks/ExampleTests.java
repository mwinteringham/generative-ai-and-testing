package com.example.checks;

import com.example.datamanager.ChatGPTClient;
import com.example.datamanager.ContactFormDetails;
import com.example.pageobjects.ContactFormPage;
import com.google.gson.Gson;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ExampleTests {

    private static WebDriver driver;

    @BeforeAll
    public static void setupChromeDriver(){
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
    }

    @AfterAll
    public static void closeChromeDriver(){
        driver.quit();
    }

    @Test
    public void exampleContactUsFormTest() throws InterruptedException {
        driver.get("https://automationintesting.online/#/");

        ContactFormPage contactFormPage = new ContactFormPage(driver);
        contactFormPage.enterName("John Smith");
        contactFormPage.enterEmail("john@example.com");
        contactFormPage.enterPhone("01234567890");
        contactFormPage.enterSubject("Test Subject");
        contactFormPage.enterDescription("This is a test message");
        contactFormPage.clickSubmitButton();

        assert contactFormPage.getSuccessMessage().contains("Thanks for getting in touch");
    }

    @Test
    public void exampleContactUsFormTestWithChatGPT() throws Exception {
        ChatGPTClient chatGPTClient = new ChatGPTClient(System.getProperty("OPENAI_API_KEY"));
        String testData = chatGPTClient.prompt("You are a data generator. Create me random data in a JSON format based on the criteria delimited by three hashes." +
                "Additional data requirements are shared between back ticks." +
                "###\n" +
                "name\n" +
                "email\n" +
                "phone `UK format`\n" +
                "subject `Over 20 characters in length`\n" +
                "description `Over 50 characters in length`\n" +
                "###");

        ContactFormDetails contactFormDetails = new Gson().fromJson(testData, ContactFormDetails.class);

        driver.get("https://automationintesting.online/#/");

        ContactFormPage contactFormPage = new ContactFormPage(driver);
        contactFormPage.enterName(contactFormDetails.getName());
        contactFormPage.enterEmail(contactFormDetails.getEmail());
        contactFormPage.enterPhone(contactFormDetails.getPhone());
        contactFormPage.enterSubject(contactFormDetails.getSubject());
        contactFormPage.enterDescription(contactFormDetails.getDescription());
        contactFormPage.clickSubmitButton();

        assert contactFormPage.getSuccessMessage().contains("Thanks for getting in touch");
    }
}

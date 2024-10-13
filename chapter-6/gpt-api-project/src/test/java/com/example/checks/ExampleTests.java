package com.example.checks;

// Import necessary libraries and classes
import com.example.datamanager.ContactFormDetails;
import com.example.pageobjects.ContactFormPage;
import com.google.gson.Gson;
import dev.langchain4j.model.openai.OpenAiChatModel;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ExampleTests {

    // Declaring the WebDriver instance
    private static WebDriver driver;

    // This method sets up the Chrome WebDriver before all tests run
    @BeforeAll
    public static void setupChromeDriver(){
        // Set up the Chrome WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Initialize the WebDriver instance with ChromeDriver
        driver = new ChromeDriver();
    }

    // This method closes the Chrome WebDriver after all tests are complete
    @AfterAll
    public static void closeChromeDriver(){
        // Quit the WebDriver session and close the browser
        driver.quit();
    }

    // Test case for submitting the contact form with hardcoded test data
    @Test
    public void exampleContactUsFormTest() throws InterruptedException {
        // Navigate to the website with the contact form
        driver.get("https://automationintesting.online/#/");

        // Initialize the page object for interacting with the contact form
        ContactFormPage contactFormPage = new ContactFormPage(driver);

        // Enter data into the contact form fields
        contactFormPage.enterName("John Smith");
        contactFormPage.enterEmail("john@example.com");
        contactFormPage.enterPhone("01234567890");
        contactFormPage.enterSubject("Test Subject");
        contactFormPage.enterDescription("This is a test message");

        // Submit the form
        contactFormPage.clickSubmitButton();

        // Verify that the success message is displayed after submission
        assert contactFormPage.getSuccessMessage().contains("Thanks for getting in touch");
    }

    // Test case for submitting the contact form using AI-generated data
    @Test
    public void exampleContactUsFormTestWithGPT() {
        // Initialize the OpenAI chat model with an API key (placeholder)
        OpenAiChatModel model = OpenAiChatModel.withApiKey("Enter API key");

        // Define the prompt to request data generation from GPT in JSON format
        String prompt = """
                You are a data generator. Create me random data in a JSON format based on the criteria delimited by three hashes.
                Additional data requirements are shared between back ticks.
                ###
                name
                email
                phone `UK format`
                subject `Over 20 characters in length`
                description `Over 50 characters in length`
                ###
                """;

        // Use the model to generate data based on the prompt
        String testData = model.generate(prompt);

        // Convert the generated JSON data to a ContactFormDetails object
        ContactFormDetails contactFormDetails = new Gson().fromJson(testData, ContactFormDetails.class);

        // Navigate to the website with the contact form
        driver.get("https://automationintesting.online/#/");

        // Initialize the page object for interacting with the contact form
        ContactFormPage contactFormPage = new ContactFormPage(driver);

        // Enter AI-generated data into the contact form fields
        contactFormPage.enterName(contactFormDetails.getName());
        contactFormPage.enterEmail(contactFormDetails.getEmail());
        contactFormPage.enterPhone(contactFormDetails.getPhone());
        contactFormPage.enterSubject(contactFormDetails.getSubject());
        contactFormPage.enterDescription(contactFormDetails.getDescription());

        // Submit the form
        contactFormPage.clickSubmitButton();

        // Verify that the success message is displayed after submission
        assert contactFormPage.getSuccessMessage().contains("Thanks for getting in touch");
    }
}

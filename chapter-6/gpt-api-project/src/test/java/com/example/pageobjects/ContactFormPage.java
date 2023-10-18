package com.example.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactFormPage {

    // Form fields
    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "phone")
    private WebElement phoneField;

    @FindBy(id = "subject")
    private WebElement subjectField;

    @FindBy(id = "description")
    private WebElement descriptionField;

    @FindBy(id = "submitContact")
    private WebElement submitButton;

    @FindBy(css = ".contact h2")
    private WebElement successMessage;

    // WebDriver instance
    private WebDriver driver;

    // Constructor
    public ContactFormPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Methods to interact with the form fields
    public void enterName(String name) {
        nameField.sendKeys(name);
    }

    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }

    public void enterPhone(String phone) {
        phoneField.sendKeys(phone);
    }

    public void enterSubject(String subject) {
        subjectField.sendKeys(subject);
    }

    public void enterDescription(String description) {
        descriptionField.sendKeys(description);
    }

    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until((d) -> successMessage.isDisplayed());
        return successMessage.getText();
    }
    public void clickSubmitButton() {
        submitButton.click();
    }
}


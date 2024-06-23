package com.example.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactFormPage {

    // WebDriver instance
    private WebDriver driver;

    // Constructor to initialize the PageFactory
    public ContactFormPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // WebElements for the form fields
    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "phone")
    private WebElement phoneInput;

    @FindBy(id = "subject")
    private WebElement subjectInput;

    @FindBy(id = "description")
    private WebElement descriptionTextarea;

    @FindBy(id = "submitContact")
    private WebElement submitButton;

    // Methods to interact with the form
    public void enterName(String name) {
        nameInput.sendKeys(name);
    }

    public void enterEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void enterPhone(String phone) {
        phoneInput.sendKeys(phone);
    }

    public void enterSubject(String subject) {
        subjectInput.sendKeys(subject);
    }

    public void enterDescription(String description) {
        descriptionTextarea.sendKeys(description);
    }

    public void clickSubmitButton() {
        submitButton.click();
    }
}


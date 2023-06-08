package com.example.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MessagePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "detail")
    private List<WebElement> messageList;

    public MessagePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust the timeout as needed
        PageFactory.initElements(driver, this);
    }

    public int getMessageCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("detail")));
        return messageList.size();
    }
}


package com.google.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleBeforeSearch {
    protected WebDriver chromeDriver;
    private WebElement searchField;
    private WebElement searchButton;

    public GoogleBeforeSearch(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    public void find(String keysFind){
        searchField = chromeDriver.findElement(By.xpath("//*/input[@class and @name='q']"));
        searchButton = chromeDriver.findElement(By.xpath("//*/input[@class and @name='btnK']"));
        searchField.click();
        searchField.sendKeys(keysFind);
        searchButton.click();
    }
}

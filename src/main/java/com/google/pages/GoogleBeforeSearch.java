package com.google.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleBeforeSearch {
    protected WebDriver chromeDriver;
    private final WebElement searchField;
    private final WebElement searchButton;

    public GoogleBeforeSearch(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        searchField = chromeDriver.findElement(By.xpath("//*/input[@class and @name='q']"));
        searchButton = chromeDriver.findElement(By.xpath("//*/input[@class and @name='btnK']"));
    }

    public void find(String keysFind){
        searchField.click();
        searchField.sendKeys(keysFind);
        searchButton.click();
    }
}

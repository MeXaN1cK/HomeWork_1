package com.google.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GoogleAfterSearch extends GoogleBeforeSearch{

    public GoogleAfterSearch(WebDriver chromeDriver) {
        super(chromeDriver);
    }

    public List<WebElement> getResultsSearch() {
        List<WebElement> results = chromeDriver.findElements(By.xpath("//*[@class='g']//*[contains(text(),'Википедия')]"));
        return results;
    }
}

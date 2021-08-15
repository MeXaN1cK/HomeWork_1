package ru.open.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class GooglePageFactory {
    private WebDriver chromeDriver;

    @FindBy(how= How.NAME, name="q")
    WebElement searchField;

    @FindBy(how= How.NAME, name="btnK")
    WebElement searchButton;

    @FindBy(how= How.XPATH, using = "//*[@class='g']//*[contains(text(),'https://www.open.ru')]")
    List<WebElement> allElements;

    public GooglePageFactory(WebDriver chromeDriver){
        this.chromeDriver=chromeDriver;
    }

    public void find (String keysFind){
        searchField.click();
        searchField.sendKeys(keysFind);
        searchButton.click();
    }

    public List<WebElement> getAllElements() {
        return allElements;
    }
}

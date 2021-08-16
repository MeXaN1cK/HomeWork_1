package com.google;

import com.google.pages.GoogleAfterSearch;
import com.google.pages.GoogleBeforeSearch;
import com.google.pages.GooglePageFactory;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Tests extends BaseTests{

    @Test
    public void FirstTest(){
        chromeDriver.get("https://www.google.com/");
        WebElement searchField = chromeDriver.findElement(By.name("q"));
        WebElement searchButton = chromeDriver.findElement(By.name("btnK"));
        searchField.click();
        searchField.sendKeys("Гладиолус");
        searchButton.click();

        List<WebElement> resultSearch = chromeDriver.findElements(By.xpath("//*[@class='g']//*/h3[@class][contains(text(),'Википедия')]"));

        Assertions.assertTrue(resultSearch.stream().anyMatch(x->x.getText().contains("Википедия")),
                "Статьи не найдены Гладиолус содержащие Википедия не найдены");
    }

    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка результатов поиска c помощью PO")
    @ParameterizedTest(name = "{displayName} {arguments}")
    @CsvSource({"Гладиолус,Википедия"})
    public void testPO(String keyWords,String result){
        chromeDriver.get("https://www.google.com/");
        GoogleBeforeSearch googleBeforeSearch = new GoogleBeforeSearch(chromeDriver);
        googleBeforeSearch.init();
        googleBeforeSearch.find(keyWords);
        GoogleAfterSearch googleAfterSearch = new GoogleAfterSearch(chromeDriver);
        Assertions.assertTrue(googleAfterSearch.getResultsSearch().stream().anyMatch(x->x.getText().contains(result)));
    }

    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка результатов поиска c помощью PF")
    @ParameterizedTest(name = "{displayName} {arguments}")
    @CsvSource({"Гладиолус,Википедия"})
    public void testPF(String keyWords,String result){
        chromeDriver.get("https://www.google.com/");
        GooglePageFactory googlePageFactory = PageFactory.initElements(chromeDriver,GooglePageFactory.class);
        googlePageFactory.find(keyWords);
        Assertions.assertTrue(googlePageFactory.getAllElements().stream().anyMatch(x->x.getText().contains(result)));
    }
}

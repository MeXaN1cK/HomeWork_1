package ru.open;

import ru.open.pages.GoogleAfterSearch;
import ru.open.pages.GoogleBeforeSearch;
import ru.open.pages.GooglePageFactory;
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
        searchField.sendKeys("Открытие");
        searchButton.click();

        List<WebElement> resultSearch = chromeDriver.findElements(By.xpath("//*[@class='g']//*[contains(text(),'https://www.open.ru')]"));

        Assertions.assertTrue(resultSearch.stream().anyMatch(x->x.getText().contains("https://www.open.ru")),
                "Статьи не найдены Открытие содержащие https://www.open.ru не найдены");
    }

    @Test
    public void SecondTest(){
        chromeDriver.get("https://www.open.ru");
        List<WebElement> moneyFields = chromeDriver.findElements(By.xpath("//*[@class='main-page-exchange__table main-page-exchange__table--online']" +
                "//*/tr[@class='main-page-exchange__row main-page-exchange__row--with-border']"));
        final WebElement[] usdField = new WebElement[1];
        final WebElement[] eurField = new WebElement[1];

        moneyFields.forEach(x->{
            if(x.getText().contains("USD")){
                usdField[0] = chromeDriver.findElement(By.xpath("//*/tr[@class='main-page-exchange__row main-page-exchange__row--with-border']//*[contains(text(),'USD')]"));
            } else if(x.getText().contains("EUR")){
                eurField[0] = chromeDriver.findElement(By.xpath("//*/tr[@class='main-page-exchange__row main-page-exchange__row--with-border']//*[contains(text(),'EUR')]"));
            }
        });

        List<WebElement> usd = usdField[0].findElements(By.xpath("//*/span[@class='main-page-exchange__rate']"));
        List<WebElement> eur = eurField[0].findElements(By.xpath("//*/span[@class='main-page-exchange__rate']"));
        double bankBuyUSD = Double.parseDouble(usd.get(0).getText().replace(",","."));
        double bankSellUSD = Double.parseDouble(usd.get(1).getText().replace(",","."));

        double bankBuyEUR = Double.parseDouble(eur.get(0).getText().replace(",","."));
        double bankSellEUR = Double.parseDouble(eur.get(1).getText().replace(",","."));
        Assertions.assertTrue(bankSellUSD > bankBuyUSD && bankSellEUR > bankBuyEUR);
    }

    @Test
    public void ThirdTest(){
        chromeDriver.get("https://www.google.com/");
        WebElement searchField = chromeDriver.findElement(By.name("q"));
        WebElement searchButton = chromeDriver.findElement(By.name("btnK"));
        searchField.click();
        searchField.sendKeys("Открытие");
        searchButton.click();

        List<WebElement> resultSearch = chromeDriver.findElements(By.xpath("//*[@class='g']//*[contains(text(),'https://www.open.ru')]"));

        Assertions.assertTrue(resultSearch.stream().anyMatch(x->x.getText().contains("https://www.open.ru")),
                "Статьи не найдены Открытие содержащие https://www.open.ru не найдены");

        chromeDriver.get("https://www.open.ru");
        List<WebElement> moneyFields = chromeDriver.findElements(By.xpath("//*[@class='main-page-exchange__table main-page-exchange__table--online']" +
                "//*/tr[@class='main-page-exchange__row main-page-exchange__row--with-border']"));
        final WebElement[] usdField = new WebElement[1];
        final WebElement[] eurField = new WebElement[1];

        moneyFields.forEach(x->{
            if(x.getText().contains("USD")){
                usdField[0] = chromeDriver.findElement(By.xpath("//*/tr[@class='main-page-exchange__row main-page-exchange__row--with-border']//*[contains(text(),'USD')]"));
            } else if(x.getText().contains("EUR")){
                eurField[0] = chromeDriver.findElement(By.xpath("//*/tr[@class='main-page-exchange__row main-page-exchange__row--with-border']//*[contains(text(),'EUR')]"));
            }
        });

        List<WebElement> usd = usdField[0].findElements(By.xpath("//*/span[@class='main-page-exchange__rate']"));
        List<WebElement> eur = eurField[0].findElements(By.xpath("//*/span[@class='main-page-exchange__rate']"));
        double bankBuyUSD = Double.parseDouble(usd.get(0).getText().replace(",","."));
        double bankSellUSD = Double.parseDouble(usd.get(1).getText().replace(",","."));

        double bankBuyEUR = Double.parseDouble(eur.get(0).getText().replace(",","."));
        double bankSellEUR = Double.parseDouble(eur.get(1).getText().replace(",","."));

        Assertions.assertTrue(bankSellUSD > bankBuyUSD && bankSellEUR > bankBuyEUR);
    }

    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка результатов поиска c помощью PO")
    @ParameterizedTest(name = "{displayName} {arguments}")
    @CsvSource({"Открытие,https://www.open.ru"})
    public void testPO(String keyWords,String result){
        chromeDriver.get("https://www.google.com/");
        GoogleBeforeSearch googleBeforeSearch = new GoogleBeforeSearch(chromeDriver);
        googleBeforeSearch.init();
        googleBeforeSearch.find(keyWords);
        GoogleAfterSearch googleAfterSearch = new GoogleAfterSearch(chromeDriver);
        Assertions.assertTrue(googleAfterSearch.getResultsSearch().stream().anyMatch(x->x.getText().contains(result)));
        Assertions.assertTrue(googleAfterSearch.checkExchanges());
    }

    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка результатов поиска c помощью PF")
    @ParameterizedTest(name = "{displayName} {arguments}")
    @CsvSource({"Открытие,https://www.open.ru"})
    public void testPF(String keyWords,String result){
        chromeDriver.get("https://www.google.com/");
        GooglePageFactory googlePageFactory = PageFactory.initElements(chromeDriver,GooglePageFactory.class);
        googlePageFactory.find(keyWords);
        Assertions.assertTrue(googlePageFactory.getAllElements().stream().anyMatch(x->x.getText().contains(result)));
    }
}

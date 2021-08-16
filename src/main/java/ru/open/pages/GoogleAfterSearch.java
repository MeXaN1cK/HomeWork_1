package ru.open.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GoogleAfterSearch extends GoogleBeforeSearch {

    public GoogleAfterSearch(WebDriver chromeDriver) {
        super(chromeDriver);
    }

    public List<WebElement> getResultsSearch() {
        List<WebElement> results = chromeDriver.findElements(By.xpath("//*[@class='g']//*[contains(text(),'https://www.open.ru')]"));
        return results;
    }

    public boolean checkExchanges(){
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

        return bankSellUSD > bankBuyUSD && bankSellEUR > bankBuyEUR;
    }
}

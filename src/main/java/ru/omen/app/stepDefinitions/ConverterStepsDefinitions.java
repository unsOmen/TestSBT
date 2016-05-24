package ru.omen.app.stepDefinitions;

import lib.Init;
import org.openqa.selenium.WebDriver;
import ru.omen.app.pages.CurrencyConverterPage;

/**
 * Created by OmeN on 21.05.2016.
 */
public class ConverterStepsDefinitions {

    private WebDriver driver;
    private CurrencyConverterPage page;
    private static String url;

    void openURL() {
        url = Init.getProperty("url.test2").toString();
        driver.get(url);
        System.out.println("Open URL = " + url);
    }

    void selectCurrencyRUB_EUR() {
        String currIN = "RUB";
        String currOUT = "EUR";
        page.selectCurrency(currIN, currOUT, 34);
        page.checkCurrencyFields(currIN, currOUT);
    }

    void selectCurrencyUSD_EUR() {
        String currIN = "USD";
        String currOUT = "EUR";
        page.selectCurrency(currIN, currOUT, 10023);
        page.checkCurrencyFields(currIN, currOUT);
    }

    void selectCurrenctyUSD_USD() {
        String currIN = "USD";
        String currOUT = "USD";
        page.selectCurrency(currIN, currOUT, 5);
        page.checkCurrencyFields("RUB", currOUT);
    }

    public void testPlan(WebDriver driver) {
        this.driver = driver;
        try {
            openURL(); // step 1
            page = new CurrencyConverterPage();

            page.checkCurrentDate(); // step 2
            page.checkElements(); // step 3
            selectCurrencyRUB_EUR(); // step 4
            page.convertCurrency(); // step 5
            selectCurrencyUSD_EUR(); // step 6
            page.convertCurrency(); // step 7
            selectCurrenctyUSD_USD(); // step 8
            page.convertCurrency(); // step 9

            System.out.println("Test complete!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

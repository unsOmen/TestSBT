package ru.omen.app.stepDefinitions;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import lib.Init;
import org.openqa.selenium.WebDriver;
import ru.omen.app.pages.CurrencyConverterPage;

import java.text.ParseException;

/**
 * Created by OmeN on 21.05.2016.
 */
public class ConverterStepsDefinitions {

    private WebDriver driver;
    private CurrencyConverterPage page;
    private static String url;

    @Before
    public void setUp() {
        Init.initProperties();
        url = Init.getProperty("url.test2").toString();
        this.driver = Init.getDriver();
    }

    @Given("^Открыть страницу, на ней размещен 'Конвертер валют'$")
    public void openURL() {
        driver.get(url);
        page = new CurrencyConverterPage(); // step 1
        System.out.println("Open URL = " + url);
    }

    @Given("^Проверить дату в блоке 'Конвертер валют'$")
    public void check_current_date_format() throws InterruptedException, ParseException {
        page.checkCurrentDate(); // step 2
    }

    @Given("^Проверить наличие компонентов в блоке «Конвертер валют»$")
    public void check_block_elements() {
        page.checkElements(); // step 3
    }

    @When("^Установить значения в конвертере: Поменять - \"([^\"]*)\" = \"([^\"]*)\", НА - \"([^\"]*)\"$")
    public void selectCurrency(String currIN, Integer valueIN, String currOUT) {
        page.selectCurrency(currIN, currOUT, valueIN); // step 4, 6
        page.checkCurrencyFields(currIN, currOUT);
    }

    @Given("^Проверить рассчитанное значение в поле «НА»$")
    public void convertCurrency() {
        page.convertCurrency(); // step 5, 7, 9
    }

    @When("^Установим значение в конвертере USD=5, USD. Система автоматически устанавливает в поле ПОМЕНЯТЬ значение RUB$")
    public void selectCurrenctyUSD_USD() {
        String currIN = "USD";
        String currOUT = "USD";
        page.selectCurrency(currIN, currOUT, 5); // step 8
        page.checkCurrencyFields("RUB", currOUT);
    }
}

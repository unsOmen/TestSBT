package ru.omen.app.pages;

import lib.Init;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by OmeN on 21.05.2016.
 */
public class CurrencyConverterPage extends AnyPages {


    private String xpathConverterText = "//div[@class='flipper']//span[contains(., 'Конвертер валют')]";

    @FindBy(xpath = "//span[contains(@class, 'currency-converter-date')]")
    private WebElement txtCurConverterDate;

    @FindBy(xpath = "//div[contains(@class, 'currency-converter-widget')]//label[contains(., 'Поменять')]")
    private WebElement txtConverterChange;

    @FindBy(xpath = "//div[contains(@class, 'currency-converter-widget')]//label[contains(., 'На')]")
    private WebElement txtConverterOn;

    /**
     * Поля для выбора валют
     * Элемент 0 - валюта "Поменять"
     * Элемент 1 - валюта "На"
     */
    @FindBy(xpath = "//a/span[contains(@class, 'select2-chosen')]")
    private List<WebElement> fieldsSelectCurrency;

    /**
     * Поля для ввода значений обмена
     * элемент 0 - количество валюты "Поменять"
     * элемент 1 - количество валюты "На"
     */
    @FindBy(xpath = "//div[contains(@class, 'currency-converter-widget')]//input[contains(@class, 'form_input_text')]")
    private List<WebElement> fieldsInputCurrency;

    /**
     *  Строка состоящая из span элементов, формирует строку формата 1 RUB = 0.0129 EUR, где
     *  элемент 0 - значение валюты "Поменять" (1)
     *  элемент 2 - текстовое название валюты "Поменять"(RUB)
     *  элемент 4 - значение валюты "На" (0.0129)
     *  элемент 6 - текстовое название валюты "На" (EUR)
     */
    @FindBy(xpath = "//div[contains(@class, 'currency-converter-result')]//span")
    private List<WebElement> txtConvertResult;

    public CurrencyConverterPage() {
        new WebDriverWait(Init.getDriver(), 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath(xpathConverterText)));

        System.out.println("url open_OK");
    }

    /**
     * Проверка формата даты отображающейся в форме
     * @throws ParseException
     */
    public void checkCurrentDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Assert.assertTrue("Формат даты не ДД ММММ ГГГГ",
                dateFormat.format(dateFormat.parse(txtCurConverterDate.getText())).equals(txtCurConverterDate.getText()));
        System.out.println("date correct_OK");
    }

    public void checkElements() {
        Assert.assertTrue("Надпись 'Поменять' отсутствует", txtConverterChange.isDisplayed());
        Assert.assertTrue("Надпись 'На' отсутствует", txtConverterOn.isDisplayed());

        Assert.assertEquals("Количество полей для выбора валют больше 2", fieldsSelectCurrency.size(), 2);
        for(WebElement element : fieldsSelectCurrency) {
            Assert.assertTrue("Поле выбора валют не отображается", element.isDisplayed());
        }

        Assert.assertEquals("Количество полей для ввода значений больше 2", fieldsInputCurrency.size(), 2);
        for(WebElement element : fieldsInputCurrency) {
            Assert.assertTrue("Поле для ввода значений не отображается", element.isDisplayed());
        }

        Assert.assertTrue("Неверный формат вывода строки " + txtConvertResult.get(0).getText(), txtConvertResult.get(0).getText().matches("1"));
        Assert.assertTrue("Неверный формат вывода строки " + txtConvertResult.get(2).getText(), txtConvertResult.get(2).getText().matches("[A-Z][A-Z][A-Z]"));
        Assert.assertTrue("Неверный формат вывода строки " + txtConvertResult.get(4).getText(), new Float(txtConvertResult.get(4).getText()) > 0);
        Assert.assertTrue("Неверный формат вывода строки " + txtConvertResult.get(6).getText(), txtConvertResult.get(6).getText().matches("[A-Z][A-Z][A-Z]"));

        System.out.println("all elements displayed_OK");
    }

    public void selectCurrency(String currencyIN, String currencyOUT, Integer countIN) {
        String xpathCurrIN = "//div[contains(@id, 'select2-drop')]//li//div[contains(.,'" + currencyIN +"')]";
        String xpathCurrOUT = "//div[contains(@id, 'select2-drop')]//li//div[contains(.,'" + currencyOUT +"')]";
        click(fieldsSelectCurrency.get(0));
        getElement(xpathCurrIN).click();
        click(fieldsSelectCurrency.get(1));
        getElement(xpathCurrOUT).click();

        setText(fieldsInputCurrency.get(0), countIN);
        Assert.assertTrue("Неверное значение 'Поменять'! Необходимо: " + countIN
                + ", текущее: " + fieldsInputCurrency.get(0).getAttribute("value"), fieldsInputCurrency.get(0).getAttribute("value").equals(countIN.toString()));

        System.out.println("currency selected_OK");
    }

    public void checkCurrencyFields(String currencyIN, String currencyOUT) {
        Assert.assertTrue("Выбрана неверная валюта: нужно: " + currencyIN
                +", выбрана: " + fieldsSelectCurrency.get(0).getText(), fieldsSelectCurrency.get(0).getText().trim().equals(currencyIN));
        Assert.assertTrue("Выбрана неверная валюта: нужно: " + currencyOUT
                +", выбрана: " + fieldsSelectCurrency.get(1).getText(), fieldsSelectCurrency.get(1).getText().trim().equals(currencyOUT));
    }

    public void convertCurrency() {
        BigDecimal countIN = new BigDecimal(fieldsInputCurrency.get(0).getAttribute("value").replaceAll(" ", "").trim());
        BigDecimal countOUT = new BigDecimal(fieldsInputCurrency.get(1).getAttribute("value").replaceAll(" ", "").trim());
        BigDecimal rate = new BigDecimal(txtConvertResult.get(4).getText());
        BigDecimal result = countIN.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);

        System.out.println(countIN+ "\n" + rate + "\n" + countOUT);
        try {
            Assert.assertTrue("Неверный результат: " + result + ", ", result.equals(countOUT));
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println("convert correct_OK");
    }
}

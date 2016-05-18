package ru.omen.app.stepDefinitions;

import lib.Init;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by OmeN on 18.05.2016.
 */
public class StepsDefinitions {

    private WebDriver driver;
    static String sumPath = ".//*[@id='views']/form/section/section/section[5]/div/dl[2]/dd[1]/span[1]"; // Сумма
    static String sportPath = "//div[span[text()='Спортивный']]"; // Спортивный
    static String pricePath = "//span[contains(@class,'b-form-sum-big-font-size') and @aria-hidden='false']"; // Текст с ценой

    float getFloatValue(String path) {
        WebElement element = driver.findElement(By.xpath(path));
        String value = element.getText();

        return new Float(value.replace(',', '.').replaceAll(" ", ""));
    }

    float getFloatValue(String path, int subChar) {
        WebElement element = driver.findElement(By.xpath(path));
        String value = element.getText().replace(',', '.');
        value = value.replaceAll(" ", "");
        value = value.substring(0, value.length()-subChar);

        return new Float(value);
    }

    void clickAndCheckSum(String pathClick, String pathAdd) throws InterruptedException {
        float sum = getFloatValue(sumPath);
        selectBlock(pathClick);
        float add = getFloatValue(pathAdd, 1);
        checkSum(sum+add, 0.5f);
    }

    // step 1
    public void openURL() {
        String str = "Страхование путешественников";

        driver.get(Init.getProperty("url.test1").toString());
        WebElement element = driver.findElement(By.className("l-header-title"));
        Assert.assertEquals(str, element.getText());
        System.out.println("openURL_OK");
    }

    // step 2
    public void checkFields() throws InterruptedException {
        Thread.sleep(2000);

        WebElement element = (new WebDriverWait(driver,10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("b-dropdown-title")));
        Assert.assertEquals(element.getText(), "Весь мир, кроме США и РФ");

        element = driver.findElement(By.name("duration"));
        Assert.assertEquals(element.getAttribute("value"), "15");

        element = driver.findElement(By.name("insuredCount60"));
        Assert.assertEquals(element.getAttribute("value"), "1");

        element = driver.findElement(By.name("insuredCount2"));
        Assert.assertEquals(element.getAttribute("value"), "0");

        element = driver.findElement(By.name("insuredCount70"));
        Assert.assertEquals(element.getAttribute("value"), "0");

        element = driver.findElement(By.xpath("//div[div[text()='Минимальная']]"));
        element.click();
        Assert.assertEquals(element.getAttribute("class").contains("b-form-active-box"), true);

        List<WebElement> elements = driver.findElements(By.className("b-form-dop-pack-box"));
        for(WebElement elm : elements) {
            if(elm.getAttribute("class").contains("b-form-active-box"))
                elm.click();

            Assert.assertEquals(elm.getAttribute("class").contains("b-form-active-box"), false);
        }

        System.out.println("checkFields_OK");
    }

    // step 3
    public void checkEnabled() {
        List<WebElement> elements = driver.findElements(By.xpath("//ul[contains(@class, 'nav')]//li"));
        for(WebElement elm : elements) {
            if(elm.getAttribute("class").contains("disabled")) {
                try {
                    elm.click();
                } catch (WebDriverException e) {
                    e.printStackTrace();
                } finally {
                    Assert.assertEquals(elm.getAttribute("class").contains("active"), false);
                }
            }
        }
        System.out.println("checkEnabled_OK");
    }

    public void checkSum(float need_sum, float difference) throws InterruptedException {
        Thread.sleep(10000);

        float sum = getFloatValue(sumPath);
        Assert.assertTrue("Большая разница суммы: sum = " + sum + " | need_sum = " + need_sum,
                Math.abs(sum - need_sum) <= difference);
        System.out.println("checkSum_OK");
    }

    public void selectBlock(String path) {
        // .//*[@id='views']/form/section/section/section[2]/div[1]/div[2]/div // Достаточная
        WebElement element = driver.findElement(By.xpath(path));
        element.click();
        Assert.assertEquals(element.getAttribute("class").contains("b-form-active-box"), true);
        System.out.println("selectBlock_OK");
    }

    // step 7
    void selectSportBlock() throws InterruptedException {
        String pathAdd = sportPath + pricePath;

        clickAndCheckSum(sportPath, pathAdd);
        System.out.println("selectSportBlock_OK");
    }

    // step 8
    public void checkTextValue() {
        String pathText1 = sportPath + "//span[contains(@class, 'b-form-box-title')]";
        String pathText2 = sportPath + "//ul//li";
        String pathText3 = sportPath + "//ul//li[2]";
        String pathText4 = sportPath + "//ul//li[3]";
        String pathText5 = sportPath + pricePath;

        WebElement element = driver.findElement(By.xpath(pathText1));
        Assert.assertEquals(element.getText(), "Спортивный");

        element = driver.findElement(By.xpath(pathText2));
        Assert.assertEquals(element.getText(), "Активные виды спорта");

        element = driver.findElement(By.xpath(pathText3));
        Assert.assertEquals(element.getText(), "Защита спортинвентаря");

        element = driver.findElement(By.xpath(pathText4));
        Assert.assertEquals(element.getText(), "Ski-pass / Лавина");

        element = driver.findElement(By.xpath(pathText5));
        Assert.assertTrue("Строка не содержит ~2 485,01", element.getText()
                .substring(0, element.getText().length()-1)
                .trim()
                .matches("^2 4\\d\\d,\\d\\d$"));

        System.out.println("checkTextValue_OK");
    }

    // step 9
    public void selectProvidentBlock() throws InterruptedException {
        String pathClick = "//div[span[text()='Предусмотрительный']]"; // Предусмотрительный
        String pathAdd = pathClick + pricePath; // Стоимость
        clickAndCheckSum(pathClick, pathAdd);
        System.out.println("selectProvidentBlock_OK");
    }

    // step 10
    public void selectProtectBag() throws InterruptedException {
        driver.findElement(By.xpath(sportPath)).click(); // Спортивный
        Thread.sleep(10000);
        String pathClick = "//div[span[text()='Защита багажа']]"; // Защита багажа
        String pathAdd = pathClick + pricePath; // Защита багажа.Стоимоость
        clickAndCheckSum(pathClick, pathAdd);
        System.out.println("selectProtectBag_OK");
    }

    public void testPlan(WebDriver driver) {
        this.driver = driver;
        try {
            openURL(); // step 1
            checkFields(); // step 2
            checkEnabled(); // step 3
            checkSum(850.26f, 50.0f); // step 4
            selectBlock("//div[div[text()='Достаточная']]"); // step 5
            checkSum(1145.02f, 50.0f); // step 6
            selectSportBlock(); // step 7
            checkTextValue(); // step 8
            selectProvidentBlock(); // step 9
            selectProtectBag(); // step 10

            System.out.println("Test complete!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

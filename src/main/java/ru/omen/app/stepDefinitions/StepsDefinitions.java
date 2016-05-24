package ru.omen.app.stepDefinitions;

import lib.Init;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import org.openqa.selenium.support.ui.WebDriverWait;
import ru.omen.app.pages.TravelInsurancePage;


/**
 * Created by OmeN on 18.05.2016.
 */
public class StepsDefinitions {

    private WebDriver driver;
    private TravelInsurancePage page;

    static final String PATH_SPORT_PACK = "//div[span[text()='Спортивный']]";
    static final String PATH_PRED_PACK = "//div[span[text()='Предусмотрительный']]";
    static final String PATH_BAG_PACK = "//div[span[text()='Защита багажа']]";
    static final String PATH_AVG_BLOCK = "//div[div[text()='Достаточная']]";

    static final String PATH_PRICE = "//span[contains(@class,'b-form-sum-big-font-size') and @aria-hidden='false']"; // Текст с ценой

    void openURL() {
        driver.get(Init.getProperty("url.test1").toString());
        System.out.println("openURL_OK");
    }

    void selectSportBlock() throws InterruptedException {
        page.selectDopPackAndCheckSumm(PATH_SPORT_PACK, PATH_PRICE);
        System.out.println("new selectSportBlock_OK");
    }

    void checkTextValue() {
        String pathText1 = PATH_SPORT_PACK + "//span[contains(@class, 'b-form-box-title')]";
        String pathText2 = PATH_SPORT_PACK + "//ul//li[1]";
        String pathText3 = PATH_SPORT_PACK + "//ul//li[2]";
        String pathText4 = PATH_SPORT_PACK + "//ul//li[3]";
        String pathText5 = PATH_SPORT_PACK + PATH_PRICE;

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

        System.out.println("new checkTextValue_OK");
    }

    void selectProvidentBlock() throws InterruptedException {
        page.selectDopPackAndCheckSumm(PATH_PRED_PACK, PATH_PRICE);
        System.out.println("new selectProvidentBlock_OK");
    }

    void selectProtectBag() throws InterruptedException {
        page.click(By.xpath(PATH_SPORT_PACK));
        page.selectDopPackAndCheckSumm(PATH_BAG_PACK, PATH_PRICE);
        System.out.println("new selectProtectBag_OK");
    }

    public void testPlan(WebDriver driver) {
        this.driver = driver;
        try {
            System.out.println("-------------STEP 1--------------");
            openURL(); // step 1

            page = new TravelInsurancePage();
            System.out.println("-------------STEP 2--------------");
            page.checkDefValues(); // step 2

            System.out.println("-------------STEP 3--------------");
            page.checkClickableTab(); // step 3

            System.out.println("-------------STEP 4--------------");
            page.checkSumm(850.26f, 50.0f); // step 4

            System.out.println("-------------STEP 5--------------");
            page.click(By.xpath(PATH_AVG_BLOCK)); // step 5

            System.out.println("-------------STEP 6--------------");
            page.checkSumm(1145.02f, 50.0f); // step 6

            System.out.println("-------------STEP 7--------------");
            selectSportBlock(); // step 7

            System.out.println("-------------STEP 8--------------");
            checkTextValue(); // step 8

            System.out.println("-------------STEP 9--------------");
            selectProvidentBlock(); // step 9

            System.out.println("-------------STEP 10--------------");
            //selectProtectBag(); // step 10

            System.out.println("Test complete!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

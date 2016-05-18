package ru.omen.app;

import junit.framework.Assert;
import lib.Init;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Function;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by OmeN on 14.05.2016.
 */
public class Test_1 {

    static WebDriver driver;
    static String pathSum = ".//*[@id='views']/form/section/section/section[5]/div/dl[2]/dd[1]/span[1]";

    @Test
    public void Test() throws Exception {
        testSetup(Init.getDriver());
    }

    @BeforeClass
    public static void beforeClassTest() {
        System.out.println("=============== START ===============");
    }

    @Before
    public void beforeTest() {
        System.out.println("SBT TEST !");
    }

    @After
    public void afterTest() {
        System.out.println(driver.getClass().getName() + " quit!");
        //driver.quit();
    }

    @AfterClass
    public static void afterClassTest() {
        System.out.println("================================================");
    }

    void setDriver(WebDriver driver) {
        this.driver = driver;
        System.out.println(this.driver.getClass().getName() + " start!");
    }

    float getFloatValue(String path) {
        WebElement element = driver.findElement(By.xpath(path));
        String value = element.getText();
        //System.out.println("value: " + value + ", path: " + path);
        return new Float(value.replace(',', '.').replaceAll(" ", ""));
    }

    float getFloatValue(String path, int subChar) {
        WebElement element = driver.findElement(By.xpath(path));
        String value = element.getText().replace(',', '.');
        value = value.replaceAll(" ", "");
        value = value.substring(0, value.length()-subChar);
        //System.out.println("value: " + value + ", path: " + path);
        return new Float(value);
    }

    void clickAndCheckSum(String pathClick, String pathSum1, String pathAdd) throws Exception {
        float sum = getFloatValue(pathSum1);
        step5(pathClick, false);
        float add = getFloatValue(pathAdd, 1);
        step4(sum+add, 0.5f, false);

        //System.out.println("\tsum = " + sum + "\n\tadd = " + add + "\n\tneed_sum = " + new Float(sum+add));
    }

    void step1() {
        String str = "Страхование путешественников";

        driver.get(Init.getProperty("url.test1"));
        WebElement element = driver.findElement(By.className("l-header-title"));
        Assert.assertEquals(str, element.getText());
        System.out.println("step1_OK");
    }

    void step2() throws Exception {
        Thread.sleep(2000);

        WebElement element = (new WebDriverWait(driver,10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("b-dropdown-title")));
        Assert.assertEquals(element.getText(), "Весь мир, кроме США и РФ");



        element = driver.findElement(By.name("duration"));
        Assert.assertEquals(element.getAttribute("value"), "15");

        element = driver.findElement(By.xpath("//input[@name='insuredCount60']"));
        Assert.assertEquals(element.getAttribute("value"), "1");

        element = driver.findElement(By.xpath("//input[@name='insuredCount2']"));
        Assert.assertEquals(element.getAttribute("value"), "0");

        element = driver.findElement(By.name("insuredCount70"));
        Assert.assertEquals(element.getAttribute("value"), "0");

        //.//*[@id='views']/form/section/section/section[2]/div[1]/div[1]/div // Минимальная
        //.//*[@id='views']/form/section/section/section[2]/div[1]/div[1]/div/div[1] // Текст.Минимальная
        String xPath = ".//*[@id='views']/form/section/section/section[2]/div[1]/div[1]/div/div[1]";
        element = driver.findElement(By.xpath(xPath));
        element.click();
        Assert.assertEquals(driver.findElement(By.xpath(xPath.substring(0, xPath.length()-7)))
                .getAttribute("class").contains("b-form-active-box"), true);


        List<WebElement> elements = driver.findElements(By.className("b-form-dop-pack-box"));
        for(WebElement elm : elements) {
            if(elm.getAttribute("class").contains("b-form-active-box"))
                elm.click();

            Assert.assertEquals(elm.getAttribute("class").contains("b-form-active-box"), false);
        }

        System.out.println("step2_OK");
    }

    void step3() {
        // html/body/div[1]/div[2]/div/ul/li[1]
        List<WebElement> elements = driver.findElements(By.xpath("html/body/div[1]/div[2]/div/ul/li"));
        for(WebElement elm : elements) {
            if(elm.getAttribute("class").contains("disabled")) {
                elm.click();
                Assert.assertEquals(elm.getAttribute("class").contains("active"), false);
            }
        }
        System.out.println("step3_OK");
    }

    void step4(float need_sum, float difference, boolean log) throws Exception {
        Thread.sleep(10000);

        float sum = getFloatValue(pathSum);
        Assert.assertTrue("Большая разница суммы: sum = " + sum + " | need_sum = " + need_sum,
                Math.abs(sum-need_sum) > difference ? false : true);
        if(log)
            System.out.println("step4_OK");
    }

    void step5(String path, boolean log) {
        // .//*[@id='views']/form/section/section/section[2]/div[1]/div[2]/div // Достаточная
        WebElement element = driver.findElement(By.xpath(path));
        element.click();
        Assert.assertEquals(element.getAttribute("class").contains("b-form-active-box"), true);
        if(log)
            System.out.println("step5_OK");
    }

    void step6() throws Exception {
        step4(1145.02f, 50.0f, false);
        System.out.println("step6_OK");
    }

    void step7() throws Exception {
        // .//*[@id='views']/form/section/section/section[3]/div/div[1]
        String pathClick = ".//*[@id='views']/form/section/section/section[3]/div/div[1]"; // Спортивный
        String pathAdd = ".//*[@id='views']/form/section/section/section[3]/div/div[1]/span[4]"; // Стоимость

        clickAndCheckSum(pathClick, pathSum, pathAdd);
        System.out.println("step7_OK");
    }

    void step8() {
        String pathText1 = ".//*[@id='views']/form/section/section/section[3]/div/div[1]/span[1]";
        String pathText2 = ".//*[@id='views']/form/section/section/section[3]/div/div[1]/ul/li[1]";
        String pathText3 = ".//*[@id='views']/form/section/section/section[3]/div/div[1]/ul/li[2]";
        String pathText4 = ".//*[@id='views']/form/section/section/section[3]/div/div[1]/ul/li[3]";
        String pathText5 = ".//*[@id='views']/form/section/section/section[3]/div/div[1]/span[4]";

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

        System.out.println("step8_OK");
    }

    void step9() throws Exception {
        String pathClick = "./*//*[@id='views']/form/section/section/section[3]/div/div[5]"; // Предусмотрительный
        String pathAdd = ".//*[@id='views']/form/section/section/section[3]/div/div[5]/span[3]"; // Стоимость

        clickAndCheckSum(pathClick, pathSum, pathAdd);
        System.out.println("step9_OK");
    }

    void step10() throws Exception {
        driver.findElement(By.xpath(".//*[@id='views']/form/section/section/section[3]/div/div[1]")).click(); // Спортивный
        Thread.sleep(10000);
        String pathClick = ".//*[@id='views']/form/section/section/section[3]/div/div[2]"; // Защита багажа
        String pathAdd = ".//*[@id='views']/form/section/section/section[3]/div/div[2]/span[3]"; // Защита багажа.Стоимоость
        clickAndCheckSum(pathClick, pathSum, pathAdd);
        System.out.println("step10_OK");
    }

    void testSetup(WebDriver driver) throws Exception {
        try {
            setDriver(driver);
            step1();
            step2();
            step3();
            step4(850.26f, 50.0f, true);
            step5(".//*[@id='views']/form/section/section/section[2]/div[1]/div[2]/div", true); // Достаточная
            step6();
            step7();
            step8();
            step9();
            step10();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

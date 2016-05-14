package ru.omen.app;

import junit.framework.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    WebDriver driver;
    static String url = "https://online.sberbankins.ru/store/vzr/index.html#/viewCalc";

    @Test
    public void Test() throws Exception {
        setDriver(new FirefoxDriver());
        //setDriver(new InternetExplorerDriver());
        step1();
        step2();
        step3();
        step4();
    }

    void setDriver(WebDriver driver) {
        this.driver = driver;
        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    void step1() {
        String str = "Страхование путешественников";
        String className = "l-header-title";

        driver.get(url);
        WebElement element = driver.findElement(By.className(className));
        /*WebElement element = (new WebDriverWait(driver,10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(className)));*/
        Assert.assertEquals(str, element.getText());
        System.out.println("step1_OK");
    }

    void step2() throws Exception {
        Thread.sleep(1500);

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

        //.//*[@id='views']/form/section/section/section[2]/div[1]/div[1]/div
        //.//*[@id='views']/form/section/section/section[2]/div[1]/div[1]/div/div[1]
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

    void step4() throws Exception {
        Thread.sleep(10000);
        // .//*[@id='views']/form/section/section/section[5]/div/dl[2]/dd[1]/span[1]
        WebElement element = driver.findElement(By.xpath(".//*[@id='views']/form/section/section/section[5]/div/dl[2]/dd[1]/span[1]"));
        float sum = new Float(element.getText().replace(',','.'));
        float need_sum = 850.26f;
        Assert.assertTrue("Большая разница суммы", Math.abs(sum-need_sum) > 50 ? false : true);
        System.out.println("step4_OK");
    }

    void step5() {

    }
}

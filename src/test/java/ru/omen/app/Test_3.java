package ru.omen.app;

import lib.Init;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.*;

/**
 * Created by OmeN on 19.05.2016.
 */
@RunWith(value = Parameterized.class)
public class Test_3 {

    private WebDriver driver;

    private String url;
    private String text;
    private String filedText;
    private String btnSearch;

    public Test_3(String url, String text, String field, String btn) {
        this.url = url;
        this.text = text;
        this.filedText = field;
        this.btnSearch = btn;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
                {"https://www.yandex.ru/", "yandex карты", "//input[@id='text']", "//button[span[text()='Найти']]"},
                {"https://www.google.ru/", "google карты", "//input[@id='lst-ib']", "//button[@class='lsb']"},
                {"https://www.bing.com/", "bing карты", "//input[@id='sb_form_q']", "//input[@id='sb_form_go']"}
        };

        return Arrays.asList(data);
    }

    @Before
    public void beforeTest() {
        Init.initProperties();
        driver = Init.getDriver();
    }

    @Test
    public void test() {
        driver.get(url);
        driver.findElement(By.xpath(filedText)).sendKeys(text);
        driver.findElement(By.xpath(btnSearch)).click();
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(.,'Википедия')]"))).click();
    }

    @After
    public void afterTest() {
        //driver.quit();
    }
}

package ru.omen.app;

import lib.Init;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by OmeN on 17.05.2016.
 */
public class Test_2 {

    private static WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("====================== START ======================");
    }

    @Before
    public void before() {
        driver = Init.getDriver();
        System.out.println(driver.getClass().getName() + " getDriver!");
    }

    @After
    public void after() {
        //driver.quit();
        System.out.println(driver.getClass().getName() + " quit!");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("====================== END ======================");
    }

    @Test
    public void scenario() throws InterruptedException {
        driver.get(Init.getProperty("url"));
        driver.findElement(By.xpath("//input[@id='text']")).sendKeys("selenium для чайников");
        driver.findElement(By.xpath("//span[@class='button__text' and text()='Найти']//..//../button")).click();
        (new WebDriverWait(driver, 10)).
                until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()[contains(.,'Википедия')]]"))).click();
        //driver.findElement(By.xpath("//*[text()[contains(.,'Википедия')]]")).click();
    }
}

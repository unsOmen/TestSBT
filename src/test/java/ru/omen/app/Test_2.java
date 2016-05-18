package ru.omen.app;

import lib.Init;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


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
        Init.initProperties();
        driver = Init.getDriver();
        System.out.println(driver.getClass().getName() + " getDriver!");
    }

    @After
    public void after() {
        //driver.quit();
        Init.clearProperties();
        System.out.println(driver.getClass().getName() + " quit!");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("====================== END ======================");
    }

    //@Test
    public void scenario() throws InterruptedException {
        driver.get(Init.getProperty("url.test2").toString());
        driver.findElement(By.xpath("//input[@id='text']")).sendKeys("selenium для чайников");
        driver.findElement(By.xpath("//button[span[text()='Найти']]")).click();
        (new WebDriverWait(driver, 10)).
                until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(.,'Википедия')]"))).click();
    }
}

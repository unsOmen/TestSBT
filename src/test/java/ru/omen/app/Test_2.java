package ru.omen.app;

import lib.Init;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.omen.app.stepDefinitions.ConverterStepsDefinitions;


/**
 * Created by OmeN on 17.05.2016.
 */

public class Test_2 {

    @BeforeClass
    public static void beforeClass() {
        System.out.println("====================== START ======================");
    }

    @Before
    public void beforeTest() {
        Init.initProperties();
    }

    //@Test
    public void test() throws InterruptedException {
        new ConverterStepsDefinitions().testPlan(Init.getDriver());
    }

    @After
    public void afterTest() {
        //driver.quit();
        System.out.println(Init.getDriver().getClass().getName() + " quit!");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("====================== END ======================");
    }
}

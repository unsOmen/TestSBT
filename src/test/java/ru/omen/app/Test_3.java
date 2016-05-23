package ru.omen.app;

import lib.Init;
import org.junit.*;
import ru.omen.app.stepDefinitions.SearchAtmStepsDefinitions;

/**
 * Created by OmeN on 19.05.2016.
 */

public class Test_3 {

    @BeforeClass
    public static void beforeClass() {
        System.out.println("====================== START ======================");
    }

    @Before
    public void beforeTest() {
        Init.initProperties();
    }

    @Test
    public void test() throws InterruptedException {
        new SearchAtmStepsDefinitions().testPlan(Init.getDriver());
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

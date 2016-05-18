package ru.omen.app;

import lib.Init;
import org.junit.*;
import ru.omen.app.stepDefinitions.StepsDefinitions;


/**
 * Created by OmeN on 14.05.2016.
 */
public class Test_1 {

    @Test
    public void Test() {
        new StepsDefinitions().testPlan(Init.getDriver());
    }

    @BeforeClass
    public static void beforeClassTest() {
        System.out.println("=============== START ===============");
    }

    @Before
    public void beforeTest() {
        Init.initProperties();
    }

    @After
    public void afterTest() {
        System.out.println(Init.getDriver().getClass().getName() + " quit!");
        //Init.getDriver().quit();
        Init.clearProperties();
    }

    @AfterClass
    public static void afterClassTest() {
        System.out.println("================================================");
    }

}

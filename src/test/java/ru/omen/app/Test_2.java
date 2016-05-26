package ru.omen.app;

import lib.Init;
import org.junit.*;


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

package ru.omen.app.stepDefinitions;

import lib.Init;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.omen.app.pages.SearchAtmPage;

/**
 * Created by OmeN on 23.05.2016.
 */
public class SearchAtmStepsDefinitions {

    private WebDriver driver;
    private SearchAtmPage page;
    private String url;

    private void openURL() {
        url = Init.getProperty("url.test3").toString();
        driver.get(url);
        System.out.println("Open URL = " + url);
    }

    public void testPlan(WebDriver driver) {
        this.driver = driver;
        try {
            openURL();
            page = new SearchAtmPage(); // step 1
            page.setCheckboxFilial(true); // step 2, 3
            page.checkLocations(); // step 4
            page.setCheckboxTerminal(true); // step 5
            page.checkLocations(); // step 6
            page.showMoreResult(); // step 7
            page.setCheckboxFilial(false); // step 8
            page.checkLocations(); // step 9

            System.out.println("Test complete!");
        } catch (InterruptedException e) {

        }
    }
}

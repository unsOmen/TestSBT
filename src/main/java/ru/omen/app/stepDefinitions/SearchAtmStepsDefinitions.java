package ru.omen.app.stepDefinitions;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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

    @Before
    public void setUp() {
        Init.initProperties();
        url = Init.getProperty("url.test3").toString();
        this.driver = Init.getDriver();
    }

    @Given("^Перейти по ссылке, на станице размещен текст «Отделения и банкоматы»$")
    public void openURL() {
        driver.get(url);
        page = new SearchAtmPage(); // step 1
        System.out.println("Open URL = " + url);
    }

    @When("^Выделить чекбокс «Отделения», Проверить информацию в блоке «Ближайшие к вам»$")
    public void set_checkbox_filial_and_check() throws InterruptedException {
        page.setCheckboxFilial(true); // step 2,3
    }

    @When("^Снять выделение чекбокса «Отделения»$")
    public void disable_checkbox_filial_and_check() throws InterruptedException {
        page.setCheckboxFilial(false);
    }

    @Then("^Проверить порядок расположения найденных ближайших локаций$")
    public void check_result_location() {
        page.checkLocations(); // step 4, 6
    }

    @When("Отметить чекбокс «Платежные устройства»")
    public void set_checkbox_terminal_and_check() throws InterruptedException {
        page.setCheckboxTerminal(true); // step 5
    }

    @When("Нажать на кнопку «Показать еще». Проверить порядок расположения найденных ближайших локаций")
    public void set_show_more_result() throws InterruptedException {
        page.showMoreResult();
    }
}

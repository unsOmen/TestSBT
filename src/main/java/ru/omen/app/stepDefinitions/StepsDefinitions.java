package ru.omen.app.stepDefinitions;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import lib.Init;
import org.openqa.selenium.*;
import ru.omen.app.pages.TravelInsurancePage;


/**
 * Created by OmeN on 18.05.2016.
 */
public class StepsDefinitions {

    private WebDriver driver;
    private TravelInsurancePage page;

    static final String PATH_AVG_BLOCK = "//div[div[text()='Достаточная']]";

    @Before
    public void setUp() {
        Init.initProperties();
        this.driver = Init.getDriver();
    }

    @Given("^Открыть страницу, нужный текст отображен$")
    public void openURL() {
        System.out.println("-------------STEP 1--------------");
        driver.get(Init.getProperty("url.test1").toString());
        System.out.println("openURL_OK");
        page = new TravelInsurancePage();
    }

    @Given("^Проверить значения по умолчанию$")
    public void check_def_values() throws InterruptedException {
        System.out.println("-------------STEP 2--------------");
        page.checkDefValues();
    }

    @Given("^Проверить доступность вкладов 'Оформление' и 'Подтверждение'$")
    public void check_clickable_tab() {
        System.out.println("-------------STEP 3--------------");
        page.checkClickableTab();
    }

    @When("^Итоговая стоимость должна быть примерно \"([^\"]*)\", допускается разность \"([^\"]*)\"$")
    public void check_sum(float sum, float diff) throws InterruptedException {
        System.out.println("-------------STEP 4--------------"); // 850.26f, 50.0f
        page.checkSumm(sum, diff);
    }

    @Given("^Выбрать блок 'Достаточная' в блоке 'Выберите сумму страховой защиты'$")
    public void select_block_avg() {
        System.out.println("-------------STEP 5--------------");
        page.click(By.xpath(PATH_AVG_BLOCK)); // step 5
    }

    @When("^Теперь 'Итоговая стоимость' должна быть примерно \"([^\"]*)\", допускается разность \"([^\"]*)\"$")
    public void check_sum_after_select_new_block(float sum, float diff) throws InterruptedException {
        System.out.println("-------------STEP 6--------------"); // 1145.02f, 50.0f
        page.checkSumm(sum, diff); // step 6
    }

    @Given("^В секции 'Рекомендуем предусмотреть' выбрать блок 'Спортивный' и проверить 'Итоговая стоимость'$")
    public void select_new_block() throws InterruptedException {
        System.out.println("-------------STEP 7--------------");
        page.selectSportBlock();
    }

    @Given("^Проверить текст значения «Спортивный» в блоке «Рекомендуем предусмотреть»$")
    public void check_text_values() {
        System.out.println("-------------STEP 8--------------");
        page.checkTextValue();
    }

    @Given("^Выбрать дополнительно «Предусмотрительный» и проверить значение «Итоговая стоимость»$")
    public void select_prov_block() throws InterruptedException {
        System.out.println("-------------STEP 9--------------");
        page.selectProvidentBlock();
    }

    @Given("^Выбрать дополнительно «Защита багажа», отключить значение «Спортивный» и проверить значение «Итоговая стоимость»$")
    public void select_protect_block() throws InterruptedException {
        System.out.println("-------------STEP 10--------------");
        page.selectProtectBag();
    }
    public void testPlan(WebDriver driver) {
        try {
            System.out.println("-------------STEP 1--------------");
            //openURL(); // step 1


            System.out.println("-------------STEP 2--------------");
            //page.checkDefValues(); // step 2

            System.out.println("-------------STEP 3--------------");
            //page.checkClickableTab(); // step 3

            System.out.println("-------------STEP 4--------------");
            //page.checkSumm(850.26f, 50.0f); // step 4

            System.out.println("-------------STEP 5--------------");
            //page.click(By.xpath(PATH_AVG_BLOCK)); // step 5

            System.out.println("-------------STEP 6--------------");
            //page.checkSumm(1145.02f, 50.0f); // step 6

            System.out.println("-------------STEP 7--------------");
            //page.selectSportBlock(); // step 7

            System.out.println("-------------STEP 8--------------");
            //page.checkTextValue(); // step 8

            System.out.println("-------------STEP 9--------------");
            //page.selectProvidentBlock(); // step 9

            System.out.println("-------------STEP 10--------------");
            //page.selectProtectBag(); // step 10

            System.out.println("Test complete!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void afterTest() {
        System.out.println("Test completed!");
    }
}

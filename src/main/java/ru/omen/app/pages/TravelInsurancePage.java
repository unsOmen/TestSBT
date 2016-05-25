package ru.omen.app.pages;

import lib.Init;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by OmeN on 20.05.2016.
 */
public class TravelInsurancePage extends AnyPages {

    @FindBy(xpath = "//span[contains(@class,'b-dropdown-title')]")
    private WebElement fieldRegion;

    @FindBy(xpath = "//input[contains(@class, 'b-checkbox-field-entity')]")
    private WebElement checkDate;

    @FindBy(name = "duration")
    private WebElement txtFieldDuration;

    @FindBy(name = "insuredCount60")
    private WebElement txtFieldInsuredCount60;

    @FindBy(name = "insuredCount2")
    private WebElement txtFieldInsuredCount2;

    @FindBy(name = "insuredCount70")
    private WebElement txtFieldInsuredCount70;

    @FindBy(xpath = "//div[div[text()='Минимальная']]")
    private WebElement blockMin;

    @FindBy(className = "b-form-dop-pack-box")
    private List<WebElement> listDopPack;

    @FindBy(xpath = "//ul[contains(@class, 'nav')]//li")
    private List<WebElement> tabsCheckClickable;

    @FindBy(xpath = "//dl[dt[contains(., 'Итоговая стоимость')]]//dd[1]//span[1]")
    private WebElement txtSumm;

    static final String PATH_SPORT_PACK = "//div[span[text()='Спортивный']]";
    static final String PATH_PRED_PACK = "//div[span[text()='Предусмотрительный']]";
    static final String PATH_BAG_PACK = "//div[span[text()='Защита багажа']]";

    static final String PATH_PRICE = "//span[contains(@class,'b-form-sum-big-font-size') and @aria-hidden='false']"; // Текст с ценой

    public TravelInsurancePage() {
        new WebDriverWait(Init.getDriver(), 30)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//h2[contains(.,'Страхование путешественников')]")));
    }

    public void checkDefValues() throws InterruptedException {
        Thread.sleep(2000);

        Assert.assertEquals(fieldRegion.getText(), "Весь мир, кроме США и РФ");
        Assert.assertFalse("Checkbox 'Хочу, чтобы полис действовал весь год' не false", checkDate.isSelected());
        Assert.assertEquals(txtFieldDuration.getAttribute("value"), "15");
        Assert.assertEquals(txtFieldInsuredCount60.getAttribute("value"), "1");
        Assert.assertEquals(txtFieldInsuredCount2.getAttribute("value"), "0");
        Assert.assertEquals(txtFieldInsuredCount70.getAttribute("value"), "0");

        click(blockMin);
        Assert.assertEquals(blockMin.getAttribute("class").contains("b-form-active-box"), true);

        for(WebElement elm : listDopPack) {
            if(elm.getAttribute("class").contains("b-form-active-box"))
                elm.click();

            Assert.assertEquals(elm.getAttribute("class").contains("b-form-active-box"), false);
        }

        System.out.println("new checkFields_OK");
    }

    public void checkClickableTab() {
        for(WebElement elm : tabsCheckClickable) {
            if(elm.getAttribute("class").contains("disabled")) {
                try {
                    elm.click();
                } catch (WebDriverException e) {
                    e.printStackTrace();
                } finally {
                    Assert.assertEquals(elm.getAttribute("class").contains("active"), false);
                }
            }
        }
        System.out.println("new checkClickableTab_OK");
    }

    /**
     * Сравнивает значение с значение в элементе txtSumm
     *
     * @param need_sum Сумма, с которой необходимо сравнить
     * @param difference Разница между значениями, которая допускается
     * @throws InterruptedException
     */
    public void checkSumm(float need_sum, float difference) throws InterruptedException {
        float sum = getSumm();
        Assert.assertTrue("Большая разница суммы: sum = " + sum + " | need_sum = " + need_sum,
                Math.abs(sum - need_sum) <= difference);
        System.out.println("new checkSum_OK");
    }

    public float getSumm() throws InterruptedException {
        Thread.sleep(10000);
        return getFloatValue(txtSumm, 1, ',');
    }

    /**
     * Выбрать дополнительный пункт в секции "Рекомендуется предусмотреть" и проверить сумму
     * до выбора пункта, и после
     *
     * @param pathPack путь пункта, который необходимо выбрать
     * @param price путь до текстого значения, которое будет добавлено к пред. значению суммы
     * @throws InterruptedException
     */
    public void selectDopPackAndCheckSumm(String pathPack, String price) throws InterruptedException {
        String pathAdd = pathPack + price;
        float add = getFloatValue(getElement(pathAdd), 1, ',');
        float sum = getSumm();
        click(By.xpath(pathPack));
        checkSumm(add+sum, 0.5f);
        System.out.println("\tsum before = " + sum + "\t\n" + "\tadd = " + add + "\t\n" + "\tsum after = " + getSumm());
    }

    public void checkTextValue() {
        String pathText1 = PATH_SPORT_PACK + "//span[contains(@class, 'b-form-box-title')]";
        String pathText2 = PATH_SPORT_PACK + "//ul//li[1]";
        String pathText3 = PATH_SPORT_PACK + "//ul//li[2]";
        String pathText4 = PATH_SPORT_PACK + "//ul//li[3]";
        String pathText5 = PATH_SPORT_PACK + PATH_PRICE;

        WebElement element = Init.getDriver().findElement(By.xpath(pathText1));
        Assert.assertEquals(element.getText(), "Спортивный");

        element = Init.getDriver().findElement(By.xpath(pathText2));
        Assert.assertEquals(element.getText(), "Активные виды спорта");

        element = Init.getDriver().findElement(By.xpath(pathText3));
        Assert.assertEquals(element.getText(), "Защита спортинвентаря");

        element = Init.getDriver().findElement(By.xpath(pathText4));
        Assert.assertEquals(element.getText(), "Ski-pass / Лавина");

        element = Init.getDriver().findElement(By.xpath(pathText5));
        Assert.assertTrue("Строка не содержит ~2 485,01", element.getText()
                .substring(0, element.getText().length()-1)
                .trim()
                .matches("^2 4\\d\\d,\\d\\d$"));

        System.out.println("new checkTextValue_OK");
    }

    public void selectSportBlock() throws InterruptedException {
        selectDopPackAndCheckSumm(PATH_SPORT_PACK, PATH_PRICE);
        System.out.println("new selectSportBlock_OK");
    }

    public void selectProvidentBlock() throws InterruptedException {
        selectDopPackAndCheckSumm(PATH_PRED_PACK, PATH_PRICE);
        System.out.println("new selectProvidentBlock_OK");
    }

    public void selectProtectBag() throws InterruptedException {
        click(By.xpath(PATH_SPORT_PACK));
        selectDopPackAndCheckSumm(PATH_BAG_PACK, PATH_PRICE);
        System.out.println("new selectProtectBag_OK");
    }
}

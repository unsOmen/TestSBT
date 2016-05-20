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
}

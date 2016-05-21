package ru.omen.app.pages;

import lib.Init;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by OmeN on 20.05.2016.
 */
public abstract class AnyPages {

    public AnyPages() {
        PageFactory.initElements(Init.getDriver(), this);
        waitPageToLoad();
    }

    private void waitPageToLoad() {
        new WebDriverWait(Init.getDriver(), 30).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor)wd)
                .executeScript("return document.readyState").equals("complete"));
        System.out.println("Page Is loaded!");
    }

    public void click(WebElement element) {
        new WebDriverWait(Init.getDriver(), 10).until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        System.out.println("\t" + element.getTagName() + " click!");
    }

    public void click(By by) {
        click(Init.getDriver().findElement(by));
    }

    /**
     * Метод для получения числа типа float из элемента, который содержит в text() текст числа.
     *
     * @param element Элемент из которого необходимо получить число
     * @param subCountChar Количество символов, которые необходимо обрезать с конца строки
     * @param replaceChar Символ, который необходимо заменить на "." (точку) для того чтобы получить число
     * @return Значение типо float
     */
    public float getFloatValue(WebElement element, int subCountChar, char replaceChar) {
        String value = element.getText().trim().replace(replaceChar, '.');
        value = value.replaceAll(" ", "");
        value = value.substring(0, value.length()-subCountChar);

        //System.out.println("\tgetValue " + element.getText() + " = " + value);
        return new Float(value);
    }

    public float getFloatValue(WebElement element, boolean attribute) {
        String value = "";
        if(attribute)
            value = element.getAttribute("value").trim();
        else
            value = element.getText();
        value = value.replaceAll(" ", "");
        //System.out.println("\tgetValue " + element.getText() + " = " + value);
        return new Float(value);
    }

    public WebElement getElement(By by) {
        return new WebDriverWait(Init.getDriver(), 10).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement getElement(String xpath) {
        return getElement(By.xpath(xpath));
    }

    public void setText(WebElement element, Object text) {
        element.clear();
        element.sendKeys(text.toString());
    }

}

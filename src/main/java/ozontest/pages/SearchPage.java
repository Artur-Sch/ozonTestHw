package ozontest.pages;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ozontest.annotation.ElementTitle;
import ozontest.annotation.PageTitle;
import ozontest.core.Init;

import java.util.List;

import static ozontest.core.Init.getDriver;

@PageTitle("Поиск")
public class SearchPage extends BasePage {

    @ElementTitle("Результаты поиска")
    @FindBy(xpath = "//div[@data-index]")
    public List<WebElement> listItems;

    @ElementTitle("Цена до")
    @FindBy(xpath = "//input[@data-test-id='range-filter-to-input']")
    private WebElement maxPriceField;

    @ElementTitle("Производитель")
    @FindBy(xpath = "//div[@class='search-show-container']")
    private List<WebElement> listBrends;

    @ElementTitle("Корзина")
    @FindBy(xpath = "//*[contains(text(),'Корзина')]")
    private WebElement cartButton;

    @ElementTitle("Посмотреть все")
    @FindBy(xpath = "//span[@data-test-id='filter-block-brand-show-all']")
    private WebElement showAll;

    @ElementTitle("В корзину")
    @FindBy(xpath = "//div[@data-index]//span[contains(text(),'В корзину')]")
    private WebElement inCart;

    @FindBy(xpath = "//*[@class='search-form']//input")
    private WebElement searchBrendField;

    @ElementTitle("Фильтр цены")
    @FindBy(xpath = "//div[@class='active-filters']//span[text()='Цена']")
    private WebElement priceFilter;

    @Override
    public void addToCart(boolean even) {
        int count = 0;
        for (WebElement listItem : listItems) {
            if (even && Integer.parseInt(listItem.getAttribute("data-index")) % 2 == 0) {
                count = setElement(count, listItem);
            } else if (!even && Integer.parseInt(listItem.getAttribute("data-index")) % 2 != 0) {
                count = setElement(count, listItem);
            }
            if (count == 8) {
                return;
            }
        }
    }

    @Step("Добавлеие элемента {nameProduct}")
    private int setElement(int count, WebElement listItem) {
        String nameProduct = listItem.findElement(By.xpath(".//a[@id='name']")).getText();
        String priceProduct = listItem.findElement(By.xpath(".//span[contains(@class, 'total-price')]")).getText().replaceAll("\\D", "");
        Init.setVariables(nameProduct, priceProduct);
        waitForReadyAndClickJSElement(listItem.findElement(By.xpath(".//span[contains(text(),'В корзину')]")));
        count++;
        return count;
    }

    @Step("Выбор бренда {0}")
    @Override
    public void selectBrand(String nameField) {
        Actions builder = new Actions(getDriver());
        builder.moveToElement(searchBrendField).click().sendKeys(nameField).perform();
        new WebDriverWait(getDriver(), 10).until(ExpectedConditions.textToBePresentInElementValue(searchBrendField, nameField));
        waitForReadyAndClickJSElement( listBrends.get(0).findElement(By.xpath(".//*[@class='checkbox-label']")));
        WebElement element = driver.findElement(By.xpath("//div[@class='active-filters']//span[text()='"+nameField+"']"));
        webDriverWait.until((ExpectedCondition<Boolean>) driver -> element.getText().equals(nameField));
    }
}


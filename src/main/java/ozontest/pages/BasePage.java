package ozontest.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ozontest.annotation.ElementTitle;
import ozontest.core.Init;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait webDriverWait;

    public BasePage() {
        this.driver = Init.getDriver();
        webDriverWait = Init.getWebDriverWait();
        PageFactory.initElements(Init.getDriver(), this);
    }

    @Step("Получение итоговой суммы заказа")
    public String getTotalAmount(String nameField) {
        return getElementByTitle(nameField).getText().replaceAll(" ", "");
    }


    @Step("Открытие меню {0} и нажатие на кнопку {1}")
    public void clickLogout(String nameFieldProfile, String buttonField) {
        getElementByTitle(nameFieldProfile).click();
        Actions builder = new Actions(driver);
        builder.moveToElement(getElementByTitle(nameFieldProfile)).click(getElementByTitle(buttonField));
        Action mouseoverAndClick = builder.build();
        mouseoverAndClick.perform();
    }


    @Step("Ожидание загрузки элемента - {0}")
    public WebElement waitForVisibilityElement(WebElement element) {
        return webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    @Step("Ожидание загрузки элемента - {0}")
    public WebElement waitForReadyElement(WebElement element) {
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    @Step("проверка видимости элемента {0}")
    public boolean isElementPresent(WebElement element) {
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }

    @Step("Прокрутка экрна")
    public void scroll() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("javascript:window.scrollBy(250,350)");
    }

    @Step("Ожидание готовности элемента {0} и клик через js")
    public void waitForReadyAndClickJSElement(WebElement element) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    @Step("Заполнение поля {0} - значением {1}")
    public void fillField(String name, String value) {
        WebElement element = getElementByTitle(name);
        element.clear();
        element.sendKeys(value);
    }

    @Step("Поиск элемента по аннотации - {0}")
    public WebElement getElementByTitle(String title) {
        Field field = Arrays.stream(this.getClass().getDeclaredFields())
                .filter(f -> f.getType().equals(WebElement.class))
                .filter(f -> f.getAnnotation(ElementTitle.class) != null)
                .filter(f -> f.getAnnotation(ElementTitle.class).value().equalsIgnoreCase(title))
                .findFirst().orElseThrow(() -> new RuntimeException("Не найден элемент с названием " + title));
        Assert.assertEquals(field.getType(), WebElement.class);
        field.setAccessible(true);
        WebElement element = null;
        try {
            element = (WebElement) field.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return waitForReadyElement(element);
    }

    @Step("Добавить товар в карзину")
    public void addToCart(boolean even) {
    }

    @Step("Выбор бренда - {0}")
    public void selectBrand(String nameField) {
    }

    @Step("Проверка корзины и добавленного товара {0}")
    public boolean productExist(String productName) {
        return true;
    }

}

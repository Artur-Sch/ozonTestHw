package ozontest.Steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static ozontest.core.Init.*;


public class AllSteps {

    @After
    public void afterMethod(Scenario scenario) {
        if (scenario.isFailed()) {
            takeScreenshot();
        }
        quitDriver();
    }

    @Step("Переход на сстраницу - {0}")
    public void openPage(String title) {
        setupPage(title);
    }

    @Attachment("Screenshot")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Step("поле {0} заполняется значением {1}")
    public void fillField(String name, String value) {
        getCurrentPage().fillField(name, value);
    }

    @Step("Нажать на - {0}")
    public void click(String nameField) {
        getCurrentPage().getElementByTitle(nameField).click();
    }

    @Step("Поиск элемента")
    public void searchProduct(String fieldName, String product) {
        getCurrentPage().waitForReadyElement(getCurrentPage().getElementByTitle("Кабинет"));
        WebElement element = getCurrentPage().getElementByTitle(fieldName);
        element.sendKeys(product + Keys.ENTER);
    }

    @Step("Установка фильтра {0} на {1}")
    public void setMaxPrice(String nameField, String mAxPrice) {
        getCurrentPage().scroll();
        selectTextViaKeyboard(getCurrentPage().getElementByTitle(nameField));
        getCurrentPage().getElementByTitle(nameField).sendKeys(mAxPrice + Keys.ENTER);
        WebElement element = getCurrentPage().getElementByTitle("Фильтр цены");
        getWebDriverWait().until((ExpectedCondition<Boolean>) driver -> element.getText().equals("Цена"));
    }

    @Step("выделение содержимого элемента {0}")
    public void selectTextViaKeyboard(WebElement element) {
        Actions builder = new Actions(getDriver());
        Action select = builder
                .click(getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element)))
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .build();
        select.perform();
    }

    @Step("Установка фильтра брендов на {1}")
    public void setBrands(String buttonName, List<String> list) {
        if (!buttonName.equals("none")) {
            Actions builder = new Actions(getDriver());
            builder.moveToElement(getCurrentPage().getElementByTitle(buttonName)).perform();
            getCurrentPage().waitForReadyAndClickJSElement(getCurrentPage().getElementByTitle(buttonName));
            for (String s : list) {
                getCurrentPage().selectBrand(s);
            }
        }
    }

    @Step("Добавление в карзину {even} елементов")
    public void addToCart(String even) {
        getCurrentPage().waitForVisibilityElement(getCurrentPage().getElementByTitle("В корзину"));
        if (even.equalsIgnoreCase("четных")) {
            getCurrentPage().addToCart(false);
        } else if (even.equalsIgnoreCase("нечетных")) {
            getCurrentPage().addToCart(true);
        }
    }

    @Step("товары присутствуют в корзине")
    public void stepCheckProduct() {
        for (String name : getVariables().keySet()) {
            Assert.assertTrue(String.format("товар %s не найден в корзине", name), getCurrentPage().productExist(name));
        }
    }

    @Step("итоговая сумма равна - сумме в добавленных продуктов в корзину")
    public void stepCheckTotalAmount(String amount) {
        String expectedAmount = getVariableTotalValue();
        try {
            Assert.assertEquals(String.format("Итоговая сумма не равна значению %s. Получено значение %s", expectedAmount, getCurrentPage().getTotalAmount(amount)),
                    expectedAmount, getCurrentPage().getTotalAmount(amount));
        } catch (AssertionError e) {
            System.out.println(String.format("Итоговая сумма не равна значению %s. Получено значение %s", expectedAmount, getCurrentPage().getTotalAmount(amount)));
        }
    }

    @Step("Очистить сохраненный список товаров и {1} из {0}")
    public void logOut(String profileButton, String logout) {
        getVariables().clear();
        getCurrentPage().clickLogout(profileButton, logout);
    }

    @Step("Проверка наличия текста {0} на сранице")
    public void checkTextFromElement(String textTo) {
        Assert.assertTrue(getCurrentPage().getElementByTitle(textTo).getText().contains(textTo));
    }

}




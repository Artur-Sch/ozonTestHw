package ozontest.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ozontest.annotation.ElementTitle;
import ozontest.annotation.PageTitle;

import java.util.List;


@PageTitle("Корзина")
public class CartPage extends BasePage {

    @ElementTitle("Товары")
    @FindBy(xpath = "//a[@class='title']")
    public List<WebElement> cartItems;

    @ElementTitle("Итоговая сумма")
    @FindBy(xpath = "//div[contains(@data-test-id,'total-price-block')]//span[contains(@class,'main')]")
    private WebElement totalAmount;

    @ElementTitle("Удалить выбранные")
    @FindBy(xpath = "//*[contains(text(),'Удалить выбранные')]")
    private WebElement clearCartButton;

    @ElementTitle("Удалить")
    @FindBy(xpath = "//button[@class='button button blue']")
    private WebElement confirmDelete;

    @ElementTitle("Кабинет")
    @FindBy(xpath = "//span[contains(text(),'Кабинет')]")
    private WebElement profile;

    @ElementTitle("Выйти")
    @FindBy(xpath = "//*[contains(text(),'Выйти')]")
    private WebElement logOutButton;

    @ElementTitle("Корзина пуста")
    @FindBy(xpath = "//h1")
    private WebElement cartTitle;

    @ElementTitle("Войти")
    @FindBy(xpath = "//span[contains(text(),'Войти')]")
    private WebElement SignIn;

    @ElementTitle("Войти по почте")
    @FindBy(xpath = "//a[contains(text(),'Войти')]")
    private WebElement EnterByEmail;

    @ElementTitle("Вoйти")
    @FindBy(xpath = "//button[contains(text(),'Войти')]")
    private WebElement SignInForm;

    @ElementTitle("Логин")
    @FindBy(xpath = "//input[@data-test-id='emailField']")
    private WebElement emailField;

    @ElementTitle("Пароль")
    @FindBy(xpath = "//input[@data-test-id='passwordField']")
    private WebElement passwordField;

    @ElementTitle("Поле поиска")
    @FindBy(xpath = "//input[@class='search-input']")
    private WebElement searchField;

    @Step("Проверить наличие товара {0}")
    @Override
    public boolean productExist(String productName) {
        for (WebElement item : cartItems) {
            if (isElementPresent(item) && item.getText().contains(productName)) {
                return true;
            }
        }
        return false;
    }


}

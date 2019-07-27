package ozontest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ozontest.annotation.ElementTitle;
import ozontest.annotation.PageTitle;


@PageTitle("Главная")
public class OzonMainPage extends BasePage {

    @ElementTitle("Войти")
    @FindBy(xpath = "//span[contains(text(),'Войти')]")
    private WebElement SignIn;

    @ElementTitle("Войти по почте")
    @FindBy(xpath = "//a[contains(text(),'Войти')]")
    private WebElement EnterByEmail;

    @ElementTitle("Кабинет")
    @FindBy(xpath = "//span[contains(text(),'Кабинет')]")
    private WebElement profile;

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

}

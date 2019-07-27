package ozontest.Steps;


import cucumber.api.java.en.When;
import cucumber.api.java.ru.Когда;
import ozontest.core.Init;

import java.util.List;
import java.util.Map;

public class OzonScenario {

    AllSteps steps;

    @When("Открыть сайт ozon.ru")
    public void start() {
        Init.setUp();
        steps = new AllSteps();
    }

    @When("На странице - \"(.+)\" нажать \"(.+)\"")
    public void clickLogIn(String page, String buttonName) {
        steps.openPage(page);
        steps.click(buttonName);
    }

    @When("Нажать \"(.+)\", ввести данные и нажать \"(.+)\"")
    public void logIn(String buttonName, String name, Map<String, String> fields) {
        steps.click(buttonName);
        fields.forEach((k, v) -> steps.fillField(k, v));
        steps.click(name);

    }

    @When("В \"(.+)\" ввести \"(.+)\"$")
    public void search(String nameField, String text){
        steps.searchProduct(nameField, text);
    }

    @When("На странице - \"(.+)\" в поле \"(.+)\" ввести \"(.+)\"$")
    public void setMaxPrice(String page, String nameField, String maxPrice){
        steps.openPage(page);
        steps.setMaxPrice(nameField, maxPrice);
    }

    @When("Нажать \"(.+)\" бренды и выбрать")
    public void selectBrands(String buttonName, List<String> list){
       steps.setBrands(buttonName,list);
    }

    @When("Из результатов поиска добавить первые 8 \"(.+)\" товаров в корзину$")
    public void addToCart(String even) {
        steps.addToCart(even);
    }

    @When("Перейти на страницу - \"(.+)\"")
    public void openCart(String page){
        steps.click(page);
        steps.openPage(page);
    }

    @When("Проверить, что все добавленные ранее товары находятся в корзине")
    public void checkCart(){
        steps.stepCheckProduct();
    }

    @When("Проверить, что \"(.+)\" равна сумме цен добавленных товаров")
    public void checkAmount(String amount) {
        steps.stepCheckTotalAmount(amount);
    }

    @When("Нажать на \"(.+)\" товары и подтвердить нажав на кнопку \"(.+)\"")
    public void clearCart(String deleteButton, String submit) {
        steps.click(deleteButton);
        steps.click(submit);
    }

    @Когда("Нажать на \"(.+)\" и \"(.+)\"")
    public void logOut(String profile, String logout){
        steps.logOut(profile, logout);
    }

    @Когда("Проверить, что \"(.+)\" и не содержит никаких товаров")
    public void checkTitle(String text) {
        steps.checkTextFromElement(text);
    }

}

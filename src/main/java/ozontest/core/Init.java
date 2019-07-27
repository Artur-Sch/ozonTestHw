package ozontest.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.reflections.Reflections;
import ozontest.annotation.PageTitle;
import ozontest.pages.BasePage;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Init {
    private static WebDriver driver;
    private static WebDriverWait webDriverWait;
    private static Properties properties = TestProperties.getInstance().getProperties();
    private static HashMap<String, String> variables = new HashMap<>();
    private static BasePage currentPage;
    private static Set<Class<? extends BasePage>> allpages;

    static {
        Reflections reflections = new Reflections("ozontest");
        allpages = reflections.getSubTypesOf(BasePage.class);
    }

    public static void setUp() {
        switch (properties.getProperty("browser")) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.gecko.driver"));
                driver = new FirefoxDriver();
                break;
            default:
                System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
                driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.get(properties.getProperty("api.url"));
        webDriverWait = new WebDriverWait(driver, 10, 1000);
    }

    public static WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static String getVariableTotalValue() {
        int amount = 0;
        for (String value : variables.values()) {
            amount += Integer.parseInt(value);
        }
        return String.valueOf(amount);
    }

    public static void setVariables(String key, String value) {
        variables.put(key, value);
    }

    public static HashMap<String, String> getVariables() {
        return variables;
    }

    public static void setupPage(String title) {
        currentPage = findPageByTitle(title);
    }

    private static BasePage findPageByTitle(String title) {
        Class<? extends BasePage> pageClass = allpages.stream()
                .filter(page -> page.getAnnotation(PageTitle.class) != null)
                .filter(page -> page.getAnnotation(PageTitle.class).value().equals(title))
                .findAny().orElseThrow(() -> new RuntimeException("Не найдена страница с названием: " + title));
        BasePage foundPage = null;
        try {
            foundPage = pageClass.getConstructor().newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return foundPage;
    }

    public static BasePage getCurrentPage() {
        return currentPage;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

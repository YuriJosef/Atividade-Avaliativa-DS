import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Questao03Test {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage",
                "--window-size=1920,1080");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void deveExecutarLoginCarrinhoERemocao() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.cssSelector("[data-test='username']")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("[data-test='password']")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("[data-test='login-button']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='inventory-container']")));
        assertTrue(driver.findElement(By.cssSelector("[data-test='title']")).getText().contains("Products"),
            "A página de produtos deve ser exibida após o login");

        String[] productIds = {"sauce-labs-backpack", "sauce-labs-bike-light", "sauce-labs-bolt-t-shirt"};
        for (int index = 0; index < productIds.length; index++) {
            driver.findElement(By.cssSelector("[data-test='add-to-cart-" + productIds[index] + "']")).click();
            int expectedCount = index + 1;
            wait.until(ExpectedConditions.textToBe(By.cssSelector("[data-test='shopping-cart-badge']"),
                String.valueOf(expectedCount)));
        }

        assertEquals("3", driver.findElement(By.cssSelector("[data-test='shopping-cart-badge']")).getText());
        driver.findElement(By.cssSelector("[data-test='remove-sauce-labs-backpack']")).click();
        wait.until(ExpectedConditions.textToBe(By.cssSelector("[data-test='shopping-cart-badge']"), "2"));

        driver.findElement(By.cssSelector("[data-test='shopping-cart-link']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='cart-list']")));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("[data-test='inventory-item']"), 2));
        assertEquals(2, driver.findElements(By.cssSelector("[data-test='inventory-item']")).size());
        assertEquals("2", driver.findElement(By.cssSelector("[data-test='shopping-cart-badge']")).getText());
    }
}

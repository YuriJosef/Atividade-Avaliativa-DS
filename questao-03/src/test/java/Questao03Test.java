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
import java.util.List;

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
        driver.findElement(By.cssSelector("[testId='username']")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("[testId='password']")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("[testId='login-button']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='shopping-cart-link']")));
        List<WebElement> addToCartButtons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//button[normalize-space()='Add to cart']")));
        assertTrue(addToCartButtons.size() >= 3);
        addToCartButtons.get(0).click();
        addToCartButtons.get(1).click();
        addToCartButtons.get(2).click();

        driver.findElement(By.cssSelector("[data-test='shopping-cart-link']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='shopping-cart-badge']")));
        assertEquals("3", driver.findElement(By.cssSelector("[data-test='shopping-cart-badge']")).getText());

        List<WebElement> removeButtons = driver.findElements(By.cssSelector("[testId^='remove-']"));
        assertTrue(removeButtons.size() >= 1);
        removeButtons.get(0).click();

        WebElement badge = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='shopping-cart-badge']")));
        assertEquals("2", badge.getText());
    }
}

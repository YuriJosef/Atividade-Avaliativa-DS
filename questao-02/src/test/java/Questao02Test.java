import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Questao02Test {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage",
                "--window-size=1920,1080");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void deveAguardarCarregamentoDinamicoTerminar() {
        boolean jqueryLoaded = false;
        for (int attempt = 0; attempt < 3 && !jqueryLoaded; attempt++) {
            driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5)).until(currentDriver ->
                    Boolean.TRUE.equals(((JavascriptExecutor) currentDriver)
                        .executeScript("return typeof window.jQuery === 'function'")));
                jqueryLoaded = true;
            } catch (TimeoutException ignored) {
                // Retry if the page's required script did not load.
            }
        }
        assertTrue(jqueryLoaded, "A biblioteca jQuery do site não carregou após três tentativas");

        WebElement startButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[normalize-space()='Start']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", startButton);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loading")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));

        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        assertTrue(result.isDisplayed(), "O resultado deve estar visível");
        assertTrue(result.getText().contains("Hello World!"), "O resultado deve conter Hello World!");
    }
}

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

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

class Questao05Test {
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
    void deveMapearEFiltrarProdutosAbaixoDeVinteDollars() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.cssSelector("[testId='username']")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("[testId='password']")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("[testId='login-button']")).click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[data-test='inventory-item-name']")));
        List<WebElement> productNames = driver.findElements(By.cssSelector("[data-test='inventory-item-name']"));
        List<WebElement> productPrices = driver.findElements(By.cssSelector("[data-test='inventory-item-price']"));
        Set<String> uniqueProducts = new LinkedHashSet<>();
        BigDecimal threshold = new BigDecimal("20.00");

        for (int index = 0; index < productNames.size(); index++) {
            String name = productNames.get(index).getText().trim();
            BigDecimal price = new BigDecimal(productPrices.get(index).getText()
                    .replaceAll("[^0-9.-]", ""));
            if (price.compareTo(threshold) < 0) {
                uniqueProducts.add(name + " - $" + price);
            }
        }

        if (uniqueProducts.isEmpty()) {
            System.out.println("Nenhum produto abaixo de $20.00 foi encontrado.");
        } else {
            uniqueProducts.forEach(System.out::println);
        }
    }
}

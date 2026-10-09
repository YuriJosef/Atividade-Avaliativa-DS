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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Questao04Test {
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
    void deveIdentificarLaptopMaisCaro() {
        driver.get("https://www.demoblaze.com/");
        List<WebElement> initialCards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.cssSelector("#tbodyid .card")));
        WebElement firstInitialCard = initialCards.get(0);
        WebElement laptopsMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[normalize-space()='Laptops']")));
        laptopsMenu.click();

        wait.until(ExpectedConditions.stalenessOf(firstInitialCard));
        List<WebElement> cards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.cssSelector("#tbodyid .card")));
        assertFalse(cards.isEmpty(), "Nenhum laptop foi carregado após selecionar a categoria Laptops");
        List<String> names = new ArrayList<>();
        List<BigDecimal> prices = new ArrayList<>();

        for (WebElement card : cards) {
            WebElement nameElement = card.findElement(By.cssSelector(".card-title a"));
            WebElement priceElement = card.findElement(By.cssSelector("h5"));
            String name = nameElement.getText().trim();
            String priceText = priceElement.getText().replaceAll("[^0-9.]", "");
            assertFalse(name.isEmpty(), "Cada laptop exibido deve possuir um nome");
            assertFalse(priceText.isEmpty(), "Cada laptop exibido deve possuir um preço numérico");
            names.add(name);
            prices.add(new BigDecimal(priceText));
        }

        int mostExpensiveIndex = 0;
        for (int index = 1; index < prices.size(); index++) {
            if (prices.get(index).compareTo(prices.get(mostExpensiveIndex)) > 0) {
                mostExpensiveIndex = index;
            }
        }
        assertTrue(prices.get(mostExpensiveIndex).compareTo(BigDecimal.ZERO) > 0);
        System.out.printf("Laptop mais caro: %s - %s%n", names.get(mostExpensiveIndex), prices.get(mostExpensiveIndex));
    }
}

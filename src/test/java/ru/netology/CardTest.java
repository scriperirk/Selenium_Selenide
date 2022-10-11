package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CardTest {

    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void shouldCardTest() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Афанасов Антон");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79994008600");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void shouldCardTestName() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Fafyfcjd Fynjy");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79994008600");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void shouldCardTestEmptyName() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79994008600");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void shouldCardTestPhone() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Афанасов Антон");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7999400860");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void shouldCardTestEmptyPhone() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Афанасов Антон");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void shouldCardTestAgree() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Афанасов Антон");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79994008600");
        driver.findElement(By.cssSelector("button")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText();
        String expectedText = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";

        Assertions.assertEquals(expectedText, actualText);
    }
}

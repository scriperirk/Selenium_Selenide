package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

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

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

        String meetingDateNearest = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        $("[data-test-id='city'] input").setValue("Иркутск");
        $("[data-test-id='date'] input").setValue(meetingDateNearest);

        $("[data-test-id=name] input").setValue("Афанасов Антон");
        $("[data-test-id=phone] input").setValue("+79000000880");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title")
                .shouldBe(Condition.appear, Duration.ofSeconds(15))
                .shouldHave((text("Успешно!")));
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(text("Встреча успешно забронирована на " + meetingDateNearest));

    }
}

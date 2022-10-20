import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.channels.Selector;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.SetValueOptions.withText;

public class TestSevece {
    WebDriver driver;

   @BeforeAll
    public static void driverSetup() {
        WebDriverManager.chromedriver().setup();
    }

    WebDriverManager wdm = WebDriverManager.chromedriver().browserInDocker()
            .enableVnc().enableRecording();
    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        open("http://localhost:9999/");

    }

    @AfterEach
    public void teardown() {
        driver.quit();
        driver = null;
    }
    String generateDate(int days){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    }

    @Test
    public void ValidTest(){
        String date = generateDate(3);
        $("[placeholder='Город']").setValue("Казань");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(date).sendKeys(Keys.ESCAPE);
        $("[name='name']").setValue("Иван Петров");
        $("[name='phone']").setValue("+79036334088");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $(Selectors.withText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));

    }

    
}

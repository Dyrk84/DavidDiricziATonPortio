import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class PortioWebsiteTesting {

    WebDriver driver;
    IndexPage indexPage;

    @BeforeEach
    public void Setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        //options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("start-maximized");
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        indexPage = new IndexPage(driver);
    }

    /*
    A választott teszt alkalmazásnak legalább az alábbi funkcióit kell lefedni tesztekkel:
             (/) Regisztráció
             (-) Bejelentkezés
             (/) Adatkezelési nyilatkozat használata
             (-) Adatok listázása
             (-) Több oldalas lista bejárása
             (-) Új adat bevitel
             (-) Ismételt és sorozatos adatbevitel adatforrásból
             (-) Meglévő adat módosítás
             (-) Adat vagy adatok törlése
             (-) Adatok lementése felületről
             (-) Kijelentkezés
    */
    @Test //Adatkezelési nyilatkozat használata
    public void privacyPolicyTest(){
        indexPage.navigate(); //fellép az oldalra
        indexPage.closeTheTermsAndConditionsPopUp(); //becsukja a popupot
        boolean actual = indexPage.checkTermsAndConditionValidation(); //megnézi hogy a popup css-e mit ír, az alapján nézem, hogy becsukta-e a popupot

        Assertions.assertFalse(actual);
    }

    @Test //Regisztráció
    public void registrationTest(){
        indexPage.navigate();
        indexPage.closeTheTermsAndConditionsPopUp();
        indexPage.registrationProcess("David", "pass123", "diriczid@freemail", "something description text");
        boolean actual = indexPage.checkRegistrationValidation();

        Assertions.assertFalse(actual);
    }

    @Test //Bejelentkezés
    public void logIN(){
        indexPage.navigate();
        indexPage.closeTheTermsAndConditionsPopUp();
        indexPage.registrationProcess("David", "pass123", "diriczid@freemail", "something description text");
        //eddigi volt a regisztráció
        indexPage.logIn("David", "pass123");
        String resultURL = indexPage.getCurrentURL();

        Assertions.assertEquals("https://lennertamas.github.io/portio/landing.html", resultURL);
    }






}

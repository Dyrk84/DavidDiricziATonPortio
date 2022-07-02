import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.apache.commons.collections4.MultiValuedMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.List;

public class PortioWebsiteTesting {

    WebDriver driver;

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
        //options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    /*
    A választott teszt alkalmazásnak legalább az alábbi funkcióit kell lefedni tesztekkel:
             (/) Regisztráció
             (/) Bejelentkezés
             (/) Adatkezelési nyilatkozat használata
             (/) Adatok listázása
             (/) Több oldalas lista bejárása
             (/) Új adat bevitel
             (/) Ismételt és sorozatos adatbevitel adatforrásból
             (/) Meglévő adat módosítás
             (-) Adat vagy adatok törlése
             (-) Adatok lementése felületről
             (-) Kijelentkezés
    */
    @Test //Adatkezelési nyilatkozat használata
    public void privacyPolicyTest() {
        IndexPage page = PageFactory.pageSwitcher("IndexPage", driver);
        page.navigateToURL(); //fellép az oldalra
        page.closeTheTermsAndConditionsPopUp(); //becsukja a popupot
        boolean actual = page.checkTermsAndConditionValidation(); //megnézi hogy a popup css-e mit ír, az alapján nézem, hogy becsukta-e a popupot

        Assertions.assertFalse(actual);
    }

    @Test //Regisztráció
    public void registrationTest() {
        IndexPage page = PageFactory.pageSwitcher("IndexPage", driver);
        page.navigateToURL();
        page.closeTheTermsAndConditionsPopUp();
        page.registrationProcess("David", "pass123", "diriczid@freemail", "something description text");
        boolean actual = page.checkRegistrationValidation();

        Assertions.assertFalse(actual);
    }

    @Test //Bejelentkezés
    public void logIn() {
        IndexPage page = PageFactory.pageSwitcher("IndexPage", driver);
        page.navigateToURL();
        page.closeTheTermsAndConditionsPopUp();
        page.registrationProcess("David", "pass123", "diriczid@freemail", "something description text");
        //eddigi volt a regisztráció
        page.logIn("David", "pass123");
        String resultURL = page.getCurrentURL();

        Assertions.assertEquals("https://lennertamas.github.io/portio/landing.html", resultURL);
    }

    @Test //Adatok listázása
    public void experiencesList() {
        LandingPage page = (LandingPage) PageFactory.pageSwitcher("LandingPage", driver);
        page.toTheWebsite();
        List<String> experiencesListFromPage = page.experiencesListCreator();
        List<String> listForTest = MethodsForTests.fileReader("files/experiences.txt");

        Assertions.assertEquals(listForTest, experiencesListFromPage);
    }

    @Test //Több oldalas lista bejárása
    public void blogPageTest() {
        LandingPage page = (LandingPage) PageFactory.pageSwitcher("LandingPage", driver);
        page.toTheWebsite();
        int postsNumbersFromSecondPageOfBlog = page.numberOfPostsFromSecondPageOfBlog();
        int inspectedPostsNumber = 3;

        Assertions.assertEquals(inspectedPostsNumber, postsNumbersFromSecondPageOfBlog);
    }

    @Test //Új adat bevitel és Meglévő adat módosítás
    public void cookieTest() {
        ProfilePage page = (ProfilePage) PageFactory.pageSwitcher("ProfilePage", driver);
        page.navigateToURL();
        page.closeTheTermsAndConditionsPopUp();
        page.registrationProcess("David", "pass123", "diriczid@freemail", "something description text");
        page.logIn("David", "pass123");
        page.clickOnProfileButton();
        Allure.addAttachment("profileScreenShot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        page.profileModifier("DefiantlyNotDavid", "Something bio description text", "06123456789");
        Allure.addAttachment("profileFilledScreenShot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        page.clickOnSaveButton();
        Allure.addAttachment("profileSavedScreenShot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        boolean cookieTestData = page.cookieTestData("David", "DefiantlyNotDavid", "Something bio description text", "06123456789");

        Assertions.assertTrue(cookieTestData); //a profilnév megegyezik, de már nem ugyanazok az adatok szerepelnek, így az átírás sikerült
    }

    @Test //Ismételt és sorozatos adatbevitel adatforrásból
    public void registrationFromFileTest() {
        int numberOrTestDataRow = 50;
        String accountHandlerListPath = "files/multivaluedMapForAccountHandlingInFile.csv";

        ProfilePage page = (ProfilePage) PageFactory.pageSwitcher("ProfilePage", driver);
        page.navigateToURL();
        page.closeTheTermsAndConditionsPopUp();
        MultiValuedMap<Integer, String> generatedData = MethodsForTests.multivaluedMapWriterForAccountHandling(numberOrTestDataRow);
        MethodsForTests.deleteFile(accountHandlerListPath);
        MethodsForTests.MapToFiles(accountHandlerListPath, generatedData);
        List<List<String>> listForAccountHandling = MethodsForTests.fromFileToStringList(accountHandlerListPath);
        for (int i = 0; i < listForAccountHandling.size()-1; i++) {
            page.registrationProcess(
                    listForAccountHandling.get(i).get(0),
                    listForAccountHandling.get(i).get(1),
                    listForAccountHandling.get(i).get(2),
                    listForAccountHandling.get(i).get(3)
            );
            String actual = page.showRegistrationMessage();
            Assertions.assertEquals("User registered!", actual); //mindegyikre megnézem, hogy sikerült-e regisztrálni
            page.registrationFieldsClearer();
        }
        Assertions.assertEquals(numberOrTestDataRow, page.cookiesCounter()); //megnézem hogy a regisztrált accountok száma megegyezik-e a cookiek számával
    }
}

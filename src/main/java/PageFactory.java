import org.openqa.selenium.WebDriver;

public class PageFactory {

    public static IndexPage pageSwitcher(String pageName, WebDriver driver){ //IndexPage a visszatérési típus, mert a főosztályból örökli az összes többi a driverjét
        switch (pageName){
            case "IndexPage": return new IndexPage(driver);
            case "LandingPage": return new LandingPage(driver);
            case "ProfilePage": return new ProfilePage(driver);
            default: return null;
        }
    }
}

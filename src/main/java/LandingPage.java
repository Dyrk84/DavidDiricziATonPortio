import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;

public class LandingPage {

    WebDriver driver;

    private final By experiencesButtonXpath = By.xpath("//*[@class=\"btn-group nav mt-5\"]//*[@href=\"#experience\"]");
    private final By experiencesListXpath = By.xpath("//*[@id=\"experience\"]//h4");

    public LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    public void buttonClicker(By buttonXPath) {
        driver.findElement(buttonXPath).click();
    }

    public List<String> experiencesListCreator() {
        buttonClicker(experiencesButtonXpath);
        List<WebElement> experiencesListWE = driver.findElements(experiencesListXpath);
        List<String> experiencesListString = new LinkedList<>();
        for (int i = 0; i < experiencesListWE.size(); i++) {
            experiencesListString.add(experiencesListWE.get(i).getText());
        }
        return experiencesListString;
    }
}

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;

public class LandingPage extends IndexPage {

    private final By experiencesButtonXpath = By.xpath("//*[@class=\"btn-group nav mt-5\"]//*[@href=\"#experience\"]");
    private final By experiencesListXpath = By.xpath("//*[@id=\"experience\"]//h4");

    private final By buttonToBlogXpath = By.xpath("//*[@id=\"blog\"]//*[@class=\"blog-preview__header_button desktop\"]//*");
    private final By buttonToNextPageOnBlogXpath = By.xpath("//*[@aria-label=\"Page navigation\"]//*[@rel=\"next\"]");
    private final By postsDivsOnPageTwo = By.xpath("//*[@class=\"row\"]/*[@class=\"col-lg-4\"]");

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public List<String> experiencesListCreator() {
        buttonClicker(experiencesButtonXpath);
        List<WebElement> experiencesListWE = driver.findElements(experiencesListXpath);
        System.out.println(experiencesListWE);
        List<String> experiencesListString = new LinkedList<>();
        for (int i = 0; i < experiencesListWE.size(); i++) {
            experiencesListString.add(experiencesListWE.get(i).getText());
        }
        return experiencesListString;
    }

    public int numberOfPostsFromSecondPageOfBlog() {
        buttonClicker(buttonToBlogXpath);
        buttonClicker(buttonToNextPageOnBlogXpath);
        List<WebElement> listOfPostsDivsOnPageTwo = driver.findElements(postsDivsOnPageTwo);
        return listOfPostsDivsOnPageTwo.size();
    }
}

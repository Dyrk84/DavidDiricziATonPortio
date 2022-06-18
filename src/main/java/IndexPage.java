import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IndexPage {

    WebDriver driver;

    public IndexPage(WebDriver driver) {
        this.driver = driver;
    }

    private final String url = "https://lennertamas.github.io/portio/";

    private final By termsAndConditionsAccept = By.id("terms-and-conditions-button");
    private final By termsAndConditionsOnTheDisplayXpath = By.xpath("//*[@class=\"overlay\"]");
    private final String cssValueNameTC = "display"; private final String cssValueTC = "none";

    private final By registerTabXpath = By.xpath("//*[@id=\"login\"]//*[@id=\"register-form-button\"]"); //a register div nem látható, ezért a login div-en belül kell rányomni a register gombra

    private final By userNameFieldXpath = By.xpath("//*[@id=\"register-username\"]");
    private final By passwordFieldXpath = By.xpath("//*[@id=\"register-password\"]");
    private final By emailFieldXpath = By.xpath("//*[@id=\"register-email\"]");
    private final By descriptionFieldXpath = By.xpath("//*[@id=\"register-description\"]");
    private final By registerButtonXpath = By.xpath("//*[@class=\"formGroup\"]//*[@onclick=\"registerUser()\"]");
    private final By registrationSuccessXpath = By.xpath("//*[@id=\"register-alert\"]");
    private final String cssValueNameReg = "display"; private final String cssValueReg = "block";


    public void buttonClicker(By buttonXPath){
        driver.findElement(buttonXPath).click();
    }

    public void inputFieldLoader(By inputFieldXPath, String text){
        driver.findElement(inputFieldXPath).sendKeys(text);
    }

    public boolean validationForTests(By xpath, String cssValueName, String cssValue){
        return !driver.findElement(xpath).getCssValue(cssValueName).equals(cssValue);
        //működhetne így is: Boolean Display = driver.findElement(By.xpath("//*[@id='next']")).isDisplayed();
    }

    public void navigate() {
        driver.navigate().to(url);
    }

    public void closeTheTermsAndConditionsPopUp(){
        buttonClicker(termsAndConditionsAccept);
    }

    public boolean checkTermsAndConditionValidation() {
         return validationForTests(termsAndConditionsOnTheDisplayXpath, cssValueNameTC, cssValueTC);//egyszerűsített if, boolent ad vissza, azt nézi, hogy a felületen a display = none-e. Akkor none, ha már ki lett nyomva a popup
    }

    public void registrationProcess(String name, String password, String email, String description) {
        buttonClicker(registerTabXpath);
        inputFieldLoader(userNameFieldXpath, name);
        inputFieldLoader(passwordFieldXpath, password);
        inputFieldLoader(emailFieldXpath, email);
        inputFieldLoader(descriptionFieldXpath, description);
        buttonClicker(registerButtonXpath);
    }

    public boolean checkRegistrationValidation(){
        return validationForTests(registrationSuccessXpath, cssValueNameReg, cssValueReg);    }
}


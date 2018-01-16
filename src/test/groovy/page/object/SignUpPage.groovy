package page.object

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.How

class SignUpPage {

    static WebDriver driver;

    @FindBy(how = How.ID, using = "id_fullname")
    static WebElement name

    @FindBy(how = How.ID, using = "id_email")
    static WebElement email

    @FindBy(how = How.ID, using = "id_password")
    static WebElement password

    @FindBy(how = How.ID, using = "submit-id-submit")
    static WebElement submit

    @FindBy(how = How.XPATH, using = "//*[@id='signup-form']/div[1]/div/div")
    static WebElement alertMessage

    SignUpPage( WebDriver driver) {
        this.driver = driver
    }
}



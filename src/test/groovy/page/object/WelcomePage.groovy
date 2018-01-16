package page.object

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.How

/**
 * Created by ayca.uzel on 1/8/2018.
 */
class WelcomePage {

    static WebDriver driver;

    @FindBy(how = How.LINK_TEXT, using = "Sign Up")
    static WebElement signup

    @FindBy(how = How.CSS, using = ".dropdown__user-mail.ellipsis")
    static WebElement mailButton

    @FindBy(how = How.CLASS_NAME, using = "user-initials")
    static WebElement userButton

    WelcomePage(WebDriver driver) {
        this.driver = driver
    }
}

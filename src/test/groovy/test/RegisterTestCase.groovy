package test

import org.apache.commons.lang3.RandomStringUtils
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.logging.LogEntries
import org.openqa.selenium.logging.LogEntry
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.support.PageFactory
import page.object.SignUpPage
import page.object.WelcomePage
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.imageio.ImageIO
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.image.BufferedImage

class RegisterTestCase extends Specification{

    @Shared WebDriver driver
    @Shared SignUpPage page
    @Shared WelcomePage welcomePage
    @Shared JavascriptExecutor js
    static boolean success = false
    static int failIndex = 0

    def setupSpec(){
        driver = new ChromeDriver()
        js = (JavascriptExecutor)driver

        driver.get("https://www.udemy.com/")
        driver.manage().window().maximize()
        welcomePage = PageFactory.initElements(driver, WelcomePage.class)

        welcomePage.signup.click()
        // wait until registery page is opened
        sleep(2000)
        page = PageFactory.initElements(driver, SignUpPage.class)
    }

    def cleanupSpec(){
        driver.close()
    }

    def setup(){
        success = false
    }

    def cleanup(){
        if(!success){
            getTheSSofScreenAndPrintLogs()
        }
    }

    def getTheSSofScreenAndPrintLogs(){
        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()))
        new File("failedTestScreenshots").mkdir()
        ImageIO.write(image, "png", new File("failedTestScreenshots" + File.separator + failIndex.toString() + "_" + System.currentTimeMillis() + ".png"))
        failIndex++

        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER)
        for (LogEntry entry : logEntries) {
            println("--->" + new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage())
        }
    }

    @Unroll()
    def "Register with following wrong args: #name, #email, #password"() {

        when:
        page.name.sendKeys(name)
        page.email.sendKeys(email)
        page.password.sendKeys(password)
        page.submit.click()

        Boolean is_valid = (Boolean)js.executeScript("return arguments[0].checkValidity();", controlElement)
        String message = (String)js.executeScript("return arguments[0].validationMessage;", controlElement)

        then:
        is_valid == validity
        message.contains(expectedResultMessage)
        (success = true) != null

        cleanup:
        page.name.clear()
        page.email.clear()
        page.password.clear()

        where:
        name     | email        | password    | expectedResultMessage         | validity | controlElement
        ""       | "email1"     | "password"  | "Please fill in this field"   | false    | page.name
        "ayca"   | ""           | "password"  | "Please fill in this field"   | false    | page.email
        "ayca"   | "email1"     | "password"  | "Please include an '@'"       | false    | page.email
        "ayca"   | "email@@"    | "password"  | "A part following '@' should" | false    | page.email
        "ayca"   | "email@info" | "pass"      | "Please lengthen this text"   | false    | page.password
     }

    def udemyMailAlert(){

        when:
        page.name.sendKeys("user")
        page.email.sendKeys("user@udemy.com")
        page.password.sendKeys("1234567")
        page.submit.click()
        sleep(2000)

        then:
        page.alertMessage.getText().contains("Your company has a Udemy for Business account")

        cleanup:
        page.name.clear()
        page.email.clear()
        page.password.clear()
    }

    def registerSuccessfully(){

        when:
        sleep(2000)
        def name = RandomStringUtils.randomAlphanumeric(5)
        def password = RandomStringUtils.randomAlphanumeric(7)
        def mail = RandomStringUtils.randomAlphanumeric(5) + "@gmail.com"

        page.name.sendKeys(name)
        page.email.sendKeys(mail)
        page.password.sendKeys(password)
        page.submit.click()
        sleep(2000)

        Actions builder = new Actions(driver)
        builder.moveToElement(welcomePage.userButton).perform()
        then:
        welcomePage.mailButton.getText() == mail
    }

}

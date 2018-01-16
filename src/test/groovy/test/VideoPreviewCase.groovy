package test

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.PageFactory
import page.object.VideoPage
import spock.lang.Shared
import spock.lang.Specification

class VideoPreviewCase extends Specification{

    @Shared WebDriver driver
    @Shared VideoPage videoPage

    def setupSpec(){
        driver = new ChromeDriver()
        driver.get("https://www.udemy.com/complete-python-bootcamp/?persist_locale&locale=tr_TR")
        driver.manage().window().maximize()
        WebElement playButton = driver.findElement(By.className("play-button"))
        playButton.click()
        sleep(2000)
        videoPage = PageFactory.initElements(driver, VideoPage.class)
        sleep(2000)
    }

    def cleanupSpec(){
        driver.close()
    }

    def verify30secondsForward(){

        when:
        videoPage.videoPlay.click()
        videoPage.videoPlaying.click()
        videoPage.forward.click()
        videoPage.forward.click()
        sleep(2000)

        then:
        videoPage.currentTime.getText().contains("0:30")
    }

    def verifySubtitles(){

        // default mode subtitles are displayed so the element of videoSubtitle has 3 child
        when:
        WebElement rootWebElement = videoPage.videoSubtitle
        List<WebElement> child = rootWebElement.findElements(By.xpath(".//*"))

        then:
        child.size() == 3

        when:
        videoPage.subtitlesButton.click()
        videoPage.subtitlesClosed.click()
        List<WebElement> childAfterOff = rootWebElement.findElements(By.xpath(".//*"))

        then:
        childAfterOff.size() == 1
    }

    def verifyVideoPaused(){

        // video is paused now
        when:
        videoPage.videoPaused.click()

        then:
        videoPage.videoPlaying.isDisplayed()

        when:
        sleep(2000)
        videoPage.videoPlaying.click()

        then:
        videoPage.videoPaused.isDisplayed()
    }

}

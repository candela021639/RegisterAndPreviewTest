package page.object

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.How

class VideoPage {

    static WebDriver driver

    @FindBy(how = How.CSS, using = ".play-button.play-button--initially-visible")
    static WebElement videoPlay

    @FindBy(how = How.CSS, using = ".vjs-play-control.vjs-control.vjs-button.vjs-playing")
    static WebElement videoPlaying

    @FindBy(how = How.CSS, using = ".vjs-play-control.vjs-control.vjs-button.vjs-paused")
    static WebElement videoPaused

    @FindBy(how = How.CSS, using = ".forward__label")
    static WebElement forward

    @FindBy(how = How.CSS, using = ".vjs-current-time-display")
    static WebElement currentTime

    @FindBy(how = How.CLASS_NAME, using = "preview__description")
    static WebElement previewHeader

    @FindBy(how = How.XPATH, using = "//div[@title='Altyazılar']")
    static WebElement subtitlesButton

    @FindBy(how = How.XPATH, using = "//*[contains(text(), 'Kapalı')]")
    static WebElement subtitlesClosed

    @FindBy(how = How.CLASS_NAME, using = "vjs-text-track-display")
    static WebElement videoSubtitle

    VideoPage(WebDriver driver) {
        this.driver = driver
    }
}

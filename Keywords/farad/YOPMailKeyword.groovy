package farad

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.WebElement

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

class YopmailKeywords {

    private void closeGoogleVignette() {

        List<String> closeButtonXpaths = [
            "//button[@aria-label='Close']",
            "//button[@aria-label='close']",
            "//*[@role='button' and @aria-label='Close']",
            "//*[@role='button' and @aria-label='close']",
            "//*[contains(@aria-label,'Close ad')]",
            "//*[contains(@aria-label,'close ad')]",
            "//*[contains(@aria-label,'Dismiss')]",
            "//*[contains(@aria-label,'dismiss')]"
        ]

        for (String xp : closeButtonXpaths) {

            TestObject closeButton = new TestObject('vignetteCloseButton')
            closeButton.addProperty('xpath', ConditionType.EQUALS, xp)

            if (WebUI.verifyElementPresent(closeButton, 1, FailureHandling.OPTIONAL)) {
                try {
                    if (WebUI.verifyElementVisible(closeButton, FailureHandling.OPTIONAL)) {
                        println("Google Vignette close button detected: " + xp)
                        WebUI.click(closeButton, FailureHandling.OPTIONAL)
                        WebUI.delay(1)
                        println("Google Vignette close button clicked successfully.")
                        return
                    }
                } catch (Exception e) {
                    println("Detected possible Vignette close button but could not click it: " + e.getMessage())
                }
            }
        }

        println("No Google Vignette close button detected. Continuing...")
    }

    /**
     * Clicks a TestObject via Selenium normally. If the click is
     * intercepted by an overlay (e.g. a Google ad iframe covering the
     * element), falls back to a JS-level click on the same element,
     * which bypasses the browser's hit-testing entirely.
     */
     private void safeClick(TestObject to) {
        try {
            WebUI.click(to, FailureHandling.STOP_ON_FAILURE)
        } catch (Exception e) {
            println("Click intercepted (likely an ad overlay), forcing JS click instead: " + e.getMessage())

            WebElement element = WebUI.findWebElement(to, 5)
            WebUI.executeJavaScript("arguments[0].scrollIntoView({block:'center'});", [element])
            WebUI.executeJavaScript("arguments[0].click();", [element])
        }
    }

    @Keyword
    def fetchOtp(String emailOrInbox) {

        String inboxName = emailOrInbox.contains('@') ?
            emailOrInbox.substring(0, emailOrInbox.indexOf('@')) :
            emailOrInbox

        // ============================================================
        // OPEN YOPMAIL
        // ============================================================

        WebUI.newTab('')

        WebUI.navigateToUrl('https://yopmail.com/wm')

        WebUI.delay(2)

        closeGoogleVignette()

        // ============================================================
        // ENTER INBOX
        // ============================================================

        WebUI.setText(
            findTestObject('DepositMoney/Page_YOPmail - Disposable Email Address - A_32619f/input_Enter your inbox here'),
            inboxName
        )

        closeGoogleVignette()

        safeClick(
            findTestObject('DepositMoney/Page_YOPmail - Disposable Email Address - A_32619f/i_')
        )

        WebUI.delay(2)

        closeGoogleVignette()

        // ============================================================
        // SWITCH TO INBOX FRAME
        // ============================================================

        TestObject inboxFrame = new TestObject('inboxFrame')
        inboxFrame.addProperty('xpath', ConditionType.EQUALS, "//*[@id='ifinbox']")
        WebUI.switchToFrame(inboxFrame, 10)

        // ============================================================
        // LOCATE FIRST EMAIL
        // ============================================================

        TestObject firstMailRow = new TestObject('firstMailRow')
        firstMailRow.addProperty(
            'xpath',
            ConditionType.EQUALS,
            "//*[@currentmail='']//*[@class and contains(concat(' ', normalize-space(@class), ' '), ' lm ')]"
        )

        safeClick(firstMailRow)

        WebUI.switchToDefaultContent()

        WebUI.delay(2)

        closeGoogleVignette()

        // ============================================================
        // SWITCH TO MAIL BODY FRAME
        // ============================================================

        List<String> candidateFrameXpaths = [
            "//*[@id='ifmail']",
            "//*[@id='mail']",
            "//iframe[contains(@name,'mail')]",
            "//iframe[contains(@id,'mail')]"
        ]

        boolean switchedToMailFrame = false

        for (String xp : candidateFrameXpaths) {
            TestObject candidate = new TestObject('mailFrameCandidate')
            candidate.addProperty('xpath', ConditionType.EQUALS, xp)

            if (WebUI.verifyElementPresent(candidate, 3, FailureHandling.OPTIONAL)) {
                WebUI.switchToFrame(candidate, 5)
                switchedToMailFrame = true
                break
            }
        }

        if (!switchedToMailFrame) {
            throw new Exception('Could not locate the mail body frame with any known candidate XPath.')
        }

        // ============================================================
        // EXTRACT OTP TEXT
        // ============================================================

        TestObject otpText = new TestObject('otpText')
        otpText.addProperty('xpath', ConditionType.EQUALS, "//div/*[(name()='p') and (position()=1)]")

        String emailText

        if (WebUI.verifyElementPresent(otpText, 5, FailureHandling.OPTIONAL)) {
            emailText = WebUI.getText(otpText)
        } else {
            TestObject otpTextFallback = new TestObject('otpTextFallback')
            otpTextFallback.addProperty('xpath', ConditionType.EQUALS, "//*[@*][self::tr][position()=3]//td")
            emailText = WebUI.getText(otpTextFallback)
        }

        // ============================================================
        // EXTRACT 6-DIGIT OTP
        // ============================================================

        WebUI.switchToDefaultContent()

        def matcher = (emailText =~ /\d{6}/)

        if (!matcher.find()) {
            throw new Exception('OTP not found in Yopmail email body: ' + emailText)
        }

        String otp = matcher.group()

        println("OTP fetched successfully: " + otp)

        // ============================================================
        // RETURN TO FARAD CONNECT
        // ============================================================

        WebUI.switchToWindowTitle('FTG Connect')

        return otp
    }
}
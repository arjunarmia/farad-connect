// farad/YopmailKeywords.groovy
package farad

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

class YopmailKeywords {

    @Keyword
    def fetchOtp(String emailOrInbox) {
        String inboxName = emailOrInbox.contains('@') ?
            emailOrInbox.substring(0, emailOrInbox.indexOf('@')) : emailOrInbox

        WebUI.newTab('')
        WebUI.navigateToUrl('https://yopmail.com/wm')

        WebUI.setText(findTestObject('DepositMoney/Page_YOPmail - Disposable Email Address - A_32619f/input_Enter your inbox here'), inboxName)
        WebUI.click(findTestObject('DepositMoney/Page_YOPmail - Disposable Email Address - A_32619f/i_'))

        // --- Switch into the inbox list iframe before locating the row ---
        TestObject inboxFrame = new TestObject('inboxFrame')
        inboxFrame.addProperty('xpath', ConditionType.EQUALS, "//*[@id = 'ifinbox']")
        WebUI.switchToFrame(inboxFrame, 10)

        TestObject firstMailRow = new TestObject('firstMailRow')
        firstMailRow.addProperty('xpath', ConditionType.EQUALS,
            "//*[@currentmail = '']//*[@class and contains(concat(' ', normalize-space(@class), ' '), ' lm ')]")
        WebUI.click(firstMailRow)

        WebUI.switchToDefaultContent()

        // --- Switch into the mail body iframe, trying known Yopmail id patterns ---
        List<String> candidateFrameXpaths = [
            "//*[@id = 'ifmail']",
            "//*[@id = 'mail']",
            "//iframe[contains(@name,'mail')]",
            "//iframe[contains(@id,'mail')]"
        ]

        boolean switchedToMailFrame = false
        for (String xp in candidateFrameXpaths) {
            TestObject candidate = new TestObject('mailFrameCandidate')
            candidate.addProperty('xpath', ConditionType.EQUALS, xp)
            if (WebUI.verifyElementPresent(candidate, 3, FailureHandling.OPTIONAL)) {
                WebUI.switchToFrame(candidate, 5)
                switchedToMailFrame = true
                break
            }
        }

        if (!switchedToMailFrame) {
            throw new Exception('Could not locate the mail body frame with any known candidate xpath — inspect the DOM manually.')
        }

        // --- Extract the OTP text from the opened email ---
        TestObject otpText = new TestObject('otpText')
        otpText.addProperty('xpath', ConditionType.EQUALS, "//div/*[(name() = 'p') and (position() = 1)]")

        String emailText
        if (WebUI.verifyElementPresent(otpText, 5, FailureHandling.OPTIONAL)) {
            emailText = WebUI.getText(otpText)
        } else {
            TestObject otpTextFallback = new TestObject('otpTextFallback')
            otpTextFallback.addProperty('xpath', ConditionType.EQUALS, "//*[(name() = 'tr') and (position() = 3)]//td")
            emailText = WebUI.getText(otpTextFallback)
        }

        WebUI.switchToDefaultContent()

        def matcher = (emailText =~ /\d{6}/)
        if (!matcher.find()) {
            throw new Exception('OTP not found in Yopmail email body: ' + emailText)
        }
        String otp = matcher.group()

        WebUI.switchToWindowTitle('Farad Connect')
        return otp
    }
}
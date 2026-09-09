import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys


WebUI.openBrowser(GlobalVariable.G_URL)

WebUI.setText(findTestObject('Object Repository/Login/input_login_userName'), GlobalVariable.G_USERNAME)

WebUI.setText(findTestObject('Object Repository/Login/input_login_password'), GlobalVariable.G_PASSWORD)

// Check if the checkbox is not already checked, then click it
if (!(WebUI.verifyElementChecked(findTestObject('Object Repository/Login/checkbox_login_rememberMe'), 2, FailureHandling.OPTIONAL))) {
    WebUI.click(findTestObject('Object Repository/Login/checkbox_login_rememberMe'))
}

// 3: Log In
WebUI.click(findTestObject('Object Repository/Login/button_login_Login'))

// 4: Verify Login Success
WebUI.verifyElementPresent(findTestObject('Object Repository/Login/element_dashboard_exist'), 10)

WebUI.comment('Login successful with Remember Me checked.')

// STEP 5: Close and Reopen Browser to test persistence
// This simulates a user returning to the site later
WebUI.closeBrowser()

WebUI.delay(2)

WebUI.openBrowser(GlobalVariable.G_URL)

// STEP 6: Verification Logic
// Depending on the site's implementation, 'Remember Me' usually does one of two things:
// Option A: Automatically logs you back in (Session persistence)
// Option B: Pre-fills the username field
boolean isLoggedIn = WebUI.verifyElementPresent(findTestObject('Object Repository/Login/element_dashboard_exist'), 5, FailureHandling.OPTIONAL)

if (isLoggedIn) {
    WebUI.comment('SUCCESS: User was automatically logged in via cookies.') // Option B: Check if the username field is pre-filled
} else {
    String savedUser = WebUI.getAttribute(findTestObject('Object Repository/Login/input_login_userName'), 'value')

    if (savedUser == GlobalVariable.G_USERNAME) {
        WebUI.comment('SUCCESS: Username field was pre-filled.')
    } else {
        KeywordUtil.markFailed('FAIL: Remember Me functionality did not persist session or pre-fill username.')
    }
}


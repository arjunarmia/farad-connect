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
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('https://beta.faradconnect.net/login.php')

WebUI.click(findTestObject('ForgotUsername/Page_Farad Connect/a_forgotUsernameLink'))

WebUI.setText(findTestObject('ForgotUsername/Page_Farad Connect/input_Enter your email'), GlobalVariable.G_EMAIL)
WebUI.click(findTestObject('ForgotUsername/Page_Farad Connect/button_Submit'))

WebUI.verifyElementVisible(findTestObject('ForgotUsername/Page_Farad Connect/span_Your username has been sent to your email'))


WebUI.navigateToUrl('https://yopmail.com/')

WebUI.setText(findTestObject('ForgotUsername/Page_YOPmail - Disposable Email Address - A_32619f/input_Enter your inbox here'),
    GlobalVariable.G_EMAIL)
WebUI.sendKeys(findTestObject('ForgotUsername/Page_YOPmail - Disposable Email Address - A_32619f/input_Enter your inbox here'),
    Keys.chord(Keys.ENTER))
WebUI.waitForPageLoad(10)

WebUI.verifyElementVisible(findTestObject('ForgotUsername/Page_Inbox/div_Farad Connect _ Request for forgotten userna'))

WebUI.click(findTestObject('ForgotUsername/Page_Inbox/div_Farad Connect _ Request for forgotten userna'))

String rawUsernameText = WebUI.getText(findTestObject('ForgotUsername/Page_Inbox/label_Username_yopmail'))
String newUserName = rawUsernameText.replaceAll('(?i)username:?\\s*', '').trim()

// --- Back to login (same window, not a separate 'Farad Connect' window) ---
WebUI.navigateToUrl('https://beta.faradconnect.net/login.php')

WebUI.setText(findTestObject('Login/Page_Farad Connect/input_Username'), newUserName)
WebUI.setText(findTestObject('Login/Page_Farad Connect/input__'), GlobalVariable.G_PASSWORD)
WebUI.click(findTestObject('Login/Page_Farad Connect/button_btnLogin'))

WebUI.closeBrowser()
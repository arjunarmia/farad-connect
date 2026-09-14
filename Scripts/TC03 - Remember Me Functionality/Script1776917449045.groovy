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

WebUI.openBrowser('')
WebUI.navigateToUrl(GlobalVariable.G_URL)
WebUI.setText(findTestObject('Login/Page_Farad Connect/input_Username'), GlobalVariable.G_USERNAME)
WebUI.setText(findTestObject('Login/Page_Farad Connect/input__'), GlobalVariable.G_PASSWORD)
WebUI.click(findTestObject('RememberFunctionality/Page_Farad Connect/span_Remember Me'))
WebUI.click(findTestObject('Login/Page_Farad Connect/button_btnLogin'))

WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/span_FTG Token'), 10)

WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_Logout'))

WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/logout_elementCheck'), 10)
WebUI.back()

String username = WebUI.getAttribute(findTestObject('Login/Page_Farad Connect/input_Username'), 'value')
String password = WebUI.getAttribute(findTestObject('Login/Page_Farad Connect/input__'), 'value')

WebUI.verifyMatch(username, GlobalVariable.G_USERNAME, false)
WebUI.verifyMatch(password, GlobalVariable.G_PASSWORD, false)

WebUI.closeBrowser()
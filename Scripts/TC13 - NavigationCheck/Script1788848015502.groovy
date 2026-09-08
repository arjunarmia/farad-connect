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

import farad.LoginKeywords
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

LoginKeywords loginKeywords = new LoginKeywords()
loginKeywords.login()

WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/span_FTG Token'), 10)

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_FTG Token'))
WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/img_FTG Token  Energy Blockchain coin'), 10)

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_My FTG Tokens'))
WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/h2_My FTG Tokens'), 10)

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_Token Activity'))
WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/h1_Token Activity'), 10)

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_Orders'))
WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/h2_ft-ordN-dashboard-title'), 10)

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_Return to main site'))

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_FTG Wallet'))
WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/p_Wallet Balance'), 10)

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_FTG Mail'))
WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/h1_Groups  Communication'), 10)

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_Reports'))
WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/h2_Reports'), 10)

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_Points'))
WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/div_FTG Connect'), 10)
WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_Go to home'))

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_Vouchers'))
WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/h1_Vouchers'), 10)

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_My Profile'))
WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/section_Profile banner'), 10)

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_My Subscription'))
WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/h1_Billing  Subscription'), 10)

WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_Logout'))

WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/logout_elementCheck'), 10)
WebUI.back()
WebUI.verifyElementPresent(findTestObject('Login/Page_Farad Connect/input_Username'), 10)

WebUI.closeBrowser()


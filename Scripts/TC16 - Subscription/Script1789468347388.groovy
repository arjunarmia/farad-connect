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
import farad.YopmailKeywords
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys


LoginKeywords loginKeywords = new LoginKeywords()
loginKeywords.login()

// Cancel Subscription
WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_My Subscription'))

WebUI.click(findTestObject('Subscription/Page_FTG Connect/button_cancelSubscription'))
WebUI.click(findTestObject('Subscription/Page_FTG Connect/button_confirmCancel'))
//WebUI.verifyElementPresent(findTestObject('Subscription/Page_FTG Connect/button_cancelSubscription'), 10)
WebUI.waitForElementClickable(findTestObject('NavigationCheck/Page_Farad Connect/button_user-menu-btn'), 10)

// Go to Profiles and deactivate Account
WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_My Profile'))
WebUI.click(findTestObject('Subscription/Page_FTG Connect/a_Account Settings'))
WebUI.click(findTestObject('Subscription/Page_FTG Connect/button_tab-account'))

WebUI.click(findTestObject('Subscription/Page_FTG Connect/button_Deactivate account'))
WebUI.setText(findTestObject('Subscription/Page_FTG Connect/input_Enter password'), GlobalVariable.G_PASSWORD)
WebUI.click(findTestObject('Subscription/Page_FTG Connect/button_Deactivate Account (1)'))
WebUI.waitForElementNotPresent(findTestObject('Subscription/Page_FTG Connect/button_Deactivate Account (1)'), 10)

// Check if account is lite now
WebUI.refresh()
String liteLabel = WebUI.getText(findTestObject('Subscription/Page_FTG Connect/span_Lite'))
WebUI.verifyMatch(liteLabel, "Lite", false)

WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_My Subscription'))
WebUI.verifyElementPresent(findTestObject('Subscription/Page_FTG Connect/strong_FTG LITE  Free'), 10)

// Upgrade to Premium
WebUI.click(findTestObject('Subscription/Page_FTG Connect/a_Upgrade to premium instantly with credit card'))
WebUI.setText(findTestObject('Subscription/Page_FTG Connect/input_0000 0000 0000 0000'), GlobalVariable.G_CREDITCARD_NUMBER)
WebUI.setText(findTestObject('Subscription/Page_FTG Connect/input_MM_YY'), GlobalVariable.G_CREDITCARD_EXPDATE)
WebUI.setText(findTestObject('Subscription/Page_FTG Connect/input_000'), GlobalVariable.G_CREDITCARD_CVV)
WebUI.click(findTestObject('Subscription/Page_FTG Connect/button_Upgrade Subscription'))

YopmailKeywords yopmailKeywords = new YopmailKeywords()
String otp = yopmailKeywords.fetchOtp(GlobalVariable.G_EMAIL)

WebUI.setText(findTestObject('Subscription/Page_FTG Connect/input_Digit 1'), otp)
WebUI.click(findTestObject('Subscription/Page_FTG Connect/button_vmodal-verify'))

WebUI.verifyElementPresent(findTestObject('Subscription/Page_FTG Connect/h2_Billing History'), 10)
String premiumLabel = WebUI.getText(findTestObject('Subscription/Page_FTG Connect/span_Premium'))
WebUI.verifyMatch(premiumLabel, "Premium", false)


/* OBSOLETE - Go to Settings and Reactivate Account
WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('NavigationCheck/Page_Farad Connect/a_My Profile'))
WebUI.click(findTestObject('Subscription/Page_FTG Connect/a_Account Settings'))
WebUI.click(findTestObject('Subscription/Page_FTG Connect/button_tab-account'))

WebUI.click(findTestObject('Subscription/Page_FTG Connect/button_Deactivate account_1'))
WebUI.click(findTestObject('Subscription/Page_FTG Connect/button_Continue'))
*/

WebUI.closeBrowser()


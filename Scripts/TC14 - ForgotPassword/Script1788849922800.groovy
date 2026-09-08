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

WebUI.openBrowser('')
WebUI.navigateToUrl('https://beta.faradconnect.net/login.php')
WebUI.click(findTestObject('ForgotPassword/Page_Farad Connect/a_forgotPasswordLink'))

WebUI.setText(findTestObject('ForgotPassword/Page_Farad Connect/input_Enter your email'), GlobalVariable.G_EMAIL)
WebUI.click(findTestObject('ForgotPassword/Page_Farad Connect/button_Submit'))

WebUI.verifyElementVisible(findTestObject('ForgotPassword/Page_Farad Connect/div_Password reset link sent to your email'))

// Go to Yopmail and open the reset-link email
WebUI.navigateToUrl('https://yopmail.com/en/')

WebUI.setText(findTestObject('ForgotPassword/Page_YOPmail - Disposable Email Address - A_32619f/input_Enter your inbox here'),
    GlobalVariable.G_EMAIL)
WebUI.click(findTestObject('ForgotPassword/Page_YOPmail - Disposable Email Address - A_32619f/i_'))

WebUI.click(findTestObject('ForgotPassword/Page_Inbox/button_12_46'))
WebUI.click(findTestObject('ForgotPassword/Page_Inbox/iframe_ifmail'))
WebUI.click(findTestObject('ForgotPassword/Page_Inbox/a_Click here'))

// Reset link
WebUI.switchToWindowTitle('Farad Connect')
WebUI.waitForPageLoad(10)

WebUI.verifyElementVisible(findTestObject('ForgotPassword/Page_Farad Connect/div_Your password has been successfully reset. A'))

// Back to Yopmail for the new-password email
WebUI.switchToWindowTitle('Inbox')

WebUI.click(findTestObject('ForgotPassword/Page_Inbox/button_refresh'))
WebUI.waitForPageLoad(10)

WebUI.click(findTestObject('ForgotPassword/Page_Inbox/div_Farad Connect _ FARAD Password Reset'))
WebUI.click(findTestObject('ForgotPassword/Page_Inbox/td_Your FARAD password has been reset. Your'))

String newPassword = WebUI.getText(findTestObject('ForgotPassword/Page_Inbox/strong_newPasswordValue')).trim()

// Log in with the new password
loginKeywords.loginWithCredentials(GlobalVariable.G_USERNAME, newPassword)
WebUI.verifyElementPresent(findTestObject('NavigationCheck/Page_Farad Connect/span_FTG Token'), 10)

// Change Password to Old Password
WebUI.click(findTestObject('ProfileCompletion/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('ProfileCompletion/Page_Farad Connect/a_My Profile'))
WebUI.click(findTestObject('ProfileCompletion/Page_Farad Connect/a_Edit Profile'))
WebUI.click(findTestObject('ForgotPassword/Page_Farad Connect/button_tab-password'))

WebUI.setText(findTestObject('ForgotPassword/Page_Farad Connect/input_Enter your current password'), newPassword)
WebUI.setText(findTestObject('ForgotPassword/Page_Farad Connect/input_Enter new password'), GlobalVariable.G_PASSWORD)
WebUI.setText(findTestObject('ForgotPassword/Page_Farad Connect/input_Confirm new password'), GlobalVariable.G_PASSWORD)
WebUI.click(findTestObject('ForgotPassword/Page_Farad Connect/button_Save password changes'))

WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_Logout'))
WebUI.back()

// Login with this new password
WebUI.setText(findTestObject('Login/Page_Farad Connect/input_Username'), GlobalVariable.G_USERNAME)
WebUI.setText(findTestObject('Login/Page_Farad Connect/input__'), GlobalVariable.G_PASSWORD)
WebUI.click(findTestObject('Login/Page_Farad Connect/button_btnLogin'))

WebUI.closeBrowser()


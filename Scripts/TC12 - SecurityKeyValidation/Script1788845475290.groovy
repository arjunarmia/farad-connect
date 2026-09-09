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

def securityKey = [
    user1Name      : GlobalVariable.G_USERNAME,
    user1Pass      : GlobalVariable.G_PASSWORD,
    user2Name      : GlobalVariable.G_RECEIVER_NAME,
    user2Pass      : GlobalVariable.G_PASSWORD
]

LoginKeywords loginKeywords = new LoginKeywords()
loginKeywords.loginWithCredentials(securityKey.user1Name, securityKey.user1Pass)

WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_FTG Token'))
WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_My FTG Tokens'))
WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_token-admin-generate-btn'))
WebUI.waitForElementVisible(findTestObject('ValidateSecurityKey/Page_Farad Connect/label_token-admin-security-key'), 10)

String user1SecurityKey = WebUI.getText(findTestObject('ValidateSecurityKey/Page_Farad Connect/label_token-admin-security-key'))
String user1TokenBalance = WebUI.getText(findTestObject('ValidateSecurityKey/Page_Farad Connect/label_totalTokenAmount_MyFTGTokens'))

WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_View FTG Token Transaction History'))
WebUI.waitForElementVisible(findTestObject('ValidateSecurityKey/Page_Farad Connect/label_totalTransactionCount_tokenTransactionHistory'), 10)

String user1TotalTransactionCount = WebUI.getText(findTestObject('ValidateSecurityKey/Page_Farad Connect/label_totalTransactionCount_tokenTransactionHistory'))

WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_Logout'))

// User 2 login
loginKeywords.loginWithCredentials(securityKey.user2Name, securityKey.user2Pass)
WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_FTG Token'))

WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_tl-validate-btn'))

WebUI.setText(findTestObject('ValidateSecurityKey/Page_Farad Connect/input_Enter FTG Token Security Key for Verificat'), 
    user1SecurityKey)

WebUI.click(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_tl-vmodal-search'))
WebUI.waitForElementVisible(findTestObject('ValidateSecurityKey/Page_Farad Connect/label_totalTokenAmount_validation'), 10)

String validationTotalTransactionCount = WebUI.getText(findTestObject('ValidateSecurityKey/Page_Farad Connect/label_totalTransactionCount_validation'))
String validationTokenBalance = WebUI.getText(findTestObject('ValidateSecurityKey/Page_Farad Connect/label_totalTokenAmount_validation'))

WebUI.verifyMatch(validationTotalTransactionCount, user1TotalTransactionCount, false)
WebUI.verifyMatch(validationTokenBalance, user1TokenBalance, false)

WebUI.verifyElementClickable(findTestObject('ValidateSecurityKey/Page_Farad Connect/button_View transactions'))

WebUI.closeBrowser()


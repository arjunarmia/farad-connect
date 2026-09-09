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

def token = [
    retireAmount    : '0.10',
    tokenPrice      : '3',
    userName        : GlobalVariable.G_RECEIVER_NAME,
    userPass        : GlobalVariable.G_PASSWORD
]

double retireAmountNum = token.retireAmount.toDouble()
double tokenPriceNum   = token.tokenPrice.toDouble()
double TOLERANCE       = 0.001

LoginKeywords loginKeywords = new LoginKeywords()
loginKeywords.loginWithCredentials(token.userName, token.userPass)

WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_FTG Token'))
WebUI.click(findTestObject('RetireToken/Page_Farad Connect/button_tl-retire-btn'))

WebUI.setText(findTestObject('RetireToken/Page_Farad Connect/input_Enter amount to retire'), token.retireAmount)

String initialTokenBalanceText = WebUI.getText(findTestObject('RetireToken/Page_Farad Connect/label_initialTokenBalance'))
double initialTokenBalance = initialTokenBalanceText.replaceAll('[^0-9.]', '').toDouble()

WebUI.click(findTestObject('RetireToken/Page_Farad Connect/button_tl-rmodal-confirm'))

String retiredTokens = WebUI.getText(findTestObject('RetireToken/Page_Farad Connect/label_retiredTotal'))
double retiredTokensNum = retiredTokens.replaceAll('[^0-9.]', '').toDouble()

String retiredTokenValueText = WebUI.getText(findTestObject('RetireToken/Page_Farad Connect/label_retiredAmount'))
double retiredTokenValue = retiredTokenValueText.replaceAll('[^0-9.]', '').toDouble()

// verify retiredTokens * tokenPrice == retiredTokenValue
double expectedRetiredValue = retiredTokensNum * tokenPriceNum
WebUI.verifyMatch(
    String.valueOf(Math.abs(expectedRetiredValue - retiredTokenValue) < TOLERANCE),
    'true',
    false)

WebUI.click(findTestObject('RetireToken/Page_Farad Connect/button_tl-confirm-ok'))

WebUI.verifyElementClickable(findTestObject('RetireToken/Page_Farad Connect/button_success-download-link'))

WebUI.click(findTestObject('RetireToken/Page_Farad Connect/button_tokenActivity'))
WebUI.click(findTestObject('RetireToken/Page_Farad Connect/button_Retired _ Tokens'))

String totalRetiredToken = WebUI.getText(findTestObject('RetireToken/Page_Farad Connect/label_tokenActivity_retiredTokenAmount'))

WebUI.verifyMatch(totalRetiredToken, token.retireAmount, false)

WebUI.click(findTestObject('RetireToken/Page_Farad Connect/button_MyFTGTokens'))

String finalTokenBalanceText = WebUI.getText(findTestObject('RetireToken/Page_Farad Connect/label_finalTokenBalance'))
double finalTokenBalance = finalTokenBalanceText.replaceAll('[^0-9.]', '').toDouble()

// verify initial balance - retireAmount == final balance
double expectedFinalBalance = initialTokenBalance - retireAmountNum
WebUI.verifyMatch(
    String.valueOf(Math.abs(expectedFinalBalance - finalTokenBalance) < TOLERANCE),
    'true',
    false)

WebUI.closeBrowser()
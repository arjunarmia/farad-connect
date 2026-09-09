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
    sendAmount      : '0.10',
    recieverName    : GlobalVariable.G_RECEIVER_NAME,
	desc            : 'This is a test.',
    userName        : GlobalVariable.G_USERNAME,
    userPass        : GlobalVariable.G_PASSWORD
]

double sendAmountNum = token.sendAmount.toDouble()
double TOLERANCE       = 0.001

LoginKeywords loginKeywords = new LoginKeywords()
loginKeywords.loginWithCredentials(token.userName, token.userPass)

WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_FTG Token'))
WebUI.click(findTestObject('SendToken/Page_Farad Connect/button_My FTG Tokens'))
WebUI.click(findTestObject('SendToken/Page_Farad Connect/button_Send_Tokens'))

String initialTokenBalanceText = WebUI.getText(findTestObject('SendToken/Page_Farad Connect/label_sendSubscriberBalance'))
double initialTokenBalance = initialTokenBalanceText.replaceAll('[^0-9.]', '').toDouble()

WebUI.setText(findTestObject('SendToken/Page_Farad Connect/input_sendAmount'), token.sendAmount)
WebUI.setText(findTestObject('SendToken/Page_Farad Connect/input_recieverName'), token.recieverName)
WebUI.click(findTestObject('SendToken/Page_Farad Connect/selection_recieverName'))
WebUI.setText(findTestObject('SendToken/Page_Farad Connect/input_description'), token.desc)

WebUI.click(findTestObject('SendToken/Page_Farad Connect/button_send-submit-btn'))

Thread.sleep(2000)

String finalTokenBalanceText = WebUI.getText(findTestObject('SendToken/Page_Farad Connect/label_sendSubscriberBalance'))
double finalTokenBalance = finalTokenBalanceText.replaceAll('[^0-9.]', '').toDouble()

double expectedFinalBalance = initialTokenBalance - sendAmountNum

WebUI.verifyMatch(
	String.valueOf(Math.abs(finalTokenBalance - expectedFinalBalance) < TOLERANCE),
	'true',
	false)

WebUI.click(findTestObject('SendToken/Page_Farad Connect/button_Token Activity'))
String tokenActivityRecieverName = WebUI.getText(findTestObject('SendToken/Page_Farad Connect/label_recieverName_tokenActivity'))
String tokenActivitySendAmount = WebUI.getText(findTestObject('SendToken/Page_Farad Connect/label_sendAmount_tokenActivity'))
String tokenActivityDesc = WebUI.getText(findTestObject('SendToken/Page_Farad Connect/label_description_tokenActivity'))

WebUI.verifyMatch(tokenActivitySendAmount, token.sendAmount, false)
WebUI.verifyMatch(tokenActivityRecieverName.trim(), token.recieverName.trim(), false)
WebUI.verifyMatch(tokenActivityDesc.trim(), token.desc.trim(), false)

WebUI.closeBrowser()


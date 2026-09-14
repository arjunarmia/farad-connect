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
import farad.PINEntry
import farad.WithdrawMethod
import farad.YopmailKeywords
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

LoginKeywords loginKeywords = new LoginKeywords()
loginKeywords.login()

WebUI.click(findTestObject('DepositMoney/Page_Farad Connect/a_FTG Wallet'))
WebUI.click(findTestObject('WithdrawMoney/Page_Farad Connect/button_withdrawFund'))

WithdrawMethod withdrawMethod = new WithdrawMethod()
//withdrawMethod.withdrawACH()
withdrawMethod.withdrawWise()

WebUI.click(findTestObject('WithdrawMoney/Page_Farad Connect/button_Continue to review your withdrawal'))

WebUI.waitForElementClickable(findTestObject('DepositMoney/Page_Farad Connect/input_PIN digit 1'), 10)
WebUI.setText(findTestObject('DepositMoney/Page_Farad Connect/input_PIN digit 1'), GlobalVariable.G_TRANSACTION_PIN)

/* 
PINEntry pinEntry = new PINEntry()
pinEntry.enterDigitsOneByOne(GlobalVariable.G_TRANSACTION_PIN, 'DepositMoney/Page_Farad Connect/input_PIN digit ')
*/

WebUI.click(findTestObject('WithdrawMoney/Page_Farad Connect/button_Confirm Transaction'))

YopmailKeywords yopmailKeywords = new YopmailKeywords()
String otp = yopmailKeywords.fetchOtp(GlobalVariable.G_EMAIL)

WebUI.setText(findTestObject('WithdrawMoney/Page_Farad Connect/input_Digit 1'), otp)

WebUI.click(findTestObject('WithdrawMoney/Page_Farad Connect/button_vmodal-verify'))

WebUI.verifyElementClickable(findTestObject('WithdrawMoney/Page_Farad Connect/a_View Wallet'))

WebUI.closeBrowser()
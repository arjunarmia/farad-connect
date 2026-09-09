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
import farad.YopmailKeywords
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

String depositMoney = '0.1'

LoginKeywords loginKeywords = new LoginKeywords()
loginKeywords.login()

WebUI.click(findTestObject('DepositMoney/Page_Farad Connect/a_FTG Wallet'))

WebUI.click(findTestObject('DepositMoney/Page_Farad Connect/button_depositFund'))

WebUI.click(findTestObject('DepositMoney/Page_Farad Connect/input_0.00'))

WebUI.doubleClick(findTestObject('DepositMoney/Page_Farad Connect/input_0.00'))

WebUI.setText(findTestObject('DepositMoney/Page_Farad Connect/input_0.00'), depositMoney)

WebUI.click(findTestObject('DepositMoney/Page_Farad Connect/button_toStep2'))

WebUI.setText(findTestObject('DepositMoney/Page_Farad Connect/input_1234 5678 9012 3456'), GlobalVariable.G_CREDITCARD_NUMBER)

WebUI.setText(findTestObject('DepositMoney/Page_Farad Connect/input_MM _ YY'), GlobalVariable.G_CREDITCARD_EXPDATE)

WebUI.setText(findTestObject('DepositMoney/Page_Farad Connect/input_123'), GlobalVariable.G_CREDITCARD_CVV)

WebUI.setText(findTestObject('DepositMoney/Page_Farad Connect/input_John Doe'), GlobalVariable.G_CREDITCARD_NAME)

WebUI.setText(findTestObject('DepositMoney/Page_Farad Connect/input_123 Main Street'), GlobalVariable.G_ADDRESS)

WebUI.setText(findTestObject('DepositMoney/Page_Farad Connect/input_New York'), GlobalVariable.G_CITY)

WebUI.setText(findTestObject('DepositMoney/Page_Farad Connect/input_10001'), GlobalVariable.G_POSTALCODE)

WebUI.click(findTestObject('DepositMoney/Page_Farad Connect/button_purchaseCard'))

PINEntry pinEntry = new PINEntry()
pinEntry.enterDigitsOneByOne(GlobalVariable.G_TRANSACTION_PIN, 'DepositMoney/Page_Farad Connect/input_PIN digit ')

WebUI.click(findTestObject('DepositMoney/Page_Farad Connect/button_Confirm Transaction'))

YopmailKeywords yopmailKeywords = new YopmailKeywords()
String otp = yopmailKeywords.fetchOtp(GlobalVariable.G_EMAIL)

WebUI.setText(findTestObject('DepositMoney/Page_Farad Connect/input_Digit 1'), otp)

WebUI.click(findTestObject('DepositMoney/Page_Farad Connect/button_vmodal-verify'))
WebUI.verifyElementClickable(findTestObject('DepositMoney/Page_Farad Connect/a_View Wallet'))

WebUI.closeBrowser()

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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys

def sendMoney = [
    amount       : '0.1',
    receiverName : GlobalVariable.G_RECEIVER_NAME,
    message      : 'This is a test message'
]

LoginKeywords loginKeywords = new LoginKeywords()
loginKeywords.login()

WebUI.click(findTestObject('DepositMoney/Page_Farad Connect/a_FTG Wallet'))

WebUI.click(findTestObject('SendMoney/Page_Farad Connect/div_Send Money'))

WebUI.setText(findTestObject('SendMoney/Page_Farad Connect/input_0.00'), sendMoney.amount)
WebUI.setText(findTestObject('SendMoney/Page_Farad Connect/input_Search by name, email, or ID'), sendMoney.receiverName)

WebUI.click(findTestObject('SendMoney/Page_Farad Connect/sendMoney_receiverName_dropdown'))
WebUI.setText(findTestObject('SendMoney/Page_Farad Connect/textarea_Add a note for the recipient'), sendMoney.message)

WebUI.click(findTestObject('SendMoney/Page_Farad Connect/button_mainBtn'))

PINEntry pinEntry = new PINEntry()
pinEntry.enterDigitsOneByOne(GlobalVariable.G_TRANSACTION_PIN, 'DepositMoney/Page_Farad Connect/input_PIN digit ')

WebUI.click(findTestObject('SendMoney/Page_Farad Connect/button_Confirm Transaction'))


// Verify displayed amount
TestObject amountObject = findTestObject('SendMoney/Page_Farad Connect/span_spm-modal-amount')
WebUI.waitForElementVisible(amountObject, 10)

String displayedAmount = WebUI.getText(amountObject).trim()

// Remove currency symbol and commas
displayedAmount = displayedAmount
    .replace('$', '')
    .replace(',', '')
    .trim()

BigDecimal actualAmount = new BigDecimal(displayedAmount)
BigDecimal expectedAmount = new BigDecimal(sendMoney.amount.toString())

WebUI.verifyEqual(actualAmount, expectedAmount)

WebUI.verifyElementClickable(
    findTestObject('SendMoney/Page_Farad Connect/a_View Wallet')
)

WebUI.closeBrowser()
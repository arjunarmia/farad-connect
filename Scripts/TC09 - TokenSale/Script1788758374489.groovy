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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory
import farad.LoginKeywords


def tokenSale = [
    tokenAmount    : '0.1',
    tokenSellPrice : '3',
    user1Name      : GlobalVariable.G_USERNAME,
    user1Pass      : GlobalVariable.G_PASSWORD,
    user2Name      : GlobalVariable.G_RECEIVER_NAME,
    user2Pass      : GlobalVariable.G_PASSWORD
]

// BROWSER 1 — User 1 places the BUY order
LoginKeywords loginKeywords = new LoginKeywords()
loginKeywords.loginWithCredentials(tokenSale.user1Name, tokenSale.user1Pass)

WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_FTG Token'))
WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_tl-buy-btn'))
WebUI.setText(findTestObject('TokenSale/Page_Farad Connect/input_tokenBuyQuantity'), tokenSale.tokenAmount)

String unitPriceBuyText = WebUI.getAttribute(findTestObject('TokenSale/Page_Farad Connect/input_tokenBuyPrice'), 'value')
double unitPriceBuy = unitPriceBuyText.toDouble()
double expectedBuyTotal = unitPriceBuy * tokenSale.tokenAmount.toDouble()

String actualBuyTotalText = WebUI.getText(findTestObject('TokenSale/Page_Farad Connect/label_tokenBuyTotal'))
double actualBuyTotal = actualBuyTotalText.replaceAll('[^0-9.]', '').toDouble()
WebUI.verifyMatch(String.valueOf(actualBuyTotal), String.valueOf(expectedBuyTotal), false)

WebUI.click(findTestObject('TokenSale/Page_Farad Connect/label_tokenBuyTotal'))
WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_tl-bmodal-buy'))
WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_tl-confirm-ok'))

WebUI.closeBrowser()

// BROWSER 2 — User 2 places the matching SELL order
loginKeywords.loginWithCredentials(tokenSale.user2Name, tokenSale.user2Pass)

WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_FTG Token'))
WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_tl-sell-btn'))
WebUI.setText(findTestObject('TokenSale/Page_Farad Connect/input_tokenSellQuantity'), tokenSale.tokenAmount)
WebUI.setText(findTestObject('TokenSale/Page_Farad Connect/input_tokenSellPrice'), tokenSale.tokenSellPrice)

double expectedSellTotal = tokenSale.tokenSellPrice.toDouble() * tokenSale.tokenAmount.toDouble()
String actualSellTotalText = WebUI.getText(findTestObject('TokenSale/Page_Farad Connect/label_tokenSellTotal'))
double actualSellTotal = actualSellTotalText.replaceAll('[^0-9.]', '').toDouble()
WebUI.verifyMatch(String.valueOf(actualSellTotal), String.valueOf(expectedSellTotal), false)

WebUI.click(findTestObject('TokenSale/Page_Farad Connect/label_tokenSellTotal'))
WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_tl-smodal-sell'))
WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_tl-confirm-ok'))


// VERIFY — check the Orders tab in BOTH browsers
// --- User 2 (seller) — already active, check here first ---
WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_Orders'))
WebUI.verifyElementText(findTestObject('TokenSale/Page_Farad Connect/tokenOrders_Type'), 'Sell')
WebUI.verifyElementText(findTestObject('TokenSale/Page_Farad Connect/tokenOrders_quantity'), tokenSale.tokenAmount + '.00')
WebUI.verifyElementText(findTestObject('TokenSale/Page_Farad Connect/tokenOrders_price'), '$' + String.format('%.2f', actualSellTotal))
WebUI.verifyElementText(findTestObject('TokenSale/Page_Farad Connect/tokenOrders_status'), 'Completed')

String actualUserNameText = WebUI.getText(findTestObject('TokenSale/Page_Farad Connect/tokenOrders_userName'))
assert actualUserNameText.contains(tokenSale.user1Name) : "Expected order row to contain username '${tokenSale.user1Name}' but got '${actualUserNameText}'"

WebUI.closeBrowser()

// --- User 1 (buyer) — switch back and re-check ---
loginKeywords.loginWithCredentials(tokenSale.user1Name, tokenSale.user1Pass)
WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_FTG Token'))

WebUI.click(findTestObject('TokenSale/Page_Farad Connect/button_Orders'))
WebUI.refresh()
WebUI.verifyElementText(findTestObject('TokenSale/Page_Farad Connect/tokenOrders_Type'), 'Buy')
WebUI.verifyElementText(findTestObject('TokenSale/Page_Farad Connect/tokenOrders_quantity'), tokenSale.tokenAmount + '.00')
WebUI.verifyElementText(findTestObject('TokenSale/Page_Farad Connect/tokenOrders_status'), 'Completed')

actualUserNameText = WebUI.getText(findTestObject('TokenSale/Page_Farad Connect/tokenOrders_userName'))
assert actualUserNameText.contains(tokenSale.user2Name) : "Expected order row to contain username '${tokenSale.user2Name}' but got '${actualUserNameText}'"

WebUI.closeBrowser() 
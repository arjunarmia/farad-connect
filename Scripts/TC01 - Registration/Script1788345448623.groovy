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

import farad.YopmailKeywords
import internal.GlobalVariable
import net.bytebuddy.agent.builder.AgentBuilder.CircularityLock.Global

import org.openqa.selenium.Keys as Keys

String prefix = "KTester" + System.currentTimeMillis()

def newUser = [
    username : prefix,
    firstname: "KTester",
    lastname : System.currentTimeMillis().toString(),
    refCode  : "TOM2558",
    phoneNum : "9" + System.currentTimeMillis().toString().substring(4),
    email    : prefix + "@yopmail.com",
    password : "Test@123"
]

WebUI.openBrowser('')

WebUI.navigateToUrl(GlobalVariable.G_REGISTER_URL)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_Username'), newUser.username)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_First Name'), newUser.firstname)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_Last Name'), newUser.lastname)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_Enter Referral Code'), newUser.refCode)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_Phone Number'), newUser.phoneNum)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_Email'), newUser.email)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_Confirm Email'), newUser.email)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input__'), newUser.password)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input___1'), newUser.password)

WebUI.click(findTestObject('Registration/Page_Farad Connect/button_nextStepBtn'))

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_Card Number _'), GlobalVariable.G_CREDITCARD_NUMBER)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_MM _ YY'), GlobalVariable.G_CREDITCARD_EXPDATE)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_CVV _'), GlobalVariable.G_CREDITCARD_CVV)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_Name on Card _'), GlobalVariable.G_CREDITCARD_NAME)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_Address _'), GlobalVariable.G_ADDRESS)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_City _'), GlobalVariable.G_CITY)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_Zip_Postal Code _'), GlobalVariable.G_POSTALCODE)

WebUI.click(findTestObject('Registration/Page_Farad Connect/span_By selecting the monthly subscription optio'))

WebUI.click(findTestObject('Registration/Page_Farad Connect/button_registerSubmitBtn'))

YopmailKeywords yopmailKeywords = new YopmailKeywords()
String otp = yopmailKeywords.fetchOtp(newUser.email)

WebUI.setText(findTestObject('Registration/Page_Farad Connect/input_otpcode1'), otp)

// WebUI.click(findTestObject('Registration/Page_Farad Connect/button_verifyotp'))

WebUI.click(findTestObject('Registration/Page_Farad Connect/button_ifModalContinueBtn'))

WebUI.closeBrowser()

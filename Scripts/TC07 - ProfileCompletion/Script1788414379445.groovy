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


def userDetails = [
	firstName : 'KTester',
	lastName  : 'Tom',
	fullName  : 'KTester Tom',
	gender    : 'F',
	website   : 'https://www.youtube.com',
	address   : GlobalVariable.G_ADDRESS,
	country   : 'India',
	state     : 'Kerala',
	city      : GlobalVariable.G_CITY ,
	zipCode   : GlobalVariable.G_POSTALCODE
]

LoginKeywords loginKeywords = new LoginKeywords()
loginKeywords.login()

WebUI.click(findTestObject('ProfileCompletion/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('ProfileCompletion/Page_Farad Connect/a_My Profile'))
WebUI.click(findTestObject('ProfileCompletion/Page_Farad Connect/a_Edit Profile'))

WebUI.setText(findTestObject('ProfileCompletion/Page_Farad Connect/input_First Name _'), userDetails.firstName)
WebUI.setText(findTestObject('ProfileCompletion/Page_Farad Connect/input_Last Name _'), userDetails.lastName)

WebUI.click(findTestObject('ProfileCompletion/Page_Farad Connect/span_April 3, 2003'))
WebUI.click(findTestObject('ProfileCompletion/Page_Farad Connect/span_April 3, 2003_1'))

WebUI.selectOptionByValue(findTestObject('ProfileCompletion/Page_Farad Connect/select_Gender'), userDetails.gender, false)
WebUI.setText(findTestObject('ProfileCompletion/Page_Farad Connect/input_https_www.example.com'), userDetails.website)

WebUI.setText(findTestObject('ProfileCompletion/Page_Farad Connect/input_Address Line 1 _'), userDetails.address)
WebUI.setText(findTestObject('ProfileCompletion/Page_Farad Connect/input_Address Line 2'), userDetails.address)
WebUI.selectOptionByValue(findTestObject('ProfileCompletion/Page_Farad Connect/select_Country _'), userDetails.country, false)
WebUI.setText(findTestObject('ProfileCompletion/Page_Farad Connect/input_Zip Code _'), userDetails.zipCode)

WebUI.setText(findTestObject('ProfileCompletion/Page_Farad Connect/input_State or Province _'), userDetails.state)

WebUI.setText(findTestObject('ProfileCompletion/Page_Farad Connect/input_City _'), userDetails.city)

WebUI.click(findTestObject('ProfileCompletion/Page_Farad Connect/button_Save Changes'))

Thread.sleep(3000)
WebUI.back()

// Verify Phone Number
TestObject phoneObject = findTestObject('ProfileCompletion/Page_Farad Connect/profile_phoneNumber_label')
WebUI.waitForElementVisible(phoneObject, 10)

String phoneNum = WebUI.getText(phoneObject).trim()
WebUI.verifyEqual(phoneNum, GlobalVariable.G_PHONE_NUM)


// Verify Email
TestObject emailObject = findTestObject('ProfileCompletion/Page_Farad Connect/profile_email_label')
WebUI.waitForElementVisible(emailObject, 10)

String email = WebUI.getText(emailObject).trim()
WebUI.verifyEqual(email, GlobalVariable.G_EMAIL)


// Verify Full Name
TestObject fullName = findTestObject('ProfileCompletion/Page_Farad Connect/profile_fullName_label')
WebUI.waitForElementVisible(fullName, 10)

String name = WebUI.getText(fullName).trim()
WebUI.verifyEqual(name, userDetails.fullName)


// Verify Website
TestObject website = findTestObject('ProfileCompletion/Page_Farad Connect/profile_website_label')
WebUI.waitForElementVisible(website, 10)

String site = WebUI.getText(website).trim()
WebUI.verifyEqual(site, userDetails.website)


// Verify Gender
TestObject genderObj = findTestObject('ProfileCompletion/Page_Farad Connect/dd_Male')
WebUI.waitForElementVisible(genderObj, 10)

String gender = WebUI.getText(genderObj).trim()
String userGender = ""

if (userDetails.gender == 'M')	userGender = 'Male'
else if (userDetails.gender == 'F') userGender = 'Female'
else userGender = 'Other'

WebUI.verifyEqual(gender, userGender)


// Verify Address
TestObject addressObj = findTestObject('ProfileCompletion/Page_Farad Connect/profile_city_address')
WebUI.waitForElementVisible(addressObj, 10)

String address = WebUI.getText(addressObj).trim()
String fullUserAddress = userDetails.address +" "+ userDetails.address

WebUI.verifyEqual(address, fullUserAddress)


// Verify City
TestObject cityObj = findTestObject('ProfileCompletion/Page_Farad Connect/profile_city_label')
WebUI.waitForElementVisible(cityObj, 10)

String city = WebUI.getText(cityObj).trim()
WebUI.verifyEqual(city, userDetails.city)

// Verify Country
TestObject countryObj = findTestObject('ProfileCompletion/Page_Farad Connect/profile_country_label')
WebUI.waitForElementVisible(countryObj, 10)

String country = WebUI.getText(countryObj).trim()
WebUI.verifyEqual(country, userDetails.country)


// Verify Zipcode
TestObject zipObj = findTestObject('ProfileCompletion/Page_Farad Connect/profile_zipcode_label')
WebUI.waitForElementVisible(zipObj, 10)

String zip = WebUI.getText(zipObj).trim()
WebUI.verifyEqual(zip, userDetails.zipCode)


// Verify State
TestObject stateObj = findTestObject('ProfileCompletion/Page_Farad Connect/profile_state_label')
WebUI.waitForElementVisible(stateObj, 10)

String state = WebUI.getText(stateObj).trim()
WebUI.verifyEqual(state, userDetails.state)

WebUI.closeBrowser()

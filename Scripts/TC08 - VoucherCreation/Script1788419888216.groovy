import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import farad.LoginKeywords
import internal.GlobalVariable as GlobalVariable


def voucher = [
	name    : 'Katalon Voucher',
	num     : '2',
	newName : 'Testing Voucher'
]


LoginKeywords loginKeywords = new LoginKeywords()
loginKeywords.login()


WebUI.click(findTestObject('VoucherCreation/Page_Farad Connect/button_user-menu-btn'))
WebUI.click(findTestObject('VoucherCreation/Page_Farad Connect/a_Vouchers'))
WebUI.click(findTestObject('VoucherCreation/Page_Farad Connect/button_addVoucher'))

WebUI.setText(findTestObject('VoucherCreation/Page_Farad Connect/input_voucherNameCreation'), voucher.name)
WebUI.setText(findTestObject('VoucherCreation/Page_Farad Connect/input_Number of Vouchers'), voucher.num)

TestObject voucherURLObject = findTestObject('VoucherCreation/Page_Farad Connect/label_voucherCreationURL')
WebUI.waitForElementVisible(voucherURLObject, 10)
String voucherURL = WebUI.getAttribute(voucherURLObject, 'value').trim()
WebUI.comment("Created Voucher URL: ${voucherURL}")

WebUI.click(findTestObject('VoucherCreation/Page_Farad Connect/span_Link my Farad Code to this voucher'))
WebUI.click(findTestObject('VoucherCreation/Page_Farad Connect/button_voucher-create-btns'))

Thread.sleep(2000)

String createdVoucherCode = ""
def matcher = voucherURL =~ /[?&]vc=([^&]+)/
if (matcher.find()) {
    createdVoucherCode = matcher.group(1).trim()
}
WebUI.comment("Created Voucher Code: ${createdVoucherCode}")
WebUI.verifyNotEqual(createdVoucherCode, '')

TestObject topVoucherCodeObject = findTestObject(
	'VoucherCreation/Page_Farad Connect/label_voucherCode_List'
)

WebUI.waitForElementVisible(topVoucherCodeObject, 10)

String topVoucherCode = WebUI.getText(topVoucherCodeObject).trim()

WebUI.comment("Created Voucher Code: ${createdVoucherCode}")
WebUI.comment("Top Voucher Code: ${topVoucherCode}")

WebUI.verifyEqual(topVoucherCode, createdVoucherCode)


WebUI.click(findTestObject('VoucherCreation/Page_Farad Connect/button_Edit voucher'))
WebUI.setText(findTestObject('VoucherCreation/Page_Farad Connect/input_voucherNameCreation'), voucher.newName)
WebUI.click(findTestObject('VoucherCreation/Page_Farad Connect/button_Update voucher'))

Thread.sleep(2000)

TestObject topVoucherNameObject = findTestObject('VoucherCreation/Page_Farad Connect/label_voucherName_List')
WebUI.waitForElementVisible(topVoucherNameObject, 10)

String updatedVoucherName = WebUI.getText(topVoucherNameObject).trim()

WebUI.comment("Expected Voucher Name: ${voucher.newName}")
WebUI.comment("Top Voucher Name: ${updatedVoucherName}")

WebUI.verifyEqual(updatedVoucherName,voucher.newName)


WebUI.click(findTestObject('VoucherCreation/Page_Farad Connect/button_deleteVoucher'))

WebUI.click(findTestObject('VoucherCreation/Page_Farad Connect/button_voucherDeleteConfirm'))


Thread.sleep(2000)

TestObject newTopVoucherCodeObject = findTestObject('VoucherCreation/Page_Farad Connect/label_voucherCode_List')
WebUI.waitForElementVisible(newTopVoucherCodeObject, 10)
String newTopVoucherCode = WebUI.getText(newTopVoucherCodeObject).trim()

WebUI.comment("Deleted Voucher Code: ${createdVoucherCode}")
WebUI.comment("New Top Voucher Code: ${newTopVoucherCode}")

WebUI.verifyNotEqual(newTopVoucherCode,createdVoucherCode)

WebUI.closeBrowser()
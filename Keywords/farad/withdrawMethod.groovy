package farad

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

public class WithdrawMethod {

    def withdrawACH() {
        def data = [
            withdrawMoney : '5000',
            username       : 'KatalonTestUser',
            phoneNum       : '9999999999',
            accountNum     : '13621011904782',
            routingNum     : '215125125',
            bankName       : "Bank of Kutch"
        ]

        WebUI.setText(findTestObject('WithdrawMoney/Page_Farad Connect/input_0.00'), data.withdrawMoney)

        WebUI.click(findTestObject('WithdrawMoney/Page_Farad Connect/label_ACH Transfer  2-3 business days  account'))
        WebUI.setText(findTestObject('WithdrawMoney/Page_Farad Connect/input_Name on the account (spelling as it appear'), data.username)
        WebUI.setText(findTestObject('WithdrawMoney/Page_Farad Connect/input_Phone number (your phone number as it is r'), data.phoneNum)
        WebUI.setText(findTestObject('WithdrawMoney/Page_Farad Connect/input_Account Number _'), data.accountNum)
        WebUI.setText(findTestObject('WithdrawMoney/Page_Farad Connect/input_Re-enter Account Number _'), data.accountNum)
        WebUI.setText(findTestObject('WithdrawMoney/Page_Farad Connect/input_ACH Routing Number _'), data.routingNum)
        WebUI.setText(findTestObject('WithdrawMoney/Page_Farad Connect/input_Re-enter routing number'), data.routingNum)
        WebUI.selectOptionByValue(findTestObject('WithdrawMoney/Page_Farad Connect/select_Account Type _'), 'checking', false)
        WebUI.setText(findTestObject('WithdrawMoney/Page_Farad Connect/input_Bank Name _'), data.bankName)
        WebUI.setText(findTestObject('WithdrawMoney/Page_Farad Connect/input_Bank Address _'), data.bankName)
    }

    def withdrawWise() {
        def data = [
            withdrawMoney : '10',
            wiseLink       : "https://wise.com/pay/me/KTest"
        ]

        WebUI.setText(findTestObject('WithdrawMoney/Page_Farad Connect/input_0.00'), data.withdrawMoney)
        WebUI.click(findTestObject('WithdrawMoney/Page_Farad Connect/button_Wise_withdrawMoney'))
        WebUI.setText(findTestObject('WithdrawMoney/Page_Farad Connect/input_paymentAddress_wise_withdrawMoney'), data.wiseLink)
    }
}
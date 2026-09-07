package farad

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import internal.GlobalVariable as GlobalVariable

class LoginKeywords {

    @Keyword
    def login() {
        WebUI.openBrowser('')
        WebUI.navigateToUrl(GlobalVariable.G_URL)
        WebUI.setText(findTestObject('Login/Page_Farad Connect/input_Username'), GlobalVariable.G_USERNAME)
        WebUI.setText(findTestObject('Login/Page_Farad Connect/input__'), GlobalVariable.G_PASSWORD)
        WebUI.click(findTestObject('Login/Page_Farad Connect/button_btnLogin'))
    }

    // Overload if you ever need to test with different/invalid credentials
    @Keyword
    def loginWithCredentials(String username, String password) {
        WebUI.openBrowser('')
        WebUI.navigateToUrl(GlobalVariable.G_URL)
        WebUI.setText(findTestObject('Login/Page_Farad Connect/input_Username'), username)
        WebUI.setText(findTestObject('Login/Page_Farad Connect/input__'), password)
        WebUI.click(findTestObject('Login/Page_Farad Connect/button_btnLogin'))
    }
}
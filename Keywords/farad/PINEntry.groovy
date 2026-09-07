package farad

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

public class PINEntry {
	/**
	 * Types a numeric code one digit at a time into a sequence of test objects.
	 * objectPathPrefix must resolve to "<prefix>1", "<prefix>2", etc.
	 * e.g. 'DepositMoney/Page_Farad Connect/input_PIN digit '
	 */
	@Keyword
	def enterDigitsOneByOne(String code, String objectPathPrefix) {
		for (int i = 0; i < code.length(); i++) {
			String digit = code.substring(i, i + 1)
			def field = findTestObject(objectPathPrefix + (i + 1))
			WebUI.setText(field, digit)
		}
	}
}

package farad

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

public class PINEntry {
	/**
	 * Types a numeric code one digit at a time into a sequence of test objects.
	 * objectPathPrefix must resolve to "<prefix>1", "<prefix>2", etc.
	 * e.g. 'DepositMoney/Page_Farad Connect/input_PIN digit '
	 *
	 * Waits for each field to be genuinely clickable (visible, enabled,
	 * not mid-animation) before typing, to avoid ElementNotInteractableException
	 * when the PIN modal is still transitioning in.
	 */
	@Keyword
	def enterDigitsOneByOne(String code, String objectPathPrefix) {
		for (int i = 0; i < code.length(); i++) {
			String digit = code.substring(i, i + 1)
			def field = findTestObject(objectPathPrefix + (i + 1))

			// Wait for the field to actually be interactable, not just present.
			boolean isClickable = WebUI.waitForElementClickable(
				field,
				10,
				FailureHandling.OPTIONAL
			)

			if (!isClickable) {
				println("PIN digit " + (i + 1) + " field not clickable after wait — retrying once after a short delay.")
				WebUI.delay(1)
			}

			WebUI.setText(field, digit, FailureHandling.STOP_ON_FAILURE)
		}
	}
}
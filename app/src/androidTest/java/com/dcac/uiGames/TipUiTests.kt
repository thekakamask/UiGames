package com.dcac.uiGames

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.dcac.uiGames.ui.theme.UiGamesTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

class TipUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip() {
        composeTestRule.setContent {
            UiGamesTheme {
                Surface (modifier = Modifier.fillMaxSize()){
                    TipTimeApp(
                        widthSizeClass = WindowWidthSizeClass.Expanded,
                        amountInput = "50",
                        tipInput = "15",
                        roundUp = false,
                        context = LocalContext.current,
                        onAmountChange = {},
                        onTipChange = {},
                        onRoundUpChange = {}
                    )
                }
            }
        }
        composeTestRule.onNodeWithText("Bill Amount")
            .performTextInput("10")
        composeTestRule.onNodeWithText("Tip Percentage").performTextInput("20")
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        composeTestRule.onNodeWithText("Tip Amount: $expectedTip").assertExists(
            "No node with this text was found."
        )
    }

}
package com.dcac.uiGames

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dcac.uiGames.ui.theme.UiGamesTheme
import org.jetbrains.annotations.VisibleForTesting
import java.text.NumberFormat

class TipTimeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UiGamesTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                var amountInput by rememberSaveable { mutableStateOf("") }
                var tipInput by rememberSaveable { mutableStateOf("") }
                var roundUp by rememberSaveable { mutableStateOf(false) }
                val context = LocalContext.current
                Scaffold(topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Tip Time",
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.tertiaryContainer),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        TipTimeApp(
                            windowSizeClass.widthSizeClass,
                            amountInput = amountInput,
                            tipInput = tipInput,
                            roundUp = roundUp,
                            context = context,
                            onAmountChange = { amountInput = it },
                            onTipChange = { tipInput = it },
                            onRoundUpChange = { roundUp = it }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TipTimeApp(
    widthSizeClass: WindowWidthSizeClass,
    amountInput: String,
    tipInput: String,
    roundUp: Boolean,
    context: Context,
    onAmountChange: (String) -> Unit,
    onTipChange: (String) -> Unit,
    onRoundUpChange: (Boolean) -> Unit
) {
    when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> CompactTipLayout(amountInput, tipInput, roundUp, context, onAmountChange, onTipChange, onRoundUpChange)
        WindowWidthSizeClass.Medium -> MediumTipLayout(amountInput, tipInput, roundUp, context, onAmountChange, onTipChange, onRoundUpChange)
        WindowWidthSizeClass.Expanded -> ExpandedTipLayout(amountInput, tipInput, roundUp, context, onAmountChange, onTipChange, onRoundUpChange)
    }
}

@Composable
fun CompactTipLayout(
    amountInput: String,
    tipInput: String,
    roundUp: Boolean,
    context: Context,
    onAmountChange: (String) -> Unit,
    onTipChange: (String) -> Unit,
    onRoundUpChange: (Boolean) -> Unit
) {
    TipTimeContentLayout(amountInput, tipInput, roundUp, context, onAmountChange, onTipChange, onRoundUpChange)
}

@Composable
fun MediumTipLayout(
    amountInput: String,
    tipInput: String,
    roundUp: Boolean,
    context: Context,
    onAmountChange: (String) -> Unit,
    onTipChange: (String) -> Unit,
    onRoundUpChange: (Boolean) -> Unit
) {
    TipTimeContentLayout(amountInput, tipInput, roundUp, context, onAmountChange, onTipChange, onRoundUpChange)
}

@Composable
fun ExpandedTipLayout(
    amountInput: String,
    tipInput: String,
    roundUp: Boolean,
    context: Context,
    onAmountChange: (String) -> Unit,
    onTipChange: (String) -> Unit,
    onRoundUpChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            TipTimeContentLeft(amountInput, tipInput, roundUp, onAmountChange, onTipChange, onRoundUpChange)
        }


        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TipTimeContentRight(context, calculateTip(amountInput.toDoubleOrNull() ?: 0.0, tipInput.toDoubleOrNull() ?: 0.0, roundUp))
        }
    }
}

@Composable
fun TipTimeContentLayout(
    amountInput: String,
    tipInput: String,
    roundUp: Boolean,
    context: Context,
    onAmountChange: (String) -> Unit,
    onTipChange: (String) -> Unit,
    onRoundUpChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TipTimeContentLeft(amountInput, tipInput, roundUp, onAmountChange, onTipChange, onRoundUpChange)
        TipTimeContentRight(context, calculateTip(amountInput.toDoubleOrNull() ?: 0.0, tipInput.toDoubleOrNull() ?: 0.0, roundUp))
    }
}

@Composable
fun TipTimeContentLeft(
    amountInput: String,
    tipInput: String,
    roundUp: Boolean,
    onAmountChange: (String) -> Unit,
    onTipChange: (String) -> Unit,
    onRoundUpChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier.padding(bottom = 16.dp, top = 40.dp)
        )
        EditNumberField(
            label = R.string.bill_amount,
            leadingIcon = R.drawable.money_30,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = amountInput,
            onValueChange = onAmountChange,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        EditNumberField(
            label = R.string.how_was_the_service,
            leadingIcon = R.drawable.pourcentage_30,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = tipInput,
            onValueChange = onTipChange,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = onRoundUpChange,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}

@Composable
fun TipTimeContentRight(
    context: Context,
    tip: String
) {
    Text(
        text = stringResource(R.string.tip_amount, tip),
        style = MaterialTheme.typography.displaySmall,
        modifier = Modifier.padding(end = 16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        modifier = Modifier.padding(8.dp),
        onClick = {
            val intent = Intent(context, WelcomeActivity::class.java)
            context.startActivity(intent)
        },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.button_home),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.go_to_home),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = label)) },
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), contentDescription = null) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.round_up_tip))
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = roundUp,
            onCheckedChange = onRoundUpChanged
        )
    }
}

@VisibleForTesting
internal fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
    var tip = tipPercent / 100 * amount
    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun TipTimeAppPreview() {
    UiGamesTheme {
        TipTimeApp(
            widthSizeClass = WindowWidthSizeClass.Compact,
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
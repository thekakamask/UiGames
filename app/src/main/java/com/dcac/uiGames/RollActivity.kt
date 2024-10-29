package com.dcac.uiGames

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dcac.uiGames.ui.theme.UiGamesTheme

class RollActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UiGamesTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                var result by rememberSaveable { mutableIntStateOf(1) }
                val context = LocalContext.current
                Scaffold(topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Dice Roller",
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
                        when (windowSizeClass.widthSizeClass) {
                            WindowWidthSizeClass.Compact -> CompactDiceLayout(result, onRollDice = { result = it }, context)
                            WindowWidthSizeClass.Medium -> MediumDiceLayout(result, onRollDice = { result = it }, context)
                            WindowWidthSizeClass.Expanded -> ExpandedDiceLayout(result, onRollDice = { result = it }, context)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CompactDiceLayout(result: Int, onRollDice: (Int) -> Unit, context : Context) {  // Width < 600dp (Phone in portrait)
    DiceWithButtonAndImageLayout(
        result = result,
        onRollDice = onRollDice,
        context = context,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun MediumDiceLayout(result: Int, onRollDice: (Int) -> Unit, context : Context) { // 600 < Width < 840dp (Tablet in portrait, and Foldable in portrait(unfolded))
    DiceWithButtonAndImageLayout(
        result = result,
        onRollDice = onRollDice,
        context = context,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun ExpandedDiceLayout(result: Int, onRollDice: (Int) -> Unit, context : Context) { // Phone in landscape, Tablet in landscape, Foldable in landscape (unfolded), Desktop
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val diceImage = when (result) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        // Image du dé à gauche
        Image(
            painter = painterResource(diceImage),
            contentDescription = "dice_image",
            modifier = Modifier.size(200.dp)
        )

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onRollDice((1..6).random()) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.button_dice),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.roll), fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
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
    }
}

@Composable
fun DiceWithButtonAndImageLayout(result: Int, onRollDice: (Int) -> Unit, context : Context, modifier: Modifier = Modifier) {

    val diceImage = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(diceImage), contentDescription = result.toString())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onRollDice((1..6).random()) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.button_dice),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.roll),
                    fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
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
}

@Preview(showBackground = true)
@Composable
fun DiceRollerAppPreview() {
    UiGamesTheme {
        ExpandedDiceLayout(result = 1, onRollDice = {}, context = LocalContext.current)
    }
}
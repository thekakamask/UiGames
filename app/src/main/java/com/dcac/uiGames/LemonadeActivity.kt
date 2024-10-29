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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dcac.uiGames.ui.theme.UiGamesTheme

class LemonadeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UiGamesTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                var currentStep by rememberSaveable { mutableIntStateOf(1) }
                var squeezeCount by rememberSaveable { mutableIntStateOf(0) }
                val context = LocalContext.current

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "Lemonade",
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
                        LemonadeApp(
                            windowSizeClass.widthSizeClass,
                            currentStep = currentStep,
                            squeezeCount = squeezeCount,
                            context,
                            onStepChange = { step, count ->
                                currentStep = step
                                squeezeCount = count
                            }
                        )
                    }
                    }
                }
            }
        }
    }

@Composable
fun LemonadeApp(
    widthSizeClass: WindowWidthSizeClass,
    currentStep: Int,
    squeezeCount: Int,
    context: Context,
    onStepChange: (Int, Int) -> Unit
) {
    when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> CompactLemonadeLayout(currentStep, squeezeCount, context, onStepChange)
        WindowWidthSizeClass.Medium -> MediumLemonadeLayout(currentStep, squeezeCount, context, onStepChange)
        WindowWidthSizeClass.Expanded -> ExpandedLemonadeLayout(currentStep, squeezeCount, context, onStepChange)
    }
}

@Composable
fun CompactLemonadeLayout(currentStep: Int, squeezeCount: Int, context: Context, onStepChange: (Int, Int) -> Unit) {
    LemonadeContentLayout(currentStep, squeezeCount, context, onStepChange)
}

@Composable
fun MediumLemonadeLayout(currentStep: Int, squeezeCount: Int, context: Context, onStepChange: (Int, Int) -> Unit) {
    LemonadeContentLayout(currentStep, squeezeCount, context, onStepChange)
}

@Composable
fun ExpandedLemonadeLayout(
    currentStep: Int,
    squeezeCount: Int,
    context: Context,
    onStepChange: (Int, Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side: Lemonade steps
        Column(
            modifier = Modifier.weight(1f),  // This ensures that the left side takes half the available space
            verticalArrangement = Arrangement.Center,  // Center content vertically in the Column
            horizontalAlignment = Alignment.CenterHorizontally  // Center content horizontally
        ) {
            when (currentStep) {
                1 -> LemonTextAndImage(
                    textLabelResourceId = R.string.lemon_tree_tap,
                    drawableResourceId = R.drawable.lemon_tree,
                    contentDescriptionResourceId = R.string.lemon_tree,
                    onImageClick = {
                        onStepChange(2, (2..4).random())
                    }
                )
                2 -> LemonTextAndImage(
                    textLabelResourceId = R.string.lemon_tap,
                    drawableResourceId = R.drawable.lemon_squeeze,
                    contentDescriptionResourceId = R.string.lemon,
                    onImageClick = {
                        val newSqueezeCount = squeezeCount - 1
                        if (newSqueezeCount == 0) {
                            onStepChange(3, 0)
                        } else {
                            onStepChange(2, newSqueezeCount)
                        }
                    }
                )
                3 -> LemonTextAndImage(
                    textLabelResourceId = R.string.lemonade_tap,
                    drawableResourceId = R.drawable.lemon_drink,
                    contentDescriptionResourceId = R.string.lemonade,
                    onImageClick = {
                        onStepChange(4, 0)
                    }
                )
                4 -> LemonTextAndImage(
                    textLabelResourceId = R.string.empty_glass_tap,
                    drawableResourceId = R.drawable.lemon_restart,
                    contentDescriptionResourceId = R.string.empty_glass_tap,
                    onImageClick = {
                        onStepChange(1, 0)
                    }
                )
            }
        }

        // Right side: Button centered in the column
        Column(
            modifier = Modifier
                .weight(1f)  // This ensures that the right side takes half the available space
                .fillMaxHeight(),  // Ensure the column takes up all vertical space
            verticalArrangement = Arrangement.Center,  // Center the button vertically
            horizontalAlignment = Alignment.CenterHorizontally  // Center the button horizontally
        ) {
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
    }
}

@Composable
fun LemonadeContentLayout(
    currentStep: Int,
    squeezeCount: Int,
    context: Context,
    onStepChange: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        when (currentStep) {
            1 -> LemonTextAndImage(
                textLabelResourceId = R.string.lemon_tree_tap,
                drawableResourceId = R.drawable.lemon_tree,
                contentDescriptionResourceId = R.string.lemon_tree,
                onImageClick = {
                    onStepChange(2, (2..4).random())
                }
            )
            2 -> LemonTextAndImage(
                textLabelResourceId = R.string.lemon_tap,
                drawableResourceId = R.drawable.lemon_squeeze,
                contentDescriptionResourceId = R.string.lemon,
                onImageClick = {
                    val newSqueezeCount = squeezeCount - 1
                    if (newSqueezeCount == 0) {
                        onStepChange(3, 0)
                    } else {
                        onStepChange(2, newSqueezeCount)
                    }
                }
            )
            3 -> LemonTextAndImage(
                textLabelResourceId = R.string.lemonade_tap,
                drawableResourceId = R.drawable.lemon_drink,
                contentDescriptionResourceId = R.string.lemonade,
                onImageClick = {
                    onStepChange(4, 0)
                }
            )
            4 -> LemonTextAndImage(
                textLabelResourceId = R.string.empty_glass_tap,
                drawableResourceId = R.drawable.lemon_restart,
                contentDescriptionResourceId = R.string.empty_glass_tap,
                onImageClick = {
                    onStepChange(1, 0)
                }
            )
        }

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
}

@Composable
fun LemonTextAndImage(
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onImageClick,
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Image(
                    painter = painterResource(drawableResourceId),
                    contentDescription = stringResource(contentDescriptionResourceId),
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.button_image_width))
                        .height(dimensionResource(R.dimen.button_image_height))
                        .padding(dimensionResource(R.dimen.button_interior_padding))
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_vertical)))
            Text(
                text = stringResource(textLabelResourceId),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    UiGamesTheme {
        LemonadeApp(
            widthSizeClass = WindowWidthSizeClass.Compact,
            currentStep = 1,
            squeezeCount = 0,
            context = LocalContext.current,
            onStepChange = { _, _ -> }
        )
    }
}
package com.dcac.uiGames

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dcac.uiGames.model.Paint
import com.dcac.uiGames.ui.theme.UiGamesTheme

class ArtGalleryActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UiGamesTheme {
                val isSlideInProgress = rememberSaveable { mutableStateOf(false) }
                val currentPaintingIndex = rememberSaveable { mutableStateOf(0) }
                val showTooltip = rememberSaveable { mutableStateOf(false) }
                val context = LocalContext.current
                val windowSizeClass = calculateWindowSizeClass(this)

                Scaffold(topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Art Gallery",
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.tertiaryContainer),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        ArtGalleryApp(
                            widthSizeClass = windowSizeClass.widthSizeClass,
                            currentPaintingIndex = currentPaintingIndex,
                            onPaintingIndexChange = { newIndex ->
                                if (newIndex != currentPaintingIndex.value) {
                                    println("Changing index : ${currentPaintingIndex.value} to $newIndex")
                                    currentPaintingIndex.value = newIndex
                                }
                            },
                            showTooltip = showTooltip.value,
                            onToggleTooltip = { showTooltip.value = !showTooltip.value },
                            isSlideInProgress = isSlideInProgress,
                            context = context
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArtGalleryApp(
    widthSizeClass: WindowWidthSizeClass,
    currentPaintingIndex: MutableState<Int>,
    showTooltip: Boolean,
    isSlideInProgress: MutableState<Boolean>,
    context: Context,
    onPaintingIndexChange: (Int) -> Unit,
    onToggleTooltip: () -> Unit
) {
    val paintings = Paint.getPaintings()
    // Log for follow paint index
    println("ArtGalleryApp Actual index for the paint : $currentPaintingIndex")
    val currentPainting = paintings[currentPaintingIndex.value]
    // Log for display actual paint data
    println("ArtGalleryApp Actual paint : ${currentPainting.title}, Artist : ${currentPainting.artist}, Years : ${currentPainting.year}")


    when (widthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            ExpandedArtGalleryLayout(
                currentPainting = currentPainting,
                currentPaintingIndex = currentPaintingIndex,
                paintings = paintings,
                isSlideInProgress = isSlideInProgress,
                onPaintingIndexChange = onPaintingIndexChange,
                showTooltip = showTooltip,
                onToggleTooltip = onToggleTooltip,
                context = context
            )
        }
        else -> {
            CompactArtGalleryLayout(
                currentPainting = currentPainting,
                currentPaintingIndex = currentPaintingIndex,
                paintings = paintings,
                isSlideInProgress = isSlideInProgress,
                onPaintingIndexChange = onPaintingIndexChange,
                showTooltip = showTooltip,
                onToggleTooltip = onToggleTooltip,
                context = context
            )
        }
    }
}

@Composable
fun ExpandedArtGalleryLayout(
    currentPainting: Paint,
    currentPaintingIndex: MutableState<Int>,
    paintings: List<Paint>,
    isSlideInProgress: MutableState<Boolean>,
    onPaintingIndexChange: (Int) -> Unit,
    showTooltip: Boolean,
    onToggleTooltip: () -> Unit,
    context: Context
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        if (!isSlideInProgress.value) {
                            isSlideInProgress.value = true
                        }
                    },
                    onDragEnd = {
                        isSlideInProgress.value = false
                        println("Slide finish, actual index: ${currentPaintingIndex.value}")
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        if (isSlideInProgress.value) {
                            when {
                                dragAmount > 0 && currentPaintingIndex.value > 0 -> {
                                    println("Right slide, new index : ${currentPaintingIndex.value - 1}")
                                    onPaintingIndexChange(currentPaintingIndex.value - 1)
                                }
                                dragAmount < 0 && currentPaintingIndex.value < paintings.size - 1 -> {
                                    println("Left slide, new index : ${currentPaintingIndex.value + 1}")
                                    onPaintingIndexChange(currentPaintingIndex.value + 1)
                                }
                            }
                            // Lock for avoid multiples changes during the same movement
                            isSlideInProgress.value = false
                        }
                    }
                )
            },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .shadow(8.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = { onToggleTooltip() })
                    }
            ) {
                Image(
                    painter = painterResource(currentPainting.image),
                    contentDescription = "painting_display",
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onTertiary)
                        .padding(16.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ArtDescription(currentPainting)
            Spacer(modifier = Modifier.height(16.dp))
            ArtControl(
                onNextClick = {
                    if (currentPaintingIndex.value < paintings.size - 1) {
                        onPaintingIndexChange(currentPaintingIndex.value + 1)
                    }
                },
                onPreviousClick = {
                    if (currentPaintingIndex.value > 0) {
                        onPaintingIndexChange(currentPaintingIndex.value - 1)
                    }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            GoHomeButton(context = context)
        }
    }

    if (showTooltip) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Tooltip(text = currentPainting.description)
        }
    }
}

@Composable
fun CompactArtGalleryLayout(
    currentPainting: Paint,
    currentPaintingIndex: MutableState<Int>,
    paintings: List<Paint>,
    isSlideInProgress: MutableState<Boolean>,
    onPaintingIndexChange: (Int) -> Unit,
    showTooltip: Boolean,
    onToggleTooltip: () -> Unit,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        if (!isSlideInProgress.value) {
                            isSlideInProgress.value = true
                        }
                    },
                    onDragEnd = {
                        isSlideInProgress.value = false
                        println("Slide finish, actual index : ${currentPaintingIndex.value}")
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        if (isSlideInProgress.value) {
                            when {
                                dragAmount > 0 && currentPaintingIndex.value > 0 -> {
                                    println("Right slide, new index : ${currentPaintingIndex.value - 1}")
                                    onPaintingIndexChange(currentPaintingIndex.value - 1)
                                }
                                dragAmount < 0 && currentPaintingIndex.value < paintings.size - 1 -> {
                                    println("Left slide, new index : ${currentPaintingIndex.value + 1}")
                                    onPaintingIndexChange(currentPaintingIndex.value + 1)
                                }
                            }
                            // Lock for avoid multiples changes during the same movement
                            isSlideInProgress.value = false
                        }
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ArtImage(
            currentPainting.image,
            onImageClick = onToggleTooltip
        )
        ArtDescription(currentPainting)
        ArtControl(
            onNextClick = {
                if (currentPaintingIndex.value < paintings.size - 1) {
                    onPaintingIndexChange(currentPaintingIndex.value + 1)
                }
            },
            onPreviousClick = {
                if (currentPaintingIndex.value > 0) {
                    onPaintingIndexChange(currentPaintingIndex.value - 1)
                }
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        GoHomeButton(context = context)
    }

    if (showTooltip) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Tooltip(text = currentPainting.description)
        }
    }
}

@Composable
fun ArtImage(image: Int, onImageClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .shadow(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onImageClick() })
            }
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "painting_display",
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onTertiary)
                .padding(16.dp)
        )
    }
}

@Composable
fun GoHomeButton(context: Context) {
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
fun ArtDescription(paint: Paint) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 50.dp, bottom = 20.dp)
            .shadow(8.dp)
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = paint.title,
            fontSize = 25.sp,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp)
        )
        Row(modifier = Modifier) {
            Text(
                text = paint.artist,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
            )
            Text(
                text = "(${paint.year})",
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}

@Composable
fun ArtControl(onNextClick: () -> Unit, onPreviousClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onPreviousClick,
            enabled = true,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.button_art),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.previous_artwork),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Button(
            onClick = onNextClick,
            enabled = true,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.button_art),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.next_artwork),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun Tooltip(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Text(
                text = text,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtGalleryAppPreview() {
    UiGamesTheme {
        ArtGalleryApp(
            widthSizeClass = WindowWidthSizeClass.Compact,
            currentPaintingIndex = remember { mutableStateOf(0) },
            showTooltip = false,
            isSlideInProgress = remember { mutableStateOf(false) },  // Add sliding argument
            context = LocalContext.current,
            onPaintingIndexChange = {},
            onToggleTooltip = {}
        )
    }
}
package com.dcac.uiGames

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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UiGamesTheme {
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
                }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.tertiaryContainer),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        ArtGalleryApp()
                    }
                }
            }
        }
    }
}

@Composable
fun ArtGalleryApp() {

    // Récupérer la liste des peintures
    val paintings = Paint.getPaintings()

    // Créer un état pour suivre l'indice de la peinture actuelle
    var currentPaintingIndex by remember { mutableIntStateOf(0) }

    // Gérer l'état de l'infobulle
    var showTooltip by remember { mutableStateOf(false) }
    val currentPainting = paintings[currentPaintingIndex]

    // Créer un état pour bloquer les glissements répétés
    var isSlideInProgress by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Colonne principale avec les peintures et les contrôles
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .safeDrawingPadding()
            .statusBarsPadding()
            .fillMaxSize()
            // Ajout du détecteur de gestes pour le glissement horizontal
            // Ajout du détecteur de gestes pour le glissement horizontal
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        // Définir isSlideInProgress sur false une fois le glissement terminé
                        isSlideInProgress = false
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        // Empêcher les slides multiples avant qu'une action soit terminée
                        if (!isSlideInProgress) {
                            isSlideInProgress = true // Bloquer d'autres slides jusqu'à la fin du traitement

                            when {
                                dragAmount > 0 -> {
                                    // Glissement vers la droite (peinture précédente)
                                    if (currentPaintingIndex > 0) {
                                        currentPaintingIndex--
                                    }
                                }
                                dragAmount < 0 -> {
                                    // Glissement vers la gauche (peinture suivante)
                                    if (currentPaintingIndex < paintings.size - 1) {
                                        currentPaintingIndex++
                                    }
                                }
                            }
                        }
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ArtImage(
            currentPainting.image,
            onImageClick = {
                // Alterner l'affichage de l'infobulle
                showTooltip = !showTooltip
            }
        )
        ArtDescription(currentPainting)
        ArtControl(
            onNextClick = {
                if (currentPaintingIndex < paintings.size - 1) {
                    currentPaintingIndex++
                }
            },
            onPreviousClick = {
                if (currentPaintingIndex > 0) {
                    currentPaintingIndex--
                }
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = {
                val intent = Intent(context, WelcomeActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Icône à gauche
                Icon(
                    painter = painterResource(id = R.drawable.button_home),
                    contentDescription = null, // description de l'icône
                    modifier = Modifier.size(30.dp) // Taille de l'icône
                )

                // Espacement entre l'icône et le texte
                Spacer(modifier = Modifier.width(8.dp))

                // Texte du bouton
                Text(
                    text = stringResource(R.string.go_to_home),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // Affichage de l'infobulle en overlay sans affecter la mise en page principale
    if (showTooltip) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center // Aligne l'infobulle au centre de l'écran
        ) {
            Tooltip(text = currentPainting.description)
        }
    }
}
@Composable
fun ArtImage(image: Int, onImageClick: () -> Unit) {
    Box(
        modifier = Modifier
            .shadow(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    // Appel de la fonction de clic sur l'image
                    onImageClick()
                })
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
fun Tooltip(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize() // Remplir tout l'écran
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Text(
                text = text,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ArtDescription(paint : Paint) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp,top= 50.dp, bottom = 20.dp)
            .shadow(8.dp)
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = paint.title,
            fontSize = 25.sp,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp)
        )
        Row(modifier=Modifier) {
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
        Button(onClick = onPreviousClick,
            enabled = true,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Icône à gauche
                Icon(
                    painter = painterResource(id = R.drawable.button_art),
                    contentDescription = null, // description de l'icône
                    modifier = Modifier.size(30.dp) // Taille de l'icône
                )

                // Espacement entre l'icône et le texte
                Spacer(modifier = Modifier.width(8.dp))

                // Texte du bouton
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
                // Icône à gauche
                Icon(
                    painter = painterResource(id = R.drawable.button_art),
                    contentDescription = null, // description de l'icône
                    modifier = Modifier.size(30.dp) // Taille de l'icône
                )

                // Espacement entre l'icône et le texte
                Spacer(modifier = Modifier.width(8.dp))

                // Texte du bouton
                Text(
                    text = stringResource(R.string.next_artwork),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtGalleryAppPreview() {
    UiGamesTheme {
        ArtGalleryApp()
    }
}
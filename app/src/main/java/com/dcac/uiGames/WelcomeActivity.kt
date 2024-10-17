package com.dcac.uiGames

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dcac.uiGames.ui.theme.UiGamesTheme

class WelcomeActivity : ComponentActivity() {
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
                                text = "Home",
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
                        WelcomeApp()
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeApp(){
    val context = LocalContext.current
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()
        .statusBarsPadding()
        .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly) {

        Column(modifier=Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(R.drawable.welcome_ui_games),
                contentDescription = "welcome_ui_games",
                modifier = Modifier
                    .padding(16.dp)
            )
            /*Text(stringResource(R.string.welcome_to_ui_games),
                fontSize = 25.sp)
            Text(stringResource(R.string.choose_activity),
                fontSize = 20.sp)*/
        }

        Column(modifier=Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {


            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    val intent = Intent(context, RollActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Icône à gauche
                    Icon(
                        painter = painterResource(id = R.drawable.button_dice),
                        contentDescription = null, // description de l'icône
                        modifier = Modifier.size(30.dp) // Taille de l'icône
                    )

                    // Espacement entre l'icône et le texte
                    Spacer(modifier = Modifier.width(8.dp))

                    // Texte du bouton
                    Text(
                        text = stringResource(R.string.go_to_dice_roller),
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    val intent = Intent(context, LemonadeActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Icône à gauche
                    Icon(
                        painter = painterResource(id = R.drawable.button_limonade),
                        contentDescription = null, // description de l'icône
                        modifier = Modifier.size(30.dp)// Taille de l'icône
                    )

                    // Espacement entre l'icône et le texte
                    Spacer(modifier = Modifier.width(8.dp))

                    // Texte du bouton
                    Text(
                        text = stringResource(R.string.go_to_lemonade_creation),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    val intent = Intent(context, TipTimeActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Icône à gauche
                    Icon(
                        painter = painterResource(id = R.drawable.button_tip),
                        contentDescription = null, // description de l'icône
                        modifier = Modifier.size(30.dp) // Taille de l'icône
                    )

                    // Espacement entre l'icône et le texte
                    Spacer(modifier = Modifier.width(8.dp))

                    // Texte du bouton
                    Text(
                        text = stringResource(R.string.go_to_tip_time),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    val intent = Intent(context, ArtGalleryActivity::class.java)
                    context.startActivity(intent)
                },
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
                        text = stringResource(R.string.go_to_art_gallery),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeAppPreview() {
    UiGamesTheme {
        WelcomeApp()
    }
}
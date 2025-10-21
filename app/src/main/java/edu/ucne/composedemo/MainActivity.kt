package edu.ucne.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.composedemo.Presentation.Jugador.JugadorScreen
import edu.ucne.composedemo.Presentation.Partida.PartidasListScreen
import edu.ucne.composedemo.ui.theme.TicTacToePlayersTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToePlayersTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val nav = rememberNavController()
                    NavHost(navController = nav, startDestination = "partidas") {
                        composable("partidas") {
                            PartidasListScreen(goToPartida = { id -> nav.navigate("juego/$id") })
                        }
                        composable(
                            route = "juego/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) { backStack ->
                            val id = backStack.arguments?.getInt("id")
                            JugadorScreen(partidaIdArg = id)
                        }
                    }
                }
            }
        }
    }
}

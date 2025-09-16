package edu.ucne.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.composedemo.Presentation.Navigation.Screen
import edu.ucne.composedemo.ui.theme.TicTacToePlayersTheme
import edu.ucne.composedemo.Presentation.Jugador.JugadorListScreen
import edu.ucne.composedemo.Presentation.Jugador.JugadorScreen
import edu.ucne.composedemo.Presentation.Partidas.PartidaListScreen
import edu.ucne.composedemo.Presentation.Partidas.PartidaFormScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToePlayersTheme {
                val nav = rememberNavController()

                NavHost(navController = nav, startDestination = Screen.List) {

                    composable<Screen.List> {
                        JugadorListScreen(
                            goToJugadores = { id -> nav.navigate(Screen.Jugador(id)) },
                            createJugador = { nav.navigate(Screen.Jugador(null)) }
                        )
                    }

                    composable<Screen.Jugador> { backStackEntry ->
                        val args = backStackEntry.toRoute<Screen.Jugador>()
                        JugadorScreen(
                            jugadorId = args.Id,
                            goback = { nav.popBackStack() },
                            id = args.Id
                        )
                    }

                    
                    composable<Screen.Partidas> {
                        PartidaListScreen(
                            onNueva = { nav.navigate(Screen.Partida(null)) },
                            onEditar = { id -> nav.navigate(Screen.Partida(id)) }
                        )
                    }

                    composable<Screen.Partida> { backStackEntry ->
                        val args = backStackEntry.toRoute<Screen.Partida>()
                        PartidaFormScreen(
                            partidaId = args.id,
                            onBack = { nav.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

package edu.ucne.jugadorestictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.ucne.jugadorestictactoe.Presentation.Jugador.JugadorListScreen
import edu.ucne.jugadorestictactoe.Presentation.Jugador.JugadorScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "jugadores"
                    ) {
                        composable("jugadores") {
                            JugadorListScreen(
                                goToJugadores = { id ->
                                    navController.navigate("jugador/$id")
                                },
                                createJugador = {
                                    navController.navigate("jugador/0")
                                }
                            )
                        }
                        composable(
                            route = "jugador/{jugadorId}",
                            arguments = listOf(
                                navArgument("jugadorId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val jugadorId = backStackEntry.arguments?.getInt("jugadorId")
                            JugadorScreen(
                                jugadorId = jugadorId,
                                goback = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

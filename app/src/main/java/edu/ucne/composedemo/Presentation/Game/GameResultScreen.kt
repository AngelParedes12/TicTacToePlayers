package edu.ucne.composedemo.Presentation.Game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun GameResultScreen(
    outcome: GameOutcome,
    partidaId: Int,
    onPlayAgain: () -> Unit,
    onNewPartida: () -> Unit,
    onBackToList: () -> Unit
) {
    val isWin = outcome is GameOutcome.Win
    val title = when (outcome) {
        is GameOutcome.Win -> "¡Ganó ${outcome.player}!"
        GameOutcome.Draw -> "¡Empate!"
    }
    val sub = "Partida #$partidaId"
    val accent = if (isWin) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(650, easing = FastOutSlowInEasing),
        label = "scaleIn"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .background(
                        MaterialTheme.colorScheme.background.copy(alpha = 0.9f),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(24.dp)
                    .scale(scale),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = accent,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = sub,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = onPlayAgain,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxSize(fraction = 0.0f),
                    colors = ButtonDefaults.buttonColors(containerColor = accent)
                ) {
                    Text("Revancha")
                }
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = onNewPartida,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Text("Nueva partida")
                }
                Spacer(Modifier.height(8.dp))
                AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                    Button(
                        onClick = onBackToList,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text("Volver a la lista")
                    }
                }
            }
        }
    }
}

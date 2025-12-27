package com.example.firstassiment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstassiment.ui.theme.FirstAssimentTheme
import com.example.firstassiment.ui.theme.PlayerOColor
import com.example.firstassiment.ui.theme.PlayerXColor
import com.example.firstassiment.ui.theme.Teal

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstAssimentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicTacToeGameScreen()
                }
            }
        }
    }
}

@Composable
fun TicTacToeGameScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        TicTacToeGame()
    }
}

@Composable
fun TicTacToeGame() {
    var board by remember { mutableStateOf(List(9) { "" }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf<String?>(null) }
    val gameActive = winner == null

    fun checkWinner() {
        val winningPositions = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), // Rows
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), // Columns
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)                      // Diagonals
        )

        for (winPos in winningPositions) {
            if (board[winPos[0]].isNotEmpty() &&
                board[winPos[0]] == board[winPos[1]] &&
                board[winPos[1]] == board[winPos[2]]
            ) {
                winner = board[winPos[0]]
                return
            }
        }

        if (board.all { it.isNotEmpty() }) {
            winner = "Draw"
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val playerDisplayName = if (currentPlayer == "X") "One" else "Two"
        val winnerDisplayName = when (winner) {
            "X" -> "One"
            "O" -> "Two"
            else -> ""
        }

        Text(
            text = when {
                winner != null -> if (winner == "Draw") "It's a draw!" else "Player $winnerDisplayName Won!"
                else -> "Player $playerDisplayName's Turn"
            },
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        GameBoard(
            board = board,
            onCellClick = {
                if (board[it].isEmpty() && gameActive) {
                    board = board.toMutableList().also { list -> list[it] = currentPlayer }
                    checkWinner()
                    if (winner == null) {
                        currentPlayer = if (currentPlayer == "X") "O" else "X"
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(visible = !gameActive, enter = fadeIn(animationSpec = tween(1000))) {
            Button(
                onClick = {
                    board = List(9) { "" }
                    currentPlayer = "X"
                    winner = null
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal)
            ) {
                Text(text = "Play Again", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun GameBoard(board: List<String>, onCellClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .size(300.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            for (i in 0 until 3) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    for (j in 0 until 3) {
                        val index = i * 3 + j
                        val playerSymbol = board[index]
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .background(MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = { onCellClick(index) },
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(0.dp), // make the button fill the box
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = when (playerSymbol) {
                                        "X" -> PlayerXColor
                                        "O" -> PlayerOColor
                                        else -> MaterialTheme.colorScheme.onSurface
                                    }
                                )
                            ) {
                                AnimatedVisibility(
                                    visible = playerSymbol.isNotEmpty(),
                                    enter = scaleIn(animationSpec = tween(durationMillis = 200))
                                ) {
                                    Text(text = playerSymbol, fontSize = 56.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
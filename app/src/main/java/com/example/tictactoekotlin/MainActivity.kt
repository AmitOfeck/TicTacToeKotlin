package com.example.tictactoekotlin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoekotlin.ui.theme.TicTacToeKotlinTheme

class MainActivity : ComponentActivity() {
    private var board = Array(3) { Array(3) { "" } }
    private var currentPlayer by mutableStateOf("X")
    private var gameOver by mutableStateOf(false)
    private var winner by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeKotlinTheme {
                GameScreen()
            }
        }
    }

    @Composable
    fun GameScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Current Player: $currentPlayer",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            for (i in 0 until 3) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    for (j in 0 until 3) {
                        Button(
                            onClick = {
                                if (board[i][j].isEmpty() && !gameOver) {
                                    board[i][j] = currentPlayer
                                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                                    checkWinner()
                                }
                            },
                            modifier = Modifier
                                .size(100.dp)
                                .padding(4.dp)
                                .background(Color.Transparent),
                            colors = when {
                                board[i][j] == "X" -> androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Red)
                                board[i][j] == "O" -> androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Blue)
                                else -> androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Gray)
                            }
                        ) {
                            Text(
                                text = board[i][j],
                                color = Color.White,
                                fontSize = 32.sp
                            )
                        }
                    }
                }
            }

            // If the game is over, show the "Play Again" button
            if (gameOver) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { restartGame() }) {
                    Text(text = "Play Again", fontSize = 20.sp)
                }
                Text(
                    text = "Winner: $winner",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }

    private fun checkWinner() {
        for (i in 0 until 3) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0].isNotEmpty()) {
                announceWinner(board[i][0])
                return
            }
        }

        for (j in 0 until 3) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j] && board[0][j].isNotEmpty()) {
                announceWinner(board[0][j])
                return
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0].isNotEmpty()) {
            announceWinner(board[0][0])
            return
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2].isNotEmpty()) {
            announceWinner(board[0][2])
            return
        }

        if (board.all { row -> row.all { it.isNotEmpty() } }) {
            announceWinner("Draw")
        }
    }

    private fun announceWinner(winner: String) {
        this.winner = winner
        gameOver = true
        Toast.makeText(this, "Winner: $winner", Toast.LENGTH_SHORT).show()
    }

    private fun restartGame() {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameOver = false
        winner = ""
    }
}

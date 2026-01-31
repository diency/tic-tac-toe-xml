package com.example.colormyviews.logic

import javax.inject.Inject

class TicTacToeEngine @Inject constructor() {

    // Returns true if there is a winner on the current board
    fun checkWinner(board: List<String>): String? {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Cols
            listOf(0, 4, 8), listOf(2, 4, 6)             // Diagonals
        )

        for (p in winPatterns) {
            if (board[p[0]].isNotEmpty() && board[p[0]] == board[p[1]] && board[p[0]] == board[p[2]]) {
                return board[p[0]]
            }
        }
        return null
    }
}
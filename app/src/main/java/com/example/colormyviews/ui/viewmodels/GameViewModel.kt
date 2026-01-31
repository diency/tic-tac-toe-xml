package com.example.colormyviews.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.colormyviews.logic.TicTacToeEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val engine: TicTacToeEngine
) : ViewModel() {

    // Using MutableStateFlow so the UI can "observe" changes
    private val _boardState = MutableStateFlow(List(9) { "" })
    val boardState: StateFlow<List<String>> = _boardState.asStateFlow()

    private val _winner = MutableStateFlow<String?>(null)
    val winner = _winner.asStateFlow()

    private var currentPlayer = "X"

    fun onCellClicked(index: Int) {
        // Only update if cell is empty and game isn't over
        if (_boardState.value[index].isEmpty() && _winner.value == null) {
            val newBoard = _boardState.value.toMutableList()
            newBoard[index] = currentPlayer
            _boardState.value = newBoard

            val winner = engine.checkWinner(newBoard)
            if (winner != null) {
                _winner.value = winner // We have a winner!
            } else if (!newBoard.contains("")) {
                _winner.value = "Draw" // Board is full, no winner
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
        }
    }

    fun resetGame() {
        _boardState.value = List(9) { "" }
        _winner.value = null
        currentPlayer = "X"
    }
}
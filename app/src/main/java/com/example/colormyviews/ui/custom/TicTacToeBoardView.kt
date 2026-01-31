package com.example.colormyviews.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.GridLayout

class TicTacToeBoardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    private val buttons = mutableListOf<Button>()
    var onCellClickListener: ((Int) -> Unit)? = null

    init {
        columnCount = 3
        rowCount = 3
        setupBoard()
    }

    private fun setupBoard() {
        for (i in 0 until 9) {
            val button = Button(context).apply {
                layoutParams = LayoutParams(
                    spec(i / 3, 1f), // row
                    spec(i % 3, 1f)  // column
                ).apply {
                    width = 0
                    height = 0
                }
                textSize = 32f
                setOnClickListener { onCellClickListener?.invoke(i) }
            }
            buttons.add(button)
            addView(button)
        }
    }

    fun updateBoard(board: List<String>) {
        board.forEachIndexed { index, value ->
            buttons[index].text = value
        }
    }
}
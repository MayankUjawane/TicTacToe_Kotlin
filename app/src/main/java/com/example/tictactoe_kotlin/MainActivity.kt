package com.example.tictactoe_kotlin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var PLAYER = true //player1-true, player2-false
    var TOTAL_COUNT = 0 //Maximum 9 counts possible and TOTAL_COUNT = 9 means draw

    var boardStatus = Array(3) { IntArray(3) }

    lateinit var arrayOfButtons: Array<Button>

    lateinit var board: Array<Array<Button>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = arrayOf(
            arrayOf(btn_1, btn_2, btn_3),
            arrayOf(btn_4, btn_5, btn_6),
            arrayOf(btn_7, btn_8, btn_9)
        )

        for (i in board) {
            for (button in i) {
                button.setOnClickListener(this)
            }
        }
        initialiseBoardStatus()

        btn_reset.setOnClickListener {
            PLAYER = true
            TOTAL_COUNT = 0
            initialiseBoardStatus()
            updateDisplay("Player X Turn")
        }
    }

    private fun initialiseBoardStatus() {
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = -1
                board[i][j].isEnabled = true
                board[i][j].text = ""
                board[i][j].setBackgroundColor(resources.getColor(R.color.purple_700))
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_1 -> {
                updateValue(row = 0, col = 0, player = PLAYER)
            }
            R.id.btn_2 -> {
                updateValue(row = 0, col = 1, player = PLAYER)
            }
            R.id.btn_3 -> {
                updateValue(row = 0, col = 2, player = PLAYER)
            }
            R.id.btn_4 -> {
                updateValue(row = 1, col = 0, player = PLAYER)
            }
            R.id.btn_5 -> {
                updateValue(row = 1, col = 1, player = PLAYER)
            }
            R.id.btn_6 -> {
                updateValue(row = 1, col = 2, player = PLAYER)
            }
            R.id.btn_7 -> {
                updateValue(row = 2, col = 0, player = PLAYER)
            }
            R.id.btn_8 -> {
                updateValue(row = 2, col = 1, player = PLAYER)
            }
            R.id.btn_9 -> {
                updateValue(row = 2, col = 2, player = PLAYER)
            }
        }
        TOTAL_COUNT++
        PLAYER = !PLAYER
        if (PLAYER) {
            updateDisplay("Player X Turn")
        } else {
            updateDisplay("Player 0 Turn")
        }
        if (TOTAL_COUNT == 9) {
            updateDisplay("Game Draw")
        }
        checkWinner()
    }

    private fun checkWinner() {
        //Horizontal Rows & Vertical Rows
        for (i in 0..2) {
            //Horizontal Rows
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]) {
                if (boardStatus[i][0] == 1) {
                    arrayOfButtons = arrayOf(board[i][0], board[i][1], board[i][2])
                    applyColor(arrayOfButtons, "yellow")
                    updateDisplay("Player X Won")
                    break
                } else if (boardStatus[i][0] == 0) {
                    arrayOfButtons = arrayOf(board[i][0], board[i][1], board[i][2])
                    applyColor(arrayOfButtons, "red")
                    updateDisplay("Player 0 Won")
                    break
                }
            } //Vertical Rows
            else if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]) {
                if (boardStatus[0][i] == 1) {
                    arrayOfButtons = arrayOf(board[0][i], board[1][i], board[2][i])
                    applyColor(arrayOfButtons, "yellow")
                    updateDisplay("Player X Won")
                    break
                } else if (boardStatus[0][i] == 0) {
                    arrayOfButtons = arrayOf(board[0][i], board[1][i], board[2][i])
                    applyColor(arrayOfButtons, "red")
                    updateDisplay("Player 0 Won")
                    break
                }
            }
        }
        //First Diagonal
        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]) {
            if (boardStatus[0][0] == 1) {
                arrayOfButtons = arrayOf(board[0][0], board[1][1], board[2][2])
                applyColor(arrayOfButtons, "yellow")
                updateDisplay("Player X Won")
            } else if (boardStatus[0][0] == 0) {
                arrayOfButtons = arrayOf(board[0][0], board[1][1], board[2][2])
                applyColor(arrayOfButtons, "red")
                updateDisplay("Player 0 Won")
            }
        }
        //Second Diagonal
        if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][0]) {
            if (boardStatus[0][2] == 1) {
                arrayOfButtons = arrayOf(board[0][2], board[1][1], board[2][0])
                applyColor(arrayOfButtons, "yellow")
                updateDisplay("Player X Won")
            } else if (boardStatus[0][2] == 0) {
                arrayOfButtons = arrayOf(board[0][2], board[1][1], board[2][0])
                applyColor(arrayOfButtons, "red")
                updateDisplay("Player 0 Won")
            }
        }
    }

    private fun applyColor(arrayOfButtons: Array<Button>, color: String) {
        for (button in arrayOfButtons) {
            if (color == "yellow") {
                button.setBackgroundColor(Color.YELLOW)
            } else {
                button.setBackgroundColor(Color.RED)
            }
        }
    }

    private fun updateDisplay(s: String) {
        tv_heading.setText(s)
        if (s.contains("Won")) {
            disableButton()
        }
    }

    private fun disableButton() {
        for (i in board) {
            for (button in i) {
                button.isEnabled = false
            }
        }
    }

    private fun updateValue(row: Int, col: Int, player: Boolean) {
        val text = if (player) "X" else "0"
        val value = if (player) 1 else 0

        board[row][col].apply {
            isEnabled = false
            setText(text)
        }
        boardStatus[row][col] = value
    }
}
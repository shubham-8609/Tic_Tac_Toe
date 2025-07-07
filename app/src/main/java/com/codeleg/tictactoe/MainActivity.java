package com.codeleg.tictactoe;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button[] btn = new Button[9];
    char[] board = new char[9]; // Internal game state: 'X', 'O', or '\0' for empty
    int state = 0; // 0 for X, 1 for O
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        resetBoardState();
    }

    private void init() {
        int[] ids = {
                R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6,
                R.id.btn7, R.id.btn8, R.id.btn9
        };
        for (int i = 0; i < btn.length; i++) {
            btn[i] = findViewById(ids[i]);
            int finalI = i;
            btn[i].setOnClickListener(v -> clicked(finalI));
        }
    }

    private void clicked(int index) {
        if (board[index] == '\0') { // empty
            count++;
            board[index] = (state == 0) ? 'X' : 'O';
            btn[index].setText(String.valueOf(board[index]));
            state = 1 - state; // toggle player

            if (count >= 5) {
                if (checkWinner()) {
                    Toast.makeText(this, "Winner is : " + board[index], Toast.LENGTH_SHORT).show();
                    resetDelayed();
                } else if (count == 9) {
                    Toast.makeText(this, "The Game is Drawn", Toast.LENGTH_SHORT).show();
                    resetDelayed();
                }
            }
        }
    }

    private boolean checkWinner() {
        int[][] winConditions = {
                {0,1,2}, {3,4,5}, {6,7,8}, // rows
                {0,3,6}, {1,4,7}, {2,5,8}, // columns
                {0,4,8}, {2,4,6}           // diagonals
        };

        for (int[] wc : winConditions) {
            if (board[wc[0]] != '\0' &&
                    board[wc[0]] == board[wc[1]] &&
                    board[wc[1]] == board[wc[2]]) {
                return true;
            }
        }
        return false;
    }

    private void resetBoardState() {
        for (int i = 0; i < board.length; i++) {
            board[i] = '\0';
            btn[i].setText("");
        }
        count = 0;
        state = 0;
    }

    private void resetDelayed() {
        new Handler().postDelayed(this::resetBoardState, 400);
    }

    public void reset(View v) {
        resetBoardState();
        Toast.makeText(this, "Game Reset", Toast.LENGTH_SHORT).show();
    }
}

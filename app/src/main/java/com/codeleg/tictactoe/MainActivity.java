package com.codeleg.tictactoe;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator; // Import the Vibrator class
import android.os.VibratorManager; // Import VibratorManager for newer Android versions
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    Button resetBtn ;
    int count = 0;
    Boolean isGameOver;
    Vibrator vibrator;
    Animation clickAnim ;
    Animation invisibleAnime;

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
        clickAnim = AnimationUtils.loadAnimation(this , R.anim.button_anim);
        invisibleAnime = AnimationUtils.loadAnimation(this , R.anim.alpha_anim);
        for (int i = 0; i < btn.length; i++) {
            btn[i] = findViewById(ids[i]);
            int finalI = i;
            btn[i].setOnClickListener(v -> clicked(finalI));
        }
            isGameOver = false;
        resetBtn = findViewById(R.id.resetBtn);
        // Get the Vibrator service
        // For Android versions 12 (API level 31) and above, VibratorManager should be used.
        // For older versions, the legacy Vibrator service is used.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Get the VibratorManager system service
            VibratorManager vibratorManager = (VibratorManager) getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            vibrator = vibratorManager.getDefaultVibrator(); // Get the default vibrator from the manager
        } else {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); // Get the legacy Vibrator service
        }
    }

    private void clicked(int index) {
        if (board[index] == '\0' &&  !isGameOver) { // empty
            count++;
            board[index] = (state == 0) ? 'X' : 'O';
            btn[index].setText(String.valueOf(board[index]));
            btn[index].startAnimation(clickAnim);
            state = 1 - state; // toggle player

            if (count >= 5) {
                if (checkWinner()) {
                    Toast.makeText(this, "Winner is : " + board[index], Toast.LENGTH_SHORT).show();
                    vibrate(300);
                    isGameOver = true;



                } else if (count == 9) {
                    Toast.makeText(this, "The Game is Drawn", Toast.LENGTH_SHORT).show();
                    vibrate(300);

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
        isGameOver = false;
    }




    public void reset(View v) {
        resetBoardState();
        Toast.makeText(this, "Game Reset", Toast.LENGTH_SHORT).show();
        vibrate(100);
        resetBtn.startAnimation(invisibleAnime);
    }

    public void vibrate( int time){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(time); // for older devices
        }

    }
}

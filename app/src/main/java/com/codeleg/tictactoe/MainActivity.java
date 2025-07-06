package com.codeleg.tictactoe;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    // Declaring views
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    String b1, b2, b3, b4, b5, b6, b7, b8, b9;
    int state = 0;
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
        init(); // Calling init() method to initialize buttons

    }


    private void init() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
    }


    // The onclick method
    public void clicked(View v) {
        Button clickedBtn = (Button) v;
        if (clickedBtn.getText().toString().isEmpty()) {
            count++;

            if (state == 0) {
                clickedBtn.setText("X");
                state = 1;
            } else {
                clickedBtn.setText("O");
                state = 0;
            }


            if (count > 4) {
                //Ensure buttons are initialized before accessing them
                b1 = btn1.getText().toString();
                b2 = btn2.getText().toString();
                b3 = btn3.getText().toString();
                b4 = btn4.getText().toString();
                b5 = btn5.getText().toString();
                b6 = btn6.getText().toString();
                b7 = btn7.getText().toString();
                b8 = btn8.getText().toString();
                b9 = btn9.getText().toString();

                // Conditions

                if (b1.equals(b2) && b2.equals(b3) && !b1.equals("")) {
                    reset();

                    Toast.makeText(this, "Winner is : " + b1, Toast.LENGTH_SHORT).show();
                } else if (b4.equals(b5) && b5.equals(b6) && !b4.equals("")) {
                    reset();

                    Toast.makeText(this, "Winner is : " + b4, Toast.LENGTH_SHORT).show();

                } else if (b7.equals(b8) && b8.equals(b9) && !b7.equals("")) {
                    reset();
                    // 3.
                    Toast.makeText(this, "Winner is : " + b7, Toast.LENGTH_SHORT).show();
                } else if (b1.equals(b4) && b4.equals(b7) && !b1.equals("")) {
                    reset();

                    // 4.
                    Toast.makeText(this, "Winner is : " + b1, Toast.LENGTH_SHORT).show();

                } else if (b2.equals(b5) && b5.equals(b8) && !b2.equals("")) {
                    reset();
                    //5.
                    Toast.makeText(this, "Winner is : " + b2, Toast.LENGTH_SHORT).show();
                } else if (b3.equals(b6) && b6.equals(b9) && !b3.equals("")) {

                    reset();

                    //6.
                    Toast.makeText(this, "Winner is : " + b3, Toast.LENGTH_SHORT).show();
                } else if (b1.equals(b5) && b5.equals(b9) && !b1.equals("")) {
                    reset();
                    // 7.
                    Toast.makeText(this, "Winner is : " + b1, Toast.LENGTH_SHORT).show();
                } else if (b3.equals(b5) && b5.equals(b7) && !b3.equals("")) {
                    reset();

                    //8.
                    Toast.makeText(this, "Winner is : " + b3, Toast.LENGTH_SHORT).show();
                } else if (count == 9) {
                    Toast.makeText(this, "The Game is Drawn", Toast.LENGTH_SHORT).show();
                    reset();
                }


            }


        }
    }

    public void reset() {
        // Implementing vibration
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {

                                          btn1.setText("");
                                          btn2.setText("");
                                          btn3.setText("");
                                          btn4.setText("");
                                          btn5.setText("");
                                          btn6.setText("");
                                          btn7.setText("");
                                          btn8.setText("");
                                          btn9.setText("");

                                          count = 0;
                                          state = 0;
                                      }
                                  } , 400);





    }
    public void reset(View v){
        reset();
        Toast.makeText(this, "Game Reset " , Toast.LENGTH_SHORT).show();
    }


}
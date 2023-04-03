package com.example.game;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    Button btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22;
    int[][] buttons = {{R.id.btn_00, R.id.btn_01, R.id.btn_02},{R.id.btn_10, R.id.btn_11, R.id.btn_12},
            {R.id.btn_20, R.id.btn_21, R.id.btn_22}};
    String figure1, figure2, figure;
    int color, color1, color2, p1, p2;
    TextView text1, text2, score1, score2;
    String[][] gameBoard = {{" ", " ", " "},{" ", " ", " "},{" ", " ", " "}};
    int moveProgress = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        figure1 = new String(getIntent().getStringExtra("figure1"));
        figure = new String(figure1);
        p1 = getIntent().getIntExtra("player1", 0);
        p2 = getIntent().getIntExtra("player2", 0);

        btn00 = (Button) findViewById(R.id.btn_00);
        btn01 = (Button) findViewById(R.id.btn_01);
        btn02 = (Button) findViewById(R.id.btn_02);
        btn10 = (Button) findViewById(R.id.btn_10);
        btn11 = (Button) findViewById(R.id.btn_11);
        btn12 = (Button) findViewById(R.id.btn_12);
        btn20 = (Button) findViewById(R.id.btn_20);
        btn21 = (Button) findViewById(R.id.btn_21);
        btn22 = (Button) findViewById(R.id.btn_22);

        text1 = (TextView) findViewById(R.id.txt_player1);
        text2 = (TextView) findViewById(R.id.txt_player2);
        score1 = (TextView) findViewById(R.id.txt_player1_score);
        score2 = (TextView) findViewById(R.id.txt_player2_score);

        if (figure1.equals("X")) {
            figure2 = new String("O");
            color1 = R.color.neon_pink;
            color2 = R.color.neon_green;
        }
        else {
            figure2 = new String("X");
            color1 = R.color.neon_green;
            color2 = R.color.neon_pink;
        }
        color = color1;

        btn00.setText(figure1);
        btn00.setTextColor(getResources().getColor(color1));

        btn22.setText(figure2);
        btn22.setTextColor(getResources().getColor(color2));

        text1.setTextColor(getResources().getColor(color1));
        text2.setTextColor(getResources().getColor(color2));
        score1.setTextColor(getResources().getColor(color1));
        score2.setTextColor(getResources().getColor(color2));

        gameBoard[0][0] = new String(figure1);
        gameBoard[2][2] = new String(figure2);

        countPoints();

    }

    public void playGame(View view){
        int buttonId = view.getId();
        Button b = findViewById(buttonId);
        int i = findIndex(buttonId);
        if (b.getText().toString().equals(figure) && moveProgress == -1) {
            b.setTextColor(getResources().getColor(R.color.dark_blue));
            b.setBackgroundColor(getResources().getColor(color));
            moveProgress = i;
        }
        else if (moveProgress != -1){
            int j = moveProgress / 10;
            int k = moveProgress % 10;
            if (b.getText().toString().equals(" ")) {

                int j1 = i / 10;
                int k1 = i % 10;
                if (((j==j1+1 || j == j1-1) && k==k1) || ((k==k1+1 || k == k1-1) && j==j1)) {
                    makeMove(i);
                    Button c = (Button) findViewById(buttons[j][k]);
                    c.setTextColor(getResources().getColor(color));
                    c.setBackgroundColor(getResources().getColor(R.color.dark_blue));
                    countPoints();
                    resetForNextPlayer();
                }
                else if (((j==j1+2 || j == j1-2) && k==k1) || ((k==k1+2 || k == k1-2) && j==j1) ||
                        (j==j1 +1 && k == k1 + 1) || (j==j1 -1 && k == k1 - 1) ||
                        (j==j1 +1 && k == k1 - 1) || (j==j1 -1 && k == k1 + 1)) {
                    makeMove(i);
                    Button c = (Button) findViewById(buttons[j][k]);
                    c.setText(" ");
                    c.setBackgroundColor(getResources().getColor(R.color.dark_blue));
                    gameBoard[j][k] = new String(" ");
                    countPoints();
                    resetForNextPlayer();
                }
                else {
                    Button c = (Button) findViewById(buttons[j][k]);
                    c.setTextColor(getResources().getColor(color));
                    c.setBackgroundColor(getResources().getColor(R.color.dark_blue));
                    moveProgress = -1;
                }

            }
            else {
                Button c = (Button) findViewById(buttons[j][k]);
                c.setTextColor(getResources().getColor(color));
                c.setBackgroundColor(getResources().getColor(R.color.dark_blue));
                moveProgress = -1;
            }
        }

    }

    public void resetForNextPlayer(){
        moveProgress = -1;
        if (figure.equals(figure1)) {
            figure = new String(figure2);
            color = color2;
        }
        else {
            figure = new String(figure1);
            color = color1;
        }
        if (endOfGame()){
            int player = 0;
            int sc1 = Integer.parseInt(String.valueOf(score1.getText()));
            int sc2 = Integer.parseInt(String.valueOf(score2.getText()));
            if (sc1 > sc2) player = 1;
            else player = 2;
            AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
            builder.setMessage("Player " + player + " won!");
            builder.setCancelable(true);
            AlertDialog alert1 = builder.create();
            alert1.show();
        }
    }

    public boolean endOfGame(){
        int emptyCells = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if (gameBoard[i][j].equals(" ")) emptyCells += 1;
                if (emptyCells >= 1 && !impossibleMove()) return false;
            }
        }
        return true;
    }

    public boolean impossibleMove(){
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if (gameBoard[i][j].equals(figure)){
                    if (i+1 <= 2 && gameBoard[i+1][j].equals(" ")) return false;
                    if (i-1 >= 0 && gameBoard[i-1][j].equals(" ")) return false;
                    if (j+1 <= 2 && gameBoard[i][j+1].equals(" ")) return false;
                    if (j-1 >= 0 && gameBoard[i][j-1].equals(" ")) return false;
                    if (i+2 <= 2 && gameBoard[i+2][j].equals(" ")) return false;
                    if (i-2 >= 0 && gameBoard[i-2][j].equals(" ")) return false;
                    if (j+2 <= 2 && gameBoard[i][j+2].equals(" ")) return false;
                    if (j-2 >= 0 && gameBoard[i][j-2].equals(" ")) return false;
                    if (i+1 <= 2 && j+1 <= 2 && gameBoard[i+1][j+1].equals(" ")) return false;
                    if (i+1 <=2 && j-1 >= 0 && gameBoard[i+1][j-1].equals(" ")) return false;
                    if (i-1 >=0 && j+1 <= 2 && gameBoard[i-1][j+1].equals(" ")) return false;
                    if (i-1 >=0 && j-1 >= 0 && gameBoard[i-1][j-1].equals(" ")) return false;
                }
            }
        }
        return true;
    }

    public void makeMove(int index){
        int i = index / 10;
        int j = index % 10;
        setField(i, j);
        if (i-1 >= 0 && !gameBoard[i-1][j].equals(figure) && !gameBoard[i-1][j].equals(" ")) setField(i-1, j);
        if (j-1 >= 0 && !gameBoard[i][j-1].equals(figure) && !gameBoard[i][j-1].equals(" ")) setField(i, j-1);
        if (j+1 <= 2 && !gameBoard[i][j+1].equals(figure) && !gameBoard[i][j+1].equals(" ")) setField(i, j+1);
        if (i+1 <= 2 && !gameBoard[i+1][j].equals(figure) && !gameBoard[i+1][j].equals(" ")) setField(i+1, j);

    }

    public void setField(int i, int j){
        gameBoard[i][j] = new String(figure);
        Button b = (Button) findViewById(buttons[i][j]);
        b.setText(figure);
        b.setTextColor(getResources().getColor(color));
        b.setBackgroundColor(getResources().getColor(R.color.dark_blue));
    }

    public void countPoints(){
        int p1 = 0, p2 = 0;
        for(int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if (gameBoard[i][j].equals(figure1)) p1 += 1;
                if (gameBoard[i][j].equals(figure2)) p2 += 1;
            }
        }
        score1.setText(Integer.toString(p1));
        score2.setText(Integer.toString(p2));
    }

    public void resetGame(View view){
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                Button b = (Button) findViewById(buttons[i][j]);
                b.setText(" ");
                gameBoard[i][j] = new String(" ");
            }
        }
        Button p1 = (Button) findViewById(buttons[0][0]);
        Button p2 = (Button) findViewById(buttons[2][2]);
        p1.setText(figure1);
        p2.setText(figure2);
        p1.setTextColor(getResources().getColor(color1));
        p2.setTextColor(getResources().getColor(color2));
        gameBoard[0][0] = new String(figure1);
        gameBoard[2][2] = new String(figure2);
        countPoints();
    }

    public int findIndex(int btnId){
        switch (btnId){
            case R.id.btn_00: {
                return 0;
            }
            case R.id.btn_01: {
                return 1;
            }
            case R.id.btn_02: {
                return 2;
            }
            case R.id.btn_10: {
                return 10;
            }
            case R.id.btn_11: {
                return 11;
            }
            case R.id.btn_12: {
                return 12;
            }
            case R.id.btn_20: {
                return 20;
            }
            case R.id.btn_21: {
                return 21;
            }
            case R.id.btn_22: {
                return 22;
            }
        }
        return 4;
    }
}
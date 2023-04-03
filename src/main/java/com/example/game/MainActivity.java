package com.example.game;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton player1Btn, player2Btn;
    Button figure1Btn, figure2Btn, playBtn;
    int[] player = {0,1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        player1Btn = (ImageButton) findViewById(R.id.player1_btn);
        player2Btn = (ImageButton) findViewById(R.id.player2_btn);
        figure1Btn = (Button) findViewById(R.id.figure1_btn);
        figure2Btn = (Button) findViewById(R.id.figure2_btn);
        playBtn = (Button) findViewById(R.id.play_btn);

        player1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlayer(0);
            }
        });

        player2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlayer(1);
            }
        });

    }

    public void setFigure(View v){
        if (figure1Btn.getText().toString().equals("X")){
            figure1Btn.setText("O");
            figure1Btn.setTextColor(getResources().getColor(R.color.neon_green));

            figure2Btn.setText("X");
            figure2Btn.setTextColor(getResources().getColor(R.color.neon_pink));
        }
        else {
            figure1Btn.setText("X");
            figure1Btn.setTextColor(getResources().getColor(R.color.neon_pink));

            figure2Btn.setText("O");
            figure2Btn.setTextColor(getResources().getColor(R.color.neon_green));
        }
    }

    public void setPlayer(int singleBtn){
        if (player[singleBtn] == 1){
            player[singleBtn] = 0;
            if (singleBtn == 0) player1Btn.setImageResource(R.drawable.person);
            else player2Btn.setImageResource(R.drawable.person);
        }
        else {
            player[singleBtn] = 1;
            if (singleBtn == 0) player1Btn.setImageResource(R.drawable.robot);
            else player2Btn.setImageResource(R.drawable.robot);
        }
    }

    public void play(View view){
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        intent.putExtra("figure1", figure1Btn.getText());
        intent.putExtra("player2", player[1]);
        intent.putExtra("player1", player[0]);
        startActivity(intent);
    }
}
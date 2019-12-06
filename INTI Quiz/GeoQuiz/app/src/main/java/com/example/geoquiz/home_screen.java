package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class home_screen extends AppCompatActivity {

    private Button startButton;
    private Button viewScoreboard;

    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        startButton = (Button) findViewById(R.id.start_button);
        //sets onclicklistener for start button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });

        viewScoreboard = (Button) findViewById(R.id.main_view_scoreboard);
        //Sets onclicklistener for scoreboard button
        viewScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_screen.this, RecyclerViewActivity.class);
                startActivity(intent);

            }
        });
    }

    //function to call another activity
    public void startQuiz(){
        Intent intent = new Intent(this,MainActivity.class);
        username = (EditText) findViewById(R.id.name_input);
        String usrname = username.getText().toString();
        intent.putExtra("username",usrname);
        startActivity(intent);
    }

}

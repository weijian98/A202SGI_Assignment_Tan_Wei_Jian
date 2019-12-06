package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class end_activity extends AppCompatActivity {
    private static final String TAG = "end_activity";
    private TextView mScore;
    private TextView mUsername;
    public Button viewScoreboard;
    User user;
    DatabaseReference ref;
    String getName;
    String getScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_activity);

        //gets intent extras from previous activity
         getScore = getIntent().getExtras().getString("final_score");
         getName = getIntent().getExtras().getString("username");

        mScore = findViewById(R.id.final_score);
        mUsername = findViewById(R.id.username);

        //if there is a username entered, the username will be shown in the final screen
        mScore.setText(getScore);
        if (getName != null && !getName.isEmpty() && !getName.equals("null")) {
            mUsername.setText(getName);
        }
        //if no username is entered, the username label will show unknown player
        else{
            mUsername.setText("Unknown Player");
            getName = "Unknown Player";
        }

        viewScoreboard = findViewById(R.id.view_scoreboard);

        ref = FirebaseDatabase.getInstance().getReference().child("user");

        user = new User();

        //sets onclicklistener for back to menu button
        viewScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUsername(getName);
                user.setScore(getScore);


                //pushes the data to firebase
                ref.push().setValue(user);

                Intent intent = new Intent(end_activity.this,RecyclerViewActivity.class);
                intent.putExtra("name",getName);
                intent.putExtra("score",getScore);
                startActivity(intent);

            }

        });
    }
}

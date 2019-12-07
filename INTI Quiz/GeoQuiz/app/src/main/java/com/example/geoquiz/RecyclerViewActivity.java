package com.example.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "recyclerview";


    DatabaseReference ref;

    //variables
    private ArrayList<String> mUsernames = new ArrayList<>();
    private ArrayList<String> mScores = new ArrayList<>();
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        // gets scoreboard data from database
        ref = FirebaseDatabase.getInstance().getReference();

        DatabaseReference myRef = ref.child("user");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!mScores.isEmpty()){
                    mScores.clear();
                    mUsernames.clear();
                }

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String scores = ds.child("score").getValue(String.class);
                    String username = ds.child("username").getValue(String.class);

                    Log.d(TAG, "onDataChange: printscores" + scores);
                    Log.d(TAG, "onDataChange: print username " + username);


                    mScores.add(scores);
                    mUsernames.add(username);

                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //onclicklistener for back button
        backButton = findViewById(R.id.back_to_menu);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecyclerViewActivity.this,home_screen.class);
                startActivity(intent);
                finishAffinity();
            }
        });

    }

    //initializes recyclerview
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,mUsernames,mScores);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this));
    }
}

package com.example.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX ="index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private Button mCheatButton;
    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private TextView mScore;
    public String username;

    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    //sets the question for quiz
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_1, false),
            new Question(R.string.question_2, true),
            new Question(R.string.question_3, true),
            new Question(R.string.question_4, true),
            new Question(R.string.question_5, true),
            new Question(R.string.question_6, false),
            new Question(R.string.question_7, false),
            new Question(R.string.question_8, true),
            new Question(R.string.question_9, true),
            new Question(R.string.question_10, true),



    };




    //updates the question
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    //checks if answer is correct
    private void checkAnswer(boolean userPressesTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int score;
        mScore = findViewById(R.id.score);
        score = Integer.parseInt(mScore.getText().toString()) + 10;

        int messageResId = 0;

        //checks if user cheated
        if(mIsCheater){
            messageResId = R.string.judgment_toast;
        }

        else {
            //if user answers correctly, a correct toast is chosen
            if (userPressesTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
                mScore.setText(Integer.toString(score));
            }
            //if user answers incorrectly, a incorrect toast is chosen
            else {
                messageResId = R.string.incorrect_toast;
            }
        }
        //shows the toast
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionTextView =  findViewById(R.id.question_text_view);
        //gets the intent extras from previous activity
        Intent getName = getIntent();
        Bundle b = getName.getExtras();

        //if there is intent extras passed, the extras will be obtained
        if(b!=null)
        {
            username = getIntent().getExtras().getString("username");
        }

        mTrueButton = (Button) findViewById(R.id.true_button);

        //sets onclicklistener for true button
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks user answer
                checkAnswer(true);
                mCurrentIndex = mCurrentIndex + 1;
                mIsCheater = false;

                //if all questions are asked, call the end screen
                if (mCurrentIndex == mQuestionBank.length ){
                    openEnd();
                }
                //proceeds to ask next question
                else {
                    updateQuestion();
                }
            }
        });


        mFalseButton = (Button) findViewById(R.id.false_button);

        //sets onclicklistener for false button
        mFalseButton.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               //checks user answer
               checkAnswer(false);
               mCurrentIndex = mCurrentIndex + 1;
               mIsCheater = false;
               //if all questions are asked, call the end screen
               if (mCurrentIndex == mQuestionBank.length ){
                   openEnd();
               }
               //proceeds to ask next question
               else {
                   updateQuestion();
               }
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Start CheatActivity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        if(savedInstanceState != null)
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

        mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionBank[0].getTextResId();

        updateQuestion();
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            if(data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            }
        }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);

    }

    //calls the score summary screen
    public void openEnd(){
        Intent intent = new Intent(this,end_activity.class);
        intent.putExtra("username",username);
        intent.putExtra("final_score",mScore.getText().toString());
        startActivity(intent);

    }
}

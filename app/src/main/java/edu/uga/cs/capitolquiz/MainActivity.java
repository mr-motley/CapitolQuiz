package edu.uga.cs.capitolquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

/**
 * This class handles the splash screen activity, it displays the basic rules and allows the user to begin a quiz or view past results
 */
public class MainActivity extends AppCompatActivity {

    private static final String welcomeMsg = "Welcome, to begin a quiz select 'start quiz', to view past results select 'past quizzes'. Each quiz consists of 6 questions where you will be asked to select the capitol of a given state from three options. Once you have made your selection, swipe left to submit your answer";

    private databaseHelper dbHelp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize layout and link variables to views
        ImageView img = findViewById(R.id.imageView);
        img.setBackgroundResource(R.drawable.usmap);
        TextView intro = findViewById(R.id.textView2);
        intro.setText(welcomeMsg);
        Button startQuiz = findViewById(R.id.button2);
        Button viewResults = findViewById(R.id.button);
        //set listeners for buttons
        startQuiz.setOnClickListener(new startOnClickListener());
        viewResults.setOnClickListener(new resOnClickListener());
    }

    private class startOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //create intent, and start quizActivity
            Intent intent = new Intent(view.getContext(), QuizActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class resOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //create intent, and start results Activity
            Intent intent = new Intent(view.getContext(), ResultsActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}
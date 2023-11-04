package edu.uga.cs.capitolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String welcomeMsg = "Welcome, to begin a quiz select 'start quiz', to view past results select 'past quizzes'. Each quiz consists of 6 questions where you will be asked to select the capitol of a given state from three options. Once you have made your selection, swipe left to submit your answer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img = findViewById(R.id.imageView);
        img.setBackgroundResource(R.drawable.usmap);
        TextView intro = findViewById(R.id.textView2);
        intro.setText(welcomeMsg);
        Button startQuiz = findViewById(R.id.button2);
        Button viewResults = findViewById(R.id.button);

        startQuiz.setOnClickListener(new startOnClickListener());
        viewResults.setOnClickListener(new resOnClickListener());
    }

    private class startOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), QuizActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class resOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ResultsActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}
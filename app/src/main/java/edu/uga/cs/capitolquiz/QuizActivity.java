package edu.uga.cs.capitolquiz;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    public final String DEBUG_TAG = "QuizActiviy: ";
    public int score = -1;

    public int[] answers = null;
    public Quiz current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ViewPager2 quest = findViewById(R.id.viewpager);
        QuizPagerAdapter qpAdapter = new QuizPagerAdapter(getSupportFragmentManager(),getLifecycle());
        quest.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        quest.setAdapter(qpAdapter);
        current = generateQuiz();
        answers = new int[] {0,0,0,0,0,0};
        Log.d(DEBUG_TAG, "Generated Quiz:" + current);
        score = 0;
    }

    public Quiz generateQuiz() {
        int[] stateVal = new int[6];
        Random gen = new Random();
        int count = 0;
        boolean hasDup = false;
        while(count < 6) {
            int temp = gen.nextInt(50);
            if(count == 0) {
                stateVal[0] = temp;
                count++;
            } else {
                for(int j = 0; j < count; j++){
                    if(temp == stateVal[j] || temp == 1){
                           hasDup = true;
                    }
                }
                if(!hasDup){
                        stateVal[count] = temp;
                        count++;
                }
            }

        }
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        Quiz temp = new Quiz(0,strDate, stateVal);

        return temp;
    }



}

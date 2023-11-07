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
    public Quiz current;
    private stateInfoData stateInfoData = null;
    private List<State> statesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ViewPager2 quest = findViewById(R.id.viewpager);
        QuizPagerAdapter qpAdapter = new QuizPagerAdapter(getSupportFragmentManager(),getLifecycle());
        quest.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        quest.setAdapter(qpAdapter);

        statesList = new ArrayList<State>();





        current = generateQuiz();
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
                    if(temp == stateVal[j]){
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
    private class stateDBReader extends AsyncTask<Void, List<State>>{
        @Override
        protected List<State> doInBackground(Void... params){
            List<State> statesList = stateInfoData.retrieveAllStates();

            Log.d(DEBUG_TAG, "stateDBRearder: States Retrieved " +statesList.size());

            return statesList;
        }

        @Override
        protected void onPostExecute(List<State> stateList){
            Log.d(DEBUG_TAG, "StateDBReader: stateList.size(): " + stateList.size());
            statesList.addAll(stateList);
        }
    }
}
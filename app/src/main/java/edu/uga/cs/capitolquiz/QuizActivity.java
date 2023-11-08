package edu.uga.cs.capitolquiz;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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

    boolean qstart = false;
    public int[] answers = null;
    public Quiz current;
    private int resId = -1;

    private StateInfoData stateInfoData = null;

    private List<Quiz> quizList =null;
    public ViewPager2 quest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled( true );
        quizList = new ArrayList<Quiz>();
        quest = findViewById(R.id.viewpager);
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
        Quiz temp = new Quiz(0,strDate, stateVal,0);
        return temp;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Implement Back button listener method.
        // This method may be used for other actions from the ActionBar menu, if provided in the layout.
        int id = item.getItemId();

        // android.R.id.home is the built-in designation of the back button widget.
        if( id == android.R.id.home ) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onPause() {
        super.onPause();
        qstart = true;
        current.setCurrentQ(quest.getCurrentItem());
        int tempScore = 0;
        for(int i =0; i < answers.length; i++){
            tempScore += answers[i];
        }
        current.setResult(tempScore);
        stateInfoData = new StateInfoData(getApplicationContext());
        stateInfoData.open();
        new quizDBWriter().execute(current);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(qstart) {
            stateInfoData = new StateInfoData(getApplicationContext());
            stateInfoData.open();
            new QuizDBReader().execute();
        }

    }

    private class quizDBWriter extends AsyncTask<Quiz, Quiz> {
        @Override
        protected Quiz doInBackground(Quiz... quizzes){
            stateInfoData.storeQuiz(quizzes[0]);
            return quizzes[0];
        }

        @Override
        protected void onPostExecute(Quiz quiz){
            resId = (int) quiz.getId();
            stateInfoData.close();
        }
    }

    private class QuizDBReader extends AsyncTask<Void, List<Quiz>> {
        @Override
        protected List<Quiz> doInBackground(Void... params) {
            Log.d(DEBUG_TAG, "Retreiving...");
            List<Quiz> quizList = stateInfoData.retrieveAllQuizzes();

            Log.d(DEBUG_TAG, "stateDBReader: Quizzes Retrieved " + quizList.size());

            return quizList;
        }

        @Override
        protected void onPostExecute(List<Quiz> qList) {
            Log.d(DEBUG_TAG, "QuizDBReader: quizList.size(): " + quizList.size());
            quizList.addAll(qList);
            stateInfoData.close();
            current = quizList.get(resId-1);
            answers = new int[]{current.getResult(), 0, 0, 0, 0, 0};
            quest.setCurrentItem(current.getCurrentQ());
            qstart = false;
        }
    }

}

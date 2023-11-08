package edu.uga.cs.capitolquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "Results Activity";

    private StateInfoData stateInfoData = null;

    private List<Quiz> quizList;

    private RecyclerView recyclerView;
    private QuizRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled( true );
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        quizList = new ArrayList<Quiz>();

        stateInfoData = new StateInfoData(getApplicationContext());

        stateInfoData.open();

        new QuizDBReader().execute();
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
            for(int i = 0; i < qList.size(); i++){
                if(qList.get(i).getCurrentQ() > 5){
                    quizList.add(qList.get(i));
                }
            }

            //create recyclerAdapter and set it
            recyclerAdapter = new QuizRecyclerAdapter(getApplicationContext(), quizList);
            recyclerView.setAdapter(recyclerAdapter);
            stateInfoData.close();
        }
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
}
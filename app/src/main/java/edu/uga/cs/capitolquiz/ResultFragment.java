package edu.uga.cs.capitolquiz;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {

    public static final String DEBUG_TAG = "Result Fragment: ";
    private StateInfoData stateInfoData = null;

    private List<Quiz> quizList;
    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public QuizActivity qz;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        qz = (QuizActivity) getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //link variables to layout objects
        TextView res = view.findViewById(R.id.textView5);
        Button ret = view.findViewById(R.id.button3);
        //set listener on the return home button
        ret.setOnClickListener(new returnOnClickListener());
        //calculate score
        int result = 0;
        for(int i = 0; i < qz.answers.length; i++){
            result += qz.answers[i];
        }
        //display score
        res.setText("Result: " + result + "/6");
        qz.current.setResult(result);
        Log.d(DEBUG_TAG, "Quiz Value: " + qz.current);
        //create and open new DataAccess class instance
        stateInfoData = new StateInfoData(getActivity());
        stateInfoData.open();
        //call async task to write to DB
        new quizDBWriter().execute(qz.current);
    }

    private class returnOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //create intent and return home
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class quizDBWriter extends AsyncTask<Quiz, Quiz> {
        @Override
        protected Quiz doInBackground(Quiz... quizzes){
            //store the quiz in the DB
            stateInfoData.storeQuiz(quizzes[0]);
            return quizzes[0];
        }

        @Override
        protected void onPostExecute(Quiz quiz){
            stateInfoData.close();
        }
    }

}
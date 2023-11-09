package edu.uga.cs.capitolquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    private static final String DEBUG_TAG = "Question Fragment";
    private int questionNum;
    private StateInfoData stateInfoData = null;
    private List<State> statesList;

    private TextView prompt;
    private RadioGroup group;
    private RadioButton ans1;
    private RadioButton ans2 ;
    private RadioButton ans3 ;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(int questionNum) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt("questionNum", questionNum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionNum = getArguments().getInt("questionNum");
        }
    }
    public QuizActivity qz;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        qz = (QuizActivity) getActivity();
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //connect values to their layout items
        prompt = view.findViewById(R.id.textView3);
        ans1 = view.findViewById(R.id.radioButton);
        ans2 = view.findViewById(R.id.radioButton2);
        ans3 = view.findViewById(R.id.radioButton3);
        group = view.findViewById(R.id.group);
        group.setOnCheckedChangeListener(new ansOnCheckedListener());

        //read the states from the database
        statesList = new ArrayList<State>();
        stateInfoData = new StateInfoData(getActivity());
        stateInfoData.open();
        //stateInfoData.populateDB(getActivity());
        Log.d(DEBUG_TAG, "Executing DB Read");
        new stateDBReader().execute();
    }

    private class stateDBReader extends AsyncTask<Void, List<State>> {
        @Override
        protected List<State> doInBackground(Void... params) {
            Log.d(DEBUG_TAG, "QFRAG: Retreiving...");
            List<State> statesList = stateInfoData.retrieveAllStates();

            Log.d(DEBUG_TAG, "stateDBRearder: States Retrieved " + statesList.size());

            return statesList;
        }

        @Override
        protected void onPostExecute(List<State> stateList) {
            Log.d(DEBUG_TAG, "StateDBReader: stateList.size(): " + stateList.size());
            statesList.addAll(stateList);

            State temp = null;
            Log.d(DEBUG_TAG, "StatesList: " + statesList.size());
            //Display the questions according to position and using the info from the correct state
            if(questionNum == 0){
                temp = statesList.get(qz.current.getQ1State());
                prompt.setText("What is the Capitol of " + temp.getState());
                ans1.setText(temp.getCapitol());
                ans2.setText(temp.getSecondCity());
                ans3.setText(temp.getThirdCity());
            } else if (questionNum == 1){
                temp = statesList.get(qz.current.getQ2State());
                prompt.setText("What is the Capitol of " + temp.getState());
                ans2.setText(temp.getCapitol());
                ans1.setText(temp.getSecondCity());
                ans3.setText(temp.getThirdCity());
            }else if (questionNum == 2){
                temp = statesList.get(qz.current.getQ3State());
                prompt.setText("What is the Capitol of " + temp.getState());
                ans1.setText(temp.getCapitol());
                ans3.setText(temp.getSecondCity());
                ans2.setText(temp.getThirdCity());
            }else if (questionNum == 3){
                temp = statesList.get(qz.current.getQ4State());
                prompt.setText("What is the Capitol of " + temp.getState());
                ans3.setText(temp.getCapitol());
                ans1.setText(temp.getSecondCity());
                ans2.setText(temp.getThirdCity());
            }else if (questionNum == 4){
                temp = statesList.get(qz.current.getQ5State());
                prompt.setText("What is the Capitol of " + temp.getState());
                ans1.setText(temp.getCapitol());
                ans3.setText(temp.getSecondCity());
                ans2.setText(temp.getThirdCity());
            }else if (questionNum == 5){
                temp = statesList.get(qz.current.getQ6State());
                prompt.setText("What is the Capitol of " + temp.getState());
                ans1.setText(temp.getCapitol());
                ans2.setText(temp.getSecondCity());
                ans3.setText(temp.getThirdCity());
            }
            stateInfoData.close();

        }
    }

    private class ansOnCheckedListener implements RadioGroup

            .OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedID){
            //check if the selected answer is correct and update score in real time
            if( questionNum == 0){
                Log.d(DEBUG_TAG, "inside if");
                if(checkedID == ans1.getId()){
                    qz.answers[0] = 1;
                    Log.d(DEBUG_TAG, "Modified Value: " + qz.answers[0]);
                }else {
                    qz.answers[0] = 0;
                }
            } else if( questionNum == 1){
                Log.d(DEBUG_TAG, "inside if");
                if(checkedID == R.id.radioButton2){
                    qz.answers[1] = 1;
                }else {
                    qz.answers[1] = 0;
                }
            }else if( questionNum == 2){
                Log.d(DEBUG_TAG, "inside if");
                if(checkedID == R.id.radioButton){
                    qz.answers[2] = 1;
                }else {
                    qz.answers[2] = 0;
                }
            } else if( questionNum == 3){
                Log.d(DEBUG_TAG, "inside if");
                if(checkedID == R.id.radioButton3){
                    qz.answers[3] = 1;
                }else {
                    qz.answers[3] = 0;
                }
            } else if( questionNum == 4){
                Log.d(DEBUG_TAG, "inside if");
                if(checkedID == R.id.radioButton){
                    qz.answers[4] = 1;
                }else {
                    qz.answers[4] = 0;
                }
            } else if (questionNum == 5) {
                Log.d(DEBUG_TAG, "inside if");
                if(checkedID == R.id.radioButton){
                    qz.answers[5] = 1;
                }else {
                    qz.answers[5] = 0;
                }
            }
            Log.d(DEBUG_TAG, "Answer tallied: " + questionNum + " = " + qz.answers[questionNum]);
        }
    }
    public static int getQuestionNum() {
        return 7;
    }
}
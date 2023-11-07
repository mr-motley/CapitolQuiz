package edu.uga.cs.capitolquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class stateInfoData {
    public static final String DEBUG_TAG = "stateInfoData";

    private SQLiteDatabase stateDB;

    private SQLiteOpenHelper dbHelper;

    private static final String[] allColumns = {
            databaseHelper.STATEINFO_COLUMN_STATE,
            databaseHelper.STATEINFO_COLUMN_CAPITOL,
            databaseHelper.STATEINFO_COLUMN_SECONDCITY,
            databaseHelper.STATEINFO_COLUMN_THIRDCITY,
            databaseHelper.STATEINFO_COLUMN_STATEHOOD,
            databaseHelper.STATEINFO_COLUMN_CAPITOLSINCE,
            databaseHelper.STATEINFO_COLUMN_SIZERANK,
            databaseHelper.STATEINFO_COLUMN_ID
    };

    private static final String[] allCol = {
            databaseHelper.QUIZZES_COLUMN_ID,
            databaseHelper.QUIZZES_COLUMN_QUIZDATE,
            databaseHelper.QUIZZES_COLUMN_RESULT
    };

    public stateInfoData(Context context){
        this.dbHelper = databaseHelper.getInstance(context);
    }

    public List<State> retrieveAllStates() {
        ArrayList<State> states = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            //Select query on stateInfo table
            cursor = stateDB.query(databaseHelper.TB_STATEINFO,allColumns,
                    null,null,null,null,null,null);

            if(cursor != null && cursor.getCount() > 0){
                while(cursor.moveToNext()){

                    if(cursor.getColumnCount() >= 8){
                        //get all attributes of the state
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_STATE);
                        String stateName = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_CAPITOL);
                        String capitol = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_SECONDCITY);
                        String secondCity = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_THIRDCITY);
                        String thirdCity = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_STATEHOOD);
                        long statehood = cursor.getLong(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_CAPITOLSINCE);
                        long capitolSince = cursor.getLong(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_SIZERANK);
                        long sizeRank = cursor.getLong(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_ID);
                        long id = cursor.getLong(columnIndex);

                        State state = new State(stateName,capitol,secondCity,thirdCity,statehood,capitolSince,sizeRank);
                        state.setId(id);
                        states.add(state);
                        Log.d(DEBUG_TAG, "Retrieved state: " + state);
                    }
                }
            }
            if(cursor != null){
                Log.d(DEBUG_TAG, "Number of records from DB: " + cursor.getCount());
            } else{
                Log.d(DEBUG_TAG, "Number of records from DB: 0");
            }
        } catch(Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } finally{
            //close cursor to avoid data leaks
            if(cursor != null){
                cursor.close();
            }
        }
        return states;
    }

    public List<Quiz> retrieveAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;
        try {
            //execute select query
            cursor = stateDB.query(databaseHelper.TB_QUIZZES, allColumns, null,null,null,null,null);
            if(cursor != null && cursor.getCount() > 0){
                while (cursor.moveToNext()) {

                    if (cursor.getColumnCount() >= 3){
                        int[] temp = new int[6];
                        //get attributes of the quiz object
                        columnIndex = cursor.getColumnIndex(databaseHelper.QUIZZES_COLUMN_ID);
                        long id = cursor.getLong(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.QUIZZES_COLUMN_QUIZDATE);
                        String quizDate = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.QUIZZES_COLUMN_RESULT);
                        int result = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.QUIZZES_COULUMN_Q1STATE);
                        temp[0] = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.QUIZZES_COULUMN_Q2STATE);
                        temp[1] = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.QUIZZES_COULUMN_Q3STATE);
                        temp[2] = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.QUIZZES_COULUMN_Q4STATE);
                        temp[3] = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.QUIZZES_COULUMN_Q5STATE);
                        temp[4] = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.QUIZZES_COULUMN_Q6STATE);
                        temp[5] = cursor.getInt(columnIndex);

                        //create new Quiz object and fill with retrieved values
                        Quiz quiz = new Quiz(result,quizDate,temp);
                        quiz.setId(id);
                        quizzes.add(quiz);
                        Log.d(DEBUG_TAG,"Retrieved Quiz: " + quiz);
                    }
                }
            }
            if(cursor != null){
                Log.d(DEBUG_TAG, "Number of Records from DB: " + cursor.getCount());
            }else {
                Log.d(DEBUG_TAG, "Number of Records from DB: 0");
            }
        } catch(Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } finally {
            if(cursor != null){
                cursor.close();
            }
        }
        return quizzes;
    }

    public Quiz storeQuiz( Quiz quiz) {
        ContentValues values = new ContentValues();
        values.put(databaseHelper.QUIZZES_COLUMN_QUIZDATE, quiz.getQuizDate());
        values.put(databaseHelper.QUIZZES_COLUMN_RESULT, quiz.getResult());
        values.put(databaseHelper.QUIZZES_COULUMN_Q1STATE, quiz.getQ1State());
        values.put(databaseHelper.QUIZZES_COULUMN_Q2STATE,quiz.getQ2State());
        values.put(databaseHelper.QUIZZES_COULUMN_Q3STATE,quiz.getQ3State());
        values.put(databaseHelper.QUIZZES_COULUMN_Q4STATE,quiz.getQ4State());
        values.put(databaseHelper.QUIZZES_COULUMN_Q5STATE,quiz.getQ5State());
        values.put(databaseHelper.QUIZZES_COULUMN_Q6STATE,quiz.getQ6State());


        //Insert new row into DB table
        long id = stateDB.insert(databaseHelper.TB_QUIZZES,null, values);

        quiz.setId(id);

        Log.d(DEBUG_TAG, "Stored new quiz with id: " + String.valueOf(quiz.getId()));

        return quiz;
    }


}

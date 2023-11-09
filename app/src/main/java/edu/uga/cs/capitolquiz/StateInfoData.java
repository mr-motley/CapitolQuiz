package edu.uga.cs.capitolquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class StateInfoData {
    public static final String DEBUG_TAG = "stateInfoData";

    private SQLiteDatabase db;

    private SQLiteOpenHelper dbHelper;

    private static final String[] allColumns = {
            databaseHelper.STATEINFO_COLUMN_STATE,
            databaseHelper.STATEINFO_COLUMN_CAPITOL,
            databaseHelper.STATEINFO_COLUMN_SECONDCITY,
            databaseHelper.STATEINFO_COLUMN_THIRDCITY,
            databaseHelper.STATEINFO_COLUMN_ID
    };

    private static final String[] allCol = {
            databaseHelper.QUIZZES_COLUMN_ID,
            databaseHelper.QUIZZES_COLUMN_QUIZDATE,
            databaseHelper.QUIZZES_COLUMN_RESULT,
            databaseHelper.QUIZZES_COULUMN_Q1STATE,
            databaseHelper.QUIZZES_COULUMN_Q2STATE,
            databaseHelper.QUIZZES_COULUMN_Q3STATE,
            databaseHelper.QUIZZES_COULUMN_Q4STATE,
            databaseHelper.QUIZZES_COULUMN_Q5STATE,
            databaseHelper.QUIZZES_COULUMN_Q6STATE,
            databaseHelper.QUIZZES_COlUMN_CURRENTQ
    };

    public StateInfoData(Context context){
        this.dbHelper = databaseHelper.getInstance(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "stateInfoData: db open");
    }

    public void close() {
        if(dbHelper != null){
            dbHelper.close();
            Log.d(DEBUG_TAG, "stateInfoData: db closed");
        }
    }

    public boolean isDBOpen() {
        return db.isOpen();
    }


    public List<State> retrieveAllStates() {
        Log.d(DEBUG_TAG, "Begin Retrieval");
        ArrayList<State> states = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;
        try {
            //Select query on stateInfo table
            cursor = db.query(databaseHelper.TB_STATEINFO,allColumns,
                    null,null,null,null,null,null);

            if(cursor != null && cursor.getCount() > 0){
                while(cursor.moveToNext()){

                    if(cursor.getColumnCount() >= 5){
                        //get all attributes of the state
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_ID);
                        long id = cursor.getLong(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_STATE);
                        String stateName = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_CAPITOL);
                        String capitol = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_SECONDCITY);
                        String secondCity = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(databaseHelper.STATEINFO_COLUMN_THIRDCITY);
                        String thirdCity = cursor.getString(columnIndex);

                        Log.d(DEBUG_TAG, "creating State Object");
                        State state = new State(stateName,capitol,secondCity,thirdCity);
                        state.setId(id);
                        states.add(state);
                        //Log.d(DEBUG_TAG, "Retrieved state: " + state);
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
            //close cursor
            if(cursor != null){
                cursor.close();
            }
        }
        //return list of states
        Log.d(DEBUG_TAG, "Returning List of States");
        return states;
    }


    public List<Quiz> retrieveAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;
        try {
            //execute select query
            cursor = db.query(databaseHelper.TB_QUIZZES, allCol, null,null,null,null,null);
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
                        columnIndex = cursor.getColumnIndex(databaseHelper.QUIZZES_COlUMN_CURRENTQ);
                        int currentQ = cursor.getInt(columnIndex);

                        //create new Quiz object and fill with retrieved values
                        Quiz quiz = new Quiz(result,quizDate,temp,currentQ);
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
        //prepare SQL statement for storing quiz
        ContentValues values = new ContentValues();
        values.put(databaseHelper.QUIZZES_COLUMN_QUIZDATE, quiz.getQuizDate());
        values.put(databaseHelper.QUIZZES_COLUMN_RESULT, quiz.getResult());
        values.put(databaseHelper.QUIZZES_COULUMN_Q1STATE, quiz.getQ1State());
        values.put(databaseHelper.QUIZZES_COULUMN_Q2STATE,quiz.getQ2State());
        values.put(databaseHelper.QUIZZES_COULUMN_Q3STATE,quiz.getQ3State());
        values.put(databaseHelper.QUIZZES_COULUMN_Q4STATE,quiz.getQ4State());
        values.put(databaseHelper.QUIZZES_COULUMN_Q5STATE,quiz.getQ5State());
        values.put(databaseHelper.QUIZZES_COULUMN_Q6STATE,quiz.getQ6State());
        values.put(databaseHelper.QUIZZES_COlUMN_CURRENTQ,quiz.getCurrentQ());


        //Insert new row into DB table
        long id = db.insert(databaseHelper.TB_QUIZZES,null, values);

        quiz.setId(id);

        Log.d(DEBUG_TAG, "Stored new quiz with id: " + String.valueOf(quiz.getId()));

        return quiz;
    }

    public void populateDB(Context context) {
        //legacy method for inserting the values from the CSV in case it did not occur properly on startup,
        //for testing purposes only
        Log.d(DEBUG_TAG, "Inserting Initial Values: ");
        try {
            InputStream in_s = context.getAssets().open("StateCapitals.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(in_s));
            String[] nextRow;
            while( (nextRow = reader.readNext() ) != null){
                ContentValues cv = new ContentValues();
                cv.put(databaseHelper.STATEINFO_COLUMN_STATE, nextRow[0].trim());
                cv.put(databaseHelper.STATEINFO_COLUMN_CAPITOL, nextRow[1].trim());
                cv.put(databaseHelper.STATEINFO_COLUMN_SECONDCITY, nextRow[2].trim());
                cv.put(databaseHelper.STATEINFO_COLUMN_THIRDCITY, nextRow[3].trim());
                db.insert(databaseHelper.TB_STATEINFO, null, cv);
            }
        } catch (Exception e){
            Log.d(DEBUG_TAG, "csv read unsuccessful: " + e.getMessage());
        }


    }
}

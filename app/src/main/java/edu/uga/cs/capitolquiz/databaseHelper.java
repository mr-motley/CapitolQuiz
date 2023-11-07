package edu.uga.cs.capitolquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


public class databaseHelper extends SQLiteOpenHelper {


    private static final String DEBUG_TAG = "databaseHelper";

    public static String DB_NAME = "StateCapitols.db";

    public static final int DB_VERSION = 1;

    private Context context1;

    //Column Name Definitions
    public static final String STATEINFO_COLUMN_ID = "_id";
    public static final String STATEINFO_COLUMN_STATE= "state";
    public static final String STATEINFO_COLUMN_CAPITOL = "capitol";
    public static final String STATEINFO_COLUMN_SECONDCITY = "secondCity";
    public static final String STATEINFO_COLUMN_THIRDCITY = "thirdCity";
    public static final String TB_STATEINFO = "stateInfo";
    public static final String QUIZZES_COLUMN_ID = "_id";
    public static final String QUIZZES_COLUMN_QUIZDATE = "quizDate";
    public static final String QUIZZES_COLUMN_RESULT = "result";
    public static final String QUIZZES_COULUMN_Q1STATE = "q1State";
    public static final String QUIZZES_COULUMN_Q2STATE = "q2State";
    public static final String QUIZZES_COULUMN_Q3STATE = "q3State";
    public static final String QUIZZES_COULUMN_Q4STATE = "q4State";
    public static final String QUIZZES_COULUMN_Q5STATE = "q5State";
    public static final String QUIZZES_COULUMN_Q6STATE = "q6State";
    public static final String TB_QUIZZES = "quizzes";

    private static databaseHelper helperInstance;

    //method to create sql table
    private static final String CREATE_STATES =
            "create table " + TB_STATEINFO + " ("
            + STATEINFO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STATEINFO_COLUMN_STATE + " TEXT, "
            + STATEINFO_COLUMN_CAPITOL + " TEXT, "
            + STATEINFO_COLUMN_SECONDCITY + " TEXT, "
            + STATEINFO_COLUMN_THIRDCITY + " TEXT "
            + ")";

    private static final String CREATE_QUIZZES =
            "create table " + TB_QUIZZES + " ("
                    + QUIZZES_COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZZES_COLUMN_QUIZDATE + " TEXT, "
                    + QUIZZES_COLUMN_RESULT + " INTEGER, "
                    + QUIZZES_COULUMN_Q1STATE + " INTEGER, "
                    + QUIZZES_COULUMN_Q2STATE + " INTEGER, "
                    + QUIZZES_COULUMN_Q3STATE + " INTEGER, "
                    + QUIZZES_COULUMN_Q4STATE + " INTEGER, "
                    + QUIZZES_COULUMN_Q5STATE + " INTEGER, "
                    + QUIZZES_COULUMN_Q6STATE + " INTEGER "
                    + ")";

    private databaseHelper(Context context) {
        super (context, DB_NAME,null, DB_VERSION);
        this.context1 = context;
    }

    public static synchronized databaseHelper getInstance(Context context) {
        if(helperInstance == null){
            helperInstance = new databaseHelper(context.getApplicationContext());
        }
        return helperInstance;
    }

    //Creates a database if one doesn't exist and inserts initial values from csv
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_STATES);
        Log.d(DEBUG_TAG, "Table " + TB_STATEINFO + "created");
        db.execSQL(CREATE_QUIZZES);
        Log.d(DEBUG_TAG, "Table " + TB_QUIZZES + "created");
        Log.d(DEBUG_TAG, "Inserting Initial Values: ");
        try {
            String file = "StateCapitals.csv";
            AssetManager manager = context1.getAssets();
            InputStream inStream = null;
            try {
                inStream = manager.open(file);
            } catch (IOException e) {
                Log.d(DEBUG_TAG, e.getMessage());
                e.printStackTrace();
            }
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
            String line = "";
            Log.d(DEBUG_TAG, "Begining Transaction: Insert initial");
            db.beginTransaction();
            try {
                while ((line = buffer.readLine()) != null) {
                    Log.d(DEBUG_TAG, "IN LOOP");
                    String[] colums = line.split(",");
                    if (colums.length != 4) {
                        Log.d("CSVParser", "Skipping Bad CSV Row");
                        continue;
                    }
                    Log.d(DEBUG_TAG, "IN LOOP");
                    ContentValues cv = new ContentValues();
                    cv.put(STATEINFO_COLUMN_STATE, colums[0].trim());
                    cv.put(STATEINFO_COLUMN_CAPITOL, colums[1].trim());
                    cv.put(STATEINFO_COLUMN_SECONDCITY, colums[2].trim());
                    cv.put(STATEINFO_COLUMN_THIRDCITY, colums[3].trim());
                    db.insert(TB_STATEINFO, null, cv);
                }
                Log.d(DEBUG_TAG, "EXITED LOOP");
            } catch (IOException e) {
                Log.d(DEBUG_TAG, e.getMessage());
                e.printStackTrace();
            } finally {
                db.setTransactionSuccessful();
                db.endTransaction();
            }

            Log.d(DEBUG_TAG, "Ended transaction: Create DB");

        } catch (Exception e) {
            Log.d(DEBUG_TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + TB_STATEINFO);
        db.execSQL("drop table if exists " + TB_QUIZZES);
        onCreate(db);
        Log.d(DEBUG_TAG, "Table " + TB_STATEINFO + "upgraded");
        Log.d(DEBUG_TAG, "Table " + TB_QUIZZES + "upgraded");
    }


}

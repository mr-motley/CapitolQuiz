package edu.uga.cs.capitolquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opencsv.CSVReader;

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
    public static final String QUIZZES_COlUMN_CURRENTQ = "currentQ";
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
                    + QUIZZES_COULUMN_Q6STATE + " INTEGER, "
                    + QUIZZES_COlUMN_CURRENTQ + " INTEGER "
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
            InputStream in_s = context1.getAssets().open("StateCapitals.csv");
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + TB_STATEINFO);
        db.execSQL("drop table if exists " + TB_QUIZZES);
        onCreate(db);
        Log.d(DEBUG_TAG, "Table " + TB_STATEINFO + "upgraded");
        Log.d(DEBUG_TAG, "Table " + TB_QUIZZES + "upgraded");
    }


}

package edu.uga.cs.capitolquiz;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class databaseHelper extends SQLiteOpenHelper {
    public static String DB_PATH = "/data/data/edu.uga.cs.capitolquiz/databases/";

    public static String DB_NAME = "StateCapitols.sqlite";

    public static final int DB_VERSION = 1;

    //Column Name Definitions
    public static final String STATEINFO_COLUMN_ID = "id";
    public static final String STATEINFO_COLUMN_STATE= "state";
    public static final String STATEINFO_COLUMN_CAPITOL = "capitol";
    public static final String STATEINFO_COLUMN_SECONDCITY = "secondCity";
    public static final String STATEINFO_COLUMN_THIRDCITY = "thirdCity";
    public static final String STATEINFO_COLUMN_STATEHOOD = "statehood";
    public static final String STATEINFO_COLUMN_CAPITOLSINCE = "capitolSince";
    public static final String STATEINFO_COLUMN_SIZERANK = "sizeRank";
    public static final String TB_STATEINFO = "stateInfo";
    public static final String QUIZZES_COLUMN_ID = "id";
    public static final String QUIZZES_COLUMN_QUIZDATE = "quizDate";
    public static final String QUIZZES_COLUMN_RESULT = "result";
    public static final String QUIZZES_COULUMN_Q1STATE = "q1State";
    public static final String QUIZZES_COULUMN_Q2STATE = "q2State";
    public static final String QUIZZES_COULUMN_Q3STATE = "q3State";
    public static final String QUIZZES_COULUMN_Q4STATE = "q4State";
    public static final String QUIZZES_COULUMN_Q5STATE = "q5State";
    public static final String QUIZZES_COULUMN_Q6STATE = "q6State";
    public static final String TB_QUIZZES = "quizes";

    private SQLiteDatabase stateDB;
    private static databaseHelper helperInstance;
    private Context context;
    public databaseHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }

    public static synchronized databaseHelper getInstance(Context context){
        //check if instance already exists
        if(helperInstance == null){
            helperInstance = new databaseHelper(context.getApplicationContext());
        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //empty because using db created in sqlite studio
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //empty because using a db created in sqlite studio
    }

    @Override
    public synchronized void close(){
        if(stateDB!= null){
            stateDB.close();
        }
        super.close();
    }

    /***
     * Checks if the database exists on the device or not
     * @return
     */
    public boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("tle99 - check", e.getMessage());
        }
        if (tempDB != null){
            tempDB.close();
        }
        return tempDB != null ? true : false;
    }

    /***
     * Copies the database from assests to the device
     * @throws IOException
     */
    public void copyDatabase() throws IOException {
        try{
            InputStream myInput = context.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer,0,length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            Log.e("tle - copyDatabase", e.getMessage());
        }
    }

    /***
     * Opens the database
     * @throws SQLException
     */
    public void openDB() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        stateDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    /***
     * Check if DB exists on the device, creates a new one if necessary
     * @throws IOException
     */
    public void createDatabase() throws IOException {
        boolean dbExist = checkDataBase();

        if(dbExist){

        } else{
            this.getReadableDatabase();
            try{
                copyDatabase();
            } catch (IOException e){
                Log.e("tle99 - create", e.getMessage());
            }
        }
    }
}

package ua.example.ioeug.surdomay.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Asus on 04.06.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "Results", null, 17);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table Test1 ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "time TEXT,"
                + "step TEXT,"
                + "answer TEXT,"
                + "procent TEXT" + ");"
        );
        db.execSQL("create table Test2 ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "time TEXT,"
                + "step TEXT,"
                + "answer TEXT,"
                + "procent TEXT" + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
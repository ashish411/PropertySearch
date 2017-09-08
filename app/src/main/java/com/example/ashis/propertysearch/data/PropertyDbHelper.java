package com.example.ashis.propertysearch.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ashis on 8/11/2017.
 */

public class PropertyDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "property.db";
    private static final int DATABASE_VERSION = 7;

    public PropertyDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_QUERY = "CREATE TABLE "+ PropertyContract.PropertyEntry.TABLE_NAME + "( "+
                PropertyContract.PropertyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
                PropertyContract.PropertyEntry.COLUMN_AREA + " TEXT NOT NULL , "+
                PropertyContract.PropertyEntry.COLUMN_FLOOR + " TEXT ,"+
                PropertyContract.PropertyEntry.COLUMN_SECTOR + " TEXT NOT NULL ,"+
                PropertyContract.PropertyEntry.COLUMN_NOTES + " TEXT , "+
                PropertyContract.PropertyEntry.COLUMN_PKT + " TEXT NOT NULL ,"+
                PropertyContract.PropertyEntry.COLUMN_PLOT + " TEXT NOT NULL ,"+
                PropertyContract.PropertyEntry.COLUMN_PRICE + " TEXT NOT NULL,"+
                PropertyContract.PropertyEntry.COLUMN_DEALER + " INTEGER NOT NULL ," +
                PropertyContract.PropertyEntry.COLUMN_LOCATION + " TEXT , "+
                PropertyContract.PropertyEntry.COLUMN_BEDROOM+ " TEXT ,"+
                PropertyContract.PropertyEntry.COLUMN_SOCIETY +" TEXT ,"+
                PropertyContract.PropertyEntry.COLUMN_REMARKS + " TEXT ); ";
        db.execSQL(CREATE_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String alterQuery = "alter table "+ PropertyContract.PropertyEntry.TABLE_NAME +
                "rename to temp ;";
        String newTable  = "CREATE TABLE "+ PropertyContract.PropertyEntry.TABLE_NAME + "( "+
                PropertyContract.PropertyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
                PropertyContract.PropertyEntry.COLUMN_AREA + " TEXT NOT NULL , "+
                PropertyContract.PropertyEntry.COLUMN_FLOOR + " TEXT ,"+
                PropertyContract.PropertyEntry.COLUMN_SECTOR + " TEXT NOT NULL ,"+
                PropertyContract.PropertyEntry.COLUMN_NOTES + " TEXT , "+
                PropertyContract.PropertyEntry.COLUMN_PKT + " TEXT NOT NULL ,"+
                PropertyContract.PropertyEntry.COLUMN_PLOT + " TEXT NOT NULL ,"+
                PropertyContract.PropertyEntry.COLUMN_PRICE + " TEXT NOT NULL,"+
                PropertyContract.PropertyEntry.COLUMN_DEALER + " INTEGER NOT NULL ," +
                PropertyContract.PropertyEntry.COLUMN_LOCATION + " TEXT , "+
                PropertyContract.PropertyEntry.COLUMN_BEDROOM+ " TEXT ,"+
                PropertyContract.PropertyEntry.COLUMN_SOCIETY +" TEXT ,"+
                PropertyContract.PropertyEntry.COLUMN_REMARKS + " TEXT ,"+
                PropertyContract.PropertyEntry.COLUMN_DEALER_NAME + " TEXT ); ";
        String insertDataQuery = "insert into "+ PropertyContract.PropertyEntry.TABLE_NAME+
                " select *,0 from temp;";
        String dropTableQuery = "drop table temp;";
        db.execSQL(alterQuery);
        db.execSQL(newTable);
        db.execSQL(dropTableQuery);
        db.execSQL(insertDataQuery);

    }
}

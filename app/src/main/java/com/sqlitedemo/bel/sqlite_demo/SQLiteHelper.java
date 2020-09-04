package com.sqlitedemo.bel.sqlite_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="CollegeDB";
    private static final String TABLE_NAME="StudentInfo";
    private static final int VERSION_NUMBER=1;
    private static final String NAME="name";
    private static final String AGE="age";
    private static final String GENDER="gender";
    private static final String ID="id";
    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(100), "+AGE+" VARCHAR(100), "+GENDER+" VARCHAR(100));";
    private static final String SELECT_TABLE="SELECT * FROM "+TABLE_NAME;
    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;
    private Context context;

    public SQLiteHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION_NUMBER);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE);
        }catch(Exception e)
        {
            Toast.makeText(context, "Exception: "+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch(Exception e)
        {
            Toast.makeText(context, "Exception: "+e, Toast.LENGTH_SHORT).show();
        }
    }

    public long addData(String name,String age,String gender)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);
        long rowID=sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return rowID;
    }

    public Cursor displayData()
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(SELECT_TABLE,null);
        return  cursor;
    }

    public boolean updateData(String id,String name,String age,String gender)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);
        sqLiteDatabase.update(TABLE_NAME,contentValues,ID+"=?",new String[]{id});
        return true;
    }

    public int deleteData(String id)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,ID+"=?",new String[]{id});
    }
}

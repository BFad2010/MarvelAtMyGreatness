package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xxsti on 10/6/2018.
 */

public class YourHerosBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "yourHeros.db";

    public YourHerosBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + YourHerosDBSchema.YourHerosTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                YourHerosDBSchema.YourHerosTable.Cols.uuid + ", " +
                YourHerosDBSchema.YourHerosTable.Cols.name + ", " +
                YourHerosDBSchema.YourHerosTable.Cols.realname + ", " +
                YourHerosDBSchema.YourHerosTable.Cols.team + ", " +
                YourHerosDBSchema.YourHerosTable.Cols.firstappearance + ", " +
                YourHerosDBSchema.YourHerosTable.Cols.createdby + ", " +
                YourHerosDBSchema.YourHerosTable.Cols.publisher + ", " +
                YourHerosDBSchema.YourHerosTable.Cols.imageurl + ", " +
                YourHerosDBSchema.YourHerosTable.Cols.bio + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
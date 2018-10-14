package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.FavoriteHerosDBSchema.FavoriteHerosTable;

/**
 * Created by xxsti on 10/3/2018.
 */

public class FavoriteHerosBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "favoriteHeros.db";

    public FavoriteHerosBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + FavoriteHerosTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                FavoriteHerosTable.Cols.uuid + ", " +
                FavoriteHerosTable.Cols.name + ", " +
                FavoriteHerosTable.Cols.realname + ", " +
                FavoriteHerosTable.Cols.team + ", " +
                FavoriteHerosTable.Cols.firstappearance + ", " +
                FavoriteHerosTable.Cols.createdby + ", " +
                FavoriteHerosTable.Cols.publisher + ", " +
                FavoriteHerosTable.Cols.imageurl + ", " +
                FavoriteHerosTable.Cols.bio + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}

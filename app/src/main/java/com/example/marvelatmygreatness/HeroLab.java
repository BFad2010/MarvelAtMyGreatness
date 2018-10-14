package com.example.marvelatmygreatness;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import database.FavoriteHerosDBSchema.FavoriteHerosTable;
import database.FavoriteHerosBaseHelper;
import database.FavoriteHerosCursorWrapper;

import database.YourHerosBaseHelper;
import database.YourHerosDBSchema.YourHerosTable;
import database.YourHerosCursorWrapper;

/**
 * Created by xxsti on 10/3/2018.
 */

public class HeroLab {
    private static HeroLab sHeroLab;
    private Context mContext;
    private SQLiteDatabase favoriteHeroDatabase;
    private SQLiteDatabase yourHeroDatabase;

    public static HeroLab get(Context context){
        if(sHeroLab == null){
            sHeroLab = new HeroLab(context);
        }
        return sHeroLab;
    }

    private HeroLab(Context context){
        mContext = context.getApplicationContext();
        favoriteHeroDatabase = new FavoriteHerosBaseHelper(mContext).getWritableDatabase();
        yourHeroDatabase = new YourHerosBaseHelper(mContext).getWritableDatabase();
    }

    public void addFavoriteHero(Hero hero){
        ContentValues heroValues = getFavoriteContentValues(hero);

        favoriteHeroDatabase.insert(FavoriteHerosTable.NAME, null, heroValues);
    }

    public void addYourHero(Hero hero){
        ContentValues heroValues = getYourHeroContentValues(hero);

        yourHeroDatabase.insert(YourHerosTable.NAME, null, heroValues);
    }

    public void removeFavoriteHero(Hero hero){
        String heroName = hero.getName();

        favoriteHeroDatabase.delete(FavoriteHerosTable.NAME, "name = '"+heroName+"'", null);
    }

    public void removeYourHero(Hero hero){
        String heroName = hero.getName();

        yourHeroDatabase.delete(YourHerosTable.NAME, "name = '"+heroName+"'", null);
    }

    public List<Hero> getFavoriteHeros(){
        List<Hero> heros = new ArrayList<>();

        FavoriteHerosCursorWrapper cursor = queryFavoriteHeros(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                heros.add(cursor.getHero());
                cursor.moveToNext();
            }
        } finally{
            cursor.close();
        }

        return heros;
    }

    public List<Hero> getYourHeros(){
        List<Hero> heros = new ArrayList<>();

        YourHerosCursorWrapper cursor = queryYourHeros(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                heros.add(cursor.getHero());
                cursor.moveToNext();
            }
        } finally{
            cursor.close();
        }

        return heros;
    }

    private static ContentValues getFavoriteContentValues(Hero hero){
        ContentValues values = new ContentValues();
        values.put(FavoriteHerosTable.Cols.uuid, hero.getId().toString());
        values.put(FavoriteHerosTable.Cols.name, hero.getName());
        values.put(FavoriteHerosTable.Cols.realname, hero.getRealname());
        values.put(FavoriteHerosTable.Cols.team, hero.getTeam());
        values.put(FavoriteHerosTable.Cols.firstappearance, hero.getFirstappearance());
        values.put(FavoriteHerosTable.Cols.createdby, hero.getCreatedby());
        values.put(FavoriteHerosTable.Cols.publisher, hero.getPublisher());
        values.put(FavoriteHerosTable.Cols.imageurl, hero.getImageurl());
        values.put(FavoriteHerosTable.Cols.bio, hero.getBio());

        return values;
    }

    private static ContentValues getYourHeroContentValues(Hero hero){
        ContentValues values = new ContentValues();
        values.put(YourHerosTable.Cols.uuid, hero.getId().toString());
        values.put(YourHerosTable.Cols.name, hero.getName());
        values.put(YourHerosTable.Cols.realname, hero.getRealname());
        values.put(YourHerosTable.Cols.team, hero.getTeam());
        values.put(YourHerosTable.Cols.firstappearance, hero.getFirstappearance());
        values.put(YourHerosTable.Cols.createdby, hero.getCreatedby());
        values.put(YourHerosTable.Cols.publisher, hero.getPublisher());
        values.put(YourHerosTable.Cols.imageurl, hero.getImageurl());
        values.put(YourHerosTable.Cols.bio, hero.getBio());

        return values;
    }

    private FavoriteHerosCursorWrapper queryFavoriteHeros(String whereClause, String[] whereArgs){
        Cursor cursor = favoriteHeroDatabase.query(
                FavoriteHerosTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new FavoriteHerosCursorWrapper(cursor);
    }

    private YourHerosCursorWrapper queryYourHeros(String whereClause, String[] whereArgs){
        Cursor cursor = yourHeroDatabase.query(
                YourHerosTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new YourHerosCursorWrapper(cursor);
    }
}

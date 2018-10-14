package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.marvelatmygreatness.Hero;

import java.util.Date;
import java.util.UUID;

import database.FavoriteHerosDBSchema.FavoriteHerosTable;

/**
 * Created by xxsti on 10/3/2018.
 */

public class FavoriteHerosCursorWrapper extends CursorWrapper{

    public FavoriteHerosCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Hero getHero(){
        String uuidString = getString(getColumnIndex(FavoriteHerosTable.Cols.uuid));
        String name = getString(getColumnIndex(FavoriteHerosTable.Cols.name));
        String realname = getString(getColumnIndex(FavoriteHerosTable.Cols.realname));
        String team = getString(getColumnIndex(FavoriteHerosTable.Cols.team));
        String firstappearance = getString(getColumnIndex(FavoriteHerosTable.Cols.firstappearance));
        String createdby = getString(getColumnIndex(FavoriteHerosTable.Cols.createdby));
        String publisher = getString(getColumnIndex(FavoriteHerosTable.Cols.publisher));
        String imageurl = getString(getColumnIndex(FavoriteHerosTable.Cols.imageurl));
        String bio = getString(getColumnIndex(FavoriteHerosTable.Cols.bio));


        Hero hero = new Hero(UUID.fromString(uuidString));
        hero.setName(name);
        hero.setRealname(realname);
        hero.setTeam(team);
        hero.setFirstappearance(firstappearance);
        hero.setCreatedby(createdby);
        hero.setPublisher(publisher);
        hero.setImageurl(imageurl);
        hero.setBio(bio);

        return hero;
    }
}

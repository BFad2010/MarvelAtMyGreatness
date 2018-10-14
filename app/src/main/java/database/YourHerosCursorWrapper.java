package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.marvelatmygreatness.Hero;

import database.YourHerosDBSchema.YourHerosTable;

import java.util.UUID;

/**
 * Created by xxsti on 10/6/2018.
 */

public class YourHerosCursorWrapper extends CursorWrapper {

    public YourHerosCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Hero getHero(){
        String uuidString = getString(getColumnIndex(YourHerosTable.Cols.uuid));
        String name = getString(getColumnIndex(YourHerosTable.Cols.name));
        String realname = getString(getColumnIndex(YourHerosTable.Cols.realname));
        String team = getString(getColumnIndex(YourHerosTable.Cols.team));
        String firstappearance = getString(getColumnIndex(YourHerosTable.Cols.firstappearance));
        String createdby = getString(getColumnIndex(YourHerosTable.Cols.createdby));
        String publisher = getString(getColumnIndex(YourHerosTable.Cols.publisher));
        String imageurl = getString(getColumnIndex(YourHerosTable.Cols.imageurl));
        String bio = getString(getColumnIndex(YourHerosTable.Cols.bio));


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

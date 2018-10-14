package com.example.marvelatmygreatness;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

public class HeroMainActivity extends SingleFragmentActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE);
        }

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        bottomNavigationView.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFrag = null;

                switch(menuItem.getItemId()){
                    case R.id.action_heros_list:
                        selectedFrag = new HeroListFragment();
                        break;
                    case R.id.action_favorite_heros_list:
                        selectedFrag = new FavoriteHerosFragment();
                        break;
                    case R.id.action_create_your_own_heros:
                        selectedFrag = new CreateHerosFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFrag)
                        .commit();

                return true;
            }
        });
    }

    @Override
    public Fragment createFragment(){
        return HeroListFragment.newInstance();
    }
}

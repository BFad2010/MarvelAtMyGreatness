package com.example.marvelatmygreatness;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xxsti on 9/24/2018.
 */

public class HeroListFragment extends Fragment {
    private static final String TAG = "JSON Response: ";
    private static final String EXTRA_HERO = "com.example.marvelatmygreatness.hero";
    private static final String EXTRA_HERO_BIO = "com.example.marvelatmygreatness.heroBio";
    private static final String EXTRA_HERO_REAL_NAME = "com.example.marvelatmygreatness.heroRealName";
    private static final String EXTRA_HERO_TEAM = "com.example.marvelatmygreatness.heroTeam";
    private static final String EXTRA_HERO_FIRST_APP = "com.example.marvelatmygreatness.heroFirstApp";
    private static final String EXTRA_HERO_CREATEDBY = "com.example.marvelatmygreatness.heroCreatedBy";
    private static final String EXTRA_HERO_PUBLISHER = "com.example.marvelatmygreatness.heroPublisher";
    private static final String EXTRA_HERO_IMAGE_URL = "com.example.marvelatmygreatness.heroImageURL";
    private static final String EXTRA_YOUR_HERO = "com.example.marvelatmygreatness.heroYourHero";
    private RecyclerView mMarvelRecyclerView;
    public List<Hero> mHeroes = new ArrayList<>();
    public List<Hero> yHeroes = new ArrayList<>();
    private String mHeroBio;
    private String mRealname;
    private String mTeam;
    private String mFirstappearance;
    private String mCreatedby;
    private String mPublisher;
    private String mImageurl;
    private String yourHero;
    private String yourHeros = null;

    public static HeroListFragment newInstance(){
        return new HeroListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getMarvelHeroList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_marvel_list, container, false);

        TabLayout heroListTabs = v.findViewById(R.id.hero_list_tab_layout);
        heroListTabs.addTab(heroListTabs.newTab().setText("Marvel Heros"));
        heroListTabs.addTab(heroListTabs.newTab().setText("Your Heros"));
        heroListTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        heroListTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText() == "Marvel Heros"){
                    getMarvelHeroList();
                }
                else if(tab.getText() == "Your Heros"){
                    getYourHerosList();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab){
                if(tab.getText() == "Marvel Heros"){
                    getMarvelHeroList();
                }
                else if(tab.getText() == "Your Heros"){
                    getYourHerosList();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //hold for code
            }
        });

        mMarvelRecyclerView = v.findViewById(R.id.hero_characters_recycler_view);
        mMarvelRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MarvelCharacterAdapter heroAdapter = new MarvelCharacterAdapter(mHeroes);
        mMarvelRecyclerView.setAdapter(heroAdapter);

        return v;
    }

    private class MarvelCharactersHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mHeroHeaderTextView;
        private ImageView mHeroImageView;
        //private LinearLayout mHeroFullItemLayout;

        @Override
        public void onClick(View v){
            String mHeroName = (String) mHeroHeaderTextView.getText();
            if(yourHeros == "false") {
                for (Hero h : mHeroes) {
                    if (h.getName() == mHeroName) {
                        mHeroBio = h.getBio();
                        mRealname = h.getRealname();
                        mTeam = h.getTeam();
                        mFirstappearance = h.getFirstappearance();
                        mCreatedby = h.getCreatedby();
                        mPublisher = h.getPublisher();
                        mImageurl = h.getImageurl();
                        yourHero = "false";
                    }
                }
            }
            else{
                for (Hero h : yHeroes) {
                    if (h.getName() == mHeroName) {
                        mHeroBio = h.getBio();
                        mRealname = h.getRealname();
                        mTeam = h.getTeam();
                        mFirstappearance = h.getFirstappearance();
                        mCreatedby = h.getCreatedby();
                        mPublisher = h.getPublisher();
                        mImageurl = h.getImageurl();
                        yourHero = "true";
                    }
                }
            }

            Bundle args = new Bundle();
            args.putString(EXTRA_HERO, mHeroName);
            args.putString(EXTRA_HERO_BIO, mHeroBio);
            args.putString(EXTRA_HERO_REAL_NAME, mRealname);
            args.putString(EXTRA_HERO_TEAM, mTeam);
            args.putString(EXTRA_HERO_FIRST_APP, mFirstappearance);
            args.putString(EXTRA_HERO_CREATEDBY, mCreatedby);
            args.putString(EXTRA_HERO_PUBLISHER, mPublisher);
            args.putString(EXTRA_HERO_IMAGE_URL, mImageurl);
            args.putString(EXTRA_YOUR_HERO, yourHero);

            Fragment marvelDetailFrag = new HeroDetailFragment();
            marvelDetailFrag.setArguments(args);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, marvelDetailFrag)
                    .addToBackStack("Character Detail")
                    .commit();
        }

        public MarvelCharactersHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.list_item_marvel_character, container, false));

            itemView.setOnClickListener(this);

            mHeroHeaderTextView = itemView.findViewById(R.id.list_item_marvel_hero_name);
            mHeroImageView = itemView.findViewById(R.id.list_item_marvel_hero_image);
        }

        public void bindMarvelCharacter(Hero hero, int heroPos){
            mHeroHeaderTextView.setText(hero.getName());
            mHeroImageView.setImageURI(null);
            Uri heroImageUri = Uri.parse(hero.getImageurl());
            //Log.i("Image URI", heroImageUri.toString());
            Picasso.get().load(heroImageUri)
                    .placeholder(R.drawable.ic_hero_image_place_holder)
                    .resize(200,150)
                    .into(mHeroImageView);
        }
    }

    private class MarvelCharacterAdapter extends RecyclerView.Adapter<MarvelCharactersHolder>{
        private List<Hero> mHeroes = new ArrayList<>();

        public MarvelCharacterAdapter(List<Hero> heros){
            mHeroes = heros;
        }

        @Override
        public MarvelCharactersHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new MarvelCharactersHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(MarvelCharactersHolder marvelHolder, int heroPos){
            Hero heroItem = mHeroes.get(heroPos);
            marvelHolder.bindMarvelCharacter(heroItem, heroPos);
        }

        @Override
        public int getItemCount(){
            return mHeroes.size();
        }
    }

    private void getMarvelHeroList(){
            yourHeros = "false";
            mHeroes = new ArrayList<>();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HeroApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            HeroApi heroApi = retrofit.create(HeroApi.class);

            Call<List<Hero>> call = heroApi.getHeroes();

            call.enqueue(new Callback<List<Hero>>() {
                @Override
                public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                    List<Hero> heroes = response.body();

                    for(Hero h : heroes){
                        Log.i(TAG, h.getName());
                        mHeroes.add(h);
                    }
                    MarvelCharacterAdapter heroAdapter = new MarvelCharacterAdapter(mHeroes);
                    mMarvelRecyclerView.setAdapter(heroAdapter);
                }

                @Override
                public void onFailure(Call<List<Hero>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void getYourHerosList(){
        yourHeros = "true";
        yHeroes = new ArrayList<>();
        yHeroes = HeroLab.get(getActivity()).getYourHeros();
        MarvelCharacterAdapter heroAdapter = new MarvelCharacterAdapter(yHeroes);
        mMarvelRecyclerView.setAdapter(heroAdapter);
    }

}

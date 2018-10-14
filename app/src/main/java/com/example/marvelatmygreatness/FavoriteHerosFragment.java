package com.example.marvelatmygreatness;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxsti on 10/1/2018.
 */

public class FavoriteHerosFragment extends Fragment {
    private static final String EXTRA_HERO = "com.example.marvelatmygreatness.favoriteHero";
    private static final String EXTRA_HERO_BIO = "com.example.marvelatmygreatness.favoriteHeroBio";
    private static final String EXTRA_HERO_REAL_NAME = "com.example.marvelatmygreatness.favoriteHeroRealName";
    private static final String EXTRA_HERO_TEAM = "com.example.marvelatmygreatness.favoriteHeroTeam";
    private static final String EXTRA_HERO_FIRST_APP = "com.example.marvelatmygreatness.favoriteHeroFirstApp";
    private static final String EXTRA_HERO_CREATEDBY = "com.example.marvelatmygreatness.favoriteHeroCreatedBy";
    private static final String EXTRA_HERO_PUBLISHER = "com.example.marvelatmygreatness.favoriteHeroPublisher";
    private static final String EXTRA_HERO_IMAGE_URL = "com.example.marvelatmygreatness.favoriteHeroImageURL";
    private static final String EXTRA_FAVORITE = "com.example.marvelatmygreatness.favoriteExtra";
    private List<Hero> favHeros = new ArrayList<>();
    private RecyclerView favoriteHerosRecyclerView;
    private String mHeroBio;
    private String mRealname;
    private String mTeam;
    private String mFirstappearance;
    private String mCreatedby;
    private String mPublisher;
    private String mImageurl;

    public static FavoriteHerosFragment newInstance(){
        return new FavoriteHerosFragment();
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        favHeros = HeroLab.get(getActivity()).getFavoriteHeros();
        for(Hero h : favHeros){
            String heroName = h.getName();
            Log.i("Favorite Heros", heroName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_favorite_heros, container, false);
        favoriteHerosRecyclerView = v.findViewById(R.id.favorite_heros_recycler_view);
        favoriteHerosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FavoriteHerosAdapter favoriteHerosAdapter = new FavoriteHerosAdapter(favHeros);
        favoriteHerosRecyclerView.setAdapter(favoriteHerosAdapter);

        return v;
    }

    private class FavoriteHerosHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView favoriteHeroNameTextView;
        private ImageView favoriteHeroPicImageView;

        @Override
        public void onClick(View v){
            String mHeroName = (String) favoriteHeroNameTextView.getText();
            for(Hero h : favHeros){
                if(h.getName() == mHeroName){
                    mHeroBio = h.getBio();
                    mRealname = h.getRealname();
                    mTeam = h.getTeam();
                    mFirstappearance = h.getFirstappearance();
                    mCreatedby = h.getCreatedby();
                    mPublisher = h.getPublisher();
                    mImageurl = h.getImageurl();
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
            args.putString(EXTRA_FAVORITE, "True");

            Fragment marvelDetailFrag = new HeroDetailFragment();
            marvelDetailFrag.setArguments(args);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, marvelDetailFrag)
                    .addToBackStack("Favorite Character Detail")
                    .commit();
        }

        public FavoriteHerosHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.list_item_favorite_heros, container, false));

            favoriteHeroNameTextView = itemView.findViewById(R.id.list_item_favorite_hero_name);
            favoriteHeroPicImageView = itemView.findViewById(R.id.list_item_favorite_hero_image);

            itemView.setOnClickListener(this);
        }

        public void bindFavoriteHero(Hero hero, int heroPos){
            favoriteHeroNameTextView.setText(hero.getName());
            Picasso.get().load(hero.getImageurl()).resize(200,150).into(favoriteHeroPicImageView);
        }
    }

    private class FavoriteHerosAdapter extends RecyclerView.Adapter<FavoriteHerosHolder>{
        private List<Hero> mHeroes = new ArrayList<>();

        public FavoriteHerosAdapter(List<Hero> heros){
            mHeroes = heros;
        }

        @Override
        public FavoriteHerosHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new FavoriteHerosHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(FavoriteHerosHolder favoriteHerosHolder, int heroPos){
            Hero heroItem = mHeroes.get(heroPos);
            favoriteHerosHolder.bindFavoriteHero(heroItem, heroPos);
        }

        @Override
        public int getItemCount(){
            return mHeroes.size();
        }
    }
}

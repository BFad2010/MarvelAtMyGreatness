package com.example.marvelatmygreatness;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by xxsti on 9/24/2018.
 */

public class HeroDetailFragment extends Fragment {

    private String mHeroName;
    private String mHeroBio;
    private String mRealName;
    private String mTeam;
    private String mFirstApp;
    private String mCreatedBy;
    private String mPublisher;
    private String mImageUrl;
    private String yourHero = "false";
    private List<Hero> mHeroes = new ArrayList<>();
    public Boolean isFavorite;
    private Hero hero;

    public static HeroDetailFragment newInstance(){
        return new HeroDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        isFavorite = false;

        Bundle args = this.getArguments();
        if(args != null){
            if((args.getString("com.example.marvelatmygreatness.favoriteExtra")) == "True"){
                mHeroName = args.getString("com.example.marvelatmygreatness.favoriteHero");
                mHeroBio = args.getString("com.example.marvelatmygreatness.favoriteHeroBio");
                mRealName = args.getString("com.example.marvelatmygreatness.favoriteHeroRealName");
                mTeam = args.getString("com.example.marvelatmygreatness.favoriteHeroTeam");
                mFirstApp = args.getString("com.example.marvelatmygreatness.favoriteHeroFirstApp");
                mCreatedBy = args.getString("com.example.marvelatmygreatness.favoriteHeroCreatedBy");
                mPublisher = args.getString("com.example.marvelatmygreatness.favoriteHeroPublisher");
                mImageUrl = args.getString("com.example.marvelatmygreatness.favoriteHeroImageURL");
            }
            else {
                mHeroName = args.getString("com.example.marvelatmygreatness.hero");
                mHeroBio = args.getString("com.example.marvelatmygreatness.heroBio");
                mRealName = args.getString("com.example.marvelatmygreatness.heroRealName");
                mTeam = args.getString("com.example.marvelatmygreatness.heroTeam");
                mFirstApp = args.getString("com.example.marvelatmygreatness.heroFirstApp");
                mCreatedBy = args.getString("com.example.marvelatmygreatness.heroCreatedBy");
                mPublisher = args.getString("com.example.marvelatmygreatness.heroPublisher");
                mImageUrl = args.getString("com.example.marvelatmygreatness.heroImageURL");
                yourHero = args.getString("com.example.marvelatmygreatness.heroYourHero");
            }
        }
        hero = new Hero();
        hero.setId(UUID.randomUUID());
        hero.setName(mHeroName);
        hero.setRealname(mRealName);
        hero.setTeam(mTeam);
        hero.setFirstappearance(mFirstApp);
        hero.setCreatedby(mCreatedBy);
        hero.setPublisher(mPublisher);
        hero.setImageurl(mImageUrl);
        hero.setBio(mHeroBio);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_marvel_detail, container, false);

        TextView mHeroDetailHeaderTextView = v.findViewById(R.id.marvel_hero_detail_header_text);
        TextView mHeroDetailBioTextView = v.findViewById(R.id.marvel_hero_detail_bio);
        ImageView mHeroDetailImageView = v.findViewById(R.id.marvel_hero_detail_image);
        TextView mHeroDetailRealNameView = v.findViewById(R.id.marvel_hero_detail_real_name);
        TextView mHeroDetailTeamView = v.findViewById(R.id.marvel_hero_detail_team);
        TextView mHeroDetailFirstAppView = v.findViewById(R.id.marvel_hero_detail_first_app);
        TextView mHeroDetailCreatedByView = v.findViewById(R.id.marvel_hero_detail_created_by);
        TextView mHeroDetailPublisherView = v.findViewById(R.id.marvel_hero_detail_publisher);

        mHeroDetailHeaderTextView.setText(mHeroName);
        mHeroDetailBioTextView.setText(mHeroBio);
        mHeroDetailRealNameView.setText(mRealName);
        mHeroDetailTeamView.setText(mTeam);
        mHeroDetailFirstAppView.setText(mFirstApp);
        mHeroDetailCreatedByView.setText(mCreatedBy);
        mHeroDetailPublisherView.setText(mPublisher);
        Picasso.get().load(mImageUrl).resize(200,150).into(mHeroDetailImageView);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu, menuInflater);
        MenuItem add_favorite;
        if(yourHero == "false"){
            menuInflater.inflate(R.menu.marvel_detail_menu, menu);
            add_favorite = menu.findItem(R.id.menu_item_add_favorite);
        }
        else{
            menuInflater.inflate(R.menu.your_hero_detail_menu, menu);
            add_favorite = menu.findItem(R.id.menu_item_add_favorite);
            MenuItem deleteHero = menu.findItem(R.id.menu_item_delete_your_hero);
        }
        checkFavorites(hero);
        if(isFavorite){
            add_favorite.setIcon(R.drawable.menu_item_favorite_pressed);
        }
        else{
            add_favorite.setIcon(R.drawable.menu_item_favorite_icon);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_item_add_favorite:
                if(!isFavorite){
                    item.setIcon(R.drawable.menu_item_favorite_pressed);
                    HeroLab.get(getActivity()).addFavoriteHero(hero);
                    LayoutInflater inflater = getLayoutInflater();
                    View toastLayout = inflater.inflate(R.layout.toast_hero_favorite, (ViewGroup) getView().findViewById(R.id.toast_favorite_root));
                    TextView favoriteTextView = toastLayout.findViewById(R.id.custom_toast_text);
                    ImageView favoriteImageView = toastLayout.findViewById(R.id.custom_toast_image);
                    favoriteTextView.setText(R.string.toast_hero_added_favorites);
                    favoriteImageView.setBackgroundResource(R.drawable.ic_action_favorites);
                    Toast favToast = new Toast(getActivity());
                    favToast.setGravity(Gravity.BOTTOM, 0, 50);
                    favToast.setDuration(Toast.LENGTH_SHORT);
                    favToast.setView(toastLayout);
                    favToast.show();
                    isFavorite = true;
                }
                else{
                    item.setIcon(R.drawable.menu_item_favorite_icon);
                    HeroLab.get(getActivity()).removeFavoriteHero(hero);
                    LayoutInflater inflater = getLayoutInflater();
                    View toastLayout = inflater.inflate(R.layout.toast_hero_favorite, (ViewGroup) getView().findViewById(R.id.toast_favorite_root));
                    TextView favoriteTextView = toastLayout.findViewById(R.id.custom_toast_text);
                    ImageView favoriteImageView = toastLayout.findViewById(R.id.custom_toast_image);
                    favoriteTextView.setText(R.string.toast_hero_removed_favorites);
                    favoriteImageView.setBackgroundResource(R.drawable.ic_action_favorites);
                    Toast favToast = new Toast(getActivity());
                    favToast.setGravity(Gravity.BOTTOM,0, 50);
                    favToast.setDuration(Toast.LENGTH_SHORT);
                    favToast.setView(toastLayout);
                    favToast.show();
                    isFavorite = false;
                }
                return true;
            case R.id.menu_item_delete_your_hero:
                HeroLab.get(getActivity()).removeFavoriteHero(hero);
                HeroLab.get(getActivity()).removeYourHero(hero);
                LayoutInflater inflater = getLayoutInflater();
                View toastLayout = inflater.inflate(R.layout.toast_hero_favorite, (ViewGroup) getView().findViewById(R.id.toast_favorite_root));
                TextView favoriteTextView = toastLayout.findViewById(R.id.custom_toast_text);
                ImageView favoriteImageView = toastLayout.findViewById(R.id.custom_toast_image);
                favoriteTextView.setText(R.string.toast_hero_deleted);
                favoriteImageView.setBackgroundResource(R.drawable.ic_action_delete);
                Toast favToast = new Toast(getActivity());
                favToast.setGravity(Gravity.BOTTOM,0, 50);
                favToast.setDuration(Toast.LENGTH_SHORT);
                favToast.setView(toastLayout);
                favToast.show();
                Fragment heroListFragment = new HeroListFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, heroListFragment)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkFavorites(Hero selectedHero){
        String heroName = selectedHero.getName();
        mHeroes = HeroLab.get(getActivity()).getFavoriteHeros();

        for(Hero h : mHeroes){
            if(h.getName().equals(heroName)){
                isFavorite = true;
                break;
            }
            isFavorite = false;
        }
    }
}

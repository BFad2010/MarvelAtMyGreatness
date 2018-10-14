package com.example.marvelatmygreatness;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.UUID;


/**
 * Created by xxsti on 10/3/2018.
 */

public class CreateHerosFragment extends Fragment {
    private static final int REQUEST_HERO_IMAGE = 1;
    private ImageView selectedHeroImageView;
    private EditText heroNameEditText;
    private EditText heroRealNameEditText;
    private EditText heroFirstAppEditText;
    private EditText heroCreatedByEditText;
    private TextView heroPublisherTextView;
    private EditText heroTeamEditText;
    private EditText heroBioEditText;
    private Button submitNewHeroButton;
    Uri heroImageUri;

    public static CreateHerosFragment newInstance(){
        return new CreateHerosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_create_heros, container, false);

        selectedHeroImageView = v.findViewById(R.id.selected_hero_image);
        heroNameEditText = v.findViewById(R.id.enter_hero_name);
        heroRealNameEditText = v.findViewById(R.id.enter_hero_real_name);
        heroFirstAppEditText = v.findViewById(R.id.enter_hero_first_app);
        heroCreatedByEditText = v.findViewById(R.id.enter_hero_created_by);
        heroPublisherTextView = v.findViewById(R.id.new_hero_publisher);
        heroTeamEditText = v.findViewById(R.id.enter_hero_team);
        heroBioEditText = v.findViewById(R.id.enter_hero_bio);
        submitNewHeroButton = v.findViewById(R.id.submit_hero_button);

        selectedHeroImageView.setImageResource(R.drawable.ic_hero_image_place_holder);

        Button selectHeroImageButton = v.findViewById(R.id.select_hero_image);
        final Intent pickHeroImage = new Intent(Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickHeroImage.setType("image/*");

        selectHeroImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickHeroImage, REQUEST_HERO_IMAGE);
            }
        });

        submitNewHeroButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Hero newHero = new Hero();
                newHero.setId(UUID.randomUUID());
                newHero.setName(heroNameEditText.getText().toString());
                newHero.setRealname(heroRealNameEditText.getText().toString());
                newHero.setFirstappearance(heroFirstAppEditText.getText().toString());
                newHero.setCreatedby(heroCreatedByEditText.getText().toString());
                newHero.setPublisher(heroPublisherTextView.getText().toString());
                newHero.setTeam(heroTeamEditText.getText().toString());
                newHero.setBio(heroBioEditText.getText().toString());
                newHero.setImageurl(heroImageUri.toString());

                HeroLab.get(getActivity()).addYourHero(newHero);
                LayoutInflater inflater = getLayoutInflater();
                View toastLayout = inflater.inflate(R.layout.toast_hero_favorite, (ViewGroup) getView().findViewById(R.id.toast_favorite_root));
                TextView favoriteTextView = toastLayout.findViewById(R.id.custom_toast_text);
                ImageView favoriteImageView = toastLayout.findViewById(R.id.custom_toast_image);
                favoriteTextView.setText(R.string.toast_hero_created);
                favoriteImageView.setBackgroundResource(R.drawable.ic_action_add_hero);
                Toast favToast = new Toast(getActivity());
                favToast.setGravity(Gravity.BOTTOM, 0, 50);
                favToast.setDuration(Toast.LENGTH_SHORT);
                favToast.setView(toastLayout);
                favToast.show();
                Fragment yourHeros = new HeroListFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, yourHeros)
                        .commit();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_HERO_IMAGE){
            heroImageUri = data.getData();

            if(heroImageUri != null){
                Picasso.get().load(heroImageUri).resize(200, 150).into(selectedHeroImageView);
            }
        }

    }
}

package com.example.dj.gyanmatrix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by DJ on 11-03-2017.
 */

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String IMAGE_TRANSITION_NAME = "transitionImage";
    ImageView playImage;
    TextView playerName,country,runs,matchesPlayed,fav, description;
    BatsmenModel mBatsman;
    private android.support.v7.app.ActionBar mActionBar;



    public static void showWithTransition(Context context, boolean clearBackStack, BatsmenModel currentBatsman,ActivityOptionsCompat optionsCompat) {
        Intent intent = new Intent(context, DescriptionActivity.class);
        intent.putExtra("currentBatsman", currentBatsman);
        if (clearBackStack)
        {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        ActivityCompat.startActivity(context,intent,optionsCompat.toBundle());

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.batsman_detail_activity);
        getBatsmen();
        setAppBar();
        setUpUI();
    }

    private void getBatsmen() {
        mBatsman= (BatsmenModel) getIntent().getSerializableExtra("currentBatsman");
    }

    private void setAppBar() {
        setTitle("Profile");
        mActionBar = getSupportActionBar();
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        mActionBar.setDisplayHomeAsUpEnabled(true); //to activate back pressed on home button press
        mActionBar.setDisplayShowHomeEnabled(false);
    }

    private void setUpUI() {
        playImage   =   (ImageView) findViewById(R.id.image);
        playerName=(TextView)findViewById(R.id.player_name);
        country=(TextView)findViewById(R.id.country);
        runs=(TextView)findViewById(R.id.runs);
        matchesPlayed=(TextView)findViewById(R.id.matches);
        fav=(TextView)findViewById(R.id.my_fav);
        description=(TextView)findViewById(R.id.description);
        ViewCompat.setTransitionName(playImage, DescriptionActivity.IMAGE_TRANSITION_NAME);
        ImageUtils.setImage(getApplicationContext(),mBatsman.mImageURL,playImage,R.drawable.icon_placeholder);
        playerName.setText(mBatsman.mName);
        country.setText(mBatsman.mCountry);
        runs.setText(mBatsman.mTotalScore+"  runs");
        matchesPlayed.setText(mBatsman.mMatchPlayed+"  matches");
        description.setText(mBatsman.mDescription);
        description.setMovementMethod(new ScrollingMovementMethod());


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View view) {

    }

    public static void show(Context context, boolean clearBackStack, BatsmenModel currentBatsman)
    {
        Intent intent = new Intent(context, DescriptionActivity.class);
        intent.putExtra("currentBatsman", currentBatsman);
        if (clearBackStack)
        {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}

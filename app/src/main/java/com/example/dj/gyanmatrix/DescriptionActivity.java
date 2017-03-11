package com.example.dj.gyanmatrix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by DJ on 11-03-2017.
 */

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String IMAGE_TRANSITION_NAME = "transitionImage";
    ImageView playImage;
    BatsmenModel mBatsman;
    private android.support.v7.app.ActionBar mActionBar;

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
        setTitle(mBatsman.mName);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(mBatsman.mName);
        mActionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        mActionBar.setDisplayHomeAsUpEnabled(true); //to activate back pressed on home button press
        mActionBar.setDisplayShowHomeEnabled(false);
    }

    private void setUpUI() {
        playImage   =(ImageView)   findViewById(R.id.image);
        ImageUtils.setImage(getApplicationContext(),mBatsman.mImageURL,playImage,R.drawable.icon_placeholder);
        ViewCompat.setTransitionName(playImage, DescriptionActivity.IMAGE_TRANSITION_NAME);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View view) {

    }
}

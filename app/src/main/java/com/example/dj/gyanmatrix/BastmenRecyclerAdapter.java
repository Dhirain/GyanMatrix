package com.example.dj.gyanmatrix;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DJ on 11-03-2017.
 */

public class BastmenRecyclerAdapter extends RecyclerView.Adapter<BastmenRecyclerAdapter.BatsmenItemHolder>  {
    Context mContext;
    List<BatsmenModel> mBatsmen;
    BatsmenModel mCurrentBatsman;
    DbManager dbManager;

    public BastmenRecyclerAdapter(Context context,List<BatsmenModel> Batsmen) {
        this.mContext       =   context;
        this.mBatsmen       =   Batsmen;
    }

    @Override
    public BatsmenItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.batsmen_list_item, parent, false);
        return new BatsmenItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BatsmenItemHolder holder, int position) {
        if(mBatsmen!=null){
            dbManager=new DbManager(mContext);
            mCurrentBatsman=mBatsmen.get(position);
            initCardViewWithCurrentItem(holder);
            setClickListners(holder);
        }
    }


    private void initCardViewWithCurrentItem(final BatsmenItemHolder currentHolder)
    {
        ImageUtils.setCircularImage(mContext,mCurrentBatsman.mImageURL,currentHolder.vBatsmenImage,R.drawable.icon_placeholder);
        currentHolder.vBatsmenName.setText(mCurrentBatsman.mName);
        currentHolder.uBatsmenCountry.setText(mCurrentBatsman.mCountry);

        if(mCurrentBatsman.mStar==1) {
            currentHolder.vStar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_start_fill));
            currentHolder.currentStartStatus=true;
        }
        else {
            currentHolder.vStar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_start_empty));
            currentHolder.currentStartStatus=false;
        }
    }


    private void setClickListners(final BatsmenItemHolder currentHolder) {
        currentHolder.vStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentHolder.currentStartStatus=!currentHolder.currentStartStatus;
                int currnetstarStatus=mBatsmen.get(currentHolder.getPosition()).mStar;
                dbManager.updateStarData(mBatsmen.get(currentHolder.getPosition()).mId,
                        (currnetstarStatus==1)?0:1);
                if(currentHolder.currentStartStatus)
                    currentHolder.vStar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_start_fill));
                else
                    currentHolder.vStar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_start_empty));
            }
        });
        currentHolder.vArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimateToNextActivity(mBatsmen.get(currentHolder.getPosition()),currentHolder.vBatsmenImage);
            }
        });

        currentHolder.vUserDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimateToNextActivity(mBatsmen.get(currentHolder.getPosition()),currentHolder.vBatsmenImage);
            }
        });

        currentHolder.vBatsmenImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimateToNextActivity(mBatsmen.get(currentHolder.getPosition()),currentHolder.vBatsmenImage);
            }
        });
    }

    private void AnimateToNextActivity(BatsmenModel batsmenModel, View view) {
        Activity activity = (Activity) mContext;
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,new Pair(view, DescriptionActivity.IMAGE_TRANSITION_NAME));
        DescriptionActivity.showWithTransition(mContext,false,batsmenModel,options);
    }

    @Override
    public int getItemCount() {
        return mBatsmen.size();
    }

    public class BatsmenItemHolder extends RecyclerView.ViewHolder {
        public ImageView vBatsmenImage,vStar,vArrow;
        public TextView vBatsmenName,uBatsmenCountry;
        LinearLayout vUserDetail;
        RelativeLayout item;
        boolean currentStartStatus=false;

        public BatsmenItemHolder(View itemView) {
            super(itemView);
            item=(RelativeLayout)itemView.findViewById(R.id.item);
            vUserDetail=(LinearLayout)itemView.findViewById(R.id.user_details);
            vBatsmenImage=(ImageView)itemView.findViewById(R.id.user_profile_pic);
            vStar=(ImageView)itemView.findViewById(R.id.star);
            vArrow=(ImageView)itemView.findViewById(R.id.arrow);
            vBatsmenName=(TextView) itemView.findViewById(R.id.user_name);
            uBatsmenCountry=(TextView) itemView.findViewById(R.id.user_location);

        }
    }
}

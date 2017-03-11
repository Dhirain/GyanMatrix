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
            mCurrentBatsman=mBatsmen.get(position);
            initCardViewWithCurrentItem(holder);
        }
    }

    private void initCardViewWithCurrentItem(final BatsmenItemHolder currentHolder)
    {

        if(null != mCurrentBatsman.mImageURL)
        {
            ImageUtils.setCircularImage(mContext,mCurrentBatsman.mImageURL,currentHolder.vBatsmenImage,R.drawable.icon_placeholder);
        }
        currentHolder.vBatsmenName.setText(mCurrentBatsman.mName);
        currentHolder.uBatsmenCountry.setText(mCurrentBatsman.mCountry);
        currentHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) mContext;
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,new Pair(currentHolder.vBatsmenImage, DescriptionActivity.IMAGE_TRANSITION_NAME));

                DescriptionActivity.showWithTransition(mContext,false,mBatsmen.get(currentHolder.getPosition()),options);
            }
        });


    }//end initCardViewWithCurrentItem

    @Override
    public int getItemCount() {
        return mBatsmen.size();
    }

    public class BatsmenItemHolder extends RecyclerView.ViewHolder {
        public ImageView vBatsmenImage,vStar,vArrow;
        public TextView vBatsmenName,uBatsmenCountry;
        RelativeLayout item;

        public BatsmenItemHolder(View itemView) {
            super(itemView);
            item=(RelativeLayout)itemView.findViewById(R.id.item);
            vBatsmenImage=(ImageView)itemView.findViewById(R.id.user_profile_pic);
            vStar=(ImageView)itemView.findViewById(R.id.star);
            vArrow=(ImageView)itemView.findViewById(R.id.arrow);
            vBatsmenName=(TextView) itemView.findViewById(R.id.user_name);
            uBatsmenCountry=(TextView) itemView.findViewById(R.id.user_location);

        }
    }
}

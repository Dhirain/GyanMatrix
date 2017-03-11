package com.example.dj.gyanmatrix;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import nl.qbusict.cupboard.annotation.Column;

/**
 * Created by DJ on 11-03-2017.
 */

public class BatsmenModel implements Serializable {
    public Long _id;
    public int        mId;
    @Column("Name")
    public String     mName;
    public String     mImageURL;

    @Column("mTotalScore")
    public int        mTotalScore;
    public String     mDescription;
    public int        mMatchPlayed;
    public String     mCountry;
    @Column("mStar")
    public int        mStar;

    public BatsmenModel(){
        mId=-1;
        mName="";
        mImageURL="";
        mTotalScore=-1;
        mDescription="";
        mMatchPlayed=-1;
        mCountry="";
        mStar=0;
    }

    public BatsmenModel(JSONObject batsmen) throws JSONException
    {
        mId=batsmen.getInt("id");
        mName=batsmen.optString("name");
        mImageURL=batsmen.optString("image");
        mTotalScore=batsmen.optInt("total_score");
        mDescription=batsmen.optString("description");
        mMatchPlayed=batsmen.optInt("matches_played");
        mCountry=batsmen.optString("country");
        mStar=0;
    }

}

package com.example.dj.gyanmatrix;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends AppCompatActivity {
    public static final String JSON_URL = "http://hackerearth.0x10.info/api/gyanmatrix?type=json&query=list_player";
    NetworkManger networkManger;
    public List<BatsmenModel> mBatsmen;
    BastmenRecyclerAdapter mAdapter;

    RecyclerView mMainList;

    private SimpleDatabaseHelper simpleDatabaseHelper;
    private SQLiteDatabase db;

    boolean isData = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleDatabaseHelper = new SimpleDatabaseHelper(this);
        db = simpleDatabaseHelper.getWritableDatabase();

        initVariables();
        initView();

        BatsmenModel batsman = cupboard().withDatabase(db).get(BatsmenModel.class, 1L);
        if(batsman == null)
            getDataFromNetwork();
        else {
            QueryResultIterable<BatsmenModel> itr = cupboard().withDatabase(db).query(BatsmenModel.class).query();
            Log.d("MainActivity", "From db");
            for (BatsmenModel b : itr)
                mBatsmen.add(b);

            mAdapter=new BastmenRecyclerAdapter(MainActivity.this,mBatsmen);
            mMainList.setAdapter(mAdapter);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
            mMainList.setLayoutManager(mLayoutManager);
            mMainList.setHasFixedSize(true);
        }

        List<BatsmenModel> a = getStarDataFromDB(1); //for starred
        List<BatsmenModel> b = getSortedByRunDataFromDB();


        for(BatsmenModel bm : searchBatsmanByCountry("south africa"))
            Log.d("MainActivity", bm.mName);

        for(BatsmenModel bm : searchBatsmanByName("sachin"))
            Log.d("MainActivity", bm.mName);
    }

    private void initView() {
        mMainList=(RecyclerView) findViewById(R.id.batsmen_ll);
    }

    private void initVariables() {
        mBatsmen=new ArrayList<>();
        networkManger=new NetworkManger();
    }

    private List<BatsmenModel> getSortedByRunDataFromDB(){
        List<BatsmenModel> batsmenModelList = new ArrayList<>();

        Iterable<BatsmenModel> itr = cupboard().withDatabase(db)
                .query(BatsmenModel.class)
                .orderBy("mTotalScore asc").query();

        for (BatsmenModel batsmenModel : itr)
            batsmenModelList.add(batsmenModel);

        return batsmenModelList;
    }

    private List<BatsmenModel> getStarDataFromDB(int value){
        List<BatsmenModel> batsmenModelList = new ArrayList<>();

        Iterable<BatsmenModel> itr = cupboard().withDatabase(db)
                .query(BatsmenModel.class)
                .withSelection("mStar = ?", Integer.toString(value))
                .orderBy("mTotalScore asc").query();

        for (BatsmenModel batsmenModel : itr)
            batsmenModelList.add(batsmenModel);

        return batsmenModelList;
    }

    private int updateStarData(int id, int value){
        ContentValues values = new ContentValues(1);
        values.put("mStar", value);
        // update all books where the title is 'android'
        return cupboard().withDatabase(db).update(BatsmenModel.class, values, "_id = ?", Long.toString(id));
    }

    private int getListSize(int id, int value){
        return cupboard().withDatabase(db)
                .query(BatsmenModel.class).getCursor().getCount();
    }

    private List<BatsmenModel> searchBatsmanByCountry(String text){
        return cupboard().withDatabase(db)
                .query(BatsmenModel.class).withSelection("mCountry Like ?", text).query().list();
    }


    private List<BatsmenModel> searchBatsmanByName(String text){
        return cupboard().withDatabase(db)
                .query(BatsmenModel.class).withSelection("mName = ?", text).query().list();
    }

    private void getDataFromNetwork() {

        NetworkManger.sendRequest(JSON_URL,getApplicationContext(), new NetworkManger.BatsmenManger() {
            @Override
            public void onRequestCompleted(List<BatsmenModel> batmenList) {
                mBatsmen=batmenList;
                Log.d("list",mBatsmen.toString());

                mAdapter=new BastmenRecyclerAdapter(MainActivity.this,mBatsmen);
                mMainList.setAdapter(mAdapter);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                mMainList.setLayoutManager(mLayoutManager);
                mMainList.setHasFixedSize(true);
                //mAdapter.notifyDataSetChanged();

                for(BatsmenModel batsman : mBatsmen){
                    long id = cupboard().withDatabase(db).put(batsman);
                    Log.d("MainActivity", "Saving batsman " + id);
                }
                isData = true;
            }
        });
    }
}
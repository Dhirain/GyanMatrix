package com.example.dj.gyanmatrix;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.Cupboard;
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

        BatsmenModel batsman = cupboard().withDatabase(db).get(BatsmenModel.class, 1L);
        if(batsman == null)
            getDataFromNetwork();
        else {
            QueryResultIterable<BatsmenModel> itr = cupboard().withDatabase(db).query(BatsmenModel.class).query();
            Log.d("MainActivity", "From db");
            for (BatsmenModel b : itr)
                mBatsmen.add(b);
        }

        initView();
        getDataFromNetwork();
    }

    private void initView() {
        mMainList=(RecyclerView) findViewById(R.id.batsmen_ll);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mMainList.setLayoutManager(mLayoutManager);
        mMainList.setHasFixedSize(true);
    }

    private void initVariables() {
        mBatsmen=new ArrayList<>();
        networkManger=new NetworkManger();
    }

    private List<BatsmenModel> getSortedByRunDataFromDB(){
        List<BatsmenModel> batsmenModelList = new ArrayList<>();

        Iterable<BatsmenModel> itr = cupboard().withDatabase(db)
                .query(BatsmenModel.class)
                .orderBy("mTotalScore").query();

        for (BatsmenModel batsmenModel : itr)
            batsmenModelList.add(batsmenModel);

        return batsmenModelList;
    }

    private List<BatsmenModel> getStarDataFromDB(){
        List<BatsmenModel> batsmenModelList = new ArrayList<>();

        Iterable<BatsmenModel> itr = cupboard().withDatabase(db)
                .query(BatsmenModel.class)
                .withSelection("mStar = ?", "1")
                .orderBy("mTotalScore").query();

        for (BatsmenModel batsmenModel : itr)
            batsmenModelList.add(batsmenModel);

        return batsmenModelList;
    }

    private void getDataFromNetwork() {

        NetworkManger.sendRequest(JSON_URL,getApplicationContext(), new NetworkManger.BatsmenManger() {
            @Override
            public void onRequestCompleted(List<BatsmenModel> batmenList) {
                mBatsmen=batmenList;
                Log.d("list",mBatsmen.toString());

                mAdapter=new BastmenRecyclerAdapter(MainActivity.this,mBatsmen);
                mMainList.setAdapter(mAdapter);
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
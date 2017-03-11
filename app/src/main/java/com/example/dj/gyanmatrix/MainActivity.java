package com.example.dj.gyanmatrix;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String JSON_URL = "http://hackerearth.0x10.info/api/gyanmatrix?type=json&query=list_player";
    NetworkManger networkManger;
    public List<BatsmenModel> mBatsmen;
    BastmenRecyclerAdapter mAdapter;
    boolean sortByRuns=false;
    boolean sortByMatch=false;
    boolean isFavSelect=false;
    RecyclerView mMainList;
    private SimpleDatabaseHelper simpleDatabaseHelper;
    private SQLiteDatabase db;
    boolean isData = false;
    DbManager dbManager;
    Button sortRunButton,sortMatchButton,filterFavButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariables();
        initView();
        BatsmenModel batsman = cupboard().withDatabase(db).get(BatsmenModel.class, 1L);
        if(batsman == null)
            getDataFromNetwork();
        else
        {
            mBatsmen=dbManager.getFromDb();
            updateList();
        }
    }



    private void updateList(){
        //mMainList.invalidate();
        mAdapter=new BastmenRecyclerAdapter(MainActivity.this,mBatsmen);
        mMainList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }



    private void initView() {
        mMainList=(RecyclerView) findViewById(R.id.batsmen_ll);
        sortRunButton=(Button) findViewById(R.id.sort_runs);
        sortMatchButton=(Button) findViewById(R.id.sort_marches);
        filterFavButton=(Button) findViewById(R.id.fav_button);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mMainList.setLayoutManager(mLayoutManager);
        mMainList.setHasFixedSize(true);
        sortMatchButton.setOnClickListener(this);
        filterFavButton.setOnClickListener(this);
        sortRunButton.setOnClickListener(this);
    }

    private void initVariables() {
        simpleDatabaseHelper = new SimpleDatabaseHelper(this);
        db = simpleDatabaseHelper.getWritableDatabase();
        mBatsmen=new ArrayList<>();
        networkManger=new NetworkManger();
        dbManager=new DbManager(getApplicationContext());
    }



    private void getDataFromNetwork() {

        NetworkManger.sendRequest(JSON_URL,getApplicationContext(), new NetworkManger.BatsmenManger() {
            @Override
            public void onRequestCompleted(List<BatsmenModel> batmenList) {
                mBatsmen=batmenList;
                Log.d("list",mBatsmen.toString());
                updateList();
                for(BatsmenModel batsman : mBatsmen){
                    long id = cupboard().withDatabase(db).put(batsman);
                    Log.d("MainActivity", "Saving batsman " + id);
                }
                isData = true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_runs:
                clearAllState();
                sortByRuns = true;
                sortList();
                break;
            case R.id.sort_marches:
                clearAllState();
                sortByMatch = true;
                sortList();
                ;
                break;
            case R.id.fav_button:
                clearAllState();
                isFavSelect = true;
                sortList();
                break;
        }
    }

    private void clearAllState() {
        sortByRuns=false;
        sortByMatch=false;
        isFavSelect=false;
        sortRunButton.setBackgroundResource(R.drawable.blueedge);
        sortRunButton.setTextColor(getResources().getColor(R.color.black));
        sortMatchButton.setBackgroundResource(R.drawable.blueedge);
        sortMatchButton.setTextColor(getResources().getColor(R.color.black));
        filterFavButton.setBackgroundResource(R.drawable.blueedge);
        filterFavButton.setTextColor(getResources().getColor(R.color.black));
        filterFavButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_start_fill),null,null,null);

    }

    private void sortList() {
        if(sortByRuns){
            sortRunButton.setBackgroundResource(R.drawable.roundrectangel_blue);
            sortRunButton.setTextColor(getResources().getColor(R.color.white));
            mBatsmen=dbManager.getSortedByRunDataFromDB();
            updateList();
        }
        else if(sortByMatch){
            sortMatchButton.setBackgroundResource(R.drawable.roundrectangel_blue);
            sortMatchButton.setTextColor(getResources().getColor(R.color.white));
            mBatsmen=dbManager.getSortedByMatchDataFromDB();
            updateList();
        }
        else if(isFavSelect){
            filterFavButton.setBackgroundResource(R.drawable.roundrectangel_blue);
            filterFavButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_start_empty),null,null,null);
            filterFavButton.setTextColor(getResources().getColor(R.color.white));
            mBatsmen=dbManager.getStarDataFromDB(1);
            updateList();
        }
    }
}
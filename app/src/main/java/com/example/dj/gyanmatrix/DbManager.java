package com.example.dj.gyanmatrix;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by DJ on 11-03-2017.
 */

public class DbManager {
    private static DbManager sSingleton;
    private static SimpleDatabaseHelper simpleDatabaseHelper;
    private static SQLiteDatabase db;
    private static Context mcontext;

    List<BatsmenModel> mBatsmen = null;

    public DbManager(Context c) {
        mcontext=c;
        simpleDatabaseHelper = new SimpleDatabaseHelper(mcontext);
        db = simpleDatabaseHelper.getWritableDatabase();
    }


    public List<BatsmenModel> getFromDb() {
        List<BatsmenModel> batsmenModelList = new ArrayList<>();
        QueryResultIterable<BatsmenModel> itr = cupboard().withDatabase(db).query(BatsmenModel.class).query();
        Log.d("MainActivity", "From db");
        for (BatsmenModel b : itr)
            batsmenModelList.add(b);
        return batsmenModelList;
    }

    public List<BatsmenModel> getStarDataFromDB(int value){
        List<BatsmenModel> batsmenModelList = new ArrayList<>();

        Iterable<BatsmenModel> itr = cupboard().withDatabase(db)
                .query(BatsmenModel.class)
                .withSelection("mStar = ?", Integer.toString(value))
                .orderBy("mTotalScore asc").query();

        for (BatsmenModel batsmenModel : itr)
            batsmenModelList.add(batsmenModel);

        return batsmenModelList;
    }

    public List<BatsmenModel> getSortedByMatchDataFromDB(){
        List<BatsmenModel> batsmenModelList = new ArrayList<>();

        Iterable<BatsmenModel> itr = cupboard().withDatabase(db)
                .query(BatsmenModel.class)
                .orderBy("mMatchPlayed asc").query();

        for (BatsmenModel batsmenModel : itr)
            batsmenModelList.add(batsmenModel);

        return batsmenModelList;
    }

    public List<BatsmenModel> getSortedByRunDataFromDB(){
        List<BatsmenModel> batsmenModelList = new ArrayList<>();

        Iterable<BatsmenModel> itr = cupboard().withDatabase(db)
                .query(BatsmenModel.class)
                .orderBy("mTotalScore asc").query();

        for (BatsmenModel batsmenModel : itr)
            batsmenModelList.add(batsmenModel);

        return batsmenModelList;
    }

    public int updateStarData(int id, int value){
        ContentValues values = new ContentValues(1);
        values.put("mStar", value);
        // update all books where the title is 'android'
        return cupboard().withDatabase(db).update(BatsmenModel.class, values, "_id = ?", Long.toString(id));
    }

    public int getListSize(int id, int value){
        return cupboard().withDatabase(db)
                .query(BatsmenModel.class).getCursor().getCount();
    }

    /*private void getFromDb() {
        QueryResultIterable<BatsmenModel> itr = cupboard().withDatabase(db).query(BatsmenModel.class).query();
        Log.d("MainActivity", "From db");
        for (BatsmenModel b : itr)
            mBatsmen.add(b);
        updateList();
    }*/

}

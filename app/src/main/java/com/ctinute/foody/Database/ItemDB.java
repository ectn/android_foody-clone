package com.ctinute.foody.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ctinute.foody.Objects.District;
import com.ctinute.foody.Objects.Street;
import com.ctinute.foody.Objects.WhereItem;

import java.util.ArrayList;
import java.util.List;

public class ItemDB {
    private static final String TABLE_NAME = "ITEM";
    private static final String COLUMN_ID = "ID";

    private SQLiteDatabase db;

    public ItemDB(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;
    }

    /*
    public boolean insertDistrict (String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", name);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        return db.rawQuery( "select * from "+TABLE_NAME+" where "+COLUMN_ID+"="+id+"", null );
    }

    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    public boolean updateDistrict (Integer id, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteDistrict (Integer id) {
        return db.delete(TABLE_NAME,
                COLUMN_ID+" = ? ",
                new String[] { Integer.toString(id) });
    }
    */

    public ArrayList<WhereItem> getItemList(String query) {
        ArrayList<WhereItem> list = new ArrayList<>();
        Cursor res =  db.rawQuery(query,null);
        if ((res.moveToFirst()) && res.getCount() > 0){
            while(!res.isAfterLast()){
                WhereItem item = new WhereItem();
                item.setAvgRating(res.getDouble(res.getColumnIndex("AVGRATING")));
                item.setName(res.getString(res.getColumnIndex("NAME")));
                item.setAddress(res.getString(res.getColumnIndex("ADDRESS")));
                item.setTotalPictures(res.getInt(res.getColumnIndex("TOTALPICTURES")));
                item.setTotalReviews(res.getInt(res.getColumnIndex("TOTALREVIEWS")));
                item.setImg(res.getString(res.getColumnIndex("IMG")));
                item.setCategoryId(res.getInt(res.getColumnIndex("CATEGORYID")));
                item.setTypeId(res.getInt(res.getColumnIndex("TYPEID")));
                item.setRestaurantId(res.getInt(res.getColumnIndex("RESTAURANTID")));

                list.add(item);
                res.moveToNext();
            }
        }
        res.close();
        return list;
    }

    public ArrayList<WhereItem> findItemsByCity(int cityId) {
        String query =  "select * from "+TABLE_NAME+ " where CITYID = "+cityId;
        return getItemList(query);
    }

    public ArrayList<WhereItem> findItemsByDistrict(int districtId) {
        String query =  "select * from "+TABLE_NAME+ " where DISTRICTID = "+districtId;
        return getItemList(query);
    }

    public ArrayList<WhereItem> findItemsByStreet(int streetId) {
        String query =  "select * from "+TABLE_NAME+ " where STREETID = "+streetId;
        return getItemList(query);
    }

    public ArrayList<WhereItem> findItemsByFields(int cityId, int districtId, int streetId, int categoryId) {
        String query;
        if (categoryId <= 1){
            // no category (all)
            if (streetId > 0) {
                // tim theo duong
                query =  "select * from "+TABLE_NAME+ " where STREETID = "+streetId;
            }
            else if (districtId > 0) {
                // tim theo quan/huyen
                query =  "select * from "+TABLE_NAME+ " where DISTRICTID = "+districtId;
            }
            else if (cityId > 0) {
                query =  "select * from "+TABLE_NAME+ " where CITYID = "+cityId;
            }
            else
                query =  "select * from "+TABLE_NAME;    // lay het
        }
        else {
            // thim theo category
            if (streetId > 0) {
                // tim theo duong
                query =  "select * from "+TABLE_NAME+ " where CATEGORYID="+categoryId+" and STREETID = "+streetId;
            }
            else if (districtId > 0) {
                // tim theo quan/huyen
                query =  "select * from "+TABLE_NAME+ " where CATEGORYID="+categoryId+" and DISTRICTID = "+districtId;
            }
            else if (cityId > 0) {
                query =  "select * from "+TABLE_NAME+ " where CATEGORYID="+categoryId+" and CITYID = "+cityId;
            }
            else
                query =  "select * from "+TABLE_NAME+" where CATEGORYID="+categoryId;    // lay het
        }
        return getItemList(query);
    }
}

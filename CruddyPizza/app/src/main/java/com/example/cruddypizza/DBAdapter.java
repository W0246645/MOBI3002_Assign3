package com.example.cruddypizza;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class DBAdapter {
    public static final String KEY_ID = "id";
    public static final String KEY_SIZE = "size";
    public static final String KEY_TOPPING1 = "topping1";
    public static final String KEY_TOPPING2 = "topping2";
    public static final String KEY_TOPPING3 = "topping3";
    public static final String KEY_FNAME = "fName";
    public static final String KEY_LNAME = "lName";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_DATETIME = "orderDateTime";

    public static final String TAG = "DBAdapter";
    private static final String DB_PATH = "/data/data/res/raw/cruddypizza.db";
    private static final String DATABASE_NAME = "cruddypizza.db";
    private static final String DATABASE_TABLE = "orders";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table orders(_id integer primary key autoincrement,"
                    + "name text not null,phone text not null,address text not null,"
                    + "date text not null,time text not null,size text not null,"
                    + "bacon text not null,cheese text not null,ham text not null,pepperoni text not null,pineapple text not null,vegetable text not null);";

    private final DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private Context context;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }



        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }//end method onCreate


        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            return;
//            Log.w(TAG, "Upgrade database from version " + oldVersion + " to "
//                    + newVersion + ", which will destroy all old data");
//            db.execSQL("DROP TABLE IF EXISTS contacts");
//            onCreate(db);
        }//end method onUpgrade

    }

    //open the database
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //close the database
    public void close() {
        DBHelper.close();
    }

    //insert order
    public long insertOrder(Order order) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SIZE, order.size);
        initialValues.put(KEY_TOPPING1, order.topping1);
        initialValues.put(KEY_TOPPING2, order.topping2);
        initialValues.put(KEY_TOPPING3, order.topping3);
        initialValues.put(KEY_FNAME, order.fName);
        initialValues.put(KEY_LNAME, order.lName);
        initialValues.put(KEY_ADDRESS, order.address);
        initialValues.put(KEY_PHONE, order.phone);

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);

        initialValues.put(KEY_DATETIME, df.format(new Date()));

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //delete order
    public boolean deleteOrder(long id) {
        return db.delete(DATABASE_TABLE, KEY_ID+"="+id, null) > 0;
    }

    //retrieve all orders
    public Cursor getOrders()throws SQLException{
        Cursor cursor = db.query(true, DATABASE_TABLE, new String[]{KEY_ID,
                        KEY_SIZE, KEY_TOPPING1, KEY_TOPPING2, KEY_TOPPING3, KEY_FNAME, KEY_LNAME, KEY_ADDRESS,KEY_PHONE, KEY_DATETIME},
                null, null, null, null, null,null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //retrieve single order
    public Cursor getOrder(long id)throws SQLException{
        Cursor cursor = db.query(true, DATABASE_TABLE, new String[]{KEY_ID,
        KEY_SIZE, KEY_TOPPING1, KEY_TOPPING2, KEY_TOPPING3, KEY_FNAME, KEY_LNAME, KEY_ADDRESS,KEY_PHONE, KEY_DATETIME},
                KEY_ID+"="+id, null, null, null, null,null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean updateOrder(long id, Order order) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_SIZE, order.size);
        updateValues.put(KEY_TOPPING1, order.topping1);
        updateValues.put(KEY_TOPPING2, order.topping2);
        updateValues.put(KEY_TOPPING3, order.topping3);
        updateValues.put(KEY_FNAME, order.fName);
        updateValues.put(KEY_LNAME, order.lName);
        updateValues.put(KEY_ADDRESS, order.address);
        updateValues.put(KEY_PHONE, order.phone);

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        updateValues.put(KEY_DATETIME, df.format(order.orderDateTime));

        return db.update(DATABASE_TABLE, updateValues, KEY_ID+"="+id, null) > 0;
    }
}






























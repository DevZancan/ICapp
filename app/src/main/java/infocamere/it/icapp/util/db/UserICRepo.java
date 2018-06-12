/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 29/05/18 12.11
 */

package infocamere.it.icapp.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import infocamere.it.icapp.model.UserIC;

public class UserICRepo {
    private DBHelper dbHelper;

    public UserICRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(UserIC user) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserIC.KEY_ID, user.getUserIC_id());
        values.put(UserIC.KEY_name, user.name);
        values.put(UserIC.KEY_email,user.email);
        values.put(UserIC.KEY_surname, user.surname);
        values.put(UserIC.KEY_YYI, user.yyi);
        values.put(UserIC.KEY_cdr, user.cdr);
        values.put(UserIC.KEY_matricola, user.matricola);
        values.put(UserIC.KEY_office, user.office);
        values.put(UserIC.KEY_phone_fix, user.phone_fix);
        values.put(UserIC.KEY_sede, user.sede);
        values.put(UserIC.KEY_phone_mobile, user.phone_mobile);

        // Inserting Row
        long user_id = db.insert(UserIC.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) user_id;
    }

    public Cursor getUsersList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * " +
                " FROM " + UserIC.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        }
        else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        return cursor;
    }


    public Cursor getUserListByKeyword(String search) {
        //Open connection to read only
        Log.i("UserICRepo", "getUserListByKeyword() " +  search);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * " +
                " FROM " + UserIC.TABLE +
                " WHERE " +  UserIC.KEY_name + " LIKE  '%" + search + "%'" +
                " OR " + UserIC.KEY_surname + " LIKE  '%" + search + "%'" +
                " OR " + UserIC.KEY_YYI + " LIKE  '%" + search + "%'" +
                " OR " + UserIC.KEY_email + " LIKE  '%" + search + "%'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        }
        else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        return cursor;
    }

    public UserIC getUserById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * " +
                "FROM " + UserIC.TABLE
                + " WHERE " +
                UserIC.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        UserIC user = new UserIC();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                user.userIC_id = cursor.getInt(cursor.getColumnIndex(UserIC.KEY_ID));
                user.name = cursor.getString(cursor.getColumnIndex(UserIC.KEY_name));
                user.email = cursor.getString(cursor.getColumnIndex(UserIC.KEY_email));
                user.surname = cursor.getString(cursor.getColumnIndex(UserIC.KEY_surname));
                user.yyi = cursor.getString(cursor.getColumnIndex(UserIC.KEY_YYI));
                user.cdr = cursor.getString(cursor.getColumnIndex(UserIC.KEY_cdr));
                user.matricola = cursor.getString(cursor.getColumnIndex(UserIC.KEY_matricola));
                user.sede = cursor.getString(cursor.getColumnIndex(UserIC.KEY_sede));
                user.phone_fix = cursor.getString(cursor.getColumnIndex(UserIC.KEY_phone_fix));
                user.phone_mobile = cursor.getString(cursor.getColumnIndex(UserIC.KEY_phone_mobile));
                user.office = cursor.getString(cursor.getColumnIndex(UserIC.KEY_office));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return user;
    }
}

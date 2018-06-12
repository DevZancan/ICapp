/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 29/05/18 12.12
 */

package infocamere.it.icapp.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import infocamere.it.icapp.model.ServiceIC;

public class ServiceICRepo {
    private DBHelper dbHelper;

    public ServiceICRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(ServiceIC serviceIC) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(serviceIC.KEY_ID, serviceIC.getSvcId());
        values.put(serviceIC.KEY_serviceName, serviceIC.getSvcName());
        values.put(serviceIC.KEY_serviceVisible, serviceIC.isSvcVisible());

        // Inserting Row
        long serviceIC_id = db.insert(ServiceIC.TABLE, null, values);
        db.close(); // Closing database connection

        return (int) serviceIC_id;
    }

    public Cursor getServiceICList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * " +
                " FROM " + ServiceIC.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        ServiceIC svcIC = new ServiceIC();

        if (cursor.moveToFirst()) {
            do {
                svcIC.setSvcId(cursor.getString(cursor.getColumnIndex(ServiceIC.KEY_ID)));
                svcIC.setSvcName(cursor.getString(cursor.getColumnIndex(ServiceIC.KEY_serviceName)));
                svcIC.setSvcVisible(
                        cursor.getInt(cursor.getColumnIndex(ServiceIC.KEY_serviceVisible)) > 0);
                Log.i("ServiceIC.KEY_ID", svcIC.getSvcId());
                Log.i("ServiceIC Name ", svcIC.getSvcName());
            } while (cursor.moveToNext());
        }

        if (cursor == null) {
            return null;
        }
        else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        return cursor;
    }

    public ServiceIC getServiceICById(int id) {
        Log.i("Service IC ", "getServiceICById");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * " +
                "FROM " + ServiceIC.TABLE
                + " WHERE " +
                ServiceIC.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        ServiceIC serviceIC = new ServiceIC();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) } );

        if (cursor.moveToFirst()) {
            do {
                Log.i("ServiceIC.KEY_ID", ServiceIC.KEY_ID);
                Log.i("Service Name ", ServiceIC.KEY_serviceName);
                serviceIC.setSvcId(cursor.getString(cursor.getColumnIndex(ServiceIC.KEY_ID)));
                serviceIC.setSvcName(cursor.getString(cursor.getColumnIndex(ServiceIC.KEY_serviceName)));
                serviceIC.setSvcVisible(
                        cursor.getInt(cursor.getColumnIndex(ServiceIC.KEY_serviceVisible)) > 0);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return serviceIC;
    }
}

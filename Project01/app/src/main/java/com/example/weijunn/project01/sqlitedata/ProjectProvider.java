package com.example.weijunn.project01.sqlitedata;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class ProjectProvider extends ContentProvider {

    public static final String LOG_TAG = ProjectProvider.class.getSimpleName();

    private ProjectDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new ProjectDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,String sortOrder){
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues){
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs){
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String [] selectionArgs){
        return 0;
    }

    @Override
    public String getType(Uri uri){
        return null;
    }
}

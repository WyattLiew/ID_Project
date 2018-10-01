package com.example.weijunn.project01;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.weijunn.project01.sqlitedata.DataProvider;
import com.example.weijunn.project01.sqlitedata.ProjectDbHelper;


public class tab3_pending extends Fragment {

    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    ProjectDbHelper projectDbHelper;
    Cursor cursor;
    ListAdapter listAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_pending, container, false);

        listView = (ListView)rootView.findViewById(R.id.pending_list);
        projectDbHelper = new ProjectDbHelper(getActivity().getApplicationContext());
        sqLiteDatabase = projectDbHelper.getReadableDatabase();
        cursor = projectDbHelper.viewData();
        listAdapter = new ListAdapter(getActivity().getApplicationContext(),R.layout.item_project);
        listView.setAdapter(listAdapter);

        if (cursor.moveToFirst()){

            do{
                String location,name,number;
                byte [] img;
                img = cursor.getBlob(0);
                location= cursor.getString(1);
                name = cursor.getString(2);
                number = cursor.getString(3);
                DataProvider dataProvider = new DataProvider(img,location,name,number);

                listAdapter.add(dataProvider);
            }

            while (cursor.moveToNext());


        }

        return rootView;

    }

}

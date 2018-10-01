package com.example.weijunn.project01;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.weijunn.project01.sqlitedata.ProjectDbHelper;

public class ProjectCursorAdapter extends CursorAdapter{

    public ProjectCursorAdapter(Context context,Cursor c){
        super(context,c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_project,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView locationTextView = (TextView)view.findViewById(R.id.project_location);
        TextView nameTextView = (TextView) view.findViewById(R.id.project_person);
        TextView numberTextView = (TextView) view.findViewById(R.id.project_number);

        int locationColumnIndex = cursor.getColumnIndex(ProjectDbHelper.COLUMN_PROJECT_LOCATION);
        int nameColumnIndex = cursor.getColumnIndex(ProjectDbHelper.COLUMN_CONTACT_NAME);
        int numberColumnIndex = cursor.getColumnIndex(ProjectDbHelper.COLUMN_CONTACT_NUMBER);

        String projectLocation =cursor.getString(locationColumnIndex);
        String conName = cursor.getString(nameColumnIndex);
        String conNum = cursor.getString(numberColumnIndex);

        locationTextView.setText(projectLocation);
        nameTextView.setText(conName);
        numberTextView.setText(conNum);
    }
}

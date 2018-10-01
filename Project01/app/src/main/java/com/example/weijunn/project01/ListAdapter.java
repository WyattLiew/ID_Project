package com.example.weijunn.project01;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weijunn.project01.sqlitedata.DataProvider;
import com.example.weijunn.project01.sqlitedata.Untils;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public ListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class LAyoutHandler{

        TextView LOCATION,NAME,NUMBER;
        ImageView IMG;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        LAyoutHandler lAyoutHandler;
        if(row ==null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.item_project,parent,false);
            lAyoutHandler = new LAyoutHandler();
            lAyoutHandler.IMG =(ImageView)row. findViewById(R.id.project_image);
            lAyoutHandler.LOCATION = (TextView)row.findViewById(R.id.project_location);
            lAyoutHandler.NAME = (TextView) row.findViewById(R.id.project_person);
            lAyoutHandler.NUMBER = (TextView) row.findViewById(R.id.project_number);
            row.setTag(lAyoutHandler);
        }else{
            lAyoutHandler = (LAyoutHandler)row.getTag();
        }

        DataProvider dataProvider = (DataProvider)this.getItem(position);

        lAyoutHandler.IMG.setImageBitmap(Untils.getImage(dataProvider.getImg()));
        lAyoutHandler.LOCATION.setText(dataProvider.getLocation());
        lAyoutHandler.NAME.setText(dataProvider.getName());
        lAyoutHandler.NUMBER.setText(dataProvider.getNumber());

        return row;
    }
}
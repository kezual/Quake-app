package com.example.android.quakeapp;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Earthquake> {

    private DecimalFormat myDecimalFormat = new DecimalFormat("0.0");

    public ListAdapter(Context context, List<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        final Earthquake currentQuake = getItem(position);
        String date[];

        TextView textMag = (TextView) listItemView.findViewById(R.id.textMag);
        TextView textDirection = (TextView) listItemView.findViewById(R.id.textDirection);
        TextView textLocation = (TextView) listItemView.findViewById(R.id.textLocation);
        TextView textTime = (TextView) listItemView.findViewById(R.id.textTime);
        TextView textDate = (TextView) listItemView.findViewById(R.id.textDate);

        String mag = myDecimalFormat.format(currentQuake.getQuakeMag());
        textMag.setText(mag);

        GradientDrawable magnitudeCircle = (GradientDrawable) textMag.getBackground();
        int magnitudeColor = getMagnitudeColor(currentQuake.getQuakeMag());
        magnitudeCircle.setColor(magnitudeColor);

        String location = currentQuake.getQuakeLocation();
        if(location.contains("of")) {
            int index = location.indexOf("of");
            textDirection.setText(location.substring(0, index + 2));
            textLocation.setText(location.substring(index + 2));
        }else{
            textDirection.setText("Near of");
            textLocation.setText(location);
        }
        date = currentQuake.getQuakeTime().split("at");
        textTime.setText(date[0]);
        textDate.setText(date[1]);

        listItemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent urlIntent = new Intent();
                urlIntent.setAction(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(currentQuake.getQuakeUrl()));
                getContext().startActivity(urlIntent);
            }
        });

        return listItemView;
    }

    public int getMagnitudeColor(Double quakeMag){
        int color;
        int i = quakeMag.intValue();
        switch (i) {
            case 0:
            case 1:
                color = R.color.magnitude1;
                break;
            case 2:
                color = R.color.magnitude2;
                break;
            case 3:
                color = R.color.magnitude3;
                break;
            case 4:
                color = R.color.magnitude4;
                break;
            case 5:
                color = R.color.magnitude5;
                break;
            case 6:
                color = R.color.magnitude6;
                break;
            case 7:
                color = R.color.magnitude7;
                break;
            case 8:
                color = R.color.magnitude8;
                break;
            case 9:
                color = R.color.magnitude9;
                break;
            default:
                color = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), color);
    }
}

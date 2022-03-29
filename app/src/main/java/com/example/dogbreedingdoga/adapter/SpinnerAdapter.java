package com.example.dogbreedingdoga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Dog> {

    private LayoutInflater layoutInflater;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Dog> dogs) {
        super(context, resource, dogs);
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView = layoutInflater.inflate(R.layout.custom_spinner_adapter, null, true);

        Dog dog = getItem(position);
        TextView textView = (TextView) rowView.findViewById(R.id.spinnerTextView);
        textView.setText(dog.getNameDog());

        return rowView;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.custom_spinner_adapter, parent, false);

        Dog dog = getItem(position);
        TextView textView = (TextView) convertView.findViewById(R.id.spinnerTextView);
        textView.setText(dog.getNameDog());

        return convertView;
    }


}

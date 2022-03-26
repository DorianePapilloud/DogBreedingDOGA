package com.example.dogbreedingdoga.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("===========_______=========== getActivity : " +getActivity() +"\nGET_CONTEXT : " +getContext()
                            +"\nPARENT_FRAGMENT : " +getParentFragment()
                            +"\nPARENT_FRAGMENT MANAGER : " +getParentFragmentManager()
                            +"\nTHIS.getActivity : " +this.getActivity());
        return new DatePickerDialog(this.getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }
}

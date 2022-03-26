package com.example.dogbreedingdoga.ui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.viewmodel.dog.AddNewDogFragment;
import com.example.dogbreedingdoga.viewmodel.dog.DogsListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends BaseActivity {

    //code for implementing camera ================================= MOVE and implements datePickerDialog ---> implements DatePickerDialog.OnDateSetListener
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("============= HELLO FROM MAINACTIVITY ! :-) ");
//        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
        setContentView(R.layout.activity_main);

        // call the main fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.nv_NavHostView, DogsListFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("").commit();
        }


    // =========================== MOVE
//    @Override
//    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.DAY_OF_MONTH, day);
//        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
//
//        TextView textView = (TextView) findViewById(R.id.textView);
//        textView.setText(currentDateString);
//    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null){
                        imageView.setImageURI(result);
                    }
                }
            }
    );

}
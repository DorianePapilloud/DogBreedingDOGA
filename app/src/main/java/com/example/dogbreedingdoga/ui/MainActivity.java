package com.example.dogbreedingdoga.ui;

import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.viewmodel.dog.DogsListFragment;

public class MainActivity extends BaseActivity {

    public TextView btnLogout;

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

        btnLogout = findViewById(R.id.tv_logout);
        btnLogout.setOnClickListener(btnLogoutListener);
        }

}
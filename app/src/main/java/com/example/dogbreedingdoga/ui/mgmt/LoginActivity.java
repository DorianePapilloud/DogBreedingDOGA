package com.example.dogbreedingdoga.ui.mgmt;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.adapter.LoginAdapter;
import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends FragmentActivity {

    TabLayout tabLayout;
    //
    TabLayout tabLogin;
    View tabNewAccount;
    //
    ViewPager viewPager;
    float v = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        //creation of tabs
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

        //titles setup
        tabLayout.getTabAt(0).setText("Login");
        tabLayout.getTabAt(1).setText("Create new account");

        //animation
        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(v);
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();

//        getApplication();

    }

    @Override
    protected void onResume() {super.onResume();}

    @Override
    protected void onPostResume() {super.onPostResume();}



}
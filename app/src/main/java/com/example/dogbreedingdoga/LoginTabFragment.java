package com.example.dogbreedingdoga;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dogbreedingdoga.R;
import com.google.android.material.tabs.TabLayout;

public class LoginTabFragment extends Fragment {

    TextView email;
    TextView pass;
    TextView registerNow;
    Button login;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);


        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        registerNow = root.findViewById(R.id.register_now);
        login = root.findViewById(R.id.button);


        email.setTranslationY(800);
        pass.setTranslationY(800);
        registerNow.setTranslationY(800);
        login.setTranslationY(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        registerNow.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        registerNow.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();


        return root;
    }
}

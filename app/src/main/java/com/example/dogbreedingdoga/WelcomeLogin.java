package com.example.dogbreedingdoga;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WelcomeLogin extends Fragment {

        FloatingActionButton arrowNext;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_welcome, container, false);

        arrowNext = root.findViewById(R.id.arrowNext);

        arrowNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);

                }
        });


        return root;
    }
}

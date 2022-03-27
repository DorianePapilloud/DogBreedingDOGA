package com.example.dogbreedingdoga.ui;

import static com.example.dogbreedingdoga.Database.AppDatabase.initializeDemoData;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dogbreedingdoga.Database.AppDatabase;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.ui.mgmt.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {


        private ImageView logo, background;
        private TextView welcome, start;

        protected int _splashTime = 5000;

        @Override
        protected void onCreate(Bundle savedInstances) {
                super.onCreate(savedInstances);
                setContentView(R.layout.activity_welcome);

//                initializeDemoData(AppDatabase.getInstance(this));

                logo = findViewById(R.id.logo);
                background = findViewById(R.id.imageView2);
                welcome = findViewById(R.id.welcomeText);
                start = findViewById(R.id.letsGetYouStartedText);

                logo.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
                background.animate().translationY(-2000).setDuration(1000).setStartDelay(4000);
                welcome.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
                start.animate().translationY(1400).setDuration(1000).setStartDelay(4000);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                        public void run() {
//                                getSupportFragmentManager();
                                setContentView(R.layout.activity_login);
                                Intent i3 = new Intent(WelcomeActivity.this, LoginActivity.class);
                                startActivity(i3);
                        }
                }, _splashTime);

        }

}


//public class WelcomeActivity extends Fragments {
//
//        FloatingActionButton arrowNext;
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//                ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_welcome, container, false);
//
//                arrowNext = root.findViewById(R.id.arrowNext);
//
//                arrowNext.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                                startActivity(intent);
//
//                        }
//                });
//
//                return root;
//        }
//}

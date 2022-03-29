package com.example.dogbreedingdoga.ui;

import static com.example.dogbreedingdoga.Database.AppDatabase.initializeDemoData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.AppDatabase;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.ui.mgmt.LoginActivity;

public class WelcomeActivity extends BaseActivity {


        private ImageView logo, background;
        private TextView welcome, start;

        protected int _splashTime = 4000;

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
                                //Verify if a user disconnect without loggout. In this case, run his profile
                                // 1 ATTENTION SI 1ère utilisation -> à check


//                                SharedPreferences activeSession = getPreferences(Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = activeSession.edit();;
//                                editor.putString("login", response)

//                                if(PreferenceManager.getDefaultSharedPreferences(getApplication()).getString(BaseActivity.PREFS_USER, null) != null) { //(SharedPreferences) getSharedPreferences(BaseActivity.PREFS_USER, 0) != null
//                                        System.out.println("PASSE PAR LOGIN DIRECT : ");
//                                        SharedPreferences currentSession = PreferenceManager.getDefaultSharedPreferences(getApplication()); //(SharedPreferences) getSharedPreferences(BaseActivity.PREFS_NAME, 0);
//                                        String currentUser = currentSession.getString(BaseActivity.PREFS_USER, null);
//
//                                        if(!currentUser.equals("")) {
//                                                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class); //BreederProfileActivity.class
//                                                startActivity(intent);
//                                        }
//                                        else{
//                                                getSupportFragmentManager();
//                                                setContentView(R.layout.activity_login);
//                                                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
//                                                startActivity(intent);
//                                        }
//                                }
//                                else {
//                                        System.out.println("PASSE PAR LOGGIN ");
//                                        getSupportFragmentManager();
//                                        setContentView(R.layout.activity_login);
                                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                        startActivity(intent);

//                                }
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

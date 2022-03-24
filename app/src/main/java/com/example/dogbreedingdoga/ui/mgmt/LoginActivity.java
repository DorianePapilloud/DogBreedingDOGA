package com.example.dogbreedingdoga.ui.mgmt;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.dogbreedingdoga.R;
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
//        setContentView(R.layout.login_tab_fragment);


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

        getApplication();
    }

    @Override
    protected void onResume() {super.onResume();}

    @Override
    protected void onPostResume() {super.onPostResume();}

//    private void attemptLogin() {
//
////        tabLayout.getTabAt(1).
//
//        // Reset errors.
//        emailView.setError(null);
//        passwordView.setError(null);
//
//        // Store values at the time of the login attempt.
//        String email = emailView.getText().toString();
//        String password = passwordView.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            passwordView.setError(getString(R.string.error_invalid_password));
//            passwordView.setText("");
//            focusView = passwordView;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            emailView.setError(getString(R.string.error_field_required));
//            focusView = emailView;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            emailView.setError(getString(R.string.error_invalid_email));
//            focusView = emailView;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            progressBar.setVisibility(View.VISIBLE);
//            repository.getClient(email, getApplication()).observe(LoginActivity.this, clientEntity -> {
//                if (clientEntity != null) {
//                    if (clientEntity.getPassword().equals(password)) {
//                        // We need an Editor object to make preference changes.
//                        // All objects are from android.context.Context
//                        SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
//                        editor.putString(BaseActivity.PREFS_USER, clientEntity.getEmail());
//                        editor.apply();
//
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        emailView.setText("");
//                        passwordView.setText("");
//                    } else {
//                        passwordView.setError(getString(R.string.error_incorrect_password));
//                        passwordView.requestFocus();
//                        passwordView.setText("");
//                    }
//                    progressBar.setVisibility(View.GONE);
//                } else {
//                    emailView.setError(getString(R.string.error_invalid_email));
//                    emailView.requestFocus();
//                    passwordView.setText("");
//                    progressBar.setVisibility(View.GONE);
//                }
//            });
//        }
//    }
//
//    private boolean isEmailValid(String email) {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }
//
//    private boolean isPasswordValid(String password) {
//        return password.length() > 4;
//    }
//
//    private void reinitializeDatabase() {
//        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//        alertDialog.setTitle(getString(R.string.action_demo_data));
//        alertDialog.setCancelable(false);
//        alertDialog.setMessage(getString(R.string.reset_msg));
//        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_reset), (dialog, which) ->{
//            initializeDemoData(AppDatabase.getInstance(this));
//            Toast.makeText(this, getString(R.string.demo_data_initiated), Toast.LENGTH_LONG).show();
//        });
//        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
//        alertDialog.show();
//    }

}
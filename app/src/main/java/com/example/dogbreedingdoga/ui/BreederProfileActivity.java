package com.example.dogbreedingdoga.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.viewmodel.breeder.BreederViewModel;

public class BreederProfileActivity extends BaseActivity {

    private static final int EDIT_CLIENT = 1;
    private static final int DELETE_CLIENT = 2;

    private Toast toast;

    private boolean isEditable;

    private TextView tv_ProfileName;
    private TextView tv_ProfileSurname;
    private TextView tv_ProfileAddress;
    private TextView tv_ProfileEmail;
    private TextView tv_ProfilePhone;

    private BreederViewModel viewModel;

    private Breeder breeder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeder_profile);

        getLayoutInflater().inflate(R.layout.create_new_account_tab_fragment, frameLayout);

        initiateView();

        System.out.println("==== email : " +tv_ProfileEmail);

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String user = settings.getString(PREFS_USER, null);

        BreederViewModel.Factory factory = new BreederViewModel.Factory(getApplication(), user);
        viewModel = new ViewModelProvider(this, factory).get(BreederViewModel.class);
        viewModel.getClient().observe(this, accountEntity -> {
            if (accountEntity != null) {
                breeder = accountEntity;
                System.out.println("BREEEEEEEEDEEEEEEEEERR" +breeder);
                updateContent();
            }
        });

    }

    private void initiateView() {
        isEditable = false;
        tv_ProfileEmail = findViewById(R.id.et_email);
    }

    private void updateContent() {
        if (breeder != null) {
            tv_ProfileEmail.setText(breeder.getEmail());
        }
    }
}
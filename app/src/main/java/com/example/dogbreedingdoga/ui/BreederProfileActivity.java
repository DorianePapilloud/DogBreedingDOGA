package com.example.dogbreedingdoga.ui;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorLong;
import androidx.lifecycle.ViewModelProvider;

import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.viewmodel.breeder.BreederViewModel;

public class BreederProfileActivity extends BaseActivity {

    private static final int EDIT_CLIENT = 1;
    private static final int DELETE_CLIENT = 2;

    private Toast toast;

    private boolean isEditable;

    private TextView tv_ProfileFullName;
    private EditText tv_ProfileName;
    private TextView tv_ProfileSurname;
    private TextView tv_ProfileAddress;
    private TextView tv_ProfileEmail;
    private TextView tv_ProfilePhone;

    private TextView tv_BtnEdit;

    private BreederViewModel viewModel;

    private Breeder breeder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeder_profile);

        getLayoutInflater().inflate(R.layout.create_new_account_tab_fragment, frameLayout);

        initiateView();

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String user = settings.getString(PREFS_USER, null);

        BreederViewModel.Factory factory = new BreederViewModel.Factory(getApplication(), user);
        viewModel = new ViewModelProvider(this, factory).get(BreederViewModel.class);
        viewModel.getClient().observe(this, accountEntity -> {
            if (accountEntity != null) {
                breeder = accountEntity;
                updateContent();
            }
        });

    }

    private void initiateView() {
        isEditable = false;
        tv_ProfileEmail = findViewById(R.id.et_email);
        //add all elements that we can modify in edit
        tv_ProfileName = findViewById(R.id.tv_ProfileName);
        tv_ProfileName.setFocusable(false);
        tv_ProfileSurname = findViewById(R.id.tv_ProfileSurname);
        tv_ProfileAddress = findViewById(R.id.tv_Address);
        tv_ProfilePhone = findViewById(R.id.tv_Phone);

        tv_BtnEdit = findViewById(R.id.btn_editProfil);
        tv_BtnEdit.setOnClickListener(btnEditListener);

        //If Name And / OR  Surname is empty...
//        if(findViewById(R.id.tv_ProfileName)==null && findViewById(R.id.tv_ProfileSurname)==null)
//            tv_ProfileFullName = findViewById(R.id.et_email);
//        else if(findViewById(R.id.tv_ProfileName) != null && findViewById(R.id.tv_ProfileSurname) == null){
//            tv_ProfileFullName = findViewById(R.id.tv_ProfileName);
//        }else if(findViewById(R.id.tv_ProfileSurname) != null && findViewById(R.id.tv_ProfileName) == null) {
//            tv_ProfileFullName = findViewById(R.id.tv_ProfileSurname);
//        } else
            //à améliorer !!
//            tv_ProfileFullName = findViewById(R.id.tv_ProfileName);
    }

    private View.OnClickListener btnEditListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //method for editing breeder
            editBreeder();
        }
    };

    private void editBreeder() {
        //make all textviews editable
        tv_ProfileName.setFocusable(true);
        tv_ProfileName.setEnabled(true);
        tv_ProfileName.setFocusableInTouchMode(true);
        tv_ProfileName.setBackgroundColor(Color.GREEN);

        //modify 'button' text + listener
        tv_BtnEdit.setText("Save");

        //Display pwd and pwdConfirm fields

        //updateContent
    }

    private void updateContent() {
        if (breeder != null) {
            tv_ProfileEmail.setText(breeder.getEmail());
        }
    }
}
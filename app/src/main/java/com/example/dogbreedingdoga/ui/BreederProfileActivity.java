package com.example.dogbreedingdoga.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.viewmodel.breeder.BreederViewModel;

public class BreederProfileActivity extends BaseActivity {

    private static final int EDIT_CLIENT = 1;
    private static final int DELETE_CLIENT = 2;

    private Toast toast;

//    private boolean isEditable;

    private EditText et_ProfileName;
    private EditText et_ProfileSurname;
    private EditText et_ProfileAddress;
    private EditText et_ProfileEmail;
    private EditText et_ProfilePhone;

    private EditText et_ProfilePwd;
    private EditText et_ProfilePwdConf;

    private TextView tv_BtnEdit;
    private TextView tv_BtnGoToDogs;

    private BreederViewModel viewModel;

    private Breeder breeder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeder_profile);

//        getLayoutInflater().inflate(R.layout.create_new_account_tab_fragment, frameLayout);

        initiateView();

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String user = settings.getString(PREFS_USER, null);

        BreederViewModel.Factory factory = new BreederViewModel.Factory(getApplication(), user);
        viewModel = new ViewModelProvider(this, factory).get(BreederViewModel.class);
        viewModel.getBreeder().observe(this, accountEntity -> {
            if (accountEntity != null) {
                breeder = accountEntity;
                updateContent();
            }
        });
    }

    private void initiateView() {
//        isEditable = false;
        et_ProfileEmail = findViewById(R.id.et_email);
        //add all elements that we can modify in edit
//        System.out.println("=================" +breeder.getNameBreeder());
        et_ProfileName = findViewById(R.id.et_ProfileName);
        et_ProfileSurname = findViewById(R.id.et_ProfileSurname);
        et_ProfileAddress = findViewById(R.id.et_Address);
        et_ProfilePhone = findViewById(R.id.et_Phone);

        et_ProfilePwd = findViewById(R.id.et_ProfilePwd);
        et_ProfilePwdConf = findViewById(R.id.et_ProfilePwdConf);

        tv_BtnEdit = findViewById(R.id.tv_BtnEditProfil);
        tv_BtnEdit.setOnClickListener(btnEditListener);

        tv_BtnGoToDogs = findViewById(R.id.tv_BtnGoToDogs);
        tv_BtnGoToDogs.setOnClickListener(btnGoToDogsListener);

    }

    private View.OnClickListener btnEditListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //method for editing breeder
            editBreeder();
        }
    };

    private View.OnClickListener btnGoToDogsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //go to dogs list
            Intent intent = new Intent(BreederProfileActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    private void editBreeder() {
        //make all EditText editable including display pwd and pwdConfirm fields
        enableTextView(et_ProfileName, et_ProfileSurname, et_ProfileAddress, et_ProfilePhone, et_ProfileEmail, et_ProfilePwd, et_ProfilePwdConf);

        //modify 'button' text + listener
        tv_BtnEdit.setText("Save");
//        tv_BtnEdit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv_BtnEdit.setOnClickListener(btnSaveListener);

        //Hide "GoToDogs" button
        tv_BtnGoToDogs.setVisibility(View.INVISIBLE);


        //updateContent
    }

    private void enableTextView(TextView... TVs) {
        if(findViewById(R.id.ly_ProfilePwd).getVisibility() == View.INVISIBLE){
            findViewById(R.id.ly_ProfilePwd).setVisibility(View.VISIBLE);
        }
        if(findViewById(R.id.ly_ProfilePwdConf).getVisibility() == View.INVISIBLE){
            findViewById(R.id.ly_ProfilePwdConf).setVisibility(View.VISIBLE);
        }

        for (TextView tv : TVs) {
            tv.setFocusable(true);
            tv.setEnabled(true);
            tv.setFocusableInTouchMode(true);
            tv.setBackgroundColor(Color.LTGRAY);
        }

    }

    private void disableTextView(EditText... TVs) {
        for (TextView tv:TVs) {
            tv.setFocusable(false);
            tv.setEnabled(false);
            tv.setFocusableInTouchMode(false);
            tv.setBackgroundColor(Color.TRANSPARENT);
        }
        if(findViewById(R.id.ly_ProfilePwd).getVisibility() == View.VISIBLE){
            findViewById(R.id.ly_ProfilePwd).setVisibility(View.INVISIBLE);
        }
        if(findViewById(R.id.ly_ProfilePwdConf).getVisibility() == View.VISIBLE){
            findViewById(R.id.ly_ProfilePwdConf).setVisibility(View.INVISIBLE);
        }
    }

    private View.OnClickListener btnSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //all save stuff
            //disable EditTexts


            saveChanges(et_ProfileName.getText().toString(), et_ProfileSurname.getText().toString(),
                        et_ProfileAddress.getText().toString(), et_ProfileEmail.getText().toString(),
                        et_ProfilePhone.getText().toString(),
                        et_ProfilePwd.getText().toString(), et_ProfilePwdConf.getText().toString());


        }
    };

    private void updateContent() {
        if (breeder != null) {
            et_ProfileEmail.setText(breeder.getEmail());
            et_ProfileName.setText(breeder.getNameBreeder());
            et_ProfileSurname.setText(breeder.getSurnameBreeder());
            et_ProfileAddress.setText(breeder.getAddressBreeder());
            et_ProfilePhone.setText(breeder.getPhone());
            et_ProfilePwd.setText(breeder.getPassword());
            et_ProfilePwdConf.setText(breeder.getPassword());
        }
    }

    private void saveChanges(String firstName, String lastName, String address ,String email, String phone, String pwd, String pwd2) {
        if (!pwd.equals(pwd2) || pwd.length() < 5) {
            toast = Toast.makeText(this, getString(R.string.error_incorrect_password), Toast.LENGTH_LONG);
            toast.show();
            et_ProfilePwd.setText("");
            et_ProfilePwdConf.setText("");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_ProfileEmail.setError(getString(R.string.error_invalid_email));
            et_ProfileEmail.requestFocus();
            return;
        }
        if(!Patterns.PHONE.matcher(phone).matches()){
            et_ProfilePhone.setError(getString((R.string.error_incorrect_phone)));
            et_ProfilePhone.requestFocus();
            return;
        }


        breeder.setEmail(email);
        breeder.setNameBreeder(firstName);
        breeder.setSurnameBreeder(lastName);
        breeder.setAddressBreeder(address);
        breeder.setPhone(phone);
        breeder.setPassword(pwd);

        //restore 'Edit Profile' textview-button
        tv_BtnEdit.setText("Edit Profile");
        tv_BtnEdit.setOnClickListener(btnEditListener);

        //restore 'GoToMyDogs' button
        tv_BtnGoToDogs.setVisibility(View.VISIBLE);

        disableTextView(et_ProfileName, et_ProfileSurname, et_ProfileAddress, et_ProfilePhone, et_ProfileEmail);

        viewModel.updateClient(breeder, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            updateContent();
            toast = Toast.makeText(this, getString(R.string.breeder_updated), Toast.LENGTH_LONG);
            toast.show();
        } else {
            et_ProfileEmail.setError(getString(R.string.error_used_email));
            et_ProfileEmail.requestFocus();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
        startActivity(new Intent(this, BreederProfileActivity.class));
    }
}
package com.example.dogbreedingdoga.ui.mgmt;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.dogbreedingdoga.R;

public class CreateNewAccountFragment extends Fragment {

    private EditText tv_email;
    private EditText tv_pwd;
    private EditText tv_pwdConf;
    private Button btn_Create;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.create_new_account_tab_fragment, container, false);

        tv_email = root.findViewById(R.id.et_email);
        tv_pwd = root.findViewById(R.id.createPass);
        tv_pwdConf = root.findViewById(R.id.pwdConf);
        btn_Create = root.findViewById(R.id.btn_CreateBreeder);

        btn_Create.setOnClickListener(view -> createAccount(
                tv_email.getText().toString(),
                tv_pwd.getText().toString(),
                tv_pwdConf.getText().toString()
                ));

        return root;
    }


    private void createAccount(String email, String pwd, String pwd2) {
        if (!pwd.equals(pwd2) || pwd.length() < 5) {
            pwd.setError(getString(R.string.error_invalid_password));
            pwd.requestFocus();
            pwd.setText("");
            pwd2.setText("");

            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email.setError(getString(R.string.error_invalid_email));
            email.requestFocus();
            return;
        }

    }

}

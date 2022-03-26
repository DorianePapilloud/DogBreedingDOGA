package com.example.dogbreedingdoga.ui.mgmt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.async.breeder.CreateBreeder;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.ui.BaseActivity;
import com.example.dogbreedingdoga.ui.BreederProfileActivity;
import com.example.dogbreedingdoga.ui.MainActivity;


public class CreateNewAccountFragment extends Fragment {

    private static final String TAG = "CreateAccountFragment";

    private TextView tv_email;
    private TextView tv_pwd;
    private TextView tv_pwdConf;
    private Button btn_Create;

    private Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.create_new_account_tab_fragment, container, false);

        tv_email = root.findViewById(R.id.et_email);
        tv_pwd = root.findViewById(R.id.createPass);
        tv_pwdConf = root.findViewById(R.id.pwdConf);
        btn_Create = root.findViewById(R.id.btn_CreateBreeder);

        toast = Toast.makeText(this.getContext(), getString(R.string.toast_BreederCreated), Toast.LENGTH_LONG);

        btn_Create.setOnClickListener(view -> createAccount(
                tv_email.getText().toString(),
                tv_pwd.getText().toString(),
                tv_pwdConf.getText().toString()
                ));

        return root;
    }


    private void createAccount(String email, String pwd, String pwd2) {
        if (!pwd.equals(pwd2) || pwd.length() < 5) {
            tv_pwd.setError(getString(R.string.error_invalid_password));
            tv_pwd.requestFocus();
            tv_pwd.setText("");
            tv_pwdConf.setText("");

            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tv_email.setError(getString(R.string.error_invalid_email));
            tv_email.requestFocus();
            return;
        }
        Breeder newBreeder = new Breeder(email, pwd);

        new CreateBreeder((BaseApp)getActivity().getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createUserWithEmail: success");
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createUserWithEmail: failure", e);
                setResponse(false);
            }
        }).execute(newBreeder);

    }

    private void setResponse(Boolean response) {
        if (response) {
            final SharedPreferences.Editor editor = getContext().getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
            editor.putString(BaseActivity.PREFS_USER, tv_email.getText().toString());
            editor.apply();
            toast.show();
            Intent intent = new Intent(CreateNewAccountFragment.this.getActivity(), BreederProfileActivity.class);
            startActivity(intent);
            tv_email.setText("");
            tv_pwd.setText("");
            tv_pwdConf.setText("");
        } else {
            tv_email.setError(getString(R.string.error_used_email));
            tv_email.requestFocus();
        }
    }

}

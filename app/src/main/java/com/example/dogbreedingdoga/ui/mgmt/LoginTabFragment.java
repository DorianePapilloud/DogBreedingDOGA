package com.example.dogbreedingdoga.ui.mgmt;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Repository.BreederRepository;
import com.example.dogbreedingdoga.ui.BreederProfileActivity;
import com.example.dogbreedingdoga.ui.MainActivity;
import com.example.dogbreedingdoga.R;

public class LoginTabFragment extends Fragment {

    private TextView tv_email;
    private TextView tv_pass;
    private TextView tv_registerNow;
    private Button btn_login;
    private float v = 0;

    //début code Gaétan
    private int position;
    private BreederRepository repository;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        LoginTabFragment tabFragment = new LoginTabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        position = getArguments().getInt("pos");
//    }

//    @Override
//    public void OnViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }

    //fin code Gaétan

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        repository = ((BaseApp) getActivity().getApplication()).getBreederRepository();

        tv_email = root.findViewById(R.id.et_email);
        tv_pass = root.findViewById(R.id.createPass);
        tv_registerNow = root.findViewById(R.id.register_now);
        btn_login = root.findViewById(R.id.btn_CreateBreeder);


        tv_email.setTranslationY(800);
        tv_pass.setTranslationY(800);
        tv_registerNow.setTranslationY(800);
        btn_login.setTranslationY(800);

        tv_email.setAlpha(v);
        tv_pass.setAlpha(v);
        tv_registerNow.setAlpha(v);
        btn_login.setAlpha(v);

        tv_email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        tv_pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        tv_registerNow.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btn_login.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();

        btn_login.setOnClickListener(btnConnectListener);


        return root;
    }

    private View.OnClickListener btnConnectListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.out.println("Cliqué !");
            attemptLogin();
        }

    };

    private void attemptLogin() {

        System.out.println("attemptLogin lancé");
        // Reset errors.
        tv_email.setError(null);
        tv_pass.setError(null);

        // Store values at the time of the login attempt.
        String email = tv_email.getText().toString();
        String password = tv_pass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            tv_pass.setError(getString(R.string.error_invalid_password));
            tv_pass.setText("");
            focusView = tv_pass;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            tv_email.setError(getString(R.string.error_field_required));
            focusView = tv_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            tv_email.setError(getString(R.string.error_invalid_email));
            focusView = tv_email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
//            progressBar.setVisibility(View.VISIBLE);
            System.out.println("========= L'application : " +this.getActivity().getApplication()); //semble CORRECT !!
            System.out.println("========= && LoginTabFragment : " +LoginTabFragment.this +" \n======== && breederId : "+email); // + repository.getBreeder(email, this.getActivity().getApplication())
            System.out.println("====== getbreeder... " +repository.getBreeder(email, this.getActivity().getApplication()));
            repository.getBreeder(email, this.getActivity().getApplication()).observe(LoginTabFragment.this, breeder -> {
                if (breeder != null) {
                    if (breeder.getPassword().equals(password)) {
                        // We need an Editor object to make preference changes.
                        // All objects are from android.context.Context
//                        SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
//                        editor.putString(BaseActivity.PREFS_USER, breeder.getEmail());
//                        editor.apply();
                        System.out.println("Connexion validée");
                        System.out.println("This.getContext() : " +this.getContext() +"\nthis.getActivity.getClass : " +this.getActivity().getClass());
                        Intent intent = new Intent(this.getContext(), BreederProfileActivity.class);
                        startActivity(intent);
                        System.out.println("============= INTENT : " +intent);
                        tv_email.setText("");
                        tv_pass.setText("");
                    } else {
                        tv_pass.setError(getString(R.string.error_incorrect_password));
                        tv_pass.requestFocus();
                        tv_pass.setText("");
                    }
//                    progressBar.setVisibility(View.GONE);
                } else {
                    tv_email.setError(getString(R.string.error_invalid_email));
                    tv_email.requestFocus();
                    tv_pass.setText("");
//                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

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

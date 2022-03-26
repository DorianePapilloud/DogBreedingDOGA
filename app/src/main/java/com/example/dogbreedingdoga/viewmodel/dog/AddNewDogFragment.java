package com.example.dogbreedingdoga.viewmodel.dog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.Gender;
import com.example.dogbreedingdoga.Database.Repository.DogRepository;
import com.example.dogbreedingdoga.Database.async.dog.CreateDog;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.ui.BaseActivity;
import com.example.dogbreedingdoga.ui.BreederProfileActivity;
import com.example.dogbreedingdoga.ui.mgmt.CreateNewAccountFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewDogFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewDogFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public AddNewDogFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddNewDogFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddNewDogFragment newInstance(String param1, String param2) {
//        AddNewDogFragment fragment = new AddNewDogFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    private static final String TAG = "New Dog added" ;

    private DogRepository dogRepository;
    private EditText et_NameDog;
    private EditText et_BreedDog;
    private EditText et_BirthDateDog;
    private EditText et_Description;
    private CheckBox cb_Availability;
    private boolean available;

    private RadioGroup radioGroup;
    private RadioButton rb_Female;
    private TextView tv_PedigInfo;

    private Gender genderDog;
    private boolean pedigree;

    private Button btn_AddDog;

    private Toast toast;

    private String currentBreederMail;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
////        }
//
//
////        //code for implementing camera
////        imageView = findViewById(R.id.iv_add_picture_dog);
////        imageView.setOnClickListener(view -> mGetContent.launch("image/*"));
////        //end
////
////
////        //this code will open a calendar in which you can select a date
////        ImageButton btnDate = (ImageButton) findViewById(R.id.ib_birth);
////        btnDate.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                DialogFragment datePicker = new DatePickerFragment();
////                datePicker.show(getSupportFragmentManager(), "Date picker");
////            }
////        });
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = ((ViewGroup) inflater.inflate(R.layout.fragment_add_new_dog,container,false));

        dogRepository = ((BaseApp) getActivity().getApplication()).getDogRepository();

        //Retrieve the id (mail) of the connected breeder
        SharedPreferences settings = getActivity().getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        currentBreederMail = settings.getString(BaseActivity.PREFS_USER, null);

        //UI initialisation
        Long idDog = getActivity().getIntent().getLongExtra("idDog", 0L);
        available = true;
        if(idDog == 0L) {
            cb_Availability = new CheckBox(getContext());
            cb_Availability.isChecked();
        } else {
            cb_Availability = root.findViewById(R.id.cb_availability);
            available = false;
        }
        et_NameDog = root.findViewById(R.id.et_name_dog);
        et_BreedDog = root.findViewById(R.id.et_breed);
        et_BirthDateDog = root.findViewById(R.id.et_birth);
        et_Description = root.findViewById(R.id.et_DogDescription);

            //Gender
        rb_Female = (RadioButton) root.findViewById(R.id.rb_DogFemale); //only setup to catch and show error if no gender has been selected
        radioGroup = root.findViewById(R.id.rbg_FemaleMaleRadioBtnGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int genderChoice) {
                switch (genderChoice) {
                    case R.id.rb_DogFemale: genderDog = Gender.Female;
                        break;
                    case R.id.rb_DogMale: genderDog = Gender.Male;
                        break;
                }
            }
        });

        //pedigree management based on switch
        tv_PedigInfo = root.findViewById(R.id.tv_PedigreeInfo);
        Switch pedig = (Switch) root.findViewById(R.id.sw_pedigree);
        //check initial state
        if(pedig.isChecked()){
            pedigree = true;
            tv_PedigInfo.setText(R.string.str_Pedigree);
            tv_PedigInfo.setTextColor(Color.GREEN);
        } else {
            pedigree = false;
            tv_PedigInfo.setText(R.string.str_NoPedigree);
            tv_PedigInfo.setTextColor(Color.red(R.color.myColor));
        }

        pedig.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(pedig.isChecked()){
                    pedigree = true;
                    tv_PedigInfo.setText(R.string.str_Pedigree);
                    tv_PedigInfo.setTextColor(Color.red(R.color.myColor));
                }
                else {
                    pedigree = false;
                    tv_PedigInfo.setText(R.string.str_NoPedigree);
                    tv_PedigInfo.setTextColor(Color.GREEN);
                }
            }
        });

        btn_AddDog = root.findViewById(R.id.btn_add_dog);
        btn_AddDog.setOnClickListener(view -> addNewDog(
                et_NameDog.getText().toString(),
                et_BreedDog.getText().toString(),
                et_BirthDateDog.getText().toString()
//                genderDog,
        ));

        return root;
    }

    private void addNewDog(String dogName, String dogBreed, String dogBirth) {

        boolean cancel = false;
        View focusView = null;

        //check if Gender is checked
        if(genderDog == null) { //no gender has been selected
            rb_Female.setError(getString(R.string.error_dog_noGender));
            focusView = radioGroup;
            cancel = true;
        }

        if(dogName.isEmpty() || dogName.equals("")) {
            et_NameDog.setError(getString(R.string.error_dog_noName));
            focusView = radioGroup;
            cancel = true;
        }

        if(dogBreed.isEmpty() || dogBreed.equals("")) {
            et_BreedDog.setError(getString(R.string.error_dog_noBreed));
            focusView = radioGroup;
            cancel = true;
        }

        if(dogBirth.isEmpty() || dogBirth.equals("")) {
            et_BirthDateDog.setError(getString(R.string.error_dog_noBirth));
            focusView = radioGroup;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
            return;
        }

        Dog newDog = new Dog(dogName, dogBreed, dogBirth, genderDog, currentBreederMail, this.pedigree, this.available);

        new CreateDog((BaseApp) getActivity().getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createDog: success");
                toast = Toast.makeText(getContext(), getString(R.string.msg_DogCreated), Toast.LENGTH_LONG);
                //retour sur la liste des chiens
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createDog: failure", e);
                toast = Toast.makeText(getContext(), getString(R.string.msg_DogNOTCreated), Toast.LENGTH_LONG);
                // OH NON
            }
        });

    }
}
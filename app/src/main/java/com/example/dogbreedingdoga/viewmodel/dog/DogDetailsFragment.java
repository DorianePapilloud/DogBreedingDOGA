package com.example.dogbreedingdoga.viewmodel.dog;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.Gender;
import com.example.dogbreedingdoga.Database.Repository.DogRepository;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.ui.BaseActivity;

public class DogDetailsFragment extends Fragment {


    private TextView tv_nameDogDetails;
    private TextView tv_breedDog;
    private TextView tv_birthDateDog;

    private TextView tv_gender;
    private TextView tv_mother;
    private TextView tv_father;
    private Switch sw_pedigree;
    private RadioGroup radioGroup;
    private RadioButton rb_Female;
    private RadioButton rb_Male;
    private Gender gender;

    private Dog dog;
    private DogRepository dogRepository;
    private DogViewModel viewModel;
    private long idDoggy;
    private String currentBreederMail;

    private ImageView iv_BtnDelete;
    private ImageView iv_BtnEdit;

    private Toast toast;

    /**
     * Class managing view with dog details (Dog Profile)
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        long idDoggy = savedInstanceState.getLong("DogID", 0L);

        SharedPreferences settings = getActivity().getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        this.currentBreederMail = settings.getString(BaseActivity.PREFS_USER, null);

        Bundle data = getArguments();
        if(data != null){
            idDoggy = data.getLong("DogID");
        }

        // Inflate the layout for this fragment
        View view = getLayoutInflater().inflate(R.layout.fragment_dog_details, container, false);

//        Long dogId = getActivity().getIntent().getLongExtra("dogId", 0L);

        DogViewModel.Factory factory = new DogViewModel.Factory(getActivity().getApplication(), idDoggy, currentBreederMail);
        viewModel = new ViewModelProvider(this, factory).get(DogViewModel.class);
        viewModel.getDog().observe(getActivity(), dogEntity -> {
            if (dogEntity != null) {
                dog = dogEntity;
                getDataFromIdDog();
            }
        });


        iv_BtnEdit = view.findViewById(R.id.iv_BtnEditDog);
        iv_BtnEdit.setOnClickListener(btnEditListener);

        iv_BtnDelete = view.findViewById(R.id.iv_BtnDeleteDog);
        iv_BtnDelete.setOnClickListener(btnDeleteListener);

        return view;
    }

    private void getDataFromIdDog() {

        tv_nameDogDetails = getActivity().findViewById(R.id.tv_dog_details_name_dog);
        tv_nameDogDetails.setText(dog.getNameDog());

        tv_breedDog = getActivity().findViewById(R.id.et_dog_details_breed);
        tv_breedDog.setText(dog.getBreedDog());

        tv_birthDateDog = getActivity().findViewById(R.id.et_dog_details_birth);
        tv_birthDateDog.setText(dog.getDateOfBirth());

        genderManagement();

        /// mention if parents are null
        tv_mother = getActivity().findViewById(R.id.et_dog_details_mother);
//        tv_mother.setText(dog.getIdMother());

        tv_father = getActivity().findViewById(R.id.et_dog_details_father);
//        tv_father.setText(dog.getIdFather());

        sw_pedigree = getActivity().findViewById(R.id.sw_pedigree_dog_details);
        if(dog.getPedigree() == true) {
            sw_pedigree.setChecked(true);
        }
        else {
            sw_pedigree.setChecked(false);
        }

        if(dog.getGender() == Gender.Female)
        {
            rb_Female.setChecked(true);
        }
        else
        {
            rb_Male.setChecked(true);
        }

        disableTextView(tv_nameDogDetails, tv_breedDog, tv_birthDateDog, tv_mother, tv_father);

    }

    private void genderManagement() {
        rb_Female = (RadioButton)getActivity().findViewById(R.id.rb_DogFemaleDogDetails);
        rb_Male = (RadioButton)getActivity().findViewById(R.id.rb_DogMaleDogDetails);
        radioGroup = getActivity().findViewById(R.id.rbg_FemaleMaleRadioBtnGroupDogDetails);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int genderChoice) {
                switch (genderChoice) {
                    case R.id.rb_DogFemale: gender = Gender.Female;
                        break;
                    case R.id.rb_DogMale: gender = Gender.Male;
                        break;
                }
            }
        });
    }


    private View.OnClickListener btnEditListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //call fragment add a new dog for edition
//            Bundle data = new Bundle();
//            data.putLong("DogID", idDoggy);
//            AddNewDogFragment addNewDogFragment = new AddNewDogFragment();
//            addNewDogFragment.setArguments(data);
//            FragmentManager fragmentManager = getParentFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.nv_NavHostView, addNewDogFragment)
//                    .setReorderingAllowed(true)
//                    .addToBackStack("").commit();
            editDog();
        }




    };

    private View.OnClickListener btnDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //method for deleting breeder
            deleteDog();
        }
    };


    private void editDog() {
        //make all EditText editable
        enableTextView(tv_nameDogDetails, tv_breedDog, tv_birthDateDog, tv_mother, tv_father);
        //modify 'button' text + listener
        iv_BtnEdit.setImageResource(R.drawable.ic_validate_24dp);

//        tv_BtnEdit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        iv_BtnEdit.setOnClickListener(btnSaveListener);

    }



        private View.OnClickListener btnSaveListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //all save stuff
                //disable EditTexts
//
//                saveChanges(tv_nameDogDetails.getText().toString(), tv_breedDog.getText().toString(),
//                        tv_gender. getText().toString(), //////////////////////////////////////////
//                        tv_birthDateDog.getText().toString(), tv_mother.getText().toString(),
//                        tv_father.getText().toString(),
//                        sw_pedigree.isChecked()); //////////////////////////////////////

//////////////////////////////// GENDER and PEDIGREE /////////////////////////
            }
        };


    private void enableTextView(TextView... TVs) {

        for (TextView tv : TVs) {
            tv.setFocusable(true);
            tv.setEnabled(true);
            tv.setFocusableInTouchMode(true);
            tv.setBackgroundColor(Color.LTGRAY);
        }

        sw_pedigree.setEnabled(true);
        radioGroup.setEnabled(true);
        rb_Female.setEnabled(true);
        rb_Male.setEnabled(true);

    }


    private void deleteDog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle(getString(R.string.action_delete));
        alertDialog.setCancelable(false);
        alertDialog.setMessage(getString(R.string.delete_msg_dog));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
            viewModel.deleteDog(dog, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.nv_NavHostView, DogsListFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("").commit();
                }

                @Override
                public void onFailure(Exception e) {}
            });
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.show();
    }

    private void saveChanges(String nameDoggy, String breedDoggy, Gender genderDoggy , String dateOfBithDoggy, int motherDoggy, int fatherDoggy, Boolean pedigreeDoggy) {


        dog.setNameDog(nameDoggy);
        dog.setBreedDog(breedDoggy);
        dog.setGender(genderDoggy);
        dog.setDateOfBirth(dateOfBithDoggy);
        dog.setIdMother(motherDoggy);
        dog.setIdFather(fatherDoggy);
        dog.setPedigree(pedigreeDoggy);

        //restore 'Edit Profile' textview-button
        iv_BtnEdit.setImageResource(R.drawable.ic_edit);
        iv_BtnEdit.setOnClickListener(btnEditListener);

        disableTextView(tv_nameDogDetails, tv_breedDog, tv_gender, tv_birthDateDog, tv_mother, tv_father);

        viewModel.updateDog(dog, new OnAsyncEventListener() {
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


    private void disableTextView(TextView... TVs) {
        for (TextView tv:TVs) {
            tv.setFocusable(false);
            tv.setEnabled(false);
            tv.setFocusableInTouchMode(false);
            tv.setBackgroundColor(Color.TRANSPARENT);
        }
        sw_pedigree.setEnabled(false);
        radioGroup.setEnabled(false);
        rb_Female.setEnabled(false);
        rb_Male.setEnabled(false);

    }

    private void setResponse(Boolean response) {
        if (response) {
            updateContent();
            toast = Toast.makeText(this.getActivity(), getString(R.string.dog_updated), Toast.LENGTH_LONG);
            toast.show();
        } else {
            tv_nameDogDetails.setError(getString(R.string.error_used_name));
            tv_nameDogDetails.requestFocus();
            updateContent();
        }
    }

    private void updateContent() {
        if (dog != null) {
//            et_ProfileEmail.setText(breeder.getEmail());
//            et_ProfileName.setText(breeder.getNameBreeder());
//            et_ProfileSurname.setText(breeder.getSurnameBreeder());
//            et_ProfileAddress.setText(breeder.getAddressBreeder());
//            et_ProfilePhone.setText(breeder.getPhone());
//            et_ProfilePwd.setText(breeder.getPassword());
//            et_ProfilePwdConf.setText(breeder.getPassword());
        }
    }

}
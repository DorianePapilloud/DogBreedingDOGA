package com.example.dogbreedingdoga.viewmodel.dog;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import java.util.Calendar;

public class DogDetailsFragment extends Fragment {

    private static final String TAG = "Dog updated" ;

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
    private TextView tv_PedigInfo;
    private boolean pedigree;
    private TextView tv_Description;
    private boolean available;
    private CheckBox cb_Availability;

    private Dog dog;
    private DogRepository dogRepository;
    private DogViewModel viewModel;
    private long idDoggy;
    private String currentBreederMail;

    private ImageView iv_BtnDelete;
    private ImageView iv_BtnEdit;

    private Toast toast;

    private ViewGroup view ;

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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_dog_details, container, false);

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

        tv_nameDogDetails = view.findViewById(R.id.tv_dog_details_name_dog);
        tv_nameDogDetails.setText(dog.getNameDog());

        tv_breedDog = view.findViewById(R.id.et_dog_details_breed);
        tv_breedDog.setText(dog.getBreedDog());

        tv_birthDateDog = view.findViewById(R.id.et_dog_details_birth);
        tv_birthDateDog.setText(dog.getDateOfBirth());

        genderManagement();

        /// mention if parents are null
        tv_mother = view.findViewById(R.id.et_dog_details_mother);
//        tv_mother.setText(dog.getIdMother());

        tv_father = view.findViewById(R.id.et_dog_details_father);
//        tv_father.setText(dog.getIdFather());

        tv_PedigInfo = view.findViewById(R.id.tv_PedigreeInfoDogDetails);
        pedigreeManagement();

        sw_pedigree = view.findViewById(R.id.sw_pedigree_dog_details);
        if(dog.getPedigree() == true) {
            sw_pedigree.setChecked(true);
        }
        else {
            sw_pedigree.setChecked(false);
        }

        if(dog.getGender() == Gender.Female)
        {
            rb_Female.setChecked(true);
            gender = Gender.Female;
        }
        else
        {
            rb_Male.setChecked(true);
            gender = Gender.Male;
        }


        tv_Description = view.findViewById(R.id.et_DogDescriptionDogDetails);
        tv_Description.setText(dog.getSpecificationsDog());

        checkBoxAvailabilityManagement();
        cb_Availability.setEnabled(false);

        disableTextView(tv_nameDogDetails, tv_breedDog, tv_birthDateDog, tv_mother, tv_father, tv_Description);

    }

    private void genderManagement() {
        rb_Female = (RadioButton)view.findViewById(R.id.rb_DogFemaleDogDetails);
        rb_Male = (RadioButton)view.findViewById(R.id.rb_DogMaleDogDetails);
        radioGroup = view.findViewById(R.id.rbg_FemaleMaleRadioBtnGroupDogDetails);
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

    private void checkBoxAvailabilityManagement() {
        //initiate checkbox and set at true
        cb_Availability =view.findViewById(R.id.cb_availabilityDogDetails);
        cb_Availability.setChecked(true);
        if(cb_Availability.isChecked()) {
            available = true;
        }
        else {available = false;}

        cb_Availability.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_Availability.isChecked()) {
                    available = true;
                }
                else {available = false;}
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
        enableTextView(tv_nameDogDetails, tv_breedDog, tv_birthDateDog, tv_mother, tv_father, tv_Description);
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

                saveChanges(tv_nameDogDetails.getText().toString(),
                        tv_breedDog.getText().toString(),
                        gender,
                        tv_birthDateDog.getText().toString(),
                        pedigree,
                        available)
                        ; //////////////////////////////////////

//////////////////////////////// GENDER and PEDIGREE /////////////////////////
            }
        };

    private void pedigreeManagement() {
        //pedigree management based on switch
        tv_PedigInfo = view.findViewById(R.id.tv_PedigreeInfoDogDetails);
        tv_PedigInfo.setFocusable(false); //avoid tab stop
        Switch pedig = (Switch) view.findViewById(R.id.sw_pedigree_dog_details);
        //check initial state
        pedig.setChecked(false);
        if(pedig.isChecked()){
            pedigree = true;
            tv_PedigInfo.setText(R.string.str_Pedigree);
            tv_PedigInfo.setTextColor(getResources().getColor(R.color.niceGreen_teal_700));
        } else {
            pedigree = false;
            tv_PedigInfo.setText(R.string.str_NoPedigree);
            tv_PedigInfo.setTextColor(getResources().getColor(R.color.myColor));
        }
        pedig.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(pedig.isChecked()){
                    pedigree = true;
                    tv_PedigInfo.setText(R.string.str_Pedigree);
                    tv_PedigInfo.setTextColor(getResources().getColor(R.color.niceGreen_teal_700));
                }
                else {
                    pedigree = false;
                    tv_PedigInfo.setText(R.string.str_NoPedigree);
                    tv_PedigInfo.setTextColor(getResources().getColor(R.color.myColor));
                }
            }
        });
    }


    private boolean valideDogAttributes(String dogName, String dogBreed, String dogBirth, Gender gender, boolean pedig) {

        boolean cancel = false;
        View focusView = null;


        if(dogName.isEmpty() || dogName.equals("")) {
            tv_nameDogDetails.setError(getString(R.string.error_dog_noName));
            focusView = radioGroup;
            cancel = true;
        }

        //check name doublon
//        for (Dog d : dogsBreederList) {
//            if(d.getNameDog().equals(dogName)){
//                et_NameDog.setError("This name is already assigned.\nPlease choose another one.");
//                focusView = et_NameDog;
//                cancel = true;
//            }
//        }

        if(dogBreed.isEmpty() || dogBreed.equals("")) {
            tv_breedDog.setError(getString(R.string.error_dog_noBreed));
            focusView = radioGroup;
            cancel = true;
        }

        if(dogBirth.isEmpty() || dogBirth.equals("")) {
            dogBirth = Calendar.getInstance().getTime().toString();
//            tv_BirthDateDog.setError(getString(R.string.error_dog_noBirth));
//            focusView = radioGroup;
//            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
            return false;
        }

        return true ;
    }


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
        cb_Availability.setEnabled(true);

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

    private void saveChanges(String nameDoggy, String breedDoggy, Gender genderDoggy , String dateOfBithDoggy,  Boolean pedigreeDoggy, boolean avlbl) {

        if( valideDogAttributes(nameDoggy, breedDoggy, dateOfBithDoggy, genderDoggy, pedigreeDoggy) ) {
            Dog newDog = new Dog(nameDoggy, breedDoggy, dateOfBithDoggy, gender, this.currentBreederMail, pedigreeDoggy, avlbl);
            newDog.setSpecificationsDog(tv_Description.getText().toString());
            newDog.setBreederMail(this.currentBreederMail);

            //set mother + father

            viewModel.updateDog(dog, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, getString(R.string.msg_DogCreated));
                    toast = Toast.makeText(getContext(), getString(R.string.msg_DogUpdated), Toast.LENGTH_LONG);
                    toast.show();

                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.nv_NavHostView, DogsListFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("").commit();
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "createDog: failure", e);
                    toast = Toast.makeText(getContext(), getString(R.string.msg_DogNOTCreated), Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }

        dog.setNameDog(nameDoggy);
        dog.setBreedDog(breedDoggy);
        dog.setGender(genderDoggy);
        dog.setDateOfBirth(dateOfBithDoggy);
//        dog.setIdMother(motherDoggy);
//        dog.setIdFather(fatherDoggy);
        dog.setPedigree(pedigreeDoggy);

        //restore 'Edit Profile' textview-button
        iv_BtnEdit.setImageResource(R.drawable.ic_edit);
        iv_BtnEdit.setOnClickListener(btnEditListener);

        disableTextView(tv_nameDogDetails, tv_breedDog, tv_birthDateDog, tv_mother, tv_father);

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
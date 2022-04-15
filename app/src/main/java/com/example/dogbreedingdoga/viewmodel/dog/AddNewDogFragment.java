package com.example.dogbreedingdoga.viewmodel.dog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.Gender;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;
import com.example.dogbreedingdoga.Database.util.RecyclerViewItemClickListener;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.adapter.RecyclerAdapter;
import com.example.dogbreedingdoga.ui.BaseActivity;
import com.example.dogbreedingdoga.ui.DatePickerFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewDogFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewDogFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "New Dog added" ;

    private DogViewModel viewModel;
    private DogListViewModel listDogsViewModel;

    private EditText et_NameDog;
    private EditText et_BreedDog;
    private TextView tv_BirthDateDog;
    private EditText et_Description;
    private CheckBox cb_Availability;
    private boolean available;

    private Spinner lv_Mother;
    private Spinner lv_Father;
    private RecyclerAdapter adapterMothers;
    private RecyclerAdapter adapterFathers;

    private RadioGroup radioGroup;
    private RadioButton rb_Female;
    private RadioButton rb_Male;
    private Gender genderDog;

    private TextView tv_PedigInfo;
    private boolean pedigree;

    private Button btn_AddDog;

    private ImageView imageView;
    private TextView tvDate;

    private Toast toast;
    private final static int REQUEST_CODE = 11;

    private String currentBreederUid;

    private Dog dog;
    private List<Dog> dogsBreederList;
    private boolean isNewDog;

    /**
     * RESTE A TRAITER :
     *      edit vs create
     *      Availability behavior
     *      Visibility with keyboard
     *      check name already taken
     *
     *      TEST VALIDATIONS
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = ((ViewGroup) inflater.inflate(R.layout.fragment_add_new_dog,container,false));

        //check if new dog and initialise checkbox Availability
        String idDog = getActivity().getIntent().getStringExtra("idDog");

        currentBreederUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //UI initialisation
        initialisation(root);



        //for the dog view
        DogViewModel.Factory factory = new DogViewModel.Factory(getActivity().getApplication(), idDog, currentBreederUid);
        viewModel = new ViewModelProvider(this, factory).get(DogViewModel.class);
//        viewModel.getDog().observe((BaseActivity)getActivity(), dogEntity -> {
//            if (dogEntity != null) {
//                dog = dogEntity;
//                updateContent();
//
//            }
//        });


        // =================== code à checker ========================

        //get fragment manager to launch from datepicker fragment
        final FragmentManager fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();

        ImageButton btnDate = (ImageButton) root.findViewById(R.id.ib_birth);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFrag = new DatePickerFragment();

                newFrag.setTargetFragment(AddNewDogFragment.this, REQUEST_CODE);
//
//                newFrag.show( root.getA, "datePicker");
                showDatePickerDialog(view);
            }
        });

        // ============================================================



        return root;
    }

    private void initialisation(ViewGroup root) {
        et_NameDog = root.findViewById(R.id.et_name_dog);
        et_BreedDog = root.findViewById(R.id.et_breed);
        tv_BirthDateDog = root.findViewById(R.id.tv_birth);
        lv_Mother = (Spinner) root.findViewById(R.id.list_Mother);
        lv_Father = (Spinner) root.findViewById(R.id.list_Father);
        tv_PedigInfo = root.findViewById(R.id.tv_PedigreeInfo);
        et_Description = root.findViewById(R.id.et_DogDescription);
        tvDate = root.findViewById(R.id.tv_birth);



        //initiate checkbox and manage availability according to this checkBox value
        checkBoxAvailabilityManagement(root);

        //=================================================

        dogsBreederList = new ArrayList<>();
        adapterMothers = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + dogsBreederList.get(position).getNameDog());

                FragmentManager fragmentManager = getParentFragmentManager();

                String idDog = dogsBreederList.get(position).getIdDog();
                Bundle data = new Bundle();
                data.putString("DogID", idDog);
                DogDetailsFragment dogDetailsFragment = new DogDetailsFragment();
                dogDetailsFragment.setArguments(data);
                fragmentManager.beginTransaction()
                        .replace(R.id.nv_NavHostView, dogDetailsFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack("").commit();
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + dogsBreederList.get(position).getNameDog());


            }
        });

        ArrayList<Dog> AL_Fathers = new ArrayList<>();
        ArrayList<Dog> AL_Mothers = new ArrayList<>();

        SpinnerAdapter motherAdapter = new com.example.dogbreedingdoga.adapter.SpinnerAdapter(getActivity(), R.layout.custom_spinner_adapter, AL_Mothers);
        SpinnerAdapter fatherAdapter = new com.example.dogbreedingdoga.adapter.SpinnerAdapter(getActivity(), R.layout.custom_spinner_adapter, AL_Fathers);

        //to fetch all current breeder's dogs for Mother - Father and check the name availability
        DogListViewModel.Factory factoryList = new DogListViewModel.Factory(
                getActivity().getApplication(), currentBreederUid);

        listDogsViewModel = new ViewModelProvider(this, factoryList).get(DogListViewModel.class);

        listDogsViewModel.getOwnAvailableFemaleDogs().observe((BaseActivity)getActivity(), dogEntities -> {
            if (dogEntities != null) {
                dogsBreederList = dogEntities;
                adapterMothers.setData(AL_Mothers); //========================================
            }
        });


//        for (Dog d: dogsBreederList) {
//            if(d.getGender() == Gender.Male) {
//                //add to father list
//                AL_Fathers.add(d);
//
//            }
//
//            if(d.getGender() == Gender.Female) {
//                //add to Mother list
//                AL_Mothers.add(d);
//            }
//        }

        lv_Mother.setAdapter(motherAdapter);
        lv_Father.setAdapter(fatherAdapter);

        //code for implementing picture selector
        imageView = root.findViewById(R.id.iv_add_picture_dog);
        imageView.setOnClickListener(view -> mGetContent.launch("image/*"));
        //end

        //initialise and check gender radio buttons
        genderManagement(root);

        //initialise pedigree switch toggle
        pedigreeManagement(root);


        btn_AddDog = root.findViewById(R.id.btn_CreateDog);
        btn_AddDog.setOnClickListener(view -> {
            saveDog(  et_NameDog.getText().toString(),
                    et_BreedDog.getText().toString(),
                    tv_BirthDateDog.getText().toString(),
                    genderDog,
                    pedigree,
                    available
            );
        });

    }

    private void checkBoxAvailabilityManagement(ViewGroup root) {
        //initiate checkbox and set at true
        cb_Availability = root.findViewById(R.id.cb_availability);
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

    /**
     * Initialising and managing gender radio buttons
     * @param root
     */
    private void genderManagement(ViewGroup root) {
        rb_Female = (RadioButton) root.findViewById(R.id.rb_DogFemale);
        rb_Male = (RadioButton) root.findViewById(R.id.rb_DogMale);
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
    }

    /**
     * Method initialising and managing pedigree switch toggle
     * @param root
     */
    private void pedigreeManagement(ViewGroup root) {
        //pedigree management based on switch
        tv_PedigInfo = root.findViewById(R.id.tv_PedigreeInfo);
        tv_PedigInfo.setFocusable(false); //avoid tab stop
        Switch pedig = (Switch) root.findViewById(R.id.sw_pedigree);
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

    /**
     * Method used to import picture from the gallery
     */
    private ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null){
                        imageView.setImageURI(result);
                    }
                }
            }
    );

    private void showDatePickerDialog(View view) {

        DialogFragment newFrag = new DatePickerFragment();

//        newFrag.setTargetFragment(AddNewDogFragment.this, REQUEST_CODE);

        DialogFragment dateShower = new DatePickerFragment();
        dateShower.show(getActivity().getSupportFragmentManager(), "datePicker");
//        newFrag.show(this.getActivity().getSupportFragmentManager(), "datePicker");
//        int day = datePicker. getDayOfMonth();
    }

    /**
     * Manage the behavior of a clic on button save
     * @param dogName
     * @param dogBreed
     * @param dogBirth
     * @param gender
     * @param pedig
     * @param avlbl
     */

    private void saveDog(String dogName, String dogBreed, String dogBirth, Gender gender, boolean pedig, boolean avlbl) {

        if( valideDogAttributes(dogName, dogBreed, dogBirth, gender, pedig, avlbl) ) {
            Dog newDog = new Dog(dogName, dogBreed, dogBirth,gender, this.currentBreederUid, pedig, avlbl);
            newDog.setSpecificationsDog(et_Description.getText().toString());
            newDog.setBreederMail(this.currentBreederUid);

            //set mother + father

            viewModel.createDog(newDog, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, getString(R.string.msg_DogCreated));
                    toast = Toast.makeText(getContext(), getString(R.string.msg_DogCreated), Toast.LENGTH_LONG);
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
    }

    private boolean valideDogAttributes(String dogName, String dogBreed, String dogBirth, Gender gender, boolean pedig, boolean avlbl) {

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
            focusView = et_NameDog;
            cancel = true;
        }

        //check name doublon
        for (Dog d : dogsBreederList) {
            if(d.getNameDog().equals(dogName)){
                et_NameDog.setError("This name is already assigned.\nPlease choose another one.");
                focusView = et_NameDog;
                cancel = true;
            }
        }


        if(dogBreed.isEmpty() || dogBreed.equals("")) {
            et_BreedDog.setError(getString(R.string.error_dog_noBreed));
            focusView = et_BreedDog;
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

    private void updateContent() {
        if(dog != null) {
            et_NameDog.setText(dog.getNameDog());
            et_BreedDog.setText(dog.getBreedDog());
            tv_BirthDateDog.setText(Calendar.getInstance().getTime().toString());
            et_Description.setText(dog.getSpecificationsDog());

        }
    }

    //for datePicker. A check...
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String selectedDate = data.getStringExtra("selectedDate");
            tv_BirthDateDog.setText(selectedDate);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        tvDate.setText(currentDateString);
    }
}
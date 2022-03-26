package com.example.dogbreedingdoga.viewmodel.dog;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.Gender;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.ui.BaseActivity;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewDogFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewDogFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "New Dog added" ;

    private DogViewModel viewModel;

    private EditText et_NameDog;
    private EditText et_BreedDog;
    private TextView tv_BirthDateDog;
    private EditText et_Description;
    private CheckBox cb_Availability;
    private boolean available;

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

    private String currentBreederMail;

    private Dog dog;
    private boolean isNewDog;


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

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

        //Retrieve the id (mail) of the connected breeder
        SharedPreferences settings = getActivity().getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        this.currentBreederMail = settings.getString(BaseActivity.PREFS_USER, null);
        System.out.println("========== BREEEEDDDEEEEEEER : " +currentBreederMail +" =======================");

        //check if new dog and initialise checkbox Availability
        Long idDog = getActivity().getIntent().getLongExtra("idDog", 0L);
        available = true;
        if(idDog == 0L) {
            cb_Availability = new CheckBox(getContext());
            cb_Availability.setChecked(true);
            isNewDog = true;
        } else {
            cb_Availability = root.findViewById(R.id.cb_availability);
//            cb_Availability.setChecked();
            isNewDog = false;
        }

        //UI initialisation
        initialisation(root);


        DogViewModel.Factory factory = new DogViewModel.Factory(getActivity().getApplication(), idDog, currentBreederMail);
        viewModel = new ViewModelProvider(this, factory).get(DogViewModel.class);
        viewModel.getDog().observe((BaseActivity)getActivity(), dogEntity -> {
            if (dogEntity != null) {
                dog = dogEntity;
                updateContent();

            }
        });


        // =================== code à checker ========================


        //this code will open a calendar in which you can select a date
//        tvDate = root.findViewById(R.id.tv_birth);
//        ImageButton btnDate = (ImageButton) root.findViewById(R.id.ib_birth);
//        btnDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerFragment datePicker = new DatePickerFragment();
//                System.out.println("=========== FROM onClick - AddNewDogFragment =========== +" +
//                        "\ngetActivity : " +getActivity() +"\nGET_CONTEXT : " +getContext()
//                        +"\nPARENT_FRAGMENT : " +getParentFragment()
//                        +"\nPARENT_FRAGMENT MANAGER : " +getParentFragmentManager()
//                        +"\nChildFragmentManager : " +getChildFragmentManager()
//                        +"\n======================================================");
//                datePicker.show(getChildFragmentManager(), "Date picker"); //.getSupportFragmentManager()
//            }
//        });

        // ============================================================



        return root;
    }

    private void initialisation(ViewGroup root) {
        et_NameDog = root.findViewById(R.id.et_name_dog);
        et_BreedDog = root.findViewById(R.id.et_breed);
        tv_BirthDateDog = root.findViewById(R.id.tv_birth);
        et_Description = root.findViewById(R.id.et_DogDescription);

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

    private void saveDog(String dogName, String dogBreed, String dogBirth, Gender gender, boolean pedig, boolean avlbl) {
        System.out.println("Saperlipopette, ne passerai-je point dans ce clique ??");

        if( valideDogAttributes(dogName, dogBreed, dogBirth, gender, pedig, avlbl) ) {
            Dog newDog = new Dog(dogName, dogBreed, dogBirth,gender, this.currentBreederMail, pedig, avlbl);
            newDog.setSpecificationsDog(et_Description.getText().toString());
            newDog.setBreederMail(this.currentBreederMail);

            //set mother + father

            viewModel.createDog(newDog, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, getString(R.string.msg_DogCreated));
                    toast = Toast.makeText(getContext(), getString(R.string.msg_DogCreated), Toast.LENGTH_LONG);
                    toast.show();
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
            focusView = radioGroup;
            cancel = true;
        }

        if(dogBreed.isEmpty() || dogBreed.equals("")) {
            et_BreedDog.setError(getString(R.string.error_dog_noBreed));
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

    private void updateContent() {
        if(dog != null) {
            et_NameDog.setText(dog.getNameDog());
            et_BreedDog.setText(dog.getBreedDog());
            tv_BirthDateDog.setText(Calendar.getInstance().getTime().toString());
            et_Description.setText(dog.getSpecificationsDog());

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
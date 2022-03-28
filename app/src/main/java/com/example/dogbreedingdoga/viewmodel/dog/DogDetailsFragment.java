package com.example.dogbreedingdoga.viewmodel.dog;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.Repository.DogRepository;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.ui.BaseActivity;

public class DogDetailsFragment extends Fragment {


    private TextView tv_nameDog;
    private TextView tv_breedDog;
    private TextView tv_birthDateDog;

    private TextView tv_gender;
    private TextView tv_mother;
    private TextView tv_father;
    private TextView tv_pedigree;

    private Dog dog;
    private DogRepository dogRepository;
    private DogViewModel viewModel;
    private long idDoggy;
    private String currentBreederMail;

    private ImageView iv_BtnDelete;
    private ImageView iv_BtnEdit;

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
        tv_nameDog = getActivity().findViewById(R.id.tv_dog_details_name_dog);
        tv_nameDog.setText(dog.getNameDog());

        tv_breedDog = getActivity().findViewById(R.id.tv_dog_details_breed);
        tv_breedDog.setText(dog.getBreedDog());

        tv_birthDateDog = getActivity().findViewById(R.id.tv_dog_details_birth);
        tv_birthDateDog.setText(dog.getDateOfBirth());

        /// mention if parents are null
//        tv_mother = getActivity().findViewById(R.id.tv_dog_details_mother);
//        tv_mother.setText(dog.getIdMother());
//
//        tv_father = getActivity().findViewById(R.id.tv_dog_details_father);
//        tv_father.setText(dog.getIdFather());

        tv_pedigree = getActivity().findViewById(R.id.tv_dog_details_pedigree);
        if(dog.getPedigree() == true) {
            tv_pedigree.setText(R.string.str_Pedigree);
        }
        tv_pedigree.setText(R.string.str_NoPedigree);

    }


    private View.OnClickListener btnEditListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //call fragment add a new dog for edition
            Bundle data = new Bundle();
            data.putLong("DogID", idDoggy);
            AddNewDogFragment addNewDogFragment = new AddNewDogFragment();
            addNewDogFragment.setArguments(data);
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.nv_NavHostView, addNewDogFragment)
                    .setReorderingAllowed(true)
                    .addToBackStack("").commit();
        }
    };

    private View.OnClickListener btnDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //method for deleting breeder
            deleteDog();
        }
    };


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

}
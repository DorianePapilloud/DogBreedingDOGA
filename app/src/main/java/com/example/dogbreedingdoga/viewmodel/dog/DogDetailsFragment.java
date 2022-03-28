package com.example.dogbreedingdoga.viewmodel.dog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.Repository.DogRepository;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.ui.BaseActivity;
import com.example.dogbreedingdoga.viewmodel.breeder.BreederViewModel;

public class DogDetailsFragment extends Fragment {


    private TextView tv_nameDog;
    private TextView tv_breedDog;
    private TextView tv_birthDateDog;

    private TextView tv_gender;
    private TextView tv_mother;
    private TextView tv_pedigree;

    private Dog dog;

    private DogRepository dogRepository;
    private DogViewModel viewModel;
    private long idDoggy;


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
//    public DogDetailsFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DogDetailsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static DogDetailsFragment newInstance(String param1, String param2) {
//        DogDetailsFragment fragment = new DogDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        long idDoggy = savedInstanceState.getLong("DogID", 0L);


        Bundle data = getArguments();
        if(data != null){
            idDoggy = data.getLong("DogID");
        }

        // Inflate the layout for this fragment
        View view = getLayoutInflater().inflate(R.layout.fragment_dog_details, container, false);

        Long dogId = getActivity().getIntent().getLongExtra("dogId", 0L);

        DogViewModel.Factory factory = new DogViewModel.Factory(getActivity().getApplication(), dogId, dog.getBreederMail());
        viewModel = new ViewModelProvider(this, factory).get(DogViewModel.class);
        viewModel.getDog().observe(getActivity(), dogEntity -> {
            if (dogEntity != null) {
                dog = dogEntity;
                getDataFromIdDog();
            }
        });

        return view;
    }

    private void getDataFromIdDog() {

        tv_nameDog = getActivity().findViewById(R.id.tv_dog_details_name_dog);
        tv_nameDog.setText(dog.getNameDog());

    }

}
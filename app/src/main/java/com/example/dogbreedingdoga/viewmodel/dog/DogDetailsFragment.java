package com.example.dogbreedingdoga.viewmodel.dog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.ui.BaseActivity;
import com.example.dogbreedingdoga.viewmodel.breeder.BreederViewModel;

public class DogDetailsFragment extends Fragment {

    BaseActivity baseActivity = new BaseActivity();


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
        // Inflate the layout for this fragment
        View view = getLayoutInflater().inflate(R.layout.fragment_dog_details, container, false);
//        view. navigationView.setCheckedItem(baseActivity.position);
//
//        Long dogId = getActivity().getIntent().getLongExtra("dogId", 0L);
//
//
//        DogViewModel.Factory factory = new DogViewModel(getActivity().getApplication(), dogId, );
//        viewModel = new ViewModelProvider(this, factory).get(BreederViewModel.class);
//        viewModel.getBreeder().observe(this, accountEntity -> {
//            if (accountEntity != null) {
//                breeder = accountEntity;
//                updateContent();
//            }
//        });

        return view;
    }
}
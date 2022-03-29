package com.example.dogbreedingdoga.viewmodel.dog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.util.RecyclerViewItemClickListener;
import com.example.dogbreedingdoga.R;
import com.example.dogbreedingdoga.adapter.RecyclerAdapter;
import com.example.dogbreedingdoga.ui.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class DogsListFragment extends Fragment {

    private static final String TAG = "DogsListFragment";
    private List<Dog> dogs;
    private RecyclerAdapter<Dog> adapter;
    private DogListViewModel viewModel;

    FloatingActionButton btn_add_new_dog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_dogs_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_list_dogs);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //Retrieve current user
        SharedPreferences settings = this.getActivity().getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String user = settings.getString(BaseActivity.PREFS_USER, null);

        dogs = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + dogs.get(position).getNameDog());

                FragmentManager fragmentManager = getParentFragmentManager();


                long idDog = dogs.get(position).getIdDog();
                Bundle data = new Bundle();
                data.putLong("DogID", idDog);
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
                Log.d(TAG, "longClicked on: " + dogs.get(position).getNameDog());


            }
        });

        DogListViewModel.Factory factory = new DogListViewModel.Factory(
                getActivity().getApplication(), user);

        viewModel = new ViewModelProvider(this, factory).get(DogListViewModel.class);

        viewModel.getOwnDogs().observe((BaseActivity)getActivity(), dogEntities -> {
            if (dogEntities != null) {
                dogs = dogEntities;
                adapter.setData(dogs);
            }
        });

        recyclerView.setAdapter(adapter);


        btn_add_new_dog = view.findViewById(R.id.btn_add_dog);
        btn_add_new_dog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.nv_NavHostView, AddNewDogFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("").commit();
            }
        });
        return view;
    }

}
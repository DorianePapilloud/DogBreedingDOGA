package com.example.dogbreedingdoga.viewmodel.dog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.dogbreedingdoga.placeholder.PlaceholderContent;
import com.example.dogbreedingdoga.ui.BaseActivity;
import com.example.dogbreedingdoga.ui.MainActivity;
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
    private RecyclerView recyclerView;

    FloatingActionButton btn_add_new_dog;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;



    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DogsListFragment newInstance(int columnCount) {
        DogsListFragment fragment = new DogsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dogs_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_list_dogs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //manage if no dogs in the list
            recyclerView.setAdapter(new MyDogsRecyclerViewAdapter(PlaceholderContent.ITEMS));
        }


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

        dogs = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + dogs.get(position).getNameDog());

                Intent intent = new Intent(getContext(), BaseActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("accountId", dogs.get(position).getIdDog());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + dogs.get(position).getNameDog());


            }
        });

        return view;
    }
}
package com.nrlm.agey.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.nrlm.agey.R;

public class HomeTestFragment extends Fragment {



    MaterialButton btn_showNxtFrag;

    public HomeTestFragment() {
        super(R.layout.fragment_home_test);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = NavHostFragment.findNavController(this);

        btn_showNxtFrag =view.findViewById(R.id.btn_showNxtFrag);

        btn_showNxtFrag.setOnClickListener(view1 -> {
            NavDirections action = HomeTestFragmentDirections.actionHomeTestFragmentToFragmet2();
            navController.navigate(action);
        });

    }
}

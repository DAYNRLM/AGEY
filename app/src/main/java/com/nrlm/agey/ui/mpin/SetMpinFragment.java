package com.nrlm.agey.ui.mpin;

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
import com.nrlm.agey.ui.BaseFragment;

public class SetMpinFragment extends Fragment {

    MaterialButton btn_setMpin;

    public SetMpinFragment() {
        super(R.layout.fragment_set_mpin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = NavHostFragment.findNavController(this);

        btn_setMpin =view.findViewById(R.id.btn_setMpin);

        btn_setMpin.setOnClickListener(view1 -> {

            /*****directly used by destination id*****/
           // navController.navigate(R.id.verifyMpinFragment);

            /****directly used by action id action created in nav_graph********/
           // navController.navigate(R.id.action_setMpinFragment2_to_verifyMpinFragment);


            /***********using safe arge library****************/
            NavDirections action = SetMpinFragmentDirections.actionSetMpinFragment2ToVerifyMpinFragment();
            navController.navigate(action);
        });



    }



    /* public static SetMpinFragment newInstance() {
        SetMpinFragment setMpinFrag = new SetMpinFragment();
        return setMpinFrag;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_set_mpin;
    }

    @Override
    public void onFragmentReady() {

    }*/


}

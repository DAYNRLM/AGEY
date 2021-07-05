package com.nrlm.agey.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.nrlm.agey.HomeNavGraphDirections;
import com.nrlm.agey.R;
import com.nrlm.agey.utils.SampleData;
import com.nrlm.agey.utils.SampleJavaData;

public class SettingFragment extends Fragment {

    TextInputEditText et_getKilometer;
    MaterialButton btn_save,btn_about_us;

    public SettingFragment() {
        super(R.layout.fragment_setting);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = NavHostFragment.findNavController(this);

        et_getKilometer =view.findViewById(R.id.et_getKilometer);
        btn_save =view.findViewById(R.id.btn_save);
        btn_about_us =view.findViewById(R.id.btn_about_us);

        MutableLiveData data =SampleData.Companion.getDefaultKilometer();
        String showData =data.getValue().toString();

        et_getKilometer.setText(showData);

        btn_save.setOnClickListener(view1 -> {
            long getValue = Long.parseLong(et_getKilometer.getText().toString());
            MutableLiveData getKm =new MutableLiveData<Long>(getValue);
            //SampleJavaData.defaultKilometer =getKm;
            SampleData.Companion.setDefaultKilometer(getKm);
        });


        btn_about_us.setOnClickListener(view1 -> {
            NavDirections action = HomeNavGraphDirections.actionGlobalAboutUsFragment();
            navController.navigate(action);

        });


    }
}

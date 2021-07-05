package com.nrlm.agey.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.material.textfield.TextInputEditText;
import com.nrlm.agey.R;
import com.nrlm.agey.utils.SampleData;

public class Fragmet2 extends Fragment {

    TextInputEditText et_getKm;

    public Fragmet2() {
        super(R.layout.fragment_frag);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_getKm =view.findViewById(R.id.et_getKm);

        MutableLiveData data = SampleData.Companion.getDefaultKilometer();
        String showData =data.getValue().toString();
        et_getKm.setText(showData);

        SampleData.Companion.getDefaultKilometer().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                et_getKm.setText(aLong.toString());
            }
        });

    }
}

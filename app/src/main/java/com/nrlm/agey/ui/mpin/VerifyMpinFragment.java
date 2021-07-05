package com.nrlm.agey.ui.mpin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.nrlm.agey.R;
import com.nrlm.agey.ui.home.HomeActivity;

public class VerifyMpinFragment extends Fragment {

    MaterialButton btn_verify;

    public VerifyMpinFragment() {
        super(R.layout.fragment_verify_mpin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_verify =view.findViewById(R.id.btn_verify);

        btn_verify.setOnClickListener(view1 -> {
            Intent intent =new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        });
    }

    /*  public static VerifyMpinFragment newInstance() {
        VerifyMpinFragment veriFyMpin = new VerifyMpinFragment();
        return veriFyMpin;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_verify_mpin;
    }

    @Override
    public void onFragmentReady() {

    }*/
}

package com.nrlm.agey.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.nrlm.agey.R;
import com.nrlm.agey.database.entity.UserDetailEntity;
import com.nrlm.agey.databinding.FragmentAuthBinding;
import com.nrlm.agey.model.response.Example;
import com.nrlm.agey.network.retrofitCall.RetrofitClient;
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.BaseAllRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.ui.home.HomeViewModelFactory;
import com.nrlm.agey.ui.mpin.MpinActivity;
import com.nrlm.agey.utils.CustomProgressDialog;
import com.nrlm.agey.utils.ViewUtilsKt;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthFragment extends BaseFragment<AuthViewModel, FragmentAuthBinding, AppRepository,AuthViewModelfactory> {
    public static final String TAG = "AuthFragment";

    @Override
    public Class<AuthViewModel> getViewModel() {
        return AuthViewModel.class;
    }

    @Override
    public FragmentAuthBinding getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentAuthBinding.inflate(inflater, container, false);
    }

    @Override
    public AppRepository getFragmentRepository() {
        return AppRepository.getInstance(getActivity().getApplication());
    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @Override
    public AuthViewModelfactory getFactory() {
        return new AuthViewModelfactory(getFragmentRepository());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewUtilsKt.tost(getContext(),""+viewModel.getRegistredNumber().size());

        Log.d(TAG, "IMEI: "+appSharedPreferences.getImeiNumber());
        Log.d(TAG, "Device info: "+appSharedPreferences.getDeviceInfo());

        final Observer<Boolean> nameObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean newName) {
                customProgressDialog.hideProgress();
                Intent intent =new Intent(getContext(), MpinActivity.class);
                startActivity(intent);


                 /*String userId = binding.etUserId.getText().toString();
            String password = binding.etPassword.getText().toString();
            Intent intent =new Intent(getContext(), MpinActivity.class);
            startActivity(intent);*/

            }
        };

        binding.btnLogin.setOnClickListener(view1 -> {
            // ViewUtilsKt.tost(getContext(),"hiiiii");
            customProgressDialog.showProgress("loading", false);
            viewModel.show().observe(getViewLifecycleOwner(), nameObserver);
            viewModel.getDataFromApi();
            ViewUtilsKt.tost(getContext(),""+viewModel.getRegistredNumber().size());
        });


        binding.tvForgetPassword.setOnClickListener(view1 -> {
            NavDirections action = AuthFragmentDirections.actionAuthFragmentToForgetPasswordFragment();
            navController.navigate(action);
        });
    }
}



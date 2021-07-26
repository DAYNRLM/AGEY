package com.nrlm.agey.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import com.nrlm.agey.network.retrofitCall.RetrofitClient;
import com.nrlm.agey.network.vollyCall.VolleyResult;
import com.nrlm.agey.network.vollyCall.VolleyService;
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.BaseAllRepository;
import com.nrlm.agey.repository.BaseRepository;
import com.nrlm.agey.ui.login.AuthViewModelfactory;
import com.nrlm.agey.utils.AppDateFactory;
import com.nrlm.agey.utils.AppDeviceInfoUtils;
import com.nrlm.agey.utils.AppSharedPreferences;
import com.nrlm.agey.utils.AppUtils;
import com.nrlm.agey.utils.CustomProgressDialog;
import com.nrlm.agey.utils.GetAllInstance;

public abstract class BaseFragment<VM extends ViewModel,
        B extends ViewBinding,
        R extends BaseRepository,
        VMF extends ViewModelProvider.Factory > extends Fragment {
    public B binding;
    public VM viewModel;
    public RetrofitClient client;
    public AppSharedPreferences appSharedPreferences;
    public CustomProgressDialog customProgressDialog;
    public AppDeviceInfoUtils deviceUtils;
    public NavController navController;
    public AppUtils  appUtils ;
    public GetAllInstance getAllInstance;
    public VolleyResult mResultCallBack = null;
    public VolleyService volleyService;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        //AuthViewModelfactory factory = new AuthViewModelfactory(getFragmentRepository());
        viewModel = new ViewModelProvider(this,getFactory()).get(getViewModel());
        appSharedPreferences =AppSharedPreferences.getInstance(getCurrentContext());
        customProgressDialog = CustomProgressDialog.newInstance(getContext());
        deviceUtils = AppDeviceInfoUtils.getInstance(getContext());
        navController = NavHostFragment.findNavController(this);
        client = RetrofitClient.getInstance();
        appUtils =AppUtils.getInstance();
        getAllInstance =GetAllInstance.getInstance(getCurrentContext());
        volleyService=VolleyService.getInstance(getCurrentContext());

        this.onFragmentReady();
        return binding.getRoot();
    }

    public abstract Class<VM> getViewModel();
    public abstract B getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container);
    public abstract R getFragmentRepository();
    public abstract Context getCurrentContext();
    public abstract  VMF getFactory();
    public abstract  void onFragmentReady();
}

package com.nrlm.agey.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nrlm.agey.R;
import com.nrlm.agey.adapter.LanguageAdapter;
import com.nrlm.agey.adapter.UnsyncDataAdapter;
import com.nrlm.agey.database.entity.MonthlyTrackingDataEntity;
import com.nrlm.agey.databinding.FragmentAboutUsBinding;
import com.nrlm.agey.databinding.FragmentAssignVehicleBinding;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.ui.BaseFragment;

import java.util.List;

public class AboutUsFragment extends BaseFragment<HomeViewModel, FragmentAboutUsBinding, HomeRepository,HomeViewModelFactory> {

    UnsyncDataAdapter unsyncDataAdapter;

    @Override
    public Class<HomeViewModel> getViewModel() {
        return HomeViewModel.class;
    }

    @Override
    public FragmentAboutUsBinding getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentAboutUsBinding.inflate(inflater, container, false);
    }

    @Override
    public HomeRepository getFragmentRepository() {
        return HomeRepository.getInstance(getActivity().getApplication());
    }

    @Override
    public Context getCurrentContext() {
            return getContext();
    }

    @Override
    public HomeViewModelFactory getFactory() {
        return  new HomeViewModelFactory(getFragmentRepository());
    }

    @Override
    public void onFragmentReady() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       if(viewModel.getTrackingData().isEmpty()){
           binding.cvUnsyncDaata.setVisibility(View.GONE);
           binding.rlShowMsgToplayout.setVisibility(View.VISIBLE);
       }else{
           unsyncDataAdapter=new UnsyncDataAdapter(viewModel.getTrackingData(),getContext(),navController,viewModel);
           binding.rvUnsyncData.setLayoutManager(new LinearLayoutManager(getContext()));
           binding.rvUnsyncData.setAdapter(unsyncDataAdapter);
           unsyncDataAdapter.notifyDataSetChanged();
       }
    }
}

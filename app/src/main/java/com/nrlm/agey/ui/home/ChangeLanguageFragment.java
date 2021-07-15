package com.nrlm.agey.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nrlm.agey.adapter.LanguageAdapter;
import com.nrlm.agey.databinding.FragmentAssignVehicleBinding;
import com.nrlm.agey.databinding.FragmentChangeLanguageBinding;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.ui.BaseFragment;

public class ChangeLanguageFragment extends BaseFragment<HomeViewModel, FragmentChangeLanguageBinding, HomeRepository,HomeViewModelFactory> {

    LanguageAdapter languageAdapter;

    @Override
    public Class<HomeViewModel> getViewModel() {
        return  HomeViewModel.class;
    }

    @Override
    public FragmentChangeLanguageBinding getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentChangeLanguageBinding.inflate(inflater, container, false);
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


        languageAdapter=new LanguageAdapter(viewModel.getLanguage(),getContext(),navController);
        binding.rvLanguageList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvLanguageList.setAdapter(languageAdapter);
        languageAdapter.notifyDataSetChanged();




    }
}

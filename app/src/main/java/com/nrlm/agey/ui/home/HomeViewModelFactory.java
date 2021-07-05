package com.nrlm.agey.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.BaseRepository;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.ui.login.AuthViewModel;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    BaseRepository baseRepository;

    public HomeViewModelFactory(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        T homeViewModel = null;

        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            homeViewModel = (T) new HomeViewModel((HomeRepository) baseRepository);
        }
        return homeViewModel;
    }
}

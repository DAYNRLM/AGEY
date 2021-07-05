package com.nrlm.agey.ui.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.BaseAllRepository;
import com.nrlm.agey.repository.BaseRepository;

public class AuthViewModelfactory implements ViewModelProvider.Factory {
    BaseRepository baseRepository;

    public AuthViewModelfactory(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        T authViewModel = null;

        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            authViewModel = (T) new AuthViewModel((AppRepository) baseRepository);
        }
        return authViewModel;
    }
}

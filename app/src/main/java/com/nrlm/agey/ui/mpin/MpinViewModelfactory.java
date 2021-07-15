package com.nrlm.agey.ui.mpin;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.BaseRepository;
import com.nrlm.agey.repository.MpinRepository;
import com.nrlm.agey.ui.login.AuthViewModel;

public class MpinViewModelfactory implements ViewModelProvider.Factory {
    BaseRepository baseRepository;

    public MpinViewModelfactory(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        T mpinViewModel = null;

        if (modelClass.isAssignableFrom(MpinViewModel.class)) {
            mpinViewModel = (T) new MpinViewModel((MpinRepository) baseRepository);
        }
        return mpinViewModel;
    }
}

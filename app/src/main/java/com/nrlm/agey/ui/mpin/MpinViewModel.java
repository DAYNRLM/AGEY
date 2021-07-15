package com.nrlm.agey.ui.mpin;

import androidx.lifecycle.ViewModel;

import com.nrlm.agey.database.entity.UserDetailEntity;
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.MpinRepository;

import java.util.List;

public class MpinViewModel  extends ViewModel {
    MpinRepository mpinRepository;

    public MpinViewModel(MpinRepository mpinRepository) {
        this.mpinRepository = mpinRepository;
    }

    public List<UserDetailEntity> getUserData(){
       return mpinRepository.getUserdetail();
    }


}

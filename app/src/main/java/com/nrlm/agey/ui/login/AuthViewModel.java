package com.nrlm.agey.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nrlm.agey.database.entity.UserDetailEntity;
import com.nrlm.agey.model.response.Example;
import com.nrlm.agey.repository.AppRepository;

import java.util.ArrayList;
import java.util.List;

public class AuthViewModel extends ViewModel {
    public AppRepository appRepository;

    //public LiveData<List<Example>> liveDItem;
    public MutableLiveData<List<Example>> mutableListData;

    private MutableLiveData<Boolean> currentState = new MutableLiveData<Boolean>();


    public AuthViewModel(AppRepository appRepository) {
        this.appRepository = appRepository;
    }


    public LiveData<List<Example>> getListExample() {
        return mutableListData;
    }

    public MutableLiveData<Boolean> show() {
        currentState = appRepository.showP();
        return currentState;
    }


    public List<Example> getDataFromApi() {
        List<Example> liveDataItem;
        liveDataItem = appRepository.callAuthApi("lincon", "1234");
        return liveDataItem;
    }

    public List<String> getRegistredNumber(){

        List<String> numList = new ArrayList<>();
        numList.add(" ");
        numList.add(" ");
        numList.add(" ");
        numList.add(" ");
        numList.add(" ");
        numList.add(" ");
        numList.add(" ");
        return numList;

    }

    public void saveUseddetail() {
        UserDetailEntity userDetailEntity = new UserDetailEntity();
        userDetailEntity.id = 1;
        userDetailEntity.name = "lincon";
        appRepository.saveDate(userDetailEntity);
    }

    public LiveData<List<UserDetailEntity>> getAllUser() {
        LiveData<List<UserDetailEntity>> listLiveData;
        listLiveData = appRepository.getAllUser();
        return listLiveData;
    }


}

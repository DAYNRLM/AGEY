package com.nrlm.agey.ui.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nrlm.agey.R;
import com.nrlm.agey.database.entity.UserDetailEntity;
import com.nrlm.agey.databinding.DialogErrorMessageBinding;
import com.nrlm.agey.model.JplaceholderTest;
import com.nrlm.agey.model.LoginError;
import com.nrlm.agey.model.request.LoginRequest;
import com.nrlm.agey.model.response.Example;
import com.nrlm.agey.model.response.MainDataResponse;
import com.nrlm.agey.repository.AppRepository;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AuthViewModel extends ViewModel {
    public AppRepository appRepository;

    public LoginError error;

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

    /*public LiveData<List<UserDetailEntity>> getAllUser() {
        LiveData<List<UserDetailEntity>> listLiveData;
        listLiveData = appRepository.getAllUser();
        return listLiveData;
    }*/


    public MutableLiveData<String> getTestApi(JSONObject jsonObject) {
        MutableLiveData<String> data = appRepository.getTestApiCall(jsonObject);
        return data;
    }


    public void getAuthResponse(JSONObject jsonObject) {
        appRepository.authApiCall(jsonObject);

    }

    public void testJsonPlaceholder() {
       /* JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("title", "foo");
        jsonObject.accumulate("body", "bar");
        jsonObject.accumulate("userId", "1");

        JplaceholderTest jplaceholderTest = new JplaceholderTest();
        jplaceholderTest.body = "bar";
        jplaceholderTest.title = "foo";
        jplaceholderTest.userId = "1";

        viewModel.getTestApi(jsonObject).observe(getViewLifecycleOwner(), responseObserver);
        // viewModel.loginAuthentication(jsonObject);
        //  viewModel.testApiiii(jplaceholderTest);
        //  viewModel.getDataFromApi();
        viewModel.getTestApi(jsonObject);*/
    }

    public void insertLoginData(MainDataResponse monthlyMainResponse) {
        appRepository.inseartData(monthlyMainResponse);

    }



    public MutableLiveData<Boolean> showErrorDialog(LoginError loginError, Context context, LayoutInflater inflater) {
        error=loginError;
        MutableLiveData<Boolean> resetData = new MutableLiveData<>();
        MaterialAlertDialogBuilder materialAlertDialogBuilder =
                new MaterialAlertDialogBuilder(context);
        // View customLayout = inflater.inflate(R.layout.dialog_error_message, null);
        //materialAlertDialogBuilder.setView(customLayout);
        DialogErrorMessageBinding dialogErrorMessageBinding = DialogErrorMessageBinding.inflate(inflater);
        dialogErrorMessageBinding.setLogInError(loginError);
        materialAlertDialogBuilder.setView(dialogErrorMessageBinding.getRoot());
        materialAlertDialogBuilder.setCancelable(false);
        AlertDialog cusDialog = materialAlertDialogBuilder.show();
        dialogErrorMessageBinding.btnClose.setOnClickListener(view -> {
            cusDialog.cancel();
            resetData.setValue(true);
        });
        dialogErrorMessageBinding.btnOk.setOnClickListener(view -> {
            cusDialog.dismiss();
            resetData.setValue(true);
        });
        return resetData;
    }

    public void noInterNetConnection(Context context){
        new  MaterialAlertDialogBuilder(context).setTitle(context.getResources().getString(R.string.dialog_network_msg_title)).setIcon(R.drawable.ic_baseline_signal_wifi_connected_no_internet)
                .setMessage(context.getResources().getString(R.string.dialog_network_msg))
                .setPositiveButton(context.getResources().getString(R.string.dialog_positive_btn),(dialogInterface, i) -> {
                    dialogInterface.dismiss();
                }).setNegativeButton(context.getResources().getString(R.string.dialog_cancel_btn),(dialogInterface, i) -> {
            dialogInterface.dismiss();
        }).show();
    }


}

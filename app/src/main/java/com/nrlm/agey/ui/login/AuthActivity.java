package com.nrlm.agey.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.nrlm.agey.R;
import com.nrlm.agey.network.retrofitCall.RetrofitClient;
import com.nrlm.agey.ui.mpin.MpinActivity;
import com.nrlm.agey.utils.GetAllInstance;
import com.nrlm.agey.utils.PermissionHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {
    GetAllInstance getAllInstance;
    boolean checkPermision;
    PermissionHelper permissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getAllInstance = GetAllInstance.getInstance(AuthActivity.this);
        permissionHelper =PermissionHelper.getInstance(AuthActivity.this);

       // checkPermision = getAllInstance.permissionHelper.checkAndRequestPermissions();
        checkPermision = permissionHelper.checkAndRequestPermissions();



      /*  String imeiNumber =getAllInstance.appSharedPreferences.getImeiNumber();
        if(imeiNumber.equalsIgnoreCase("")||imeiNumber.isEmpty()){
            getAllInstance.appSharedPreferences.setImeiNumber( getAllInstance.deviceUtils.getIMEINo1());
            getAllInstance.appSharedPreferences.setDeviceInfo(getAllInstance.deviceUtils.getDeviceInfo());
        }*/
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.requestPermissionResult(PermissionHelper.REQUEST_ID_MULTIPLE_PERMISSIONS, permissions, grantResults);
        checkPermision = true;
    }


}
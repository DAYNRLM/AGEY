package com.nrlm.agey.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.nrlm.agey.R;
import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.databinding.FragmentAssignVehicleBinding;
import com.nrlm.agey.model.SampleObj;
import com.nrlm.agey.model.TestObject;
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.ui.login.AuthViewModelfactory;
import com.nrlm.agey.utils.AppConstant;
import com.nrlm.agey.utils.SampleData;
import com.nrlm.agey.utils.ViewUtilsKt;

import java.util.ArrayList;
import java.util.List;

public class AssignVehicleFragment extends BaseFragment<HomeViewModel,FragmentAssignVehicleBinding, HomeRepository,HomeViewModelFactory> {

    ArrayAdapter<String> loanSourceAdapter;
    String regNum="";
   public  TestObject testObject;

    @Override
    public Class<HomeViewModel> getViewModel() {
        return HomeViewModel.class;
    }

    @Override
    public FragmentAssignVehicleBinding getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentAssignVehicleBinding.inflate(inflater, container, false);
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

        loanSourceAdapter =new ArrayAdapter<String>(getContext(), R.layout.spinner_text, viewModel.getRegistredNumber());
        binding.spinnerAssignVehicle.setAdapter(loanSourceAdapter);
        loanSourceAdapter.notifyDataSetChanged();

        binding.spinnerAssignVehicle.setOnItemClickListener((adapterView, view1, i, l) -> {
            regNum = viewModel.getRegistredNumber().get(i);
            binding.setAssignVehicleModel(viewModel.getVehicleData(regNum));
            appSharedPreferences.setVehicleRegNum(regNum);

            binding.etVehicleType.setText(viewModel.vechileType(viewModel.getVehicleData(regNum).vehicleType));
            binding.etManufactureOfVehicle.setText(viewModel.getManufacturer(viewModel.getVehicleData(regNum).vehicleManufacture));
            binding.etVehicleModel.setText(viewModel.getModel(viewModel.getVehicleData(regNum).vehicleManufacture,viewModel.getVehicleData(regNum).vehicleModel));

            ViewUtilsKt.tost(getContext(),regNum);


            testObject = TestObject.getInstance();
            appUtils.showLog("object is: "+testObject,AssignVehicleFragment.class);
            SampleObj sampleObj = new SampleObj();
            appUtils.showLog("object is Sample: "+sampleObj,AssignVehicleFragment.class);

        });

        binding.btnGoToMonthelyTracking.setOnClickListener(view1 -> {
            if(regNum.equalsIgnoreCase("")||regNum.isEmpty()){
                ViewUtilsKt.tost(getContext(),"Please Select Vehicle Registration Number");
            }else {
                NavDirections action  = AssignVehicleFragmentDirections.actionAssignVehicleFragmentToMonthlyTrackingFragment();
                navController.navigate(action);
            }

        });


    }


    @Override
    public void onResume() {
        super.onResume();
        appUtils.showLog("inside OnResume AssignVehicle",AssignVehicleFragment.class);

        testObject =TestObject.resetTheInstance();
        appUtils.showLog("object is null or not.."+testObject,AssignVehicleFragment.class);
    }
}

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
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.ui.login.AuthViewModelfactory;
import com.nrlm.agey.utils.AppConstant;
import com.nrlm.agey.utils.ViewUtilsKt;

import java.util.ArrayList;
import java.util.List;

public class AssignVehicleFragment extends BaseFragment<HomeViewModel,FragmentAssignVehicleBinding, HomeRepository,HomeViewModelFactory> {

    ArrayAdapter<String> loanSourceAdapter;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> numList = viewModel.getRegistredNumber();
        loanSourceAdapter =new ArrayAdapter<String>(getContext(), R.layout.spinner_text, numList);
        binding.spinnerAssignVehicle.setAdapter(loanSourceAdapter);
        loanSourceAdapter.notifyDataSetChanged();

        binding.spinnerAssignVehicle.setOnItemClickListener((adapterView, view1, i, l) -> {
            String regNum = numList.get(i);
            AssignVehicleDataEntity assignVehicleDataEntity = viewModel.getVehicleData(regNum);
            binding.setAssignVehicleModel(assignVehicleDataEntity);
            ViewUtilsKt.tost(getContext(),regNum);
        });


        binding.tvClick.setOnClickListener(view1 -> {
            NavDirections action = AssignVehicleFragmentDirections.actionAssignVehicleFragmentToHomeTestFragment();
            navController.navigate(action);

        });
    }
}

package com.nrlm.agey.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.nrlm.agey.HomeNavGraphDirections;
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
   MenuItem menuItem;
    TextView badgeTv;

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

        setHasOptionsMenu(true);
        appSharedPreferences.setMpinCount("3");

        checkNotificationStatus();


        binding.spinnerAssignVehicle.setOnItemClickListener((adapterView, view1, i, l) -> {
            regNum = viewModel.getRegistredNumber().get(i);
            if(regNum.equalsIgnoreCase("Data Not Found")){
                ViewUtilsKt.tost(getContext(), regNum);
                regNum="";
            }else {
                binding.setAssignVehicleModel(viewModel.getVehicleData(regNum));
                appSharedPreferences.setVehicleRegNum(regNum);
                appSharedPreferences.setBlockCode(viewModel.getVehicleData(regNum).blockCode);
                appSharedPreferences.setStateShortName(viewModel.getUserDetailData().state_short_name);
                appSharedPreferences.setStateShortCode(viewModel.getUserDetailData().state_code);

                binding.etVehicleType.setText(viewModel.vechileType(viewModel.getVehicleData(regNum).vehicleType));
                binding.etManufactureOfVehicle.setText(viewModel.getManufacturer(viewModel.getVehicleData(regNum).vehicleManufacture));
                binding.etVehicleModel.setText(viewModel.getModel(viewModel.getVehicleData(regNum).vehicleManufacture, viewModel.getVehicleData(regNum).vehicleModel));

                ViewUtilsKt.tost(getContext(), regNum);

                testObject = TestObject.getInstance();
                appUtils.showLog("object is: " + testObject, AssignVehicleFragment.class);
                SampleObj sampleObj = new SampleObj();
                appUtils.showLog("object is Sample: " + sampleObj, AssignVehicleFragment.class);
            }
        });

        binding.btnGoToMonthelyTracking.setOnClickListener(view1 -> {
            if(regNum.equalsIgnoreCase("")||regNum.isEmpty()){
                ViewUtilsKt.tost(getContext(),getCurrentContext().getResources().getString(R.string.toast_home_select_vehicle));
            }else {
                NavDirections action  = AssignVehicleFragmentDirections.actionAssignVehicleFragmentToMonthlyTrackingFragment();
                navController.navigate(action);
            }
        });

    }

    private void checkNotificationStatus() {
        viewModel.getTrackingData();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        appUtils.showLog("inside fragment menu ",AssignVehicleFragment.class);

        menuItem = menu.findItem(R.id.item_about_app);
        SampleData.Companion.getNotificationCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==0){
                    appUtils.showLog("inside ifl"+integer,HomeActivity.class);
                    menuItem.setActionView(null);
                }else {
                    appUtils.showLog("inside else"+integer,HomeActivity.class);
                    menuItem.setActionView(R.layout.notification_badge);
                    View view = menuItem.getActionView();
                    badgeTv= view.findViewById(R.id.tv_badgeCounter);
                    badgeTv.setText(""+integer);

                    FrameLayout frameLayout = view.findViewById(R.id.top_layout);

                    frameLayout.setOnClickListener(view1 -> {
                        NavDirections action = HomeNavGraphDirections.actionGlobalAboutUsFragment();
                        navController.navigate(action);

                    });
                }
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean status = false;

        switch (item.getItemId()) {
            case R.id.settingFragment:
                status = NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
                break;
            case R.id.item_about_app:
                NavDirections action = HomeNavGraphDirections.actionGlobalAboutUsFragment();
                navController.navigate(action);
                status = true;
                break;
            case R.id.logOut:
                navController.navigate(R.id.logoutDialog);
                status = true;
                break;
        }
        return status;
    }*/

    @Override
    public void onResume() {
        super.onResume();
        appUtils.showLog("inside OnResume AssignVehicle",AssignVehicleFragment.class);

        testObject =TestObject.resetTheInstance();
        appUtils.showLog("object is null or not.."+testObject,AssignVehicleFragment.class);

        loanSourceAdapter =new ArrayAdapter<String>(getContext(), R.layout.spinner_text, viewModel.getRegistredNumber());
        binding.spinnerAssignVehicle.setAdapter(loanSourceAdapter);
        loanSourceAdapter.notifyDataSetChanged();

        /*AssignVehicleDataEntity assignVehicleDataEntity = new AssignVehicleDataEntity();
        binding.setAssignVehicleModel(assignVehicleDataEntity);*/

        binding.spinnerAssignVehicle.setText("");

        binding.etVehicleType.setText("");
        binding.etManufactureOfVehicle.setText("");
        binding.etVehicleModel.setText("");
        regNum="";



    }
}

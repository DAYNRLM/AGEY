package com.nrlm.agey.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;

import com.nrlm.agey.HomeNavGraphDirections;
import com.nrlm.agey.R;
import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.databinding.FragmentMonthlyTrackingBinding;
import com.nrlm.agey.databinding.FragmentMonthlyTrackingTwoBinding;
import com.nrlm.agey.model.SampleObj;
import com.nrlm.agey.model.TestObject;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.utils.SampleData;
import com.nrlm.agey.utils.ViewUtilsKt;

public class MonthlyTrackingFragmentTwo extends BaseFragment<HomeViewModel, FragmentMonthlyTrackingTwoBinding, HomeRepository,HomeViewModelFactory> {

    TestObject testObject;
    ArrayAdapter<String> daysAdapter;
    ArrayAdapter<String> yesnoAdapter;
    ArrayAdapter<String> reasonAdapter;
    ArrayAdapter<String> assesmentAdapter;
    MenuItem menuItem;
    TextView badgeTv;
    @Override
    public Class<HomeViewModel> getViewModel() {
        return HomeViewModel.class;
    }

    @Override
    public FragmentMonthlyTrackingTwoBinding getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentMonthlyTrackingTwoBinding.inflate(inflater, container, false);
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
    public void onResume() {
        super.onResume();
        daysAdapter =new ArrayAdapter<String>(requireContext(), R.layout.spinner_text,viewModel.getDaysList());
        daysAdapter.setDropDownViewResource(R.layout.spinner_text);
        binding.spinnerSpecialTrip.setAdapter(daysAdapter);
        binding.spinnerDaysRunInMonth.setAdapter(daysAdapter);
        binding.spinnerAvgdaysTrip.setAdapter(daysAdapter);
        binding.spinnerVillageTrip.setAdapter(daysAdapter);
        daysAdapter.notifyDataSetChanged();

        yesnoAdapter =new ArrayAdapter<String>(requireContext(), R.layout.spinner_text,viewModel.getYesNoList());
        yesnoAdapter.setDropDownViewResource(R.layout.spinner_text);
        binding.spinnerPreDefiend.setAdapter(yesnoAdapter);
        binding.spinnerRenewalTax.setAdapter(yesnoAdapter);
        binding.spinnerInsuranceRenewed.setAdapter(yesnoAdapter);
        binding.spinnerVehicleOperational.setAdapter(yesnoAdapter);
        yesnoAdapter.notifyDataSetChanged();

        reasonAdapter =new ArrayAdapter<String>(requireContext(), R.layout.spinner_text,viewModel.getReasonList());
        reasonAdapter.setDropDownViewResource(R.layout.spinner_text);
        binding.spinnerNotOperation.setAdapter(reasonAdapter);
        reasonAdapter.notifyDataSetChanged();

        assesmentAdapter =new ArrayAdapter<String>(requireContext(), R.layout.spinner_text,viewModel.getAssessmentList());
        assesmentAdapter.setDropDownViewResource(R.layout.spinner_text);
        binding.spinnerAssessment.setAdapter(assesmentAdapter);
        assesmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        testObject =TestObject.getInstance();
        binding.setTestObj(testObject);
        setHasOptionsMenu(true);
        viewModel.getTrackingData();
        appUtils.showLog("re******"+appSharedPreferences.getVehicleRegNum(),MonthlyTrackingFragmentTwo.class);

        binding.tvRegNumber.setText(appSharedPreferences.getVehicleRegNum());

        AssignVehicleDataEntity vehicleObject = viewModel.getVehicleData(appSharedPreferences.getVehicleRegNum());

        if(vehicleObject.vehicleCategory.equalsIgnoreCase("P")){
            hidelayoutForGood();
        }else {
            hideLayoutForPessenger();
        }


        setListner();
        SampleObj sampleObj = new SampleObj();
        appUtils.showLog("object is Sample: "+sampleObj,AssignVehicleFragment.class);

        binding.btnNext.setOnClickListener(view1 -> {
            testObject.taxAmount =binding.etTaxAmount.getText().toString();
            if(testObject.vehicleRunningPredefiendRoute.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_pre_define_route));
            }else if(testObject.renewalOfRoadTax.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_text_renewal));
            }else if(testObject.isvehicleOperational.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_vehicle_option));
            }else {
                NavDirections action = MonthlyTrackingFragmentTwoDirections.actionMonthlyTrackingFragmentTwo2ToMonthlyTrackingFragmentThree();
                navController.navigate(action);
            }
        });
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

    private void setListner() {

        binding.spinnerSpecialTrip.setOnItemClickListener((adapterView, view, i, l) -> {
           testObject.numberOftripForStudent= viewModel.getDaysList().get(i);

        });
        binding.spinnerDaysRunInMonth.setOnItemClickListener((adapterView, view, i, l) -> {
            testObject.numberOfDaysRun= viewModel.getDaysList().get(i);

        });
        binding.spinnerAvgdaysTrip.setOnItemClickListener((adapterView, view, i, l) -> {
            testObject.numberOfTripPerDay= viewModel.getDaysList().get(i);

        });
        binding.spinnerVillageTrip.setOnItemClickListener((adapterView, view, i, l) -> {
            testObject.numberOfVillage= viewModel.getDaysList().get(i);

        });


        //by deafuly cardview visibily is gone
        binding.spinnerPreDefiend.setOnItemClickListener((adapterView, view, i, l) -> {
            testObject.vehicleRunningPredefiendRoute= viewModel.getYesorNoInitial(viewModel.getYesNoList().get(i));
            if(testObject.vehicleRunningPredefiendRoute.equalsIgnoreCase("N")){
                binding.cardViewPreDefiend.setVisibility(View.GONE);
            }else {
                binding.cardViewPreDefiend.setVisibility(View.VISIBLE);
            }

        });
        binding.spinnerRenewalTax.setOnItemClickListener((adapterView, view, i, l) -> {
            testObject.renewalOfRoadTax= viewModel.getYesorNoInitial(viewModel.getYesNoList().get(i));

        });
        binding.spinnerInsuranceRenewed.setOnItemClickListener((adapterView, view, i, l) -> {
            testObject.insuranceRenewed= viewModel.getYesorNoInitial(viewModel.getYesNoList().get(i));

        });

        binding.spinnerVehicleOperational.setOnItemClickListener((adapterView, view, i, l) -> {
            testObject.isvehicleOperational= viewModel.getYesorNoInitial(viewModel.getYesNoList().get(i));
        });


        binding.spinnerNotOperation.setOnItemClickListener((adapterView, view, i, l) -> {
            testObject.ifNotReason= viewModel.getReasonList().get(i);
        });

        binding.spinnerAssessment.setOnItemClickListener((adapterView, view, i, l) -> {
            testObject.assesmentForClfVo= viewModel.getAssessmentList().get(i);
        });


    }

    private void  hideLayoutForPessenger() {
        binding.llVillageTrip.setVisibility(View.GONE);

    }

    private void hidelayoutForGood() {
        binding.ttlSpecialTrip.setVisibility(View.GONE);
        binding.llDaysRunInMonth.setVisibility(View.GONE);
        binding.llAvgdaysTrip.setVisibility(View.GONE);
    }
}

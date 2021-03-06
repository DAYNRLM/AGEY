package com.nrlm.agey.ui.home;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavArgs;
import androidx.navigation.NavDirections;

import com.nrlm.agey.HomeNavGraphDirections;
import com.nrlm.agey.R;
import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.CategoryEntity;
import com.nrlm.agey.databinding.FragmentMonthlyTrackingBinding;
import com.nrlm.agey.model.TestObject;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.utils.SampleData;
import com.nrlm.agey.utils.ViewUtilsKt;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthlyTrackingFragment extends BaseFragment<HomeViewModel, FragmentMonthlyTrackingBinding, HomeRepository, HomeViewModelFactory> {
    TestObject testObject;
    ArrayAdapter<String> categoryAdapter;
    MenuItem menuItem;
    TextView badgeTv;


    @Override
    public Class<HomeViewModel> getViewModel() {
        return HomeViewModel.class;
    }

    @Override
    public FragmentMonthlyTrackingBinding getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentMonthlyTrackingBinding.inflate(inflater, container, false);
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
        return new HomeViewModelFactory(getFragmentRepository());
    }

    @Override
    public void onFragmentReady() {
        appUtils.showLog("****onfragment***ready",MonthlyTrackingFragment.class);
    }

    @Override
    public void onResume() {
        super.onResume();

        categoryAdapter =new ArrayAdapter<String>(requireContext(), R.layout.spinner_text,viewModel.getSelectedCategory() );
        categoryAdapter.setDropDownViewResource(R.layout.spinner_text);
        binding.spinnerSelectCat.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appUtils.showLog("****on create********",MonthlyTrackingFragment.class);
        setHasOptionsMenu(true);
        viewModel.getTrackingData();


        testObject =TestObject.getInstance();
        binding.setTestObj(testObject);
        binding.setViewModel(viewModel);
        binding.tvRegNumber.setText(appSharedPreferences.getVehicleRegNum());

        AssignVehicleDataEntity assignVehicle = viewModel.getVehicleData(appSharedPreferences.getVehicleRegNum());
        binding.setAssignVehicleModel(assignVehicle);

        loadData();
        loadCalender();
        loadTotalKm();

        binding.spinnerSelectCat.setOnItemClickListener((adapterView, view1, i, l) -> {
           /* if(viewModel.getAllcategory().get(i).equalsIgnoreCase("Goods")){
                testObject.cat_of_vehicle = "G";
            }else {
                testObject.cat_of_vehicle = "P";
            }*/

            String value = viewModel.getSelectedCategory().get(i).equalsIgnoreCase("Goods")?"G":"P";
            testObject.cat_of_vehicle=value;
        });




        binding.btnNext.setOnClickListener(view1 -> {
            testObject.openingKm =binding.etOpeningKm.getText().toString();
            testObject.clossingKm =binding.etClosingKm.getText().toString();

            String catVehicle =testObject.cat_of_vehicle;
            String trackingYear =testObject.tracking_year;
            String trackingMonth =testObject.tracking_month;
            String openingKm =binding.etOpeningKm.getText().toString();
            String closingKm =binding.etClosingKm.getText().toString();
            String totalKm =binding.etTotlKm.getText().toString();
            String amountPaid =binding.etAmountRepaid.getText().toString();
            String balanceAmount =binding.etBlancedAmount.getText().toString();
            String noOfEmi =binding.etNoOfEmi.getText().toString();
            String amountOverDue =binding.etAmountOverdue.getText().toString();
            String monthlyIncome =binding.etMonthlyIncome.getText().toString();

            if(catVehicle.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_select_cat));
            }else if (trackingYear.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_select_year));

            }else if(trackingMonth.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_select_month));

            }else if(openingKm.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_opening_km));

            }else if(closingKm.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_closing_km));

            }else if(totalKm.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_enter_totak_km));

            }else if(amountPaid.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_enter_repaid_amount));

            }else if(balanceAmount.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_enter_blnce_amount));

            }else if (monthlyIncome.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_enter_agey_income));

            }else {
                testObject.openingKm=openingKm;
                testObject.clossingKm=closingKm;
                testObject.totall_km=totalKm;
                testObject.amountRepaidInCurrentMonth=amountPaid;
                testObject.blancedLoanAmount=balanceAmount;
                testObject.noOfEmiDue=noOfEmi;
                testObject.amountOverDue=amountOverDue;
                testObject.netIncomeFromAegy =monthlyIncome;
                NavDirections action = MonthlyTrackingFragmentDirections.actionMonthlyTrackingFragmentToMonthlyTrackingFragmentTwo2();
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


    private void loadTotalKm() {

        String openingKm =viewModel.getLstMonthClosingKm(appSharedPreferences.getVehicleRegNum());
       // String openingKm ="";
        if(!openingKm.equalsIgnoreCase("")) {
            binding.etOpeningKm.setInputType(InputType.TYPE_NULL);
            binding.etOpeningKm.setText(openingKm);
            setFocsChangListner();
        }else {
           // int opening = Integer.parseInt(binding.etOpeningKm.getText().toString());
            setFocsChangListner();

        }
    }

    public void setFocsChangListner(){

        binding.etClosingKm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    if(binding.etOpeningKm.getText().toString().isEmpty()){
                        ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_opening_km_first));
                    }
                }else {

                    if(binding.etOpeningKm.getText().toString().isEmpty()){
                        ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_opening_km_first));
                        binding.etClosingKm.setText("");
                    }else {
                        int opening = Integer.parseInt(binding.etOpeningKm.getText().toString());
                        int closing = Integer.parseInt(binding.etClosingKm.getText().toString());

                        if(closing<opening){
                            ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_more_then));
                            binding.etClosingKm.setText("");
                        }else {
                            int result =closing-opening;
                            binding.etTotlKm.setText(""+result);
                        }
                    }
                }
            }
        });

    }

    private void loadKmReadings() {
        String openingKm ="2000";
        if(!openingKm.equalsIgnoreCase("")) {
            binding.etOpeningKm.setInputType(InputType.TYPE_NULL);
            binding.etOpeningKm.setText(openingKm);
            notClosingKm();
        }else {
            int opening = Integer.parseInt(binding.etOpeningKm.getText().toString());


        }






    }

    private void notClosingKm() {
        binding.etClosingKm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!binding.etClosingKm.getText().toString().isEmpty()){
                    if(binding.etOpeningKm.getText().toString().isEmpty()){
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_error_opening_km_first));
                                binding.etClosingKm.setText("");
                            }
                        }, 1000);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
              int opening = Integer.parseInt(binding.etOpeningKm.getText().toString());
              int closing = Integer.parseInt(binding.etClosingKm.getText().toString());

              if(!binding.etClosingKm.getText().toString().isEmpty()){
                  if(closing<opening){
                      Handler handler = new Handler();
                      handler.postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              ViewUtilsKt.tost(getCurrentContext(),"Closing KM is not less then Opening Km");
                              binding.etClosingKm.setText("");
                          }
                      }, 5000);
                  }else {
                      int result =opening-closing;
                      binding.etTotlKm.setText(""+result);
                  }

              }
            }
        });
    }


    private void loadCalender() {
        Calendar today = Calendar.getInstance();
        binding.etSelectMonth.setOnClickListener(view1 -> {
           // ViewUtilsKt.tost(getCurrentContext(),"hiiiii");

            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getCurrentContext(), new MonthPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int selectedMonth, int selectedYear) {

                    SimpleDateFormat month_date = new SimpleDateFormat("MMM");
                    today.set(Calendar.MONTH,selectedMonth);
                    String month_name = month_date.format(today.getTime());


                    binding.etSelectMonth.setText(month_name);
                    binding.etSelectYear.setText(""+selectedYear);

                    testObject.tracking_year=""+selectedYear;
                    testObject.tracking_month=""+selectedMonth;

                }
            }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

            //.setMinMonth(Calendar.FEBRUARY)
            builder.setActivatedMonth(today.get(Calendar.MONTH))
                    .setMinYear(1990)
                    .setActivatedYear(today.get(Calendar.YEAR))
                    .setMaxYear(today.get(Calendar.YEAR))
                    .setTitle("Select Month")
                    .setMonthRange(Calendar.JANUARY, today.get(Calendar.MONTH)).build().show();
        });

    }


    private void loadData() {

        if(!testObject.cat_of_vehicle.isEmpty()){
            if(testObject.cat_of_vehicle.equalsIgnoreCase("G")){
                binding.spinnerSelectCat.setText("Good");

            }else{
                binding.spinnerSelectCat.setText("Passenger");
            }
        }

        if(!testObject.tracking_month.isEmpty()){

        }

    }
}




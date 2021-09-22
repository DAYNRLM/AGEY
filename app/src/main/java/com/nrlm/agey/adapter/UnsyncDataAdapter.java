package com.nrlm.agey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nrlm.agey.R;
import com.nrlm.agey.database.entity.MonthlyTrackingDataEntity;
import com.nrlm.agey.databinding.CustomAdapterLanguageLayoutBinding;
import com.nrlm.agey.databinding.CustomDapterUnsyncdataLayoutBinding;
import com.nrlm.agey.network.vollyCall.VolleyResult;
import com.nrlm.agey.network.vollyCall.VolleyService;
import com.nrlm.agey.ui.home.AboutUsFragmentDirections;
import com.nrlm.agey.ui.home.HomeViewModel;
import com.nrlm.agey.utils.AppUtils;
import com.nrlm.agey.utils.Cryptography;
import com.nrlm.agey.utils.CustomProgressDialog;
import com.nrlm.agey.utils.PrefrenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static com.nrlm.agey.network.vollyCall.VolleyService.volleyService;

public class UnsyncDataAdapter extends RecyclerView.Adapter<UnsyncDataAdapter.MyViewHolder> {

    List<MonthlyTrackingDataEntity> daataItem;
    Context context;
    NavController navController;
    HomeViewModel homeViewModel;
    CustomProgressDialog customProgressDialog;
    AppUtils appUtils;
    VolleyResult mResultCallBack = null;
    VolleyService volleyService;

    public UnsyncDataAdapter(List<MonthlyTrackingDataEntity> daataItem, Context context, NavController navController, HomeViewModel homeViewModel) {
        this.daataItem = daataItem;
        this.context = context;
        this.navController = navController;
        this.homeViewModel = homeViewModel;
        customProgressDialog = CustomProgressDialog.newInstance(context);
        appUtils = AppUtils.getInstance();
        volleyService = VolleyService.getInstance(context);
    }

    @NonNull
    @Override
    public UnsyncDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CustomDapterUnsyncdataLayoutBinding rootView = CustomDapterUnsyncdataLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new UnsyncDataAdapter.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull UnsyncDataAdapter.MyViewHolder holder, int position) {
        holder.itemBinding.tvRegNumber.setText(daataItem.get(position).userID);
        holder.itemBinding.tvUserId.setText(daataItem.get(position).userID);
        holder.itemBinding.tvYear.setText(daataItem.get(position).tracking_year);

        Calendar today = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        today.set(Calendar.MONTH, Integer.parseInt(daataItem.get(position).tracking_month));
        String month_name = month_date.format(today.getTime());
        holder.itemBinding.tvMonth.setText(month_name);


        holder.itemBinding.btnSyncdata.setOnClickListener(view -> {
            customProgressDialog.showProgress(context.getResources().getString(R.string.dialog_loading), false);
            try {
                JSONObject jsonObject =homeViewModel.getSyncObject(daataItem.get(position).id);

                JSONObject encryptedObject =new JSONObject();
                try {
                    Cryptography cryptography = new Cryptography();

                    encryptedObject.accumulate("data",cryptography.encrypt(jsonObject.toString()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                mResultCallBack = new VolleyResult() {
                    @Override
                    public void notifySuccess(String requestType, JSONObject response) {
                        customProgressDialog.hideProgress();
                        try {
                            if (response.has("data_Sync")) {
                                String data_Sync = response.getString("data_Sync");
                                if (data_Sync.equalsIgnoreCase("Success")) {
                                    new MaterialAlertDialogBuilder(context).setTitle(context.getResources().getString(R.string.dialog_adapter_title)).setIcon(R.drawable.ic_baseline_signal_wifi_connected_no_internet)
                                            .setMessage(context.getResources().getString(R.string.dialog_data_saved))
                                            .setPositiveButton(context.getResources().getString(R.string.dialog_ok_btn),(dialogInterface, i) -> {
                                                dialogInterface.dismiss();
                                                homeViewModel.deleteTrackingObject(daataItem.get(position).id);
                                                NavDirections action = AboutUsFragmentDirections.actionAboutUsFragmentToAssignVehicleFragment();
                                                navController.navigate(action);
                                            }).show();

                                } else {
                                    new MaterialAlertDialogBuilder(context).setTitle(context.getResources().getString(R.string.dialog_duplicate_data)).setIcon(R.drawable.ic_baseline_signal_wifi_connected_no_internet)
                                            .setMessage(context.getResources().getString(R.string.dialog_duplicate_data_msg))
                                            .setPositiveButton(context.getResources().getString(R.string.dialog_ok_btn),(dialogInterface, i) -> {
                                                dialogInterface.dismiss();
                                                homeViewModel.deleteTrackingObject(daataItem.get(position).id);
                                                NavDirections action = AboutUsFragmentDirections.actionAboutUsFragmentToAssignVehicleFragment();
                                                navController.navigate(action);
                                            }).show();
                                }
                            } else {

                            }

                        } catch (Exception e) {
                            customProgressDialog.hideProgress();
                            appUtils.showLog("Expection in response"+e.toString(),UnsyncDataAdapter.class);
                        }
                    }

                    @Override
                    public void notifyError(String requestType, VolleyError error) {
                        customProgressDialog.hideProgress();
                        appUtils.showLog("VOLLEY ERROR"+error.toString(),UnsyncDataAdapter.class);
                    }
                };
               // volleyService.postDataVolley("synData", "https://nrlm.gov.in/nrlmwebservicedemo/services/ageysync/data", jsonObject, mResultCallBack);
                volleyService.postDataVolley("synData", PrefrenceManager.SYNC_URL, encryptedObject, mResultCallBack);
            } catch (Exception e) {
                appUtils.showLog("Expection in Main JSON"+e.toString(),UnsyncDataAdapter.class);
            }

        });
    }

    @Override
    public int getItemCount() {
        return daataItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomDapterUnsyncdataLayoutBinding itemBinding;

        public MyViewHolder(@NonNull CustomDapterUnsyncdataLayoutBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }
    }
}

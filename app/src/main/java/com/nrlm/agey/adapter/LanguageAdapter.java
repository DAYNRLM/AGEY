package com.nrlm.agey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nrlm.agey.R;
import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.databinding.CustomAdapterLanguageLayoutBinding;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.ui.home.ChangeLanguageFragment;
import com.nrlm.agey.ui.home.ChangeLanguageFragmentDirections;
import com.nrlm.agey.ui.login.ForgetPasswordFragmentDirections;
import com.nrlm.agey.utils.AppSharedPreferences;
import com.nrlm.agey.utils.AppUtils;


import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyViewHolder> {
    List<LanguageEntity> dataItem;
    Context context;
    NavController navController;
    AppSharedPreferences appSharedPreferences;
    AppUtils appUtilsl;

    public LanguageAdapter(List<LanguageEntity> dataItem, Context context, NavController navController) {
        this.dataItem = dataItem;
        this.context = context;
        this.navController = navController;
        appSharedPreferences = AppSharedPreferences.getInstance(context);
        appUtilsl = AppUtils.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomAdapterLanguageLayoutBinding rootView = CustomAdapterLanguageLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.iteBbinding.tvLangName.setText(dataItem.get(position).name);
        holder.iteBbinding.tvLangLocalName.setText(dataItem.get(position).localName);
        holder.iteBbinding.getRoot().setOnClickListener(view -> {

            if (dataItem.get(position).languageCode.equalsIgnoreCase("hi") ||
                    dataItem.get(position).languageCode.equalsIgnoreCase("pa") ||
                    dataItem.get(position).languageCode.equalsIgnoreCase("en")) {

                new MaterialAlertDialogBuilder(context).setTitle(context.getResources().getString(R.string.dialog_language_title)).setIcon(R.drawable.ic_baseline_change_circle_24)
                        .setMessage(context.getResources().getString(R.string.dialog_language_msg) +" " + dataItem.get(position).localName)
                        .setPositiveButton(context.getResources().getString(R.string.dialog_yes_btn), (dialogInterface, i) -> {
                            dialogInterface.dismiss();

                            appSharedPreferences.setLanguageCode(dataItem.get(position).languageCode);
                            appUtilsl.setLocale(dataItem.get(position).languageCode, context.getResources());

                            NavDirections action = ChangeLanguageFragmentDirections
                                    .actionChangeLanguageFragmentToAssignVehicleFragment();
                            navController.navigate(action);

                        }).setNegativeButton(context.getResources().getString(R.string.dialog_no_btn), (dialogInterface, i) -> {
                    NavDirections action = ChangeLanguageFragmentDirections
                            .actionChangeLanguageFragmentToAssignVehicleFragment();
                    navController.navigate(action);
                }).show();

            } else {
                new MaterialAlertDialogBuilder(context).setTitle(context.getResources().getString(R.string.dialog_language_title)).setIcon(R.drawable.ic_baseline_change_circle_24)
                        .setMessage("Sorry.."+dataItem.get(position).localName+" language is not implemented in this app")
                        .setPositiveButton(context.getResources().getString(R.string.dialog_ok_btn), (dialogInterface, i) -> {
                            dialogInterface.dismiss();

                            NavDirections action = ChangeLanguageFragmentDirections
                                    .actionChangeLanguageFragmentToAssignVehicleFragment();
                            navController.navigate(action);

                        }).show();

            }


        });
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomAdapterLanguageLayoutBinding iteBbinding;

        public MyViewHolder(@NonNull CustomAdapterLanguageLayoutBinding itemView) {
            super(itemView.getRoot());
            iteBbinding = itemView;
        }
    }
}

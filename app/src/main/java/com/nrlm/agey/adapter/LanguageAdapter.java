package com.nrlm.agey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.databinding.CustomAdapterLanguageLayoutBinding;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.ui.home.ChangeLanguageFragment;
import com.nrlm.agey.ui.home.ChangeLanguageFragmentDirections;


import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyViewHolder> {
    List<LanguageEntity> dataItem;
    Context context;
    NavController navController;

    public LanguageAdapter(List<LanguageEntity> dataItem, Context context,NavController navController) {
        this.dataItem = dataItem;
        this.context = context;
        this.navController = navController;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomAdapterLanguageLayoutBinding rootView =CustomAdapterLanguageLayoutBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.iteBbinding.tvLangName.setText(dataItem.get(position).name);
        holder.iteBbinding.tvLangLocalName.setText(dataItem.get(position).localName);
        holder.iteBbinding.getRoot().setOnClickListener(view -> {
            NavDirections action = ChangeLanguageFragmentDirections
                    .actionChangeLanguageFragmentToAssignVehicleFragment();
            navController.navigate(action);
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
            iteBbinding=itemView;
        }
    }
}

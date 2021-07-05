package com.nrlm.agey.bindingadapters;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.google.android.material.textfield.TextInputEditText;
import com.nrlm.agey.R;

import java.text.NumberFormat;
import java.util.Locale;

public class BindingAdapterUtils {

    @BindingAdapter("priceText")
    public static void setPriceText(TextInputEditText view, String amount){
        String value = amount==null ? "" :amount+ " "+view.getContext().getResources().getString(R.string.Rs_new);
        view.setText(value);
    }
}

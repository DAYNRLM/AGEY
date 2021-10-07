package com.nrlm.agey.testinterface;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class TestClass {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getData(){
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        numbers.add(5);
        numbers.add(9);
        numbers.add(8);
        numbers.add(1);
        numbers.forEach( (n) -> { System.out.println(n); } );

    }
}

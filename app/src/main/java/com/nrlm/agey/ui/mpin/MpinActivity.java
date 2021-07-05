package com.nrlm.agey.ui.mpin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.nrlm.agey.R;
import com.nrlm.agey.utils.AppUtils;

public class MpinActivity extends AppCompatActivity {

    public static final String abc ="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpin);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        NavInflater inflater = navController.getNavInflater();
        NavGraph graph = inflater.inflate(R.navigation.mpin_nav_graph);

        //set condition for shared preference ...and show fragment
        if(abc.equalsIgnoreCase("2")){
            graph.setStartDestination(R.id.verifyMpinFragment);
        }else {
            graph.setStartDestination(R.id.setMpinFragment2);
        }

        navController.setGraph(graph);
    }
}
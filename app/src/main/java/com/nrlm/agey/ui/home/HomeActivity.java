package com.nrlm.agey.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.nrlm.agey.HomeNavGraphDirections;
import com.nrlm.agey.R;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.ui.SplashScreenActivity;
import com.nrlm.agey.ui.login.AuthActivity;
import com.nrlm.agey.utils.AppSharedPreferences;
import com.nrlm.agey.utils.AppUtils;
import com.nrlm.agey.utils.CustomProgressDialog;
import com.nrlm.agey.utils.ViewUtilsKt;

public class HomeActivity extends AppCompatActivity {

    NavigationView navigation_view;
    DrawerLayout home_drawer_layout;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    Toolbar tollBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tollBar = findViewById(R.id.tollBar);
        navigation_view = findViewById(R.id.navigation_view);
        home_drawer_layout = findViewById(R.id.home_drawer_layout);

        setSupportActionBar(tollBar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.home_nav_host);
        navController = navHostFragment.getNavController();

        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(home_drawer_layout).build();

        /*AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(home_drawer_layout)
                        .build();*/


        NavigationUI.setupActionBarWithNavController(HomeActivity.this, navController, appBarConfiguration);
        // NavigationUI.setupWithNavController(tollBar,navController);

        NavigationUI.setupWithNavController(navigation_view, navController);
        //cntl+p

      /* navigation_view.setNavigationItemSelectedListener(item -> {
           boolean status=false;
           if(item.getItemId()==R.id.logOut_drawer){
               ViewUtilsKt.tost(this,"hiiiiiiiii");
               status=true;
           }else {
               status=false;
           }
           return status;
       });*/
    }

    @Override
    public boolean onSupportNavigateUp() {

        // return navController.navigateUp()|| super.onSupportNavigateUp();
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(HomeActivity.this);
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
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
    }

}
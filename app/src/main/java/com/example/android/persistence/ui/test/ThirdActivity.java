package com.example.android.persistence.ui.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.persistence.R;
import com.example.android.persistence.ui.ProductListFragment;

public class ThirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.
        setContentView(R.layout.third_activity);
        /*MyViewModel model = new ViewModelProvider(this).get(MyViewModel.class);
        Log.i(ProductListFragment.TAG,  "ThirdActivity model:"+model);
        model.getUsers().observe(this, users -> {
            // update UI
            TextView tv = findViewById(R.id.content);
            tv.setText("load user size = " + users.size());
        });*/
        ThirdListFragment leftFragment = new ThirdListFragment();
        ThirdDetailFragment rightFragment = new ThirdDetailFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.left, leftFragment).add(R.id.right, rightFragment).commit();


    }


}



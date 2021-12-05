package com.example.android.persistence.ui.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.persistence.R;
import com.example.android.persistence.ui.ProductListFragment;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.
        setContentView(R.layout.second_activity);

        // 第二个activity 与 第三个获取到viewModel 获取到的viewmodel不是同一个并且
        // liveData<User> 也不是同一个哦
        MyViewModel model = new ViewModelProvider(this).get(MyViewModel.class);
        Log.i(ProductListFragment.TAG,  "SecondActivity model:"+model);
        model.getUsers().observe(this, users -> {
            // update UI
            TextView tv = findViewById(R.id.content);
            tv.setText("load user size = " + users.size());
        });
    }

    public void jumThirdActivity(View view) {
        startActivity(new Intent(getApplicationContext(), ThirdActivity.class));
    }
}



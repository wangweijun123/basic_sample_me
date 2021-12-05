package com.example.android.persistence.ui.test;

import android.app.Application;
import android.util.Log;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.persistence.ui.ProductListFragment;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends ViewModel {

    private MutableLiveData<List<User>> users;

    public LiveData<List<User>> getUsers() {
        if (users == null) {
            Log.i(ProductListFragment.TAG, this + "users is null");
            users = new MutableLiveData<>();
            loadUsers();
        } else {
            Log.i(ProductListFragment.TAG, this + "users is :" + users);
        }
        return users;
    }

    @Override
    protected void onCleared() {
        Log.i(ProductListFragment.TAG, this + "  onCleared 清理保存的数据");
        super.onCleared();
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<User> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add(new User(i));
                }
                users.postValue(list);
            }
        }).start();
    }
}

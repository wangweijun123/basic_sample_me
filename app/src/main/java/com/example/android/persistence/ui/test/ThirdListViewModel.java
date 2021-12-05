package com.example.android.persistence.ui.test;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.persistence.db.entity.ProductEntity;
import com.example.android.persistence.ui.ProductListFragment;

import java.util.ArrayList;
import java.util.List;

public class ThirdListViewModel extends ViewModel {
    private final MutableLiveData<List<ProductEntity>> items;

    public ThirdListViewModel() {
        items = new MutableLiveData<>();
        loadItems();
    }

    private void loadItems() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(ProductListFragment.TAG, " 加载数据 ");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<ProductEntity> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add(new ProductEntity(i, "name:"+i, "desc:"+i, i*10));
                }
                items.postValue(list);
            }
        }).start();
    }

    public MutableLiveData<List<ProductEntity>> getItems() {
        return items;
    }

    public void setItems(List<ProductEntity> items) {
        this.items.postValue(items);
    }
}

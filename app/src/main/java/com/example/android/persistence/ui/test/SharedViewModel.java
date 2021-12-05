package com.example.android.persistence.ui.test;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.persistence.db.entity.ProductEntity;
import com.example.android.persistence.model.Product;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Product> selected = new MutableLiveData();

    public void select(Product item) {
        selected.setValue(item);
    }

    public LiveData<Product> getSelected() {
        return selected;
    }
}

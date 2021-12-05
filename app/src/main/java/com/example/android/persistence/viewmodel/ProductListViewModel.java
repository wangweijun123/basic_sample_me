/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.persistence.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;

import com.example.android.persistence.BasicApp;
import com.example.android.persistence.DataRepository;
import com.example.android.persistence.db.entity.ProductEntity;
import com.example.android.persistence.ui.ProductListFragment;
import com.example.android.persistence.ui.test.User;
import com.example.android.persistence.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * viewmodel管理着数据的存储, 同时处理与界面(Activity/Fragment)的交互
 */
public class ProductListViewModel extends AndroidViewModel {
    private static final String QUERY_KEY = "QUERY";

    private final SavedStateHandle mSavedStateHandler;
    private final DataRepository mRepository;
    private final MutableLiveData<List<ProductEntity>> mProducts;

    public ProductListViewModel(@NonNull Application application,
                                @NonNull SavedStateHandle savedStateHandle) {
        super(application);
        mSavedStateHandler = savedStateHandle;

        mRepository = ((BasicApp) application).getRepository();

        // Use the savedStateHandle.getLiveData() as the input to switchMap,
        // allowing us to recalculate what LiveData to get from the DataRepository
        // based on what query the user has entered
        /*mProducts = Transformations.switchMap(
                savedStateHandle.getLiveData("QUERY", null),
                (Function<CharSequence, LiveData<List<ProductEntity>>>) query -> {
                    if (TextUtils.isEmpty(query)) {
                        return mRepository.getProducts();
                    }
                    return mRepository.searchProducts("*" + query + "*");
                });*/

        mProducts = new MutableLiveData();
        Log.i(ProductListFragment.TAG, this + " create mProducts:" + mProducts);
        loadProducts();
    }

    private void loadProducts() {
        // Do an asynchronous operation to fetch users.
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
                mProducts.postValue(list);
            }
        }).start();
    }

    public void setQuery(CharSequence query) {
        // Save the user's query into the SavedStateHandle.
        // This ensures that we retain the value across process death
        // and is used as the input into the Transformations.switchMap above
        mSavedStateHandler.set(QUERY_KEY, query);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<ProductEntity>> getProducts() {
        return mProducts;
    }
}

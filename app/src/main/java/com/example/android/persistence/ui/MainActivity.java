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

package com.example.android.persistence.ui;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.persistence.R;
import com.example.android.persistence.model.Product;
import com.example.android.persistence.util.ThreadUtil;
import com.example.android.persistence.viewmodel.ProductListViewModel;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Log.i(ProductListFragment.TAG, "MainActivity savedInstanceState:" + savedInstanceState);
        // Add product list fragment if this is first creation
        if (savedInstanceState == null) {
            ProductListFragment fragment = new ProductListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("age", 100);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, ProductListFragment.TAG).commit();
        }
        test();
//        testArrayMap();
    }

    /** Shows the product detail fragment */
    public void show(Product product) {
        ProductFragment productFragment = ProductFragment.forProduct(product.getId());
//        ProductFragment2 productFragment = ProductFragment2.forProduct(product.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("product")
                .replace(R.id.fragment_container,
                        productFragment, null).commit();
    }

    private final MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
    private void test() {
        /**
         * Creates {@code ViewModelProvider}. This will create {@code ViewModels}
         * and retain them in a store of the given {@code ViewModelStoreOwner}.
         *
         *  ViewModelProvider ????????? viewMode, ??????????????????ViewModelStoreOwner
         *  ?????????????????? ViewmodelStoreOwner, Fragment ???Activity
         *  ViewModelStoreOwner ?????????viewmodel ??????, ?????????
         *  ComponentActivity ?????? ViewModelStore ??????
         *  ComponentActivity {
         *      private ViewModelStore mViewModelStore;
         *  }
         *
         *  ViewModelStore {
         *      HashMap<String, ViewModel> mMap;
         *  }
         *
         * --> ComponentActivity implement LifecycleOwner, ViewModelStoreOwner
         *
         *
         */
        // ?????????ViewModelStoreOwner ??????, Activity ??? fragment?????????ProductListViewModel???????????????
        // ???????????????????????????????????? ViewModel
        final ProductListViewModel viewModel =
                new ViewModelProvider(this).get(ProductListViewModel.class);
        Log.i(ProductListFragment.TAG, "MainActivity viewModel:" + viewModel);
        viewModel.getProducts().observe(this, myProducts -> {
            Log.i(ProductListFragment.TAG, "MainActivity myProducts :" + myProducts);
        });
        /*mutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i(ProductListFragment.TAG, "onChanged s:" + s);
            }
        });

        mutableLiveData.postValue("sssss");*/
    }


    public void testArrayMap() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayMap<String, String> map = new ArrayMap<>();
                for (int i = 0; i < 3; i++) {
                    map.put("key"+1, "val"+1);
                }
            }
        }).start();
    }
}

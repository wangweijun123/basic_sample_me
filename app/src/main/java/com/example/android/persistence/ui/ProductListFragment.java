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
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.persistence.R;
import com.example.android.persistence.databinding.ListFragmentBinding;
import com.example.android.persistence.db.entity.ProductEntity;
import com.example.android.persistence.util.ThreadUtil;
import com.example.android.persistence.viewmodel.ProductListViewModel;

import java.util.List;

public class ProductListFragment extends Fragment {

    public static final String TAG = "ProductListFragment";

    private ProductAdapter mProductAdapter;

    private ListFragmentBinding mBinding;

    public ProductListFragment() {}

    // test
    int age; // 配置变化,Fragment 参数丢失
    public ProductListFragment(int age) {
        this.age = age;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(ProductListFragment.TAG, "ProductListFragment onCreate: savedInstanceState:" + savedInstanceState);
        if (savedInstanceState != null) {
            age = savedInstanceState.getInt("age", -1);
            Log.i(ProductListFragment.TAG, "从savedInstanceState 获取age = " + age);
        }


        Bundle bundle = requireArguments();
        int age = bundle.getInt("age", -100);
        Log.i(ProductListFragment.TAG, "requireArguments 获取age = " + age);

        // databinding 作用: inflate 布局，而且把布局的控件保存起来,包括根view
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        // 在onCreateView中了设置适配器
        mProductAdapter = new ProductAdapter(mProductClickCallback);
        mBinding.productsList.setAdapter(mProductAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // ProductListViewModel 由ViewModelProvider反射生成,如果需要传参,需要自己实现factory
        // viewmodel 不是自己去new的, ViewModelProvider 提供

        // fragment is LifecycleOwner and ViewModelStoreOwner
        final ProductListViewModel viewModel =
                new ViewModelProvider(this).get(ProductListViewModel.class);

        mBinding.productsSearchBtn.setOnClickListener(v -> {
            Editable query = mBinding.productsSearchBox.getText();
            viewModel.setQuery(query);
        });

        subscribeUi(viewModel.getProducts());
    }

    private void subscribeUi(LiveData<List<ProductEntity>> liveData) {
        // Update the list when the data changes
        // getViewLifecycleOwner() 等价于 this
        liveData.observe(getViewLifecycleOwner(), myProducts -> {
             if (myProducts != null) {
                Log.i(TAG, "subscribeUi: 数据回来了 " + ThreadUtil.getThreadInfo());
                mBinding.setIsLoading(false);
                mProductAdapter.setProductList(myProducts);
            } else {
                Log.i(TAG, "subscribeUi: 数据 is null " + ThreadUtil.getThreadInfo());
                mBinding.setIsLoading(true);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }


    /**
     * 适合保存少量数据
     */
    /*@Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i(TAG, "onSaveInstanceState  ");
        outState.putInt("age", age);
    }*/

    /**
     *
     */
    @Override
    public void onDestroyView() {
        mBinding = null;
        mProductAdapter = null;
        super.onDestroyView();
    }

    private final ProductClickCallback mProductClickCallback = product -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) requireActivity()).show(product);
        }
    };
}

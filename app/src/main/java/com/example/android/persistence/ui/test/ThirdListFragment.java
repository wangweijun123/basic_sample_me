package com.example.android.persistence.ui.test;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.persistence.R;
import com.example.android.persistence.databinding.ThirdListFragmentBinding;
import com.example.android.persistence.db.entity.ProductEntity;
import com.example.android.persistence.ui.MainActivity;
import com.example.android.persistence.ui.ProductAdapter;
import com.example.android.persistence.ui.ProductClickCallback;
import com.example.android.persistence.ui.ProductListFragment;

import java.util.List;

public class ThirdListFragment extends Fragment {

    private SharedViewModel model;
    private ThirdListViewModel thirdListViewModel;
    ThirdListFragmentBinding binding;
    ProductAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.third_list_fragment, container, false);
        adapter = new ProductAdapter(mProductClickCallback);
        binding.productsList.setAdapter(adapter);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        thirdListViewModel = new ViewModelProvider(this).get(ThirdListViewModel.class);
        thirdListViewModel.getItems().observe(getViewLifecycleOwner(), items -> {
            Log.i(ProductListFragment.TAG, "observe items = " + items);
            adapter.setProductList(items);
        });
        binding.sendMsg2Right.setOnClickListener(clickView -> {
            ProductEntity item = new ProductEntity();
            Log.i(ProductListFragment.TAG, "clickView item = " + item);
            model.select(item);
        });
        /*itemSelector.setOnClickListener(item -> {
            model.select(item);
        });*/
    }

    @Override
    public void onDestroyView() {
        model = null;
        binding = null;
        super.onDestroyView();
    }


    private final ProductClickCallback mProductClickCallback = product -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            model.select(product);
        }
    };
}

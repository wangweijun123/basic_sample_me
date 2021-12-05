package com.example.android.persistence.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.persistence.R;
import com.example.android.persistence.databinding.ProductFragment2Binding;
import com.example.android.persistence.db.entity.CommentEntity;
import com.example.android.persistence.db.entity.ProductEntity;
import com.example.android.persistence.model.Comment;
import com.example.android.persistence.viewmodel.ProductViewModel2;

import java.util.List;

public class ProductFragment2 extends Fragment {
    private static final String KEY_PRODUCT_ID = "product_id";
    private ProductFragment2Binding binding;
    private CommentAdapter2 commentAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.product_fragment2, container, false);
        commentAdapter = new CommentAdapter2(commentClickCallback);
        binding.commentList.setAdapter(commentAdapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        int productId = requireArguments().getInt(KEY_PRODUCT_ID);
        ProductViewModel2.Factory factory = new ProductViewModel2.Factory(requireActivity()
                .getApplication(), productId);
        ProductViewModel2 viewModel2 = new ViewModelProvider(this, factory).get(ProductViewModel2.class);

        //  当在xml定义AndroidViewModel对象, 必须调用binding.setLifecycleOwner
//        binding.setLifecycleOwner(getViewLifecycleOwner());
//        binding.setProductViewModel(viewModel2);
        binding.setIsLoading(true);
        viewModel2.getProduct().observe(getViewLifecycleOwner(),
                productEntity -> binding.setProductEntry(productEntity));

        viewModel2.getComments().observe(getViewLifecycleOwner(), new Observer<List<CommentEntity>>() {
            @Override
            public void onChanged(List<CommentEntity> commentEntities) {
                if (commentEntities != null) {
                    binding.setIsLoading(false);
                    commentAdapter.submitList(commentEntities);
                } else {
                    binding.setIsLoading(true);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        commentAdapter = null;
        super.onDestroyView();
    }

    /** Creates product fragment for specific product ID */
    public static ProductFragment2 forProduct(int productId) {
        ProductFragment2 productFragment = new ProductFragment2();
        Bundle args = new Bundle();
        args.putInt(KEY_PRODUCT_ID, productId);
        productFragment.setArguments(args);
        return productFragment;
    }

    private final CommentClickCallback commentClickCallback = new CommentClickCallback() {

        @Override
        public void onClick(Comment comment) {
            Log.i(ProductListFragment.TAG, "ProductFragment2 comment: " + comment);
//            Toast.makeText(getContext(), )
        }
    };
}

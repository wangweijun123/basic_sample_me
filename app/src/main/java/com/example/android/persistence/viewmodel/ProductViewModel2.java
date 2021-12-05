package com.example.android.persistence.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.persistence.BasicApp;
import com.example.android.persistence.DataRepository;
import com.example.android.persistence.db.entity.CommentEntity;
import com.example.android.persistence.db.entity.ProductEntity;

import java.util.List;

public class ProductViewModel2 extends AndroidViewModel {
    final int mProductId;
    final DataRepository repository;
    private LiveData<ProductEntity> mObservableProduct;
    private final LiveData<List<CommentEntity>> mObservableComments;

    public ProductViewModel2(@NonNull Application application, DataRepository repository,
                             final int productId) {
        super(application);
        this.mProductId = productId;
        this.repository = repository;
        mObservableComments = repository.loadComments(mProductId);
        mObservableProduct = repository.loadProduct(mProductId);
    }


    /**
     * Expose the LiveData Comments query so the UI can observe it.
     */
    public LiveData<List<CommentEntity>> getComments() {
        return mObservableComments;
    }

    public LiveData<ProductEntity> getProduct() {
        return mObservableProduct;
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        final Application mApplication;
        private final int mProductId;
        private final DataRepository mRepository;
        public Factory(@NonNull Application application, int productId) {
            mApplication = application;
            mProductId = productId;
            mRepository = ((BasicApp) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProductViewModel2(mApplication, mRepository, mProductId);
        }
    }
}

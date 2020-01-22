package com.omise.tamboon.ui.listing;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.omise.tamboon.service.repository.TamboonService;
import com.omise.tamboon.ui.base.BaseViewModel;

import java.util.List;

public class CharityViewModel extends BaseViewModel {
    private TamboonService tamboonService;
    private MutableLiveData<List<Charity>> charityLiveData;

    private CharityViewModel(@NonNull TamboonService tamboonService) {
        this.tamboonService = tamboonService;
        charityLiveData = new MutableLiveData<>();
    }


    /**
     * A creator is used to inject the project ID into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final TamboonService tamboonService;

        public Factory(@NonNull TamboonService tamboonService) {
            this.tamboonService = tamboonService;
        }

        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(CharityViewModel.class)) {
                //noinspection unchecked
                return (T) new CharityViewModel(tamboonService);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

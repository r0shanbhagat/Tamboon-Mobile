package com.omise.tamboon.ui.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.omise.tamboon.service.repository.TamboonService;
import com.omise.tamboon.ui.base.BaseViewModel;
import com.omise.tamboon.ui.donation.model.DonationRequestModel;
import com.omise.tamboon.ui.donation.model.DonationResponse;
import com.omise.tamboon.ui.listing.Charity;
import com.omise.tamboon.ui.listing.CharityResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TamboonViewModel extends BaseViewModel {
    private TamboonService tamboonService;
    private MutableLiveData<List<Charity>> charityLiveData;
    private MutableLiveData<DonationResponse> donationLiveData;

    private TamboonViewModel(@NonNull TamboonService tamboonService) {
        this.tamboonService = tamboonService;
        charityLiveData = new MutableLiveData<>();
        donationLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Charity>> getCharityObservable() {
        return charityLiveData;
    }

    public LiveData<DonationResponse> getDonationObservable() {
        return donationLiveData;
    }

    public void getCharityList() {
        tamboonService.getCharityList().enqueue(new Callback<CharityResponse>() {
            @Override
            public void onResponse(@NotNull Call<CharityResponse> call, @NotNull Response<CharityResponse> response) {
                CharityResponse charityResponse = response.body();
                if (null != charityResponse) {
                    charityLiveData.setValue(charityResponse.getCharities());
                } else {
                    charityLiveData.setValue(Collections.<Charity>emptyList());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CharityResponse> call, @NotNull Throwable t) {
                charityLiveData.setValue(null);
            }
        });
    }

    public void updateDonation(DonationRequestModel requestModel) {
        tamboonService.updateDonation(requestModel).enqueue(new Callback<DonationResponse>() {
            @Override
            public void onResponse(@NotNull Call<DonationResponse> call, @NotNull Response<DonationResponse> response) {
                donationLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<DonationResponse> call, @NotNull Throwable t) {
                donationLiveData.setValue(null);
            }
        });
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
            if (modelClass.isAssignableFrom(TamboonViewModel.class)) {
                //noinspection unchecked
                return (T) new TamboonViewModel(tamboonService);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

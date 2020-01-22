package com.omise.tamboon.ui.listing;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.omise.tamboon.R;
import com.omise.tamboon.databinding.ActivityCharityListBinding;
import com.omise.tamboon.service.repository.TamboonRepository;
import com.omise.tamboon.ui.base.BaseActivity;
import com.omise.tamboon.ui.common.TamboonViewModel;
import com.omise.tamboon.ui.donation.DonationActivity;

public class CharityListActivity extends BaseActivity<TamboonViewModel> {

    private CharityAdapter charityAdapter;
    private ActivityCharityListBinding binding;


    @NonNull
    @Override
    protected TamboonViewModel createViewModel() {
        TamboonViewModel.Factory factory = new TamboonViewModel.Factory(TamboonRepository.getInstance().getTamboonService());
        return ViewModelProviders.of(this, factory).get(TamboonViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_charity_list);
        setSupportActionBar(binding.toolbar);//Insert Toolbar
        setupAdapter();
        observeViewModel(viewModel);
        viewModel.getCharityList(); //calling fetch charity api
    }

    private void setupAdapter() {
        binding.setIsLoading(true);
        charityAdapter = new CharityAdapter();
        binding.charityList.setAdapter(charityAdapter);
        charityAdapter.setListener((view, charity) -> {
            Intent donationIntent = new Intent(CharityListActivity.this, DonationActivity.class);
            donationIntent.putExtra(DonationActivity.KEY_NAME, charity.getName());
            startActivity(donationIntent);
        });
    }

    private void observeViewModel(TamboonViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getCharityObservable().observe(this, charity -> {
           /* binding.setIsLoading(false);
            binding.setIsListEmpty(null == charity || charity.isEmpty());
            charityAdapter.setItems(charity);*/

          /*  hideLoading()
            when (it) {
                is CustomerProfileData -> {
                    CustomerGlobalData.customerProfileData = it
                    customerMyAccountVM.userData = it
                    setUserData(it)
                    customerMyAccountVM.apiResponse.value = null
                }
                is ApiErrorModel -> {
                    CommonDialogsUtils.showApiErrorDialog(
                            requireActivity(),
                            it
                    )
                    customerMyAccountVM.apiResponse.value = null
                }
                is Throwable -> {
                    CommonDialogsUtils.showApiErrorDialog(
                            requireActivity(),
                            null
                    )
                    customerMyAccountVM.apiResponse.value = null
                }
            }*/
        });
    }
}

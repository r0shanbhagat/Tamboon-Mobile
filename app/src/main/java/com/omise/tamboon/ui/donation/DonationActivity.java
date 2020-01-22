package com.omise.tamboon.ui.donation;


import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.omise.tamboon.R;
import com.omise.tamboon.databinding.ActivityDonationBinding;
import com.omise.tamboon.service.repository.TamboonRepository;
import com.omise.tamboon.ui.base.BaseActivity;
import com.omise.tamboon.ui.common.TamboonViewModel;
import com.omise.tamboon.ui.donation.model.DonationRequestModel;
import com.omise.tamboon.ui.donation.model.DonationResponse;
import com.omise.tamboon.ui.success.SuccessFragment;
import com.omise.tamboon.utility.Utils;

public class DonationActivity extends BaseActivity<TamboonViewModel> {

    public static final String KEY_NAME = "key_name";
    private ActivityDonationBinding binding;
    private Dialog mLoadingDialog;

    @NonNull
    @Override
    protected TamboonViewModel createViewModel() {
        TamboonViewModel.Factory factory = new TamboonViewModel.Factory(TamboonRepository.getInstance().getTamboonService());
        return ViewModelProviders.of(this, factory).get(TamboonViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_donation);
        binding.setDonation(this);
        binding.setModel(new DonationRequestModel());
        binding.getModel().setName(getIntent().getStringExtra(KEY_NAME));
        setupToolbar();
        observeViewModel(viewModel);
        viewModel.getCharityObservable();

    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDialog();
                    finish();
                }
            });
        }
    }

    /**
     * Onclick for donation submission
     */
    public void onSubmitClick() {
        if (isInputValidated()) {
            mLoadingDialog = Utils.Companion.showDialog(this);
            binding.getModel().setAmount(Long.valueOf(binding.getModel().getAmountText()));
            viewModel.updateDonation(binding.getModel());
        }
    }

    private void showSuccessView(DonationResponse response) {
        SuccessFragment dialog = new SuccessFragment();
        Bundle bundleArgs = new Bundle();
        bundleArgs.putBoolean(SuccessFragment.KEY_SUCCESS, response.getSuccess());
        bundleArgs.putString(SuccessFragment.KEY_CODE, response.getErrorCode());
        bundleArgs.putString(SuccessFragment.KEY_MESSAGE, response.getErrorMessage());
        dialog.setArguments(bundleArgs);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        dialog.show(ft, SuccessFragment.TAG);
    }

    private boolean isInputValidated() {
        boolean isInputValidated = false;
        if (TextUtils.isEmpty(binding.getModel().getCreditCardNum())) {
            binding.tilCreditCardNumber.setError("Please enter credit card number.");
            binding.tilCreditCardNumber.requestFocus();
            binding.tilAmount.setErrorEnabled(false);
        } else if (binding.getModel().getCreditCardNum().length() < 16) {
            binding.tilCreditCardNumber.setError("Please enter valid credit card number.");
            binding.tilCreditCardNumber.requestFocus();
            binding.tilAmount.setErrorEnabled(false);
        } else if (TextUtils.isEmpty(binding.getModel().getAmountText())) {
            binding.tilCreditCardNumber.setErrorEnabled(false);
            binding.tilAmount.setError("Please enter Amount.");
            binding.tilAmount.requestFocus();
        } else if (!TextUtils.isEmpty(binding.getModel().getAmountText())
                && Long.valueOf(binding.getModel().getAmountText()) <= 0) {
            binding.tilCreditCardNumber.setErrorEnabled(false);
            binding.tilAmount.setError("Please enter Amount greater than zero.");
            binding.tilAmount.requestFocus();
        } else {
            binding.tilCreditCardNumber.setErrorEnabled(false);
            binding.tilAmount.setErrorEnabled(false);
            isInputValidated = true;
        }
        return isInputValidated;
    }


    private void hideDialog() {
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
        }
    }

    private void observeViewModel(TamboonViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getDonationObservable().observe(this, donationResponse -> {
            hideDialog();
            if (null != donationResponse) {
                showSuccessView(donationResponse);
            } else {
                Toast.makeText(DonationActivity.this, getString(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideDialog();
    }
}

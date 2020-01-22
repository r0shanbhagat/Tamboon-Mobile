package com.omise.tamboon.ui.success;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.omise.tamboon.R;
import com.omise.tamboon.databinding.FragmentSuccessBinding;
import com.omise.tamboon.ui.listing.CharityListActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SuccessFragment extends DialogFragment {

    public static final String TAG = "SuccessDialog";
    public static final String KEY_CODE = "key_code";
    public static final String KEY_MESSAGE = "key_message";
    public static final String KEY_SUCCESS = "key_success";
    private FragmentSuccessBinding binding;
    private boolean isDonationSuccess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil
                .inflate(LayoutInflater.from(getContext()), R.layout.fragment_success, container, false);
        binding.setSuccessView(this);
        handleBundleData();
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return binding.getRoot();
    }

    private void handleBundleData() {
        if (null != getArguments()) {
            isDonationSuccess = Objects.requireNonNull(getArguments()).getBoolean(KEY_SUCCESS);
            binding.tvMessage.setText(isDonationSuccess ? getArguments().getString(KEY_MESSAGE) : getString(R.string.donation_failure));
            binding.btnSubmit.setText(isDonationSuccess ? getString(R.string.donate_again) : getString(R.string.retry));
            binding.toolbar.setTitle(isDonationSuccess ? getArguments().getString(KEY_CODE) : getString(R.string.complete));
        }
    }


    public void onButtonClick() {
        dismiss();
        if (isDonationSuccess) {
            Intent intent = new Intent(getContext(), CharityListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        }
    }
}

package com.omise.tamboon.ui.listing;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.omise.tamboon.R;
import com.omise.tamboon.databinding.CharityListItemBinding;
import com.omise.tamboon.ui.common.OnClickCallback;

import java.util.List;

public class CharityAdapter extends RecyclerView.Adapter<CharityAdapter.CharityViewHolder> {
    private List<Charity> mCharityList;
    private OnClickCallback mListener;

    public void setListener(OnClickCallback mListener) {
        this.mListener = mListener;
    }

    public void setItems(List<Charity> mCharityList) {
        this.mCharityList = mCharityList;
        notifyDataSetChanged();
    }

    @Override
    public CharityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CharityListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.charity_list_item,
                        parent, false);
        return new CharityViewHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(CharityViewHolder holder, int position) {
        holder.bindView(mCharityList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCharityList == null ? 0 : mCharityList.size();
    }

    static class CharityViewHolder extends RecyclerView.ViewHolder {
        private final CharityListItemBinding binding;
        private Charity charity;


        public CharityViewHolder(CharityListItemBinding binding, final OnClickCallback mListener) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(v -> {
                if (null != mListener) {
                    mListener.onClick(v, charity);
                }
            });
        }

        void bindView(Charity charity) {
            this.charity = charity;
            binding.setCharity(charity);
            binding.executePendingBindings();
        }
    }

}

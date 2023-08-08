package com.pandacorp.weatherui.presentation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pandacorp.weatherui.databinding.ItemSettingsBinding;
import com.pandacorp.weatherui.presentation.utils.Constants;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {
    private final List<SettingsItem> settingsItemList;
    private OnClickListener onClickListener;
    private final String preferenceKey;

    public SettingsAdapter(List<SettingsItem> settingsItemList, String preferenceKey) {
        this.settingsItemList = settingsItemList;
        this.preferenceKey = preferenceKey;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(ItemSettingsBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SettingsItem item = settingsItemList.get(position);

        holder.binding.imageView.setImageDrawable(item.getDrawable());
        holder.binding.textview.setText(item.getTitle());

        holder.itemView.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(item);
            }
        });

        if (preferenceKey.equals(Constants.Dialog.THEME)) {
            holder.binding.cardView.setRadius(80f);
        }
    }

    @Override
    public int getItemCount() {
        return settingsItemList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(SettingsItem settingsItem);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSettingsBinding binding;

        public ViewHolder(@NonNull ItemSettingsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
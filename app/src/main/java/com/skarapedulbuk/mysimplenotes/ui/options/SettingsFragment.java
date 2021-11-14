package com.skarapedulbuk.mysimplenotes.ui.options;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.SettingsStorage;

public class SettingsFragment extends DialogFragment {

    public static final String TAG = "SettingsFragment";

    public SettingsFragment() {
        super(R.layout.fragment_settings);
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SettingsStorage settingsStorage = new SettingsStorage(super.requireContext());
        Bundle settingsBundle = settingsStorage.getSettings();

        CheckBox checkBoxBase = view.findViewById(R.id.checkbox_base);
        CheckBox checkBoxAdd = view.findViewById(R.id.checkbox_additional);

        checkBoxBase.setChecked(settingsBundle.getBoolean(SettingsStorage.ARG_BASE_CHECKBOX, false));
        checkBoxAdd.setChecked(settingsBundle.getBoolean(SettingsStorage.ARG_ADDITIONAL_CHECKBOX, false));

        checkBoxBase.setOnClickListener(v -> {

            settingsBundle.putBoolean(SettingsStorage.ARG_BASE_CHECKBOX, checkBoxBase.isChecked());
            settingsStorage.setSettings(settingsBundle);
            getParentFragmentManager().setFragmentResult(TAG, settingsBundle);

        });

        checkBoxAdd.setOnClickListener(v -> {

            settingsBundle.putBoolean(SettingsStorage.ARG_ADDITIONAL_CHECKBOX, checkBoxAdd.isChecked());
            settingsStorage.setSettings(settingsBundle);
            getParentFragmentManager().setFragmentResult(TAG, settingsBundle);

        });

        view.findViewById(R.id.btn_ok).setOnClickListener(v ->
            getParentFragmentManager().beginTransaction()
                    .remove(this)
                    .commit()
        );
    }
}
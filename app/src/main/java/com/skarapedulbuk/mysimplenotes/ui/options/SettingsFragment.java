package com.skarapedulbuk.mysimplenotes.ui.options;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.SettingsStorage;

public class SettingsFragment extends Fragment {

    public static final String KEY_RESULT = "KEY_RESULT";

    public SettingsFragment() {
        super(R.layout.fragment_settings);
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
            getParentFragmentManager().setFragmentResult(KEY_RESULT, settingsBundle);

        });

        checkBoxAdd.setOnClickListener(v -> {

            settingsBundle.putBoolean(SettingsStorage.ARG_ADDITIONAL_CHECKBOX, checkBoxAdd.isChecked());
            settingsStorage.setSettings(settingsBundle);
            getParentFragmentManager().setFragmentResult(KEY_RESULT, settingsBundle);

        });

        view.findViewById(R.id.btn_ok).setOnClickListener(v ->
            getParentFragmentManager().beginTransaction()
                    .remove(this)
                    .commit()
        );
    }
}
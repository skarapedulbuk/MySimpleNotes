package com.skarapedulbuk.mysimplenotes.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.SettingsStorage;
import com.skarapedulbuk.mysimplenotes.ui.list.ListFragment;
import com.skarapedulbuk.mysimplenotes.ui.options.SettingsFragment;

public class MainFragment extends Fragment {
    Boolean isBaseChecked;
    Boolean isAddChecked;
    SettingsStorage settingsStorage;

    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingsStorage = new SettingsStorage(super.requireContext());
        Bundle settingsBundle = settingsStorage.getSettings();

        isBaseChecked = settingsBundle.getBoolean(SettingsStorage.ARG_BASE_CHECKBOX, false);
        isAddChecked = settingsBundle.getBoolean(SettingsStorage.ARG_ADDITIONAL_CHECKBOX, false);

        Button btn_base = view.findViewById(R.id.btn_base_func);
        Button btn_add = view.findViewById(R.id.btn_add_func);

        btn_base.setEnabled(isBaseChecked);
        btn_add.setEnabled(isAddChecked);

        getChildFragmentManager().setFragmentResultListener(
                SettingsFragment.KEY_RESULT,
                getViewLifecycleOwner(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                        isBaseChecked = result.getBoolean(SettingsStorage.ARG_BASE_CHECKBOX, false);
                        isAddChecked = result.getBoolean(SettingsStorage.ARG_ADDITIONAL_CHECKBOX, false);

                        btn_base.setEnabled(isBaseChecked);
                        btn_add.setEnabled(isAddChecked);

                    }
                }
        );

        view.findViewById(R.id.btn_settings).setOnClickListener(v ->
                getChildFragmentManager().beginTransaction() // здесь, как указано в задании, используем getChildFragmentManager, хотя напрашивается, конечно, диалог
                        .add(R.id.child_container, new SettingsFragment(), "Settings")
                        .commit()
        );

        view.findViewById(R.id.btn_base_func).setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .add(R.id.main_container, new ListFragment())
                        .addToBackStack("List")
                        .commitAllowingStateLoss());

        view.findViewById(R.id.btn_add_func).setOnClickListener(v ->
            Toast.makeText(requireContext(), R.string.checkbox_additional, Toast.LENGTH_SHORT).show()
        );
    }
}
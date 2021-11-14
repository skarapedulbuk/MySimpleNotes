package com.skarapedulbuk.mysimplenotes.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.SettingsStorage;
import com.skarapedulbuk.mysimplenotes.ui.list.ListFragment;
import com.skarapedulbuk.mysimplenotes.ui.options.SettingsFragment;

public class MainFragment extends Fragment {
    private Boolean isBaseChecked;
    private Boolean isAddChecked;
    SettingsStorage settingsStorage;

    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbarMenu(view);

        settingsStorage = new SettingsStorage(super.requireContext());
        Bundle settingsBundle = settingsStorage.getSettings();

        isBaseChecked = settingsBundle.getBoolean(SettingsStorage.ARG_BASE_CHECKBOX, false);
        isAddChecked = settingsBundle.getBoolean(SettingsStorage.ARG_ADDITIONAL_CHECKBOX, false);

        Button buttonBase = view.findViewById(R.id.btn_base_func);
        Button buttonAdd = view.findViewById(R.id.btn_add_func);

        buttonBase.setEnabled(isBaseChecked);
        buttonAdd.setEnabled(isAddChecked);

        getParentFragmentManager().setFragmentResultListener(
                SettingsFragment.TAG,
                getViewLifecycleOwner(),
                (requestKey, result) -> {

                    isBaseChecked = result.getBoolean(SettingsStorage.ARG_BASE_CHECKBOX, false);
                    isAddChecked = result.getBoolean(SettingsStorage.ARG_ADDITIONAL_CHECKBOX, false);

                    buttonBase.setEnabled(isBaseChecked);
                    buttonAdd.setEnabled(isAddChecked);

                }
        );

        view.findViewById(R.id.btn_settings).setOnClickListener(v ->
                // showHideSettingsFragment()
                SettingsFragment.newInstance()
                        .show(getParentFragmentManager(), SettingsFragment.TAG)
        );

        view.findViewById(R.id.btn_base_func).setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main_container, new ListFragment())
                        .addToBackStack("List")
                        .commit()
        );

        view.findViewById(R.id.btn_add_func).setOnClickListener(v ->
                Toast.makeText(requireContext(), R.string.checkbox_additional, Toast.LENGTH_SHORT).show()
        );
    }

    private void initToolbarMenu(View view) {
        MaterialToolbar toolbar = view.findViewById(R.id.main_fragment_toolbar);
        if (getActivity() instanceof Drawer) {
            Drawer drawer = (Drawer) getActivity();
            drawer.setToolbar(toolbar);
        }

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_settings) {
                //showHideSettingsFragment();
                SettingsFragment.newInstance()
                        .show(getParentFragmentManager(), SettingsFragment.TAG);
            }
            if (item.getItemId() == R.id.action_about) {
                Toast.makeText(requireContext(), R.string.action_about, Toast.LENGTH_SHORT).show();
                return true;
            }
            return super.onOptionsItemSelected(item);
        });
    }

    public void showHideSettingsFragment() {
        if (getChildFragmentManager().findFragmentByTag("Settings") == null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.child_container, new SettingsFragment(), "Settings")
                    .commit();
            Toast.makeText(requireContext(), "Показываю настройки", Toast.LENGTH_SHORT).show();
        } else {
            getChildFragmentManager().beginTransaction()
                    .remove(getChildFragmentManager().findFragmentByTag("Settings"))
                    .commit();
            Toast.makeText(requireContext(), "Скрываю настройки", Toast.LENGTH_SHORT).show();
        }
    }
}
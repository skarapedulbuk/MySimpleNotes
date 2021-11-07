package com.skarapedulbuk.mysimplenotes.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    Boolean isBaseChecked;
    Boolean isAddChecked;
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            showSettingsFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.modern_toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_settings) {
                showSettingsFragment();
            }
            if (item.getItemId() == R.id.action_about) {
                Toast.makeText(requireContext(), R.string.action_about, Toast.LENGTH_SHORT).show();
                return true;
            }
            return super.onOptionsItemSelected(item);

    });

        toolbar.setNavigationOnClickListener(v ->
                Toast.makeText(requireContext(), "Back pressed", Toast.LENGTH_SHORT).show());

    settingsStorage =new

    SettingsStorage(super.requireContext());
    Bundle settingsBundle = settingsStorage.getSettings();

    isBaseChecked =settingsBundle.getBoolean(SettingsStorage.ARG_BASE_CHECKBOX,false);
    isAddChecked =settingsBundle.getBoolean(SettingsStorage.ARG_ADDITIONAL_CHECKBOX,false);

    Button btn_base = view.findViewById(R.id.btn_base_func);
    Button btn_add = view.findViewById(R.id.btn_add_func);

        btn_base.setEnabled(isBaseChecked);
        btn_add.setEnabled(isAddChecked);

    getChildFragmentManager().

    setFragmentResultListener(
            SettingsFragment.KEY_RESULT,
            getViewLifecycleOwner(),
            (requestKey, result) -> {

                isBaseChecked = result.getBoolean(SettingsStorage.ARG_BASE_CHECKBOX, false);
                isAddChecked = result.getBoolean(SettingsStorage.ARG_ADDITIONAL_CHECKBOX, false);

                btn_base.setEnabled(isBaseChecked);
                btn_add.setEnabled(isAddChecked);

            }
    );

        view.findViewById(R.id.btn_settings).

    setOnClickListener(v ->

    showSettingsFragment()
        );

        view.findViewById(R.id.btn_base_func).

    setOnClickListener(v ->

    getParentFragmentManager().

    beginTransaction()
                        .

    replace(R.id.main_container, new ListFragment()) // если использовать add, меню фрагмента начального экрана (шестеренка) будет показываться там, где не надо
            .

    addToBackStack("List")
                        .

    commit());

        view.findViewById(R.id.btn_add_func).

    setOnClickListener(v ->
            Toast.makeText(

    requireContext(),R.string.checkbox_additional,Toast.LENGTH_SHORT).

    show()
        );
}

    public void showSettingsFragment() {
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
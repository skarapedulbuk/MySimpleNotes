package com.skarapedulbuk.mysimplenotes.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.MyTask;
import com.skarapedulbuk.mysimplenotes.ui.Drawer;


public class DetailsFragment extends Fragment {

    public static final String ARG_TASK = "ARG_TASK";
    public static final String KEY_LIST_DETAILS = "KEY_LIST_DETAILS";


    public DetailsFragment() {
        super(R.layout.fragment_details);

    }

    public static DetailsFragment newInstance(MyTask task) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK, task);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbarMenu(view);

        CheckBox checkBox = view.findViewById(R.id.task_checkbox);
        EditText title = view.findViewById(R.id.task_title);
        EditText description = view.findViewById(R.id.task_description);
        FloatingActionButton backButton = view.findViewById(R.id.btn_back);

        backButton.setOnClickListener(v -> {
                    Toast.makeText(requireContext(), "Назад", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().popBackStack();
                }
        );
        if (getArguments() != null && getArguments().containsKey(ARG_TASK)) {

            MyTask task = getArguments().getParcelable(ARG_TASK);

            title.setText(task.getTaskTitle());
            description.setText(task.getTaskDescription());
            checkBox.setChecked(getResources().getBoolean(task.getTaskIsDone()));

        }

        getParentFragmentManager().setFragmentResultListener(DetailsFragment.KEY_LIST_DETAILS,
                getViewLifecycleOwner(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        MyTask task1 = result.getParcelable(ARG_TASK);

                        title.setText(task1.getTaskTitle());
                        description.setText(task1.getTaskDescription());
                        checkBox.setChecked(getResources().getBoolean(task1.getTaskIsDone()));

                    }
                });
    }

    private void initToolbarMenu(View view) {
        MaterialToolbar toolbar = view.findViewById(R.id.details_toolbar);
        if (getActivity() instanceof Drawer) {
            Drawer drawer = (Drawer) getActivity();
            drawer.setToolbar(toolbar);
        }
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_share) {
                Toast.makeText(requireContext(), "Поделиться", Toast.LENGTH_SHORT).show();
            }
            if (item.getItemId() == R.id.action_save) {
                Toast.makeText(requireContext(), "Сохранить", Toast.LENGTH_SHORT).show();
            }
            if (item.getItemId() == R.id.action_delete) {
                Toast.makeText(requireContext(), "Удалить", Toast.LENGTH_SHORT).show();
            }
            return super.onOptionsItemSelected(item);
        });
    }
}
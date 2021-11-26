package com.skarapedulbuk.mysimplenotes.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.InmemoryTasksRepository;
import com.skarapedulbuk.mysimplenotes.domain.MyTask;
import com.skarapedulbuk.mysimplenotes.domain.SettingsStorage;
import com.skarapedulbuk.mysimplenotes.ui.Drawer;
import com.skarapedulbuk.mysimplenotes.ui.details.DetailsFragment;
import com.skarapedulbuk.mysimplenotes.ui.options.SettingsFragment;

import java.util.Collections;
import java.util.List;


public class ListFragment extends Fragment implements ListView {
    public static final String KEY_LIST_ACTIVITY = "KEY_LIST_ACTIVITY";
    public static final String ARG_TASK = "ARG_TASK";

    private RecyclerView tasksListRoot;
    private ListPresenter presenter;
    private BottomNavigationView bottomNavigationView;
    private Boolean isAddChecked;

    private TasksAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ListPresenter(this, new InmemoryTasksRepository());
        adapter = new TasksAdapter();

        adapter.setTaskClicked(new TasksAdapter.OnTaskClicked() {
            @Override
            public void onTaskClicked(MyTask task) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_TASK, task);

                getParentFragmentManager()
                        .setFragmentResult(KEY_LIST_ACTIVITY, bundle);
            }
        });

        presenter.requestTasks();

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tasksListRoot = view.findViewById(R.id.list_root);
        /*tasksListRoot.setLayoutManager(new LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false));*/

        tasksListRoot.setAdapter(adapter);

        SettingsStorage settingsStorage = new SettingsStorage(super.requireContext());
        isAddChecked = settingsStorage.getSettings().getBoolean(SettingsStorage.ARG_ADDITIONAL_CHECKBOX, false);

        initBottomMenu(view);
        initToolbarMenu(view);

        setBottomMenuVisibility(isAddChecked);

        getParentFragmentManager().setFragmentResultListener(
                SettingsFragment.TAG,
                getViewLifecycleOwner(),
                (requestKey, result) -> {
                    isAddChecked = result.getBoolean(SettingsStorage.ARG_ADDITIONAL_CHECKBOX, false);
                    setBottomMenuVisibility(isAddChecked);
                }
        );

        getParentFragmentManager().setFragmentResultListener(ListFragment.KEY_LIST_ACTIVITY,
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                        MyTask selectedTask = result.getParcelable(ListFragment.ARG_TASK);

                        DetailsFragment detailsFragment = DetailsFragment.newInstance(selectedTask);
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.main_container, detailsFragment)
                                .addToBackStack(null)
                                .commit();

                    }
                });
    }

    private void setBottomMenuVisibility(Boolean bottomMenuVisibility) {
        if (bottomMenuVisibility) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    private void initToolbarMenu(View view) {
        MaterialToolbar toolbar = view.findViewById(R.id.list_toolbar);
        if (getActivity() instanceof Drawer) {
            Drawer drawer = (Drawer) getActivity();
            drawer.setToolbar(toolbar);
        }
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_add) {
                Toast.makeText(requireContext(), R.string.add_task, Toast.LENGTH_SHORT).show();
                presenter.add(getString(R.string.task3),getString(R.string.description3), false);
                return true;
            }
            if (item.getItemId() == R.id.action_clear_all) {
                Toast.makeText(requireContext(), R.string.action_clear_all, Toast.LENGTH_SHORT).show();
                presenter.removeAll();
                return true;
            }
            if (item.getItemId() == R.id.action_search) {
                Toast.makeText(requireContext(), R.string.search, Toast.LENGTH_SHORT).show();
                return true;
            }
            if (item.getItemId() == R.id.action_about) {
                Toast.makeText(requireContext(), R.string.action_about, Toast.LENGTH_SHORT).show();
                return true;
            }
            return super.onOptionsItemSelected(item);
        });
    }

    private void initBottomMenu(View view) {
        bottomNavigationView = view.findViewById(R.id.bottom_nav_menu);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.filter_done) {
                    Toast.makeText(requireContext(), R.string.done, Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (item.getItemId() == R.id.filter_undone) {
                    Toast.makeText(requireContext(), R.string.undone, Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (item.getItemId() == R.id.filter_all) {
                    Toast.makeText(requireContext(), R.string.all, Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void showTasks(List<MyTask> tasks) {
        adapter.setTasks(tasks);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearTasks() {
        adapter.setTasks(Collections.emptyList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addTask(MyTask result) {
        adapter.addTask(result);

        adapter.notifyItemInserted(adapter.getItemCount() - 1);

        tasksListRoot.smoothScrollToPosition(adapter.getItemCount() - 1);
    }
}
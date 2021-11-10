package com.skarapedulbuk.mysimplenotes.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.InmemoryTasksRepository;
import com.skarapedulbuk.mysimplenotes.domain.MyTask;
import com.skarapedulbuk.mysimplenotes.ui.Drawer;
import com.skarapedulbuk.mysimplenotes.ui.details.DetailsFragment;

import java.util.List;


public class ListFragment extends Fragment implements ListView {
    public static final String KEY_LIST_ACTIVITY = "KEY_LIST_ACTIVITY";
    public static final String ARG_TASK = "ARG_TASK";

    private LinearLayout tasksListRoot;
    private ListPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ListPresenter(this, new InmemoryTasksRepository());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tasksListRoot = view.findViewById(R.id.list_root);
        presenter.requestTasks();

        initBottomMenu(view);
        initToolbarMenu(view);

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

    private void initToolbarMenu(View view) {
        MaterialToolbar toolbar = view.findViewById(R.id.list_toolbar);
        if (getActivity() instanceof Drawer) {
            Drawer drawer = (Drawer) getActivity();
            drawer.setToolbar(toolbar);
        }
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_add) {
                Toast.makeText(requireContext(), R.string.add_task, Toast.LENGTH_SHORT).show();
                return true;
            }
            if (item.getItemId() == R.id.action_clear_all) {
                Toast.makeText(requireContext(), R.string.action_clear_all, Toast.LENGTH_SHORT).show();
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

        //кнопка назад на тулбаре не видна, но ее действие выполняется вместо выезжаения дроера
      /*  toolbar.setNavigationOnClickListener(v -> {
                    Toast.makeText(requireContext(), "Назад", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().popBackStack();
                });*/

    }

    private void initBottomMenu(View view) {
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_nav_menu);

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
        for (MyTask task : tasks
        ) {
            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_task, tasksListRoot, false);
            FloatingActionButton editButton = itemView.findViewById(R.id.edit_button);
            editButton.setOnClickListener(v -> {

                PopupMenu popupMenu = new PopupMenu(requireContext(), editButton);

                requireActivity().getMenuInflater().inflate(R.menu.menu_action_popup, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.popup_delete) {
                            Toast.makeText(requireContext(), "Удаляем задачу из попап меню", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (item.getItemId() == R.id.popup_edit) {
                            Toast.makeText(requireContext(), "Редактируем задачу из попап меню", Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(ARG_TASK, task);

                            getParentFragmentManager()
                                    .setFragmentResult(KEY_LIST_ACTIVITY, bundle);
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                popupMenu.show();
            });

            CheckBox title = itemView.findViewById(R.id.checkbox_of_task);
            title.setText(task.getTaskTitle());
            title.setChecked(getResources().getBoolean(task.getTaskIsDone()));

            tasksListRoot.addView(itemView);
        }
    }
}
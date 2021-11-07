package com.skarapedulbuk.mysimplenotes.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.domain.InmemoryTasksRepository;
import com.skarapedulbuk.mysimplenotes.domain.MyTask;
import com.skarapedulbuk.mysimplenotes.ui.details.DetailsFragment;

import java.util.List;


public class ListFragment extends Fragment implements ListView {
    public static final String KEY_LIST_ACTIVITY = "KEY_LIST_ACTIVITY";
    public static final String ARG_TASK = "ARG_TASK";

    private LinearLayout tasksListRoot;
    private ListPresenter presenter;
    //private OnTaskClicked onTaskClicked;

    /*public interface OnTaskClicked {
        void onTaskClicked(MyTask task);
    }*/

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


    @Override
    public void showTasks(List<MyTask> tasks) {
        for (MyTask task : tasks
        ) {
            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_task, tasksListRoot, false);
            Button editButton = itemView.findViewById(R.id.edit_button);
            editButton.setOnClickListener(v -> {

                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_TASK, task);

                getParentFragmentManager()
                        .setFragmentResult(KEY_LIST_ACTIVITY, bundle);

                /*if (onTaskClicked != null) {
                    onTaskClicked.onTaskClicked(task);
                }*/
            });

            CheckBox title = itemView.findViewById(R.id.checkbox_of_task);
            title.setText(task.getTaskTitle());
            title.setChecked(getResources().getBoolean(task.getTaskIsDone()));

            tasksListRoot.addView(itemView);
        }
    }
}
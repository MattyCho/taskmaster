package com.mattc.taskmaster.adapters;

import static com.mattc.taskmaster.activities.MainActivity.TASK_TITLE_EXTRA_STRING;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mattc.taskmaster.R;
import com.mattc.taskmaster.activities.TaskDetailActivity;
import com.mattc.taskmaster.models.Task;

import java.util.List;

public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.TaskListViewHolder> {

    AppCompatActivity associatedActivity;
    List<Task> taskList;
    public final static String TAG = "mattyc_taskmaster_tasklistrecyclerviewadapter";

    public TaskListRecyclerViewAdapter(AppCompatActivity associatedActivity, List<Task> taskList) {
        this.associatedActivity = associatedActivity;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        TaskListViewHolder taskListViewHolder = new TaskListViewHolder(fragment);
        return taskListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        Task task = taskList.get(position);
        View taskFragment = holder.itemView;
        TextView taskFragmentTextView = taskFragment.findViewById(R.id.taskFragmentTextView);
        taskFragmentTextView.setText(task.toString());

        holder.itemView.setOnClickListener(view -> {
            Intent taskDetailIntent = new Intent(associatedActivity, TaskDetailActivity.class);
            taskDetailIntent.putExtra(TASK_TITLE_EXTRA_STRING, task.taskTitle);
            associatedActivity.startActivity(taskDetailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskListViewHolder extends RecyclerView.ViewHolder {
        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

package com.mattc.taskmaster.adapters;

import static com.mattc.taskmaster.activities.MainActivity.TASK_ID_EXTRA_STRING;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;
import com.mattc.taskmaster.R;
import com.mattc.taskmaster.activities.TaskDetailActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.TaskListViewHolder> {

    AppCompatActivity associatedActivity;
    List<Task> taskList;
    public final static String TAG = "mattyc_taskmaster_tasklistrecyclerviewadapter";

    public TaskListRecyclerViewAdapter(AppCompatActivity associatedActivity, List<Task> taskList) {
        this.associatedActivity = associatedActivity;
        this.taskList = taskList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
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

        // Formatting Date
        DateFormat localIso8601InputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        localIso8601InputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormat dateOutputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateOutputFormat.setTimeZone(TimeZone.getDefault());
        String timeAddedString = "";
        try
        {
            Date timeAddedDate = localIso8601InputDateFormat.parse(task.getTaskDate().format());
            if (timeAddedDate != null)
            {
                timeAddedString = dateOutputFormat.format(timeAddedDate);
            }
        }
        catch (ParseException pe)
        {
            Log.i(TAG, "Error converting shopping item date to string: " + pe.getMessage(), pe);
        }

        String taskString = "Task Title: " + task.getTaskTitle() +
                "\nTeam: " + task.getTeam().getTeamName() +
                "\nDate Added: " + timeAddedString +
                "\nTask Description: " + task.getTaskDescription() +
                "\nTask Status: " + task.getTaskStatus() +"\n";
        taskFragmentTextView.setText(taskString);

        holder.itemView.setOnClickListener(view -> {
            Intent taskDetailIntent = new Intent(associatedActivity, TaskDetailActivity.class);
            taskDetailIntent.putExtra(TASK_ID_EXTRA_STRING, task.getId());
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

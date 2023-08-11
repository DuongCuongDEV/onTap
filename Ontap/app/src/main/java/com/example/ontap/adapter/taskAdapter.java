package com.example.ontap.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ontap.R;
import com.example.ontap.model.task;
import com.example.ontap.netWorking.API;
import com.example.ontap.view.DetailActivity;
import com.example.ontap.view.UpdateActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.ViewHolder> {
    private static List<task> taskList;
    private Context context; // Thêm biến Context
    private API apiService;
    private static onClickItem onclick;

    public taskAdapter(Context context, List<task> taskList, onClickItem onclick) {
        this.context = context;
        this.taskList = taskList;
        this.onclick = onclick;
    }
    public taskAdapter(List<task> taskList) {
        this.taskList = taskList;
    }
    public static void addTask(task newTask) {
        taskList.add(newTask);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        task task = taskList.get(position);
        holder.textName.setText(task.getTitle());
        holder.textEmail.setText(task.getCreatedAt());
        Picasso.get().load(task.getImage()).into(holder.imageTask);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open DetailActivity and pass necessary data
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("task", task); // Pass the task object to DetailActivity
                v.getContext().startActivity(intent);
            }
        });

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.onclickDeleteItem(task);
            }
        });

        holder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin công việc tại vị trí position
                task task = taskList.get(position);

                // Tạo Intent để chuyển đến màn hình cập nhật
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("task_id", task.getId()); // Truyền ID của công việc cần cập nhật
                intent.putExtra("task_title", task.getTitle()); // Truyền tiêu đề của công việc cần cập nhật
                intent.putExtra("task_content", task.getContent()); // Truyền nội dung của công việc cần cập nhật
                intent.putExtra("task_end_date", task.getEnd_date()); // Truyền ngày kết thúc của công việc cần cập nhật
                intent.putExtra("task_image", task.getImage()); // Truyền đường dẫn ảnh của công việc cần cập nhật
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textEmail;
        ImageView imageTask, imageDelete, imageEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textTitle);
            textEmail = itemView.findViewById(R.id.textEndDate);
            imageTask = itemView.findViewById(R.id.imageTask);
            imageDelete = itemView.findViewById(R.id.imageDelete);
            imageEdit = itemView.findViewById(R.id.imageEdit);
        }
    }

    public interface onClickItem{
        void onclickDeleteItem(task task);

    }

}

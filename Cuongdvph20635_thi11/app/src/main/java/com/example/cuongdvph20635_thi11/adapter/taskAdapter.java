package com.example.cuongdvph20635_thi11.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuongdvph20635_thi11.R;
import com.example.cuongdvph20635_thi11.model.task;
import com.example.cuongdvph20635_thi11.netWorking.API;
import com.example.cuongdvph20635_thi11.view.DetailActivity;
import com.example.cuongdvph20635_thi11.view.UpdateActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.ViewHolder> {
    private static List<task> taskList;
    private Context context;
    private static onClickItem onclick;

    public taskAdapter(Context context, List<task> taskList, onClickItem onclick) {
        this.context = context;
        this.taskList = taskList;
        this.onclick = onclick;
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
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("task", task);
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
                task task = taskList.get(position);

                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("task_id", task.getId());
                intent.putExtra("task_title", task.getTitle());
                intent.putExtra("task_content", task.getContent());
                intent.putExtra("task_end_date", task.getEnd_date());
                intent.putExtra("task_image", task.getImage());
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

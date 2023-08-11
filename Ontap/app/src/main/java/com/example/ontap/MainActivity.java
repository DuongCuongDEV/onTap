package com.example.ontap;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ontap.adapter.taskAdapter;
import com.example.ontap.model.task;
import com.example.ontap.netWorking.API;
import com.example.ontap.view.AddTaskActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private taskAdapter taskAdapter;
    private Button btnNew;
    private API apiService;

    private static final int ADD_TASK_REQUEST_CODE = 1;
    public static List<task> taskList = new ArrayList<>(); // Khai báo và khởi tạo danh sách công việc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6475a8c5e607ba4797dc4582.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(API.class);

        btnNew = findViewById(R.id.btnNew);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo Adapter
//        taskAdapter = new taskAdapter(taskList);
        taskAdapter = new taskAdapter(MainActivity.this, taskList, new taskAdapter.onClickItem() {
            @Override
            public void onclickDeleteItem(task task) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa công việc này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       apiService.deleteTask(task.getId()).enqueue(new Callback<com.example.ontap.model.task>() {
                           @Override
                           public void onResponse(Call<com.example.ontap.model.task> call, Response<com.example.ontap.model.task> response) {
                               taskList.remove(task);
                               taskAdapter.notifyDataSetChanged();
                           }

                           @Override
                           public void onFailure(Call<com.example.ontap.model.task> call, Throwable t) {

                           }
                       });

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }


        });




        recyclerView.setAdapter(taskAdapter);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST_CODE);
            }
        });




        // Gọi API để lấy danh sách công việc
        Call<List<task>> call = apiService.getTask();
        call.enqueue(new Callback<List<task>>() {
            @Override
            public void onResponse(Call<List<task>> call, Response<List<task>> response) {
                if (response.isSuccessful()) {
                    // Cập nhật dữ liệu trong danh sách công việc
                    taskList.clear();
                    taskList.addAll(response.body());
                    taskAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<task>> call, Throwable t) {
                // Xử lý lỗi khi không thể kết nối hoặc lấy dữ liệu từ API
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            task newTask = (task) data.getSerializableExtra("newTask");
            if (newTask != null) {
                taskList.add(newTask); // Thêm công việc mới vào danh sách
                taskAdapter.notifyDataSetChanged();
            }
        }
    }
}
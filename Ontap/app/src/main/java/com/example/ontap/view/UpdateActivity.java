package com.example.ontap.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ontap.MainActivity;
import com.example.ontap.activity_login;
import com.example.ontap.activity_register;
import com.example.ontap.adapter.taskAdapter;
import com.example.ontap.model.task;
import com.example.ontap.R;
import com.example.ontap.netWorking.API;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateActivity extends AppCompatActivity {

    private EditText editTitle, editContent, editEndDate, editImage;
    private Button btnUpdate, btnBack;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editTitle = findViewById(R.id.editTitleUpdate);
        editContent = findViewById(R.id.editContentUpdate);
        editEndDate = findViewById(R.id.editEndDateUpdate);
        editImage = findViewById(R.id.editImageUpdate);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnBack = findViewById(R.id.btnBackUpdate);


        Intent intent = getIntent();
        if (intent != null) {
            String taskTitle = intent.getStringExtra("task_title");
            String taskContent = intent.getStringExtra("task_content");
            String taskEndDate = intent.getStringExtra("task_end_date");
            String taskImage = intent.getStringExtra("task_image");

            // Gắn dữ liệu lên các EditText tương ứng
            editTitle.setText(taskTitle);
            editContent.setText(taskContent);
            editEndDate.setText(taskEndDate);
            editImage.setText(taskImage);
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the DetailActivity and go back to the previous activity (list)
                finish();
            }
        });





        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy ngày hiện tại để đặt làm ngày mặc định
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Tạo DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                // Người dùng đã chọn ngày
                                String selectedDate = String.format("%d-%02d-%02d", year, month + 1, day);
                                editEndDate.setText(selectedDate);
                            }
                        },
                        year, month, day);

                // Hiển thị DatePickerDialog
                datePickerDialog.show();
            }
        });




        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các EditText
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();
                String end_Date = editEndDate.getText().toString();
                String imageUri = editImage.getText().toString();
                int status = 0;

                // Get current date and time
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String createdAt = dateFormat.format(calendar.getTime());

                // Lấy ID của công việc cần cập nhật từ Intent
                String taskId = getIntent().getStringExtra("task_id");

                // Tạo đối tượng task mới với các thông tin cập nhật
                task updatedTask = new task(createdAt, title, content, end_Date, status, imageUri);

                // Initialize Retrofit
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://6475a8c5e607ba4797dc4582.mockapi.io/") // Your base URL
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                API apiService = retrofit.create(API.class);

                // Send the updatedTask to the server for updating
                Call<task> call = apiService.updateTask(taskId, updatedTask);
                call.enqueue(new Callback<task>() {
                    @Override
                    public void onResponse(Call<task> call, Response<task> response) {
                        // Xử lý thành công (nếu cần)
                        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<task> call, Throwable t) {
                        // Handle failure (if needed)
                    }
                });
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            editImage.setText(imageUri.toString());
        }
    }
}

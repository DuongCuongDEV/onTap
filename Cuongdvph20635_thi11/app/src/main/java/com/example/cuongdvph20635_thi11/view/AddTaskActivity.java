package com.example.cuongdvph20635_thi11.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cuongdvph20635_thi11.MainActivity;
import com.example.cuongdvph20635_thi11.R;
import com.example.cuongdvph20635_thi11.model.task;
import com.example.cuongdvph20635_thi11.netWorking.API;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTitle, editContent, editEndDate, editImage;
    private Button btnAdd, btnBack;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        editEndDate = findViewById(R.id.editEndDate);
        editImage = findViewById(R.id.editImage);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBackAdd);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddTaskActivity.this,
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();
                String endDate = editEndDate.getText().toString();
                String imageUri = editImage.getText().toString();
                int status = 0;

                if (title.isEmpty() || content.isEmpty() || endDate.isEmpty() || imageUri.isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "Phải nhập đủ các trường", Toast.LENGTH_SHORT).show();
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String createdAt = dateFormat.format(calendar.getTime());

                task newTask = new task(createdAt, title, content, endDate, status, imageUri);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://6475a8c5e607ba4797dc4582.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                API apiService = retrofit.create(API.class);

                Call<task> call = apiService.addTask(newTask);
                call.enqueue(new Callback<task>() {
                    @Override
                    public void onResponse(Call<task> call, Response<task> response) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("newTask", newTask);

                        Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<task> call, Throwable t) {
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

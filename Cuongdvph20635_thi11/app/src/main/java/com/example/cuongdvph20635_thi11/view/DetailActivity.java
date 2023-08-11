package com.example.cuongdvph20635_thi11.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuongdvph20635_thi11.R;
import com.example.cuongdvph20635_thi11.model.task;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private TextView textTitle, textContent, textEndDate, textCreatedAt, textStatus;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textTitle = findViewById(R.id.textTitleDetail);
        textContent = findViewById(R.id.textContentDetail);
        textEndDate = findViewById(R.id.textEndDateDetail);
        textCreatedAt = findViewById(R.id.textCreatedAtDetail);
        textStatus = findViewById(R.id.textStatusDetail);
        btnBack = findViewById(R.id.btnBack);

        task task = getIntent().getParcelableExtra("task");

        if (task != null) {
            textTitle.setText(task.getTitle());
            textContent.setText(task.getContent());
            textEndDate.setText("End Date: " + task.getEnd_date());
            textCreatedAt.setText("Created At: " + task.getCreatedAt());
            textStatus.setText("Status: " + task.getStatus());
            ImageView imageDetail = findViewById(R.id.imageDetail);
            Picasso.get().load(task.getImage()).into(imageDetail);

        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

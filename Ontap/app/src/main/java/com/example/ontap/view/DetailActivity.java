package com.example.ontap.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ontap.R;
import com.example.ontap.model.task;
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

        // Get the task object passed from the previous activity
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
                // Finish the DetailActivity and go back to the previous activity (list)
                finish();
            }
        });
    }
}

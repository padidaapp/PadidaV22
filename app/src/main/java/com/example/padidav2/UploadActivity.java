package com.example.padidav2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UploadActivity extends AppCompatActivity {
    private TextView ShowUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        ShowUploads = (TextView) findViewById(R.id.shwuplds);


        ShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivity.this,ShowUploadsActivity.class);
                startActivity(intent);
            }
        });
    }
}

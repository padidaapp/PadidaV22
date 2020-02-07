package com.example.padidav2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {

    private Button UploadButton, ShowuploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        UploadButton = (Button) findViewById(R.id.upldbtn);
        ShowuploadButton = (Button) findViewById(R.id.showuploadbtn);

        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,UploadActivity.class);
                startActivity(intent);
            }
        });

        ShowuploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,ShowUploadsActivity.class);
                startActivity(intent);
            }
        });
    }

}

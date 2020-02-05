package com.example.padidav2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.rey.material.widget.Button;

public class loginactivity extends AppCompatActivity
{
    private EditText InputNumber, InputPassword;
    private android.widget.Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        LoginButton = (Button) findViewById(R.id.textView5);
        InputPassword = (EditText) findViewById(R.id.loginpassword);
        InputNumber = (EditText) findViewById(R.id.loginrollno);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUser();
            }
        });
    }

    private void loginUser()
    {
        String User_Roll = InputNumber.getText().toString();
        String User_Pass = InputPassword.getText().toString();
    }
}

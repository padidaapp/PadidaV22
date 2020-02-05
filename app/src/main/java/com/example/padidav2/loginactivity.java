package com.example.padidav2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.padidav2.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginactivity extends AppCompatActivity
{
    private EditText InputNumber, InputPassword;
    private android.widget.Button LoginButton;
    private ProgressDialog Loading_Bar;
    private TextView SignupRedirector;

    private String ParentDbName = "Users";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        LoginButton = (Button) findViewById(R.id.btnLogin);
        InputPassword = (EditText) findViewById(R.id.loginpassword);
        InputNumber = (EditText) findViewById(R.id.loginrollno);
        SignupRedirector = (TextView) findViewById(R.id.signupredirect);
        LoginButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            loginUser();
        }
    });

        SignupRedirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(loginactivity.this,Dashboard.class);
                startActivity(intent);
            }
        });

    }

    private void loginUser()
    {
        String User_Roll = InputNumber.getText().toString();
        String User_Pass = InputPassword.getText().toString();

        if (TextUtils.isEmpty(User_Roll))
        {
            Toast.makeText(this, "Write your Roll no", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(User_Pass))
         {
            Toast.makeText(this, "Enter your Password", Toast.LENGTH_SHORT).show();
         }

        else
        {
            Loading_Bar.setTitle("Create Account");
            Loading_Bar.setMessage("Wait while we check your credentials");
            Loading_Bar.setCanceledOnTouchOutside(false);
            Loading_Bar.show();

            AllowAccessToAccount(User_Roll, User_Pass);
        }

    }

    private void AllowAccessToAccount(final String user_roll, final String user_pass)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(ParentDbName).child(user_roll).exists())
                {
                    Users usersData = dataSnapshot.child(ParentDbName).child(user_roll).getValue(Users.class);
                    if(usersData.getRoll_no().equals(user_roll))
                    {
                        if(usersData.getPassword().equals(user_pass))
                        {
                            Toast.makeText(loginactivity.this, "Welcome home puppy ma!!", Toast.LENGTH_SHORT).show();
                            Loading_Bar.dismiss();

                        }
                    }
                }
                else
                {
                    Toast.makeText(loginactivity.this, "Invalid credentials or account doesn't exist", Toast.LENGTH_SHORT).show();
                    Loading_Bar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

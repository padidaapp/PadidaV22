package com.example.padidav2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.padidav2.Model.Users;
import com.example.padidav2.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginactivity extends AppCompatActivity
{
    private static final String TAG = "";
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


        LoginButton.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View v)
        {
            loginUser();
        }
    });

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(loginactivity.TAG, "Failed to read value.", error.toException());
            }
        });

        SignupRedirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(loginactivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void loginUser()
    {
        final String User_Roll = InputNumber.getText().toString();
        final String User_Pass = InputPassword.getText().toString();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");

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
            //Toast.makeText(this, "actially varudhu", Toast.LENGTH_SHORT).show();
            //Loading_Bar.setTitle("logging into Account");
            //Loading_Bar.setMessage("Wait while we check your credentials");
            //Loading_Bar.setCanceledOnTouchOutside(false);
            //Loading_Bar.show();

            //AllowAccessToAccount(User_Roll, User_Pass);

            //Toast.makeText(loginactivity.this, "fuck yeahhh", Toast.LENGTH_SHORT).show();

            /*final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference("users");*/


            myRef.addListenerForSingleValueEvent(new ValueEventListener()
            {

                @Override

                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    Toast.makeText(loginactivity.this, "fuck yeahhh", Toast.LENGTH_SHORT).show();
                    if(dataSnapshot.child(ParentDbName).child(User_Roll).exists())
                    {

                        Toast.makeText(loginactivity.this, "OOigiiyyy", Toast.LENGTH_SHORT).show();
                        Users usersData = dataSnapshot.child(ParentDbName).child(User_Roll).getValue(Users.class);
                        if(usersData.Roll_no.equals(User_Roll))
                        {
                            if(usersData.Password.equals(User_Pass))
                            {
                                Toast.makeText(loginactivity.this, "Welcome da, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                Loading_Bar.dismiss();

                                Intent intent = new Intent(loginactivity.this, Dashboard.class);
                                //Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);

                            }
                            else
                            {
                                Loading_Bar.dismiss();
                                Toast.makeText(loginactivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
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

    /*private void AllowAccessToAccount(final String user_roll, final String user_pass)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        Toast.makeText(loginactivity.this, "fuck yeahhh", Toast.LENGTH_SHORT).show();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(ParentDbName).child(user_roll).exists())
                {

                    //Toast.makeText(loginactivity.this, "OOigiiyyy", Toast.LENGTH_SHORT).show();
                    Users usersData = dataSnapshot.child(ParentDbName).child(user_roll).getValue(Users.class);
                    if(usersData.Roll_no.equals(user_roll))
                    {
                        if(usersData.Password.equals(user_pass))
                        {
                            Toast.makeText(loginactivity.this, "Welcome da, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                            Loading_Bar.dismiss();

                            Intent intent = new Intent(loginactivity.this, Dashboard.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);

                        }
                        else
                        {
                            Loading_Bar.dismiss();
                            Toast.makeText(loginactivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
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

    }*/
}

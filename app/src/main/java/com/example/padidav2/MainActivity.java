package com.example.padidav2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    private EditText Name, Password,Roll_no,Email_ID;
    private Button SignUpBtn;
    private TextView SignInRedirector;
    private ProgressDialog Loading_Bar;
    public static String User_Roll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name=(EditText) findViewById(R.id.signupnamefield);
        Password =(EditText) findViewById(R.id.signuppasswordfield);
        Roll_no =(EditText) findViewById(R.id.signuprollnofield);
        Email_ID =(EditText) findViewById(R.id.signupemailfield);
        SignUpBtn =(Button) findViewById(R.id.MainSignupBtn);
        SignInRedirector =(TextView) findViewById(R.id.Signinredirect);
        Loading_Bar = new ProgressDialog(this);

        Toast.makeText(this, "Vanakkam Da Mapla!", Toast.LENGTH_SHORT).show();

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
                Log.w(MainActivity.TAG, "Failed to read value.", error.toException());
            }
        });

        SignInRedirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,loginactivity.class);
                startActivity(intent);
            }
        });

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();
            }
        });
    }

    private void CreateAccount()
    {
        String User_name = Name.getText().toString();
        User_Roll = Roll_no.getText().toString();
        String User_Email = Email_ID.getText().toString();
        String User_Pass = Password.getText().toString();

        if (TextUtils.isEmpty(User_Roll))
        {
            Toast.makeText(this, "Write your Roll no", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(User_Pass))
        {
            Toast.makeText(this, "Enter your Password", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(User_Email))
        {
            Toast.makeText(this, "Enter your Email ID", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(User_name))
        {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
        }

        else
        {
            Loading_Bar.setTitle("Create Account");
            Loading_Bar.setMessage("Wait while we check your credentials");
            Loading_Bar.setCanceledOnTouchOutside(false);
            Loading_Bar.show();

            ValidateRollNo(User_name,User_Roll,User_Pass,User_Email);
        }
    }

    public void ValidateRollNo(final String user_name, final String user_roll, final String user_pass, final String user_email)
    {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Users").child(user_roll).exists()))
                {
                    HashMap<String,Object> userdatamap = new HashMap<>();
                    userdatamap.put("Roll_no",user_roll);
                    userdatamap.put("Password",user_pass);
                    userdatamap.put("Name",user_name);
                    userdatamap.put("email",user_email);

                    RootRef.child("Users").child(user_roll).updateChildren(userdatamap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(MainActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                        Loading_Bar.dismiss();

                                        Intent intent = new Intent(MainActivity.this,Dashboard.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Loading_Bar.dismiss();
                                        Toast.makeText(MainActivity.this, "Account Not Created! Sorry", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }

                else
                {
                    Toast.makeText(MainActivity.this, "This roll number " + user_roll + " already exists.", Toast.LENGTH_SHORT).show();
                    Loading_Bar.dismiss();
                    Toast.makeText(MainActivity.this, "Please try using different roll number.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this,Dashboard.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


    }

}

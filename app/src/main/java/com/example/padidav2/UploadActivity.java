package com.example.padidav2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadActivity extends AppCompatActivity {
    private TextView ShowUploads, book, Author;
    private Button chimg, upldbtn;
    private ImageView imgPreview;
    private Uri imgUrl;
    private static final int CHOOSE_IMAGE = 1;

    private StorageReference mstorageRef;
    private DatabaseReference mDataRef;
    private StorageTask mUploadTask;
    private ProgressBar pgbr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        book =(TextView) findViewById(R.id.bookname);
        Author=(TextView) findViewById((R.id.author));
        chimg=(Button) findViewById(R.id.chooseimage);
        upldbtn=(Button) findViewById(R.id.FinUploadButton);
        imgPreview=(ImageView) findViewById(R.id.ourimage);
        pgbr=findViewById(R.id.progress);
        FirebaseDatabase a;

        mstorageRef= FirebaseStorage.getInstance().getReference("uploads");
        mDataRef= FirebaseDatabase.getInstance().getReference("Users/"+MainActivity.User_Roll+"/books");

        upldbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(UploadActivity.this, "Uploading...", Toast.LENGTH_SHORT).show();
                if(mUploadTask !=null && mUploadTask.isInProgress()) {
                    Toast.makeText(UploadActivity.this, "Konjom poru machine nikkatum", Toast.LENGTH_LONG).show();
                }
                else
                {
                    upimg();
                }
            }
        });

        chimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showfilechoose();
            }
        });


        ShowUploads = (TextView) findViewById(R.id.shwuplds);

        ShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivity.this,ShowUploadsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void upimg()
    {
        if(imgUrl!=null)
        {
            StorageReference filereference = mstorageRef.child(System.currentTimeMillis()+"."+getFilesExtn(imgUrl));

            mUploadTask= filereference.putFile(imgUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            //Toast.makeText(UploadActivity.this, "overover", Toast.LENGTH_SHORT).show();
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    pgbr.setProgress(0);
                                }

                            }, 500);

                            Upload upload = new Upload(book.getText().toString().trim(),taskSnapshot.getUploadSessionUri().toString());
                            String uploadID = mDataRef.push().getKey();
                            mDataRef.child(uploadID).setValue(upload);
                            book.setText("");

                            Toast.makeText(UploadActivity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();

                            /*a= FirebaseDatabase.getInstance().getReference()
                            mDataRef= FirebaseDatabase.getInstance().getReference("Users/"+MainActivity.User_Roll+uploadID);*/

                            upload = new Upload(Author.getText().toString().trim(),taskSnapshot.getUploadSessionUri().toString());
                            String upload1ID = mDataRef.push().getKey();
                            mDataRef.child(upload1ID).setValue(upload);
                            Author.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            pgbr.setProgress((int) progress);
                        }
                    });



        }

        else
        {
            Toast.makeText(this, "File yaaru ungoppana kuduppan", Toast.LENGTH_SHORT).show();
        }


    }

    private String getFilesExtn(Uri uri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void showfilechoose()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_IMAGE && resultCode==RESULT_OK && data != null && data.getData() != null)
        {
            imgUrl = data.getData();

            Picasso.get().load(imgUrl).into(imgPreview);
        }

    }
}

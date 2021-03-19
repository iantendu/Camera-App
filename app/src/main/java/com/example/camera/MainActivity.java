package com.example.camera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    Button camera;
    Button saveImage;
    ImageView savedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.thumbnail);
        camera= findViewById(R.id.takephoto);
        saveImage = findViewById(R.id.save);
        savedImage = findViewById(R.id.saved);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open camera
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            //get capture image
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");

            //set captured image to imageview (load image on the image view)
            image.setImageBitmap(captureImage);

            //Homework
            //send the image to the gallery
            saveImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String savedImageURL = MediaStore.Images.Media.insertImage(
                            getContentResolver(),
                            captureImage,
                            "Test",
                            "Test"
                    );
                    Toast.makeText(MainActivity.this, "Image saved successfully", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

}
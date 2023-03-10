package com.zaid.firebaselearn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    Button imageUploadButton;
    String profilePictureLink;

    Uri profilePicturePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initializeUI();

        fetchImage();
        imageUploadButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

        });
    }

    private void initializeUI() {
        imageView = findViewById(R.id.image_view);
        imageUploadButton = findViewById(R.id.image_upload_button);
    }

    public static final int PICK_IMAGE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            this.profilePicturePath = data.getData();
            uploadImage();
        }
    }

    private void uploadImage() {
        // Upload Image
    }

    private void fetchImage() {
        // fetch image
    }
}
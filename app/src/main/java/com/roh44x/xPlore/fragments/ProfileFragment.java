package com.roh44x.xPlore.fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.roh44x.xPlore.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private Button openCamera, uploadPhoto;
    private static final int PICK_IMAGE_REQUEST = 101;
    private StorageReference storageRef, imageRef;
    private ImageView imageView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, null);


        storageRef = FirebaseStorage.getInstance().getReference();
        openCamera = (Button) rootView.findViewById(R.id.buttonCamera);
        imageView = (ImageView)rootView.findViewById(R.id.imageProfile);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("pics").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Task<Uri> uri = storageReference.getDownloadUrl();

        Glide.with(this )
                .load(uri)
                .into(imageView);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST)
        {
            if(resultCode == RESULT_OK){
                Uri fileUri = data.getData();
                storageRef.child("pics").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).putFile(fileUri);

            }
        }
    }

}

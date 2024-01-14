package com.example.ema.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ema.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepTwoFragment extends Fragment implements Step,View.OnClickListener {
    public static final int TAKE_PICTURE_EVENT_REQUEST_CODE = 1;
    public static final int PICK_PICTURE_FROM_GALLERY_EVENT_REQUEST_CODE = 2;
    @BindView(R.id.imageViewCamera)
    ImageView imageViewCamera;
    @BindView(R.id.imageViewGallery)
    ImageView imageViewGallery;
    @BindView(R.id.imageView)
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_two_fragment, container, false);
        ButterKnife.bind(this,v);

        imageViewCamera.setOnClickListener(this);
        imageViewGallery.setOnClickListener(this);
        return v;
    }
    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onClick(View view) {
          switch(view.getId()){
              case R.id.imageViewCamera:
                  Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  startActivityForResult(takePictureIntent, TAKE_PICTURE_EVENT_REQUEST_CODE);
                  break;

              case R.id.imageViewGallery:
                  Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                  startActivityForResult(galleryIntent, PICK_PICTURE_FROM_GALLERY_EVENT_REQUEST_CODE);
                  break;
          }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
           Uri  imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (data.getExtras() != null) {
            // Slika je snimljena kamerom
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);

        }
    }


}

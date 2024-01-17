package com.example.ema.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageCapture;
import androidx.fragment.app.Fragment;
import com.example.ema.R;
import com.github.mmin18.widget.RealtimeBlurView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepTwoFragment extends Fragment implements BlockingStep,View.OnClickListener {
    public static final int TAKE_PICTURE_EVENT_REQUEST_CODE = 1;
    public static final int PICK_PICTURE_FROM_GALLERY_EVENT_REQUEST_CODE = 2;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.fabCamera)
    FloatingActionButton fabCamera;
    Dialog dialog;
    Bitmap bitmap;
    private Vibrator vibrator;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;
    private boolean imageAdded = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_two_fragment, container, false);
        ButterKnife.bind(this,v);

        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        fabCamera.setOnClickListener(this);

        GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                vibrate();
                showFullScreenImage();
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);

                return true;
            }
        });

        return v;
    }




    private void vibrate() {
        // Vibrirajte 500 milisekundi
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(100);
        }
    }

    private void showFullScreenImage() {
        dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_full_screen_image);

        RealtimeBlurView realtimeBlurView = dialog.findViewById(R.id.realtimeBlurView);
        realtimeBlurView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ImageView fullScreenImageView = dialog.findViewById(R.id.fullScreenImageView);
        fullScreenImageView.setImageBitmap(bitmap);

        dialog.show();
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
              case R.id.fabCamera:

                  MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext(), R.style.CustomMaterialAlertDialog);
                  builder.setTitle("Choose an option")
                          .setItems(new CharSequence[]{"Take a picture", "Open from gallery"}, (dialog, which) -> {
                              // Handle the selection
                              switch (which) {
                                  case 0:
                                       Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                       startActivityForResult(cameraIntent, 1);
                                      break;
                                  case 1:
                                      Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                      startActivityForResult(galleryIntent, PICK_PICTURE_FROM_GALLERY_EVENT_REQUEST_CODE);
                                      break;
                              }
                          })
                          .setNegativeButton("Cancel", (dialog, which) -> {
                              // Handle cancel button click
                          });

                   builder.create();
                   builder.show();
                  break;

//              case R.id.imageViewGallery:
//                  Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                  startActivityForResult(galleryIntent, PICK_PICTURE_FROM_GALLERY_EVENT_REQUEST_CODE);
//                  break;
          }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
           Uri  imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
                imageAdded = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (data.getExtras() != null) {
            // Slika je snimljena kamerom
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
            imageAdded = true;

        }
    }


    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if(imageAdded) {
            callback.goToNextStep();
        }else{
            Toast.makeText(getContext(), "Please upload a picture to continue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }
}

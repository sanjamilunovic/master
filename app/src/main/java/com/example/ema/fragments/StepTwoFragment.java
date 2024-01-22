package com.example.ema.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.ema.AddReimbursementActivity;
import com.example.ema.R;
import com.example.ema.helpers.CameraHelper;
import com.example.ema.helpers.PermissionHelper;
import com.example.ema.viewmodels.ReimbursementViewModel;
import com.github.mmin18.widget.RealtimeBlurView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;


import java.io.File;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepTwoFragment extends Fragment implements BlockingStep,View.OnClickListener {
    public static final int TAKE_PICTURE_EVENT_REQUEST_CODE = 1;
    public static final int PICK_PICTURE_FROM_GALLERY_EVENT_REQUEST_CODE = 2;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.fabMain)
    FloatingActionButton fabMain;
    @BindView(R.id.fabCamera)
    FloatingActionButton fabCamera;
    @BindView(R.id.fabGallery)
    FloatingActionButton fabGallery;
    Dialog dialog;
    Bitmap bitmap;
    private boolean imageAdded = false;
    private Float translationY=100f;
    Animation fabOpen,fabClose,fabRClockwise,fabAntiRClockwise;
    LinearInterpolator interpolator=new LinearInterpolator();
    private Boolean isMenuOpen=false;
    private ReimbursementViewModel reimbursementViewModel;
    public String cameraOutput;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_two_fragment, container, false);
        ButterKnife.bind(this,v);

        initFabMain();
        reimbursementViewModel = ((AddReimbursementActivity)getActivity()).reimbursementViewModel;

        imageView.setVisibility(View.GONE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFullScreenImage();
            }
        });

        return v;
    }

    public void initFabMain(){
        fabMain.setOnClickListener(this);
        fabCamera.setOnClickListener(this);
        fabGallery.setOnClickListener(this);
        fabCamera.setVisibility(View.GONE);
        fabGallery.setVisibility(View.GONE);
        fabGallery.setTranslationY(100f);
        fabCamera.setTranslationY(100f);
        fabOpen= AnimationUtils.loadAnimation(getContext(),R.anim.fab_open);
        fabClose= AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        fabRClockwise=AnimationUtils.loadAnimation(getContext(),R.anim.rotate_clockwise);
        fabAntiRClockwise=AnimationUtils.loadAnimation(getContext(),R.anim.rotate_anticlockwise);
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
              case R.id.fabMain:
                  if(isMenuOpen){
                      closeMenu();
                  }else {
                      openMenu();
                  }
                  break;

              case R.id.fabCamera:
                  cameraOutput = CameraHelper.getOutputMediaFile(getActivity());
                  Uri photoURI;

                  if (!PermissionHelper.checkPermissions(getContext(), PermissionHelper.getPicturePermissions())) {
                      Toast.makeText(getContext(), "No permission to take a picture", Toast.LENGTH_SHORT).show();
                      return;
                  }
                  StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                  StrictMode.setVmPolicy(builder.build());

                  final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  if (Build.VERSION.SDK_INT < 26)
                      photoURI = Uri.fromFile(new File(cameraOutput));
                  else
                      photoURI = FileProvider.getUriForFile(getContext(),  ".fileprovider", new File(cameraOutput));
                  intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                  intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                  intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, 90);
                  try {
                     startActivityForResult(intent, 1);

                  } catch (Exception ex) {
                      ex.printStackTrace();

                  }
                  closeMenu();
                  break;

              case R.id.fabGallery:
                  Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                          startActivityForResult(galleryIntent, PICK_PICTURE_FROM_GALLERY_EVENT_REQUEST_CODE);
                          closeMenu();

                  break;


          }
    }

    private void openMenu(){
        isMenuOpen=!isMenuOpen;
        fabCamera.setVisibility(View.VISIBLE);
        fabGallery.setVisibility(View.VISIBLE);
        fabMain.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(500).start();
        fabCamera.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(500).start();
        fabGallery.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(500).start();

    }

    private void closeMenu(){
        isMenuOpen=!isMenuOpen;
        fabMain.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(500).start();
        fabCamera.animate().translationY(50f).alpha(0f).setInterpolator(interpolator).setDuration(500).start();
        fabGallery.animate().translationY(50f).alpha(0f).setInterpolator(interpolator).setDuration(500).start();
        fabCamera.setVisibility(View.GONE);
        fabGallery.setVisibility(View.GONE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
                imageAdded = true;
                reimbursementViewModel.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                File output = new File(cameraOutput);
                Uri photoURI;
                if (Build.VERSION.SDK_INT < 26)
                    photoURI = Uri.fromFile(output);
                else
                    photoURI = FileProvider.getUriForFile(getContext(), ".fileprovider", output);

                InputStream inputStream = getContext().getContentResolver().openInputStream(photoURI);
                ExifInterface exif = new ExifInterface(inputStream);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                bitmap = android.provider.MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photoURI);
                bitmap = rotateBitmap(bitmap, orientation);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
                imageAdded = true;
                reimbursementViewModel.setImageBitmap(bitmap);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                return bitmap;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if(imageAdded) {
            PermissionHelper.checkAndSetPermissions(getActivity(), PermissionHelper.getRecordAudioPermissions(), 0);
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
          callback.goToPrevStep();
    }


}

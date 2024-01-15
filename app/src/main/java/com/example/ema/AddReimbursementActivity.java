package com.example.ema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ema.adapters.StepperAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddReimbursementActivity extends AppCompatActivity implements StepperLayout.StepperListener{
    @BindView(R.id.stepperLayout)
    StepperLayout stepperLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reimbursment);
        ButterKnife.bind(this);

        stepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));
        stepperLayout.setListener(this);


    }

    @Override
    public void onCompleted(View completeButton) {
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
    }

    @Override
    public void onError(VerificationError verificationError) {
//        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
//        Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onReturn() {

    }
}
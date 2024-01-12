package com.example.ema;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.ema.adapters.StepperAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.stepstone.stepper.StepperLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddReimbursementActivity extends AppCompatActivity{
    @BindView(R.id.stepperLayout)
    StepperLayout stepperLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reimbursment);
        ButterKnife.bind(this);

        stepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));


    }

}
package com.example.ema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ema.adapters.StepperAdapter;
import com.example.ema.viewmodels.ReimbursementViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddReimbursementActivity extends AppCompatActivity implements StepperLayout.StepperListener{
    public enum DataHolder {
        INSTANCE;

        private ArrayList<ReimbursementViewModel> lstReimbursements;

        public static boolean hasData() {

            return INSTANCE.lstReimbursements != null;
        }

        public static void setData(final ArrayList<ReimbursementViewModel> objectList) {
            INSTANCE.lstReimbursements = objectList;
        }

        public static ArrayList<ReimbursementViewModel> getData() {
            final ArrayList<ReimbursementViewModel> retList = INSTANCE.lstReimbursements;
            INSTANCE.lstReimbursements = null;
            return retList;
        }
    }
    @BindView(R.id.stepperLayout)
    StepperLayout stepperLayout;
    @BindView(R.id.materialToolbar)
    MaterialToolbar materialToolbar;

    public ArrayList<ReimbursementViewModel>lstReimbursements = new ArrayList<ReimbursementViewModel>();
    public ReimbursementViewModel reimbursementViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reimbursment);
        ButterKnife.bind(this);

        if (AddReimbursementActivity.DataHolder.hasData()) {
            lstReimbursements = AddReimbursementActivity.DataHolder.getData();
        }

        reimbursementViewModel = new ReimbursementViewModel();

        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        stepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));
        stepperLayout.setListener(this);


    }

    @Override
    public void onCompleted(View completeButton) {
//      Intent intent = new Intent(this, MainActivity.class);
//      startActivity(intent);
        stepperLayout.setVisibility(View.GONE);
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
package com.example.ema.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ema.R;
import com.example.ema.viewmodels.ReimbursementViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepOneFragment extends Fragment implements BlockingStep {
    @BindView(R.id.spinner)
    MaterialAutoCompleteTextView spinner;
    private boolean isStepValid = false;
    private ReimbursementViewModel reimbursement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_one_fragment, container, false);
        ButterKnife.bind(this,v);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.students, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.drop_down_item);
        reimbursement = ReimbursementViewModel.getInstance();
        reimbursement.setDate(new Date());

        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 reimbursement.setStudent(parent.getItemAtPosition(position).toString());
                 isStepValid = true;

            }
        });

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
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
             if(isStepValid){
                 callback.goToNextStep();
             }else{
                 Toast.makeText(getContext(), "Please select a student to continue", Toast.LENGTH_SHORT).show();
             }
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }
}

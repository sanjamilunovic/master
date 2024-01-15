package com.example.ema.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.ema.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepThreeFragment extends Fragment implements Step, View.OnClickListener{
    @BindView(R.id.tilPurchaseDate)
    TextInputLayout tilPurchaseDate;
    @BindView(R.id.etPurchaseDate)
    TextInputEditText etPurchaseDate;
    @BindView(R.id.spinnerCategory)
    MaterialAutoCompleteTextView spinnerCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step_three, container, false);
        ButterKnife.bind(this,v);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.drop_down_item);

        spinnerCategory.setAdapter(adapter);

        tilPurchaseDate.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });


        return v;
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTheme(R.style.ThemeOverlay_App_MaterialCalendar)
                .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
            etPurchaseDate.setText(datePicker.getHeaderText());
        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }
    @Override
    public void onClick(View v) {

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
}

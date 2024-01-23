package com.example.ema.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bugsnag.android.Bugsnag;
import com.example.ema.AddReimbursementActivity;
import com.example.ema.MainActivity;
import com.example.ema.R;
import com.example.ema.adapters.AddItemAdapter;
import com.example.ema.adapters.ListItemReimbursementAdapter;
import com.example.ema.viewmodels.ItemViewModel;
import com.example.ema.viewmodels.ReimbursementViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepThreeFragment extends Fragment implements BlockingStep {
    public static final Integer RECORD_AUDIO_INVOICE_RESULT_CODE = 1;
    public static final Integer RECORD_AUDIO_DESCRIPTION_RESULT_CODE = 2;
    public static final Integer RECORD_AUDIO_AMOUNT_RESULT_CODE = 3;
    public static final Integer RECORD_AUDIO_EDUCATIONAL_BENEFIT_RESULT_CODE = 4;
    @BindView(R.id.mainCont)
    LinearLayout mainCont;
    @BindView(R.id.subLayou1)
    LinearLayout subLayou1;
    @BindView(R.id.subLayou2)
    LinearLayout subLayout2;
    @BindView(R.id.buttonAddItem)
    Button buttonAddItem;
    @BindView(R.id.buttonSaveItem)
    Button buttonSaveItem;
    @BindView(R.id.buttonSubmitForApproval)
    Button buttonSubmitForApproval;
    @BindView(R.id.buttonRequestAnotherReimbursement)
    Button buttonRequestAnotherReimbursement;
    @BindView(R.id.buttonCheckTheStatus)
    Button buttonCheckTheStatus;
    @BindView(R.id.itemRecyclerView)
    RecyclerView itemRecyclerView;
    @BindView(R.id.addItemRecyclerView)
    RecyclerView addItemRecyclerView;

    private ArrayList<ReimbursementViewModel> lstReimbursement;
    private ReimbursementViewModel reimbursementViewModel;
    private ArrayList<ItemViewModel> items;
    private AddItemAdapter addItemAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step_three, container, false);
        ButterKnife.bind(this, v);


        items = new ArrayList<>();
        lstReimbursement = ((AddReimbursementActivity) getActivity()).lstReimbursements;
        reimbursementViewModel = ((AddReimbursementActivity) getActivity()).reimbursementViewModel;

        buttonSubmitForApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainCont.setVisibility(View.GONE);
                buttonAddItem.setVisibility(View.GONE);
                subLayou1.setVisibility(View.GONE);
                subLayout2.setVisibility(View.VISIBLE);
            }
        });
        buttonRequestAnotherReimbursement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reimbursementViewModel.setItems(items);
                lstReimbursement.add(reimbursementViewModel);
                AddReimbursementActivity.DataHolder.setData(lstReimbursement);
                Intent intent = new Intent(getContext(), AddReimbursementActivity.class);
                startActivity(intent);
            }
        });

        buttonCheckTheStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reimbursementViewModel.setItems(items);
                lstReimbursement.add(reimbursementViewModel);
                Intent intent = new Intent(getContext(), MainActivity.class);
                MainActivity.DataHolder.setData(lstReimbursement);
                startActivity(intent);
            }
        });


        buttonSaveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addItemAdapter.validateForm()) {
                    addItemAdapter.saveData();
                    buttonSaveItem.setVisibility(View.GONE);
                    buttonAddItem.setVisibility(View.VISIBLE);

                }

            }
        });
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSaveItem.setVisibility(View.VISIBLE);
                buttonAddItem.setVisibility(View.GONE);
                addItemAdapter.showNewItem(items);


            }
        });

        items.add(new ItemViewModel());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        addItemRecyclerView.setLayoutManager(layoutManager);
        addItemAdapter = new AddItemAdapter(items, getContext(), this);
        addItemRecyclerView.setAdapter(addItemAdapter);


        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            try {
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String recognizedText = results.get(0);
                if (requestCode == RECORD_AUDIO_INVOICE_RESULT_CODE) {
                    addItemAdapter.fillForm(RECORD_AUDIO_INVOICE_RESULT_CODE, recognizedText);
                } else if (requestCode == RECORD_AUDIO_AMOUNT_RESULT_CODE) {
                    addItemAdapter.fillForm(RECORD_AUDIO_AMOUNT_RESULT_CODE, recognizedText);
                } else if (requestCode == RECORD_AUDIO_EDUCATIONAL_BENEFIT_RESULT_CODE) {
                    addItemAdapter.fillForm(RECORD_AUDIO_EDUCATIONAL_BENEFIT_RESULT_CODE, recognizedText);
                } else if (requestCode == RECORD_AUDIO_DESCRIPTION_RESULT_CODE) {
                    addItemAdapter.fillForm(RECORD_AUDIO_DESCRIPTION_RESULT_CODE, recognizedText);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Bugsnag.notify(ex);
            }

        }
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

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        try{
        if (addItemAdapter.getItemCount() == 0) {
            Toast.makeText(getContext(), "To continue, you must add at least one item.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (addItemAdapter.validateForm()) {
            addItemAdapter.saveData();

            mainCont.setVisibility(View.GONE);
            buttonSaveItem.setVisibility(View.GONE);
            buttonAddItem.setVisibility(View.GONE);
            buttonAddItem.setVisibility(View.GONE);
            subLayou1.setVisibility(View.VISIBLE);


            callback.getStepperLayout().setCompleteButtonColor(getResources().getColor(R.color.white));
            callback.getStepperLayout().setCompleteButtonEnabled(false);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            itemRecyclerView.setLayoutManager(layoutManager);
            ListItemReimbursementAdapter adapter = new ListItemReimbursementAdapter(items, getContext(), reimbursementViewModel);
            itemRecyclerView.setAdapter(adapter);

        }
          }catch(Exception ex){
            ex.printStackTrace();
            Bugsnag.notify(ex);
        }

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        try{
        mainCont.setVisibility(View.VISIBLE);
        buttonAddItem.setVisibility(View.VISIBLE);
        subLayou1.setVisibility(View.GONE);
        subLayout2.setVisibility(View.GONE);
        callback.getStepperLayout().setCompleteButtonColor(getResources().getColor(R.color.primaryRed));
        callback.getStepperLayout().setCompleteButtonEnabled(true);
        }catch(Exception ex){
            ex.printStackTrace();
            Bugsnag.notify(ex);
        }

    }


    public void setVoiceRecognizer(int requestCode) {
        try{
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            // identifying application to the Google service
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
            // hint in the dialog
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Try saying something");
            // hint to the recognizer about what the user is going to say
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            // number of results
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
            // recognition language
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
            startActivityForResult(intent, requestCode);
        }catch(Exception ex){
            ex.printStackTrace();
            Bugsnag.notify(ex);
        }

    }

    public void changeButtons() {
        buttonAddItem.setVisibility(View.VISIBLE);
        buttonSaveItem.setVisibility(View.GONE);

    }
    public void updateButtons() {
        buttonAddItem.setVisibility(View.GONE);
        buttonSaveItem.setVisibility(View.VISIBLE);

    }
}

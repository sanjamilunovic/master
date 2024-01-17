package com.example.ema.fragments;

import android.Manifest;
import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.ema.AddReimbursementActivity;
import com.example.ema.MainActivity;
import com.example.ema.R;
import com.example.ema.viewmodels.ItemViewModel;
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

public class StepThreeFragment extends Fragment implements BlockingStep{
    public static final Integer RECORD_AUDIO_RESULT_CODE = 1;
    public static final Integer RECORD_AUDIO_INVOICE_RESULT_CODE = 2;
    public static final Integer RECORD_AUDIO_AMOUNT_RESULT_CODE = 3;
    public static final Integer RECORD_AUDIO_EDUCATIONAL_BENEFIT_RESULT_CODE = 4;
//    private SpeechRecognizer speechRecognizer;
    @BindView(R.id.tilPurchaseDate)
    TextInputLayout tilPurchaseDate;
    @BindView(R.id.etPurchaseDate)
    TextInputEditText etPurchaseDate;
    @BindView(R.id.tilInvoice)
    TextInputLayout tilInvoice;
    @BindView(R.id.etInvoice)
    TextInputEditText etInvoice;
    @BindView(R.id.spinnerCategory)
    MaterialAutoCompleteTextView spinnerCategory;
    @BindView(R.id.tilType)
    TextInputLayout tilType;
    @BindView(R.id.tilDescription)
    TextInputLayout tilDescription;
    @BindView(R.id.tilPayment)
    TextInputLayout tilPayment;
    @BindView(R.id.tilCategory)
    TextInputLayout tilCategory;
    @BindView(R.id.tilAmount)
    TextInputLayout tilAmount;
    @BindView(R.id.tilEducationalBenefit)
    TextInputLayout tilEducationalBenefit;
    @BindView(R.id.etAmount)
    TextInputEditText etAmount;
    @BindView(R.id.etEducationalBenefit)
    TextInputEditText etEducationalBenefit;
    @BindView(R.id.mainCont)
    LinearLayout mainCont;
    @BindView(R.id.subLayou1)
    LinearLayout subLayou1;
    @BindView(R.id.subLayou2)
    LinearLayout subLayout2;
    @BindView(R.id.buttonAddItem)
    Button buttonAddItem;
    @BindView(R.id.txtItem)
    TextView txtItem;
    @BindView(R.id.buttonSubmitForApproval)
    Button buttonSubmitForApproval;
    @BindView(R.id.buttonRequestAnotherReimbursement)
    Button buttonRequestAnotherReimbursement;
    @BindView(R.id.buttonCheckTheStatus)
    Button buttonCheckTheStatus;

    private ArrayList<ItemViewModel>items;
    private int itemPosition=1;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step_three, container, false);
        ButterKnife.bind(this,v);

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }

       // speechRecognizer =  SpeechRecognizer.createSpeechRecognizer(getContext());
        items = new ArrayList<>();
        txtItem.setText("ITEM" + " " +  itemPosition);

//        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//
//        speechRecognizer.setRecognitionListener(new RecognitionListener() {
//            @Override
//            public void onReadyForSpeech(Bundle bundle) {
//
//            }
//
//            @Override
//            public void onBeginningOfSpeech() {
////                etInvoice.setText("");
////                etInvoice.setHint("Listening...");  Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//    // identifying your application to the Google service
//
//            }
//
//            @Override
//            public void onRmsChanged(float v) {
//
//            }
//
//            @Override
//            public void onBufferReceived(byte[] bytes) {
//
//            }
//
//            @Override
//            public void onEndOfSpeech() {
//
//            }
//
//            @Override
//            public void onError(int i) {
//
//            }
//
//            @Override
//            public void onResults(Bundle bundle) {
//                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//                etInvoice.setText(data.get(0));
//
//
//            }
//
//            @Override
//            public void onPartialResults(Bundle bundle) {
//
//            }
//
//            @Override
//            public void onEvent(int i, Bundle bundle) {
//
//            }
//        });

        buttonSubmitForApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainCont.setVisibility(View.GONE);
                subLayou1.setVisibility(View.GONE);
                subLayout2.setVisibility(View.VISIBLE);
            }
        });
        buttonRequestAnotherReimbursement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent = new Intent(getContext(), AddReimbursementActivity.class);
           startActivity(intent);
            }
        });

        buttonCheckTheStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()){
                    ItemViewModel itemViewModel = new ItemViewModel();
                    itemViewModel.setPurchaseDate(new Date(etPurchaseDate.getText().toString().trim()));
                    itemViewModel.setInvoice(etInvoice.getText().toString().trim());
                    itemViewModel.setCategory(spinnerCategory.getText().toString().trim());
                    itemViewModel.setAmount(Integer.valueOf(etAmount.getText().toString().trim()));
                    itemViewModel.setEducationalBenefit(etEducationalBenefit.getText().toString().trim());

                    items.add(itemViewModel);
                    itemPosition++;
                    txtItem.setText("ITEM" + " " + itemPosition);
                    clearInputs();
                }

            }
        });

        tilInvoice.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilInvoice.requestFocus();
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
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US");
                startActivityForResult(intent, RECORD_AUDIO_INVOICE_RESULT_CODE);
            }
        });

        tilAmount.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilAmount.requestFocus();
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
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US");
                startActivityForResult(intent, RECORD_AUDIO_AMOUNT_RESULT_CODE);
            }
        });

        tilEducationalBenefit.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilEducationalBenefit.requestFocus();
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
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US");
                startActivityForResult(intent, RECORD_AUDIO_EDUCATIONAL_BENEFIT_RESULT_CODE);
            }
        });


        tilPurchaseDate.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.drop_down_item);

        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tilType.setVisibility(View.VISIBLE);
                tilDescription.setVisibility(View.VISIBLE);
                tilPayment.setVisibility(View.VISIBLE);
                tilCategory.setError(null);

            }
        });

        etPurchaseDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(isFieldValid(etPurchaseDate)) {
                        etPurchaseDate.setError(null);
                    }else{
                        tilPurchaseDate.setError("Purchase date required.");
                    }
                }
            }
        });


        etInvoice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(isFieldValid(etInvoice)) {
                        etInvoice.setError(null);
                    }else{
                        tilInvoice.setError("Invoice # required.");
                    }
                }
            }
        });
        spinnerCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(isDropDownFieldValid(spinnerCategory)) {
                        spinnerCategory.setError(null);
                    }else{
                        tilCategory.setError("Category required.");
                    }
                }
            }
        });

        etAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(isFieldValid(etAmount)) {
                        etAmount.setError(null);
                    }else{
                        tilAmount.setError("Amount required.");
                    }
                }
            }
        });
        etEducationalBenefit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(isFieldValid(etEducationalBenefit)) {
                        etEducationalBenefit.setError(null);
                    }else{
                        tilEducationalBenefit.setError("Educational benefit required.");
                    }
                }
            }
        });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( resultCode == Activity.RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String recognizedText = results.get(0);
            if(requestCode == RECORD_AUDIO_INVOICE_RESULT_CODE) {
                etInvoice.setText(recognizedText);
                tilInvoice.setError(null);
            }else if(requestCode == RECORD_AUDIO_AMOUNT_RESULT_CODE){
                etAmount.setText(recognizedText);
                tilAmount.setError(null);
            }else if(requestCode == RECORD_AUDIO_EDUCATIONAL_BENEFIT_RESULT_CODE){
                etEducationalBenefit.setText(recognizedText);
                tilEducationalBenefit.setError(null);
            }

        }
    }

    private void clearInputs(){
        etPurchaseDate.setText(null);
        tilPurchaseDate.clearFocus();
        tilPurchaseDate.setError(null);
        etAmount.setText(null);
        tilAmount.clearFocus();
        tilAmount.setError(null);
        etInvoice.setText(null);
        tilInvoice.clearFocus();
        tilInvoice.setError(null);
        spinnerCategory.setText(null);
        tilCategory.clearFocus();
        tilCategory.setError(null);
        etEducationalBenefit.setText(null);
        tilEducationalBenefit.clearFocus();
        tilEducationalBenefit.setError(null);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO},RECORD_AUDIO_RESULT_CODE);
        }
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTheme(R.style.ThemeOverlay_App_MaterialCalendar)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                etPurchaseDate.setText(formatSelectedDate(selection));
                tilPurchaseDate.setError(null);

        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }

    private String formatSelectedDate(Long selectedDate) {
        Date date = new Date(selectedDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        return sdf.format(date);
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
        if(validateForm()){
            ItemViewModel itemViewModel = new ItemViewModel();
            itemViewModel.setPurchaseDate(new Date(etPurchaseDate.getText().toString().trim()));
            itemViewModel.setInvoice(etInvoice.getText().toString().trim());
            itemViewModel.setCategory(spinnerCategory.getText().toString().trim());
            itemViewModel.setAmount(Integer.valueOf(etAmount.getText().toString().trim()));
            itemViewModel.setEducationalBenefit(etEducationalBenefit.getText().toString().trim());

            items.add(itemViewModel);
            mainCont.setVisibility(View.GONE);
            subLayou1.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
//        mainCont.setVisibility(View.VISIBLE);
//        subCont.setVisibility(View.GONE);
        callback.goToPrevStep();
    }

    private boolean validateForm() {
        if (isFormValid()) {
            return true;
        } else {
            if (!isFieldValid(etPurchaseDate)) {
                tilPurchaseDate.setError("Purchase date required.");
                tilPurchaseDate.setErrorIconDrawable(null);
                tilPurchaseDate.requestFocus();
            } else {
                tilPurchaseDate.setError(null);
            }

            if (!isFieldValid(etInvoice)) {
                tilInvoice.setError("Invoice # required.");
                tilInvoice.setErrorIconDrawable(null);
                tilInvoice.requestFocus();
            } else {
                tilInvoice.setError(null);
            }

            if (!isDropDownFieldValid(spinnerCategory)) {
                tilCategory.setError("Category required.");
                tilCategory.requestFocus();
            } else {
                tilCategory.setError(null);
            }

            if (!isFieldValid(etAmount)) {
                tilAmount.setError("Amount required.");
                tilAmount.setErrorIconDrawable(null);
                tilAmount.requestFocus();
            } else {
                tilAmount.setError(null);
            }
            if (!isFieldValid(etEducationalBenefit)) {
                tilEducationalBenefit.setError("Educational benefit required.");
                tilEducationalBenefit.setErrorIconDrawable(null);
                tilEducationalBenefit.requestFocus();
            } else {
                tilEducationalBenefit.setError(null);
            }

        }
        return false;
    }

    private boolean isFormValid() {
        return isFieldValid(etPurchaseDate) && isFieldValid(etInvoice) && isDropDownFieldValid(spinnerCategory) && isFieldValid(etAmount) && isFieldValid(etEducationalBenefit) ;
    }

    private boolean isFieldValid(TextInputEditText editText) {
        String text = editText.getText().toString().trim();
        return !TextUtils.isEmpty(text);
    }
    private boolean isDropDownFieldValid(MaterialAutoCompleteTextView field) {
        String text = field.getText().toString().trim();
        return !TextUtils.isEmpty(text);
    }
}

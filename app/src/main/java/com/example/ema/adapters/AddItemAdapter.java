package com.example.ema.adapters;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.ema.R;
import com.example.ema.fragments.StepThreeFragment;
import com.example.ema.helpers.PermissionHelper;
import com.example.ema.viewmodels.ItemViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddItemAdapter extends RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder> {
    private ArrayList<ItemViewModel> lstItems;
    private Context context;
    AddItemAdapter.AddItemViewHolder currentViewHolder;
    private StepThreeFragment fragment;
    private int lastSelectedPosition = 0;
    public static final Integer RECORD_AUDIO_INVOICE_RESULT_CODE = 1;
    public static final Integer RECORD_AUDIO_DESCRIPTION_RESULT_CODE = 2;
    public static final Integer RECORD_AUDIO_AMOUNT_RESULT_CODE = 3;
    public static final Integer RECORD_AUDIO_EDUCATIONAL_BENEFIT_RESULT_CODE = 4;
    private boolean isExpanded = false;


    public AddItemAdapter(ArrayList<ItemViewModel> lstItems, Context context, StepThreeFragment fragment) {
        this.lstItems = lstItems;
        this.context = context;
        this.fragment = fragment;

    }

    @Override
    public int getItemCount() {
        return lstItems.size();
    }

    @Override
    public AddItemAdapter.AddItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_add_item, parent, false);

        AddItemAdapter.AddItemViewHolder holder = new AddItemAdapter.AddItemViewHolder(v);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(context, R.array.categories, android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.setDropDownViewResource(R.layout.drop_down_item);
        holder.spinnerCategory.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(context, R.array.types, android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(R.layout.drop_down_item);
        holder.spinnerType.setAdapter(typeAdapter);

        ArrayAdapter<CharSequence> vendorAdapter = ArrayAdapter.createFromResource(context, R.array.vendors, android.R.layout.simple_spinner_dropdown_item);
        vendorAdapter.setDropDownViewResource(R.layout.drop_down_item);
        holder.spinnerVendor.setAdapter(vendorAdapter);

        holder.tilInvoice.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermissionHelper.checkPermissions(context, PermissionHelper.getRecordAudioPermissions())) {
                    Toast.makeText(context, "No permission to record audio", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.tilInvoice.requestFocus();
                fragment.setVoiceRecognizer(RECORD_AUDIO_INVOICE_RESULT_CODE);
            }
        });

        holder.tilAmount.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermissionHelper.checkPermissions(context, PermissionHelper.getRecordAudioPermissions())) {
                    Toast.makeText(context, "No permission to record audio", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.tilAmount.requestFocus();
                fragment.setVoiceRecognizer(RECORD_AUDIO_AMOUNT_RESULT_CODE);
            }
        });

        holder.tilEducationalBenefit.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermissionHelper.checkPermissions(context, PermissionHelper.getRecordAudioPermissions())) {
                    Toast.makeText(context, "No permission to record audio", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.tilEducationalBenefit.requestFocus();
                fragment.setVoiceRecognizer(RECORD_AUDIO_EDUCATIONAL_BENEFIT_RESULT_CODE);
            }
        });

        holder.tilDescription.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermissionHelper.checkPermissions(context, PermissionHelper.getRecordAudioPermissions())) {
                    Toast.makeText(context, "No permission to record audio", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.tilDescription.requestFocus();
                fragment.setVoiceRecognizer(RECORD_AUDIO_DESCRIPTION_RESULT_CODE);
            }
        });

        holder.tilPurchaseDate.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(holder);
            }
        });

        holder.spinnerCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                holder.tilType.setVisibility(View.VISIBLE);
                holder.tilDescription.setVisibility(View.VISIBLE);
                holder.tilVendor.setVisibility(View.VISIBLE);
                holder.tilCategory.setError(null);

            }
        });

        holder.etPurchaseDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (isFieldValid(holder.etPurchaseDate)) {
                        holder.tilPurchaseDate.setError(null);
                    } else {
                        holder.tilPurchaseDate.setError("Purchase date required.");
                        holder.tilPurchaseDate.setErrorIconDrawable(null);
                    }
                }
            }
        });


        holder.etInvoice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (isFieldValid(holder.etInvoice)) {
                        holder.tilInvoice.setError(null);
                    } else {
                        holder.tilInvoice.setError("Invoice # required.");
                        holder.tilInvoice.setErrorIconDrawable(null);
                    }
                }
            }
        });
        holder.spinnerCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (isDropDownFieldValid(holder.spinnerCategory)) {
                        holder.tilCategory.setError(null);
                    } else {
                        holder.tilCategory.setError("Category required.");
                    }
                } else {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        holder.spinnerType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (isDropDownFieldValid(holder.spinnerType)) {
                        holder.tilType.setError(null);
                    } else {
                        holder.tilType.setError("Type required.");
                        holder.tilType.setErrorIconDrawable(null);
                    }
                } else {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        holder.spinnerVendor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (isDropDownFieldValid(holder.spinnerVendor)) {
                        holder.tilVendor.setError(null);
                    } else {
                        holder.tilVendor.setError("Vendor required.");
                    }
                } else {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        holder.etAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (isFieldValid(holder.etAmount)) {
                        holder.tilAmount.setError(null);
                        String userInput = holder.etAmount.getText().toString();

                        if (!TextUtils.isEmpty(userInput)) {
                            float floatValue = Float.parseFloat(userInput);
                            userInput = String.format("%.2f",floatValue);
                        }

                        holder.etAmount.setText("$" + userInput);
                    } else {
                        holder.tilAmount.setError("Amount required.");
                        holder.tilAmount.setErrorIconDrawable(null);
                    }
                }
            }
        });
        holder.etDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (isFieldValid(holder.etDescription)) {
                        holder.tilDescription.setError(null);
                    } else {
                        holder.tilDescription.setError("Description required.");
                        holder.tilDescription.setErrorIconDrawable(null);
                    }
                }
            }
        });
        holder.etEducationalBenefit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (isFieldValid(holder.etEducationalBenefit)) {
                        holder.tilEducationalBenefit.setError(null);
                    } else {
                        holder.tilEducationalBenefit.setError("Educational benefit required.");
                        holder.tilEducationalBenefit.setErrorIconDrawable(null);
                    }
                }
            }
        });

        holder.detailsLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        currentViewHolder = holder;

        return holder;
    }

    @Override
    public void onBindViewHolder(AddItemAdapter.AddItemViewHolder holder, int position) {
        ItemViewModel item = lstItems.get(position);
        try {
            holder.txtItem.setText("Item" + " " + (position + 1));
            holder.mainCont.setOnClickListener(v -> onClick(holder, position));
            holder.iconDelete.setOnClickListener(v -> deleteItem(holder, position));
            holder.imageViewExpandMore.setOnClickListener(v -> expandItem(holder,position));
            holder.imageViewCollapse.setOnClickListener(v -> collapseItem(holder,position));

            if (item.getPurchaseDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                holder.etPurchaseDate.setText(sdf.format(item.getPurchaseDate()));
            }
            if (item.getInvoice() != null) {
                holder.etInvoice.setText(String.valueOf(item.getInvoice()));
            }
            if (item.getCategory() != null) {
                holder.spinnerCategory.setText(String.valueOf(item.getCategory()));
            }
            if (item.getType() != null) {
                holder.spinnerType.setText(String.valueOf(item.getType()));
            }
            if (item.getAmount() != 0.0) {
                holder.etAmount.setText("$" + item.getAmount());
            }
            if (item.getDescription() != null) {
                holder.txtItem.setText(item.getDescription());
                holder.etDescription.setText(item.getDescription());
            }
            if (item.getVendor() != null) {
                holder.spinnerVendor.setText(String.valueOf(item.getVendor()));
            }
            if (item.getEducationalBenefit() != null) {
                holder.etEducationalBenefit.setText(item.getEducationalBenefit());
            }

            if (lstItems.size() == 1) {
                lastSelectedPosition = position;
            }

            if (lstItems.size() - 1 == position) {
                currentViewHolder = holder;
                currentViewHolder.detailsLayout.setVisibility(View.VISIBLE);
                currentViewHolder.imageViewExpandMore.setVisibility(View.GONE);
                currentViewHolder.imageViewCollapse.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    static class AddItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tilPurchaseDate)
        TextInputLayout tilPurchaseDate;
        @BindView(R.id.etPurchaseDate)
        TextInputEditText etPurchaseDate;
        @BindView(R.id.tilInvoice)
        TextInputLayout tilInvoice;
        @BindView(R.id.etInvoice)
        TextInputEditText etInvoice;
        @BindView(R.id.etDescription)
        TextInputEditText etDescription;
        @BindView(R.id.tilDescription)
        TextInputLayout tilDescription;
        @BindView(R.id.spinnerCategory)
        MaterialAutoCompleteTextView spinnerCategory;
        @BindView(R.id.spinnerType)
        MaterialAutoCompleteTextView spinnerType;
        @BindView(R.id.spinnerVendor)
        MaterialAutoCompleteTextView spinnerVendor;
        @BindView(R.id.tilType)
        TextInputLayout tilType;
        @BindView(R.id.tilVendor)
        TextInputLayout tilVendor;
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
        @BindView(R.id.contMain)
        LinearLayout mainCont;
        @BindView(R.id.txtItem)
        TextView txtItem;
        @BindView(R.id.cardView)
        CardView cardViewCont;
        @BindView(R.id.detailsLayout)
        LinearLayout detailsLayout;
        @BindView(R.id.iconDelete)
        ImageView iconDelete;
        @BindView(R.id.imageViewExpandMore)
        ImageView imageViewExpandMore;
        @BindView(R.id.imageViewCollapse)
        ImageView imageViewCollapse;

        public AddItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void onClick(AddItemAdapter.AddItemViewHolder holder, int position) {
        try {
            this.lastSelectedPosition = position;
            currentViewHolder = holder;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void expandItem(AddItemAdapter.AddItemViewHolder holder,int position) {
        try {
            if(currentViewHolder==null){
                currentViewHolder = holder;
            }else{
                currentViewHolder.detailsLayout.setVisibility(View.GONE);
                currentViewHolder.imageViewExpandMore.setVisibility(View.VISIBLE);
                currentViewHolder.imageViewCollapse.setVisibility(View.GONE);
            }

            holder.detailsLayout.setVisibility(View.VISIBLE);
            holder.imageViewExpandMore.setVisibility(View.GONE);
            holder.imageViewCollapse.setVisibility(View.VISIBLE);
            currentViewHolder = holder;
            lastSelectedPosition = position;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void collapseItem(AddItemAdapter.AddItemViewHolder holder, int position) {
        try {
            currentViewHolder.detailsLayout.setVisibility(View.GONE);
            currentViewHolder.imageViewExpandMore.setVisibility(View.VISIBLE);
            currentViewHolder.imageViewCollapse.setVisibility(View.GONE);
            holder.detailsLayout.setVisibility(View.GONE);
            holder.imageViewExpandMore.setVisibility(View.VISIBLE);
            holder.imageViewCollapse.setVisibility(View.GONE);
            currentViewHolder = holder;
            lastSelectedPosition = position;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void showDatePicker(AddItemAdapter.AddItemViewHolder holder) {
        try {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTheme(R.style.ThemeOverlay_App_MaterialCalendar)
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                holder.etPurchaseDate.setText(formatSelectedDate(selection));
                holder.tilPurchaseDate.setError(null);

            });

            datePicker.show(fragment.getParentFragmentManager(), "DATE_PICKER");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String formatSelectedDate(Long selectedDate) {
        Date date = new Date(selectedDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    private boolean isFormValid() {
        try {
            if (currentViewHolder == null && lstItems.size() > 0) {
                return true;
            } else if (lstItems.size() == 0) {
                return false;
            } else if (currentViewHolder != null) {
                return isFieldValid(currentViewHolder.etPurchaseDate) && isFieldValid(currentViewHolder.etInvoice) && isDropDownFieldValid(currentViewHolder.spinnerCategory) && isFieldValid(currentViewHolder.etAmount) && isFieldValid(currentViewHolder.etEducationalBenefit)
                        && isDropDownFieldValid(currentViewHolder.spinnerType) && isDropDownFieldValid(currentViewHolder.spinnerVendor) && isFieldValid(currentViewHolder.etDescription);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private boolean isFieldValid(TextInputEditText editText) {
        String text = "";
        try {
            text = editText.getText().toString().trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return !TextUtils.isEmpty(text);

    }

    private boolean isDropDownFieldValid(MaterialAutoCompleteTextView field) {
        String text = "";
        try {
            text = field.getText().toString().trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return !TextUtils.isEmpty(text);
    }

    public boolean validateForm() {
        try {
            if (isFormValid()) {
                return true;
            } else {
                if (!isFieldValid(currentViewHolder.etPurchaseDate)) {
                    currentViewHolder.tilPurchaseDate.setError("Purchase date required.");
                    currentViewHolder.tilPurchaseDate.setErrorIconDrawable(null);
                    currentViewHolder.tilPurchaseDate.requestFocus();
                } else {
                    currentViewHolder.tilPurchaseDate.setError(null);
                }

                if (!isFieldValid(currentViewHolder.etInvoice)) {
                    currentViewHolder.tilInvoice.setError("Invoice # required.");
                    currentViewHolder.tilInvoice.setErrorIconDrawable(null);
                    currentViewHolder.tilInvoice.requestFocus();
                } else {
                    currentViewHolder.tilInvoice.setError(null);
                }

                if (!isDropDownFieldValid(currentViewHolder.spinnerCategory)) {
                    currentViewHolder.tilCategory.setError("Category required.");
                    currentViewHolder.tilCategory.requestFocus();
                } else {
                    currentViewHolder.tilCategory.setError(null);
                }

                if (!isFieldValid(currentViewHolder.etAmount)) {
                    currentViewHolder.tilAmount.setError("Amount required.");
                    currentViewHolder.tilAmount.setErrorIconDrawable(null);
                    currentViewHolder.tilAmount.requestFocus();
                } else {
                    currentViewHolder.tilAmount.setError(null);
                }
                if (!isFieldValid(currentViewHolder.etEducationalBenefit)) {
                    currentViewHolder.tilEducationalBenefit.setError("Educational benefit required.");
                    currentViewHolder.tilEducationalBenefit.setErrorIconDrawable(null);
                    currentViewHolder.tilEducationalBenefit.requestFocus();
                } else {
                    currentViewHolder.tilEducationalBenefit.setError(null);
                }

                if (!isDropDownFieldValid(currentViewHolder.spinnerType)) {
                    currentViewHolder.tilType.setError("Type required.");
                    currentViewHolder.tilType.requestFocus();
                } else {
                    currentViewHolder.tilType.setError(null);
                }
                if (!isFieldValid(currentViewHolder.etDescription)) {
                    currentViewHolder.tilDescription.setError("Description required.");
                    currentViewHolder.tilDescription.setErrorIconDrawable(null);
                    currentViewHolder.tilDescription.requestFocus();
                } else {
                    currentViewHolder.tilDescription.setError(null);
                }
                if (!isDropDownFieldValid(currentViewHolder.spinnerVendor)) {
                    currentViewHolder.tilVendor.setError("Type required.");
                    currentViewHolder.tilVendor.requestFocus();
                } else {
                    currentViewHolder.tilVendor.setError(null);
                }

            }
            return false;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void fillForm(int requestCode, String recognizedText) {
        try {
            if (requestCode == RECORD_AUDIO_INVOICE_RESULT_CODE) {
                currentViewHolder.etInvoice.setText(recognizedText);
                currentViewHolder.tilInvoice.setError(null);
            } else if (requestCode == RECORD_AUDIO_AMOUNT_RESULT_CODE) {
                currentViewHolder.etAmount.setText(recognizedText);
                currentViewHolder.tilAmount.setError(null);
            } else if (requestCode == RECORD_AUDIO_EDUCATIONAL_BENEFIT_RESULT_CODE) {
                currentViewHolder.etEducationalBenefit.setText(recognizedText);
                currentViewHolder.tilEducationalBenefit.setError(null);
            } else if (requestCode == RECORD_AUDIO_DESCRIPTION_RESULT_CODE) {
                currentViewHolder.etDescription.setText(recognizedText);
                currentViewHolder.tilDescription.setError(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveData() {
        try {
            if (currentViewHolder != null) {
                ItemViewModel itemViewModel = lstItems.get(lastSelectedPosition);
                itemViewModel.setPurchaseDate(new Date(currentViewHolder.etPurchaseDate.getText().toString().trim()));
                itemViewModel.setInvoice(currentViewHolder.etInvoice.getText().toString().trim());
                itemViewModel.setCategory(currentViewHolder.spinnerCategory.getText().toString().trim());
                String amountText = currentViewHolder.etAmount.getText().toString().replace("$", "");
                itemViewModel.setAmount(Double.valueOf(amountText));
                itemViewModel.setDescription(currentViewHolder.etDescription.getText().toString().trim());
                itemViewModel.setType(currentViewHolder.spinnerType.getText().toString().trim());
                itemViewModel.setVendor(currentViewHolder.spinnerVendor.getText().toString().trim());
                itemViewModel.setEducationalBenefit(currentViewHolder.etEducationalBenefit.getText().toString().trim());
                lstItems.set(lastSelectedPosition, itemViewModel);
                currentViewHolder.txtItem.setText(itemViewModel.getDescription());
                currentViewHolder.detailsLayout.setVisibility(View.GONE);
                currentViewHolder.imageViewExpandMore.setVisibility(View.VISIBLE);
                currentViewHolder.imageViewCollapse.setVisibility(View.GONE);
                Toast.makeText(context, "Item saved.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public void showNewItem(ArrayList<ItemViewModel> items) {
        try {
            lstItems.add(new ItemViewModel());
            lastSelectedPosition = lstItems.size() - 1;
            if (currentViewHolder != null) {
                currentViewHolder.detailsLayout.setVisibility(View.GONE);
                currentViewHolder.imageViewCollapse.setVisibility(View.GONE);
                currentViewHolder.imageViewExpandMore.setVisibility(View.VISIBLE);
            }
            notifyItemInserted(lstItems.size() - 1);
            notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void deleteItem(AddItemAdapter.AddItemViewHolder holder, int position) {
        try {
            lstItems.remove(position);
            lastSelectedPosition = position;
            fragment.changeButtons();
            currentViewHolder = holder;
            clearInputs();
            currentViewHolder = null;
            notifyItemRemoved(position);
           // notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void clearInputs() {
        try {
            if (currentViewHolder != null) {
                currentViewHolder.etPurchaseDate.setText(null);
                currentViewHolder.tilPurchaseDate.clearFocus();
                currentViewHolder.tilPurchaseDate.setError(null);
                currentViewHolder.etAmount.setText(null);
                currentViewHolder.tilAmount.clearFocus();
                currentViewHolder.tilAmount.setError(null);
                currentViewHolder.etInvoice.setText(null);
                currentViewHolder.tilInvoice.clearFocus();
                currentViewHolder.tilInvoice.setError(null);
                currentViewHolder.spinnerCategory.setText(null);
                currentViewHolder.spinnerType.setText(null);
                currentViewHolder.tilType.setError(null);
                currentViewHolder.tilCategory.clearFocus();
                currentViewHolder.tilCategory.setError(null);
                currentViewHolder.etEducationalBenefit.setText(null);
                currentViewHolder.tilEducationalBenefit.clearFocus();
                currentViewHolder.tilEducationalBenefit.setError(null);
                currentViewHolder.etDescription.setText(null);
                currentViewHolder.tilDescription.clearFocus();
                currentViewHolder.tilDescription.setError(null);
                currentViewHolder.tilVendor.clearFocus();
                currentViewHolder.tilVendor.setError(null);
                currentViewHolder.spinnerVendor.setText(null);
                currentViewHolder.detailsLayout.setVisibility(View.GONE);
                currentViewHolder.imageViewExpandMore.setVisibility(View.VISIBLE);
                currentViewHolder.imageViewCollapse.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}




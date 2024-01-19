package com.example.ema;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ema.viewmodels.ItemViewModel;
import com.example.ema.viewmodels.ReimbursementViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDetailActivity extends AppCompatActivity {
    public enum DataHolder {
    INSTANCE;

    private ItemViewModel itemViewModel;

    public static boolean hasData() {

        return INSTANCE.itemViewModel != null;
    }

    public static void setData(final ItemViewModel objectList) {
        INSTANCE.itemViewModel = objectList;
    }

    public static ItemViewModel getData() {
        final ItemViewModel retList = INSTANCE.itemViewModel;
        INSTANCE.itemViewModel = null;
        return retList;
    }
}
    @BindView(R.id.txtPurchaseDate)
    TextView txtPurchaseDate;
    @BindView(R.id.txtInvoice)
    TextView txtInvoice;
    @BindView(R.id.txtCategory)
    TextView txtCategory;
    @BindView(R.id.txtItemName)
    TextView txtItemName;
    @BindView(R.id.txtType)
    TextView txtType;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.txtItemAmount)
    TextView txtItemAmount;
    @BindView(R.id.txtVendor)
    TextView txtVendor;
    @BindView(R.id.txtEducationalBenefit)
    TextView txtEducationalBenefit;
    @BindView(R.id.materialToolbar)
    MaterialToolbar toolbar;




    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

       setUpToolbar();

        if (ItemDetailActivity.DataHolder.hasData()) {
            itemViewModel = ItemDetailActivity.DataHolder.getData();
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

         if(itemViewModel!=null){
             txtItemName.setText(itemViewModel.getDescription());
             txtPurchaseDate.setText(sdf.format(date));
             txtInvoice.setText(itemViewModel.getInvoice());
             txtCategory.setText(itemViewModel.getCategory());
             txtDescription.setText(itemViewModel.getDescription());
             txtType.setText(itemViewModel.getType());
             txtVendor.setText(itemViewModel.getVendor());
             txtItemAmount.setText("$" + itemViewModel.getAmount());
             txtEducationalBenefit.setText(itemViewModel.getEducationalBenefit());
         }


    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("Item");
    }
}
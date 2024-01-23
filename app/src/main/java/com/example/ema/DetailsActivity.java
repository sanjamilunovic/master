package com.example.ema;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ema.adapters.DetailItemAdapter;
import com.example.ema.events.Events;
import com.example.ema.viewmodels.ReimbursementViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    public enum DataHolder {
        INSTANCE;

        private ReimbursementViewModel reimbursementViewModel;

        public static boolean hasData() {

            return INSTANCE.reimbursementViewModel != null;
        }

        public static void setData(final ReimbursementViewModel objectList) {
            INSTANCE.reimbursementViewModel = objectList;
        }

        public static ReimbursementViewModel getData() {
            final ReimbursementViewModel retList = INSTANCE.reimbursementViewModel;
            INSTANCE.reimbursementViewModel = null;
            return retList;
        }
    }


    @BindView(R.id.itemRecyclerView)
    RecyclerView itemRecyclerView;
    @BindView(R.id.materialToolbar)
    MaterialToolbar materialToolbar;
    @BindView(R.id.buttonCancelRequest)
    Button buttonCancelRequest;

    private ReimbursementViewModel reimbursementViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setUpToolbar();
        setTitle();
        if (DetailsActivity.DataHolder.hasData()) {
            reimbursementViewModel = DetailsActivity.DataHolder.getData();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        itemRecyclerView.setLayoutManager(layoutManager);
        DetailItemAdapter adapter = new DetailItemAdapter(reimbursementViewModel.getItems(), this);
        itemRecyclerView.setAdapter(adapter);

        buttonCancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   showAlertDialog();
            }
        });
    }

    private void showAlertDialog(){
        new MaterialAlertDialogBuilder(DetailsActivity.this)
                .setTitle("Are you sure you want to cancel request?")
                .setMessage("By clicking confirm, your reimbursement will be deleted.")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Events.CancelRequest event = new Events.CancelRequest();
                        event.reimbursementViewModel = reimbursementViewModel;
                        EventBus.getDefault().post(event);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void setUpToolbar() {
        try {
            setSupportActionBar(materialToolbar);
            final ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setDisplayShowHomeEnabled(true);
                ab.setDisplayShowTitleEnabled(false);
            }

            Drawable drawable = materialToolbar.getNavigationIcon();
            drawable.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setTitle() {
        try {
            materialToolbar.setTitle("Reimbursement Details");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
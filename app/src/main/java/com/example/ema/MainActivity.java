package com.example.ema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ema.adapters.ListItemReimbursementAdapter;
import com.example.ema.adapters.ReimbursementAdapter;
import com.example.ema.viewmodels.ReimbursementViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
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
    @BindView(R.id.materialToolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.fabAddButton)
    FloatingActionButton fabAddButton;
    @BindView(R.id.reimbursementRecyclerView)
    RecyclerView reimbursementRecyclerView;
    @BindView(R.id.mainCont)
    RelativeLayout mainCont;
    @BindView(R.id.tvReimbursementsEmpty)
    TextView tvReimbursementsEmpty;
    private ArrayList<ReimbursementViewModel>lstReimbursements;
    private ReimbursementViewModel lastReimbursement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        lstReimbursements = new ArrayList<ReimbursementViewModel>();
        if (MainActivity.DataHolder.hasData()) {
            lastReimbursement = MainActivity.DataHolder.getData();
        }

        if(lastReimbursement!=null && !lstReimbursements.contains(lastReimbursement)){
            lstReimbursements.add(lastReimbursement);
        }

        if(lstReimbursements.size()==0){
            tvReimbursementsEmpty.setVisibility(View.VISIBLE);
            tvReimbursementsEmpty.setText("No recent reimbursements");
            mainCont.setBackgroundColor(getResources().getColor(R.color.defaultColor));
        }else{
            tvReimbursementsEmpty.setVisibility(View.GONE);
            mainCont.setBackgroundColor(getResources().getColor(R.color.white));
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            reimbursementRecyclerView.setLayoutManager(layoutManager);
            ReimbursementAdapter adapter = new ReimbursementAdapter(lstReimbursements,this);
            reimbursementRecyclerView.setAdapter(adapter);
        }


        fabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddReimbursementActivity.class);
                startActivity(intent);
            }
        });




    }
}

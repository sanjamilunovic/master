package com.example.ema;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ema.adapters.ListItemReimbursementAdapter;
import com.example.ema.adapters.ReimbursementAdapter;
import com.example.ema.viewmodels.ReimbursementViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

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
    @BindView(R.id.accountIcon)
    ImageView accountIcon;
    private ArrayList<ReimbursementViewModel> lstReimbursements;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        lstReimbursements = new ArrayList<ReimbursementViewModel>();


        if (MainActivity.DataHolder.hasData()) {
            lstReimbursements = MainActivity.DataHolder.getData();
        }

        if (lstReimbursements == null || lstReimbursements.size() == 0) {
            tvReimbursementsEmpty.setVisibility(View.VISIBLE);
            tvReimbursementsEmpty.setText("No recent reimbursements");
            mainCont.setBackgroundColor(getResources().getColor(R.color.defaultColor));
        } else {
            tvReimbursementsEmpty.setVisibility(View.GONE);
            mainCont.setBackgroundColor(getResources().getColor(R.color.white));
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            reimbursementRecyclerView.setLayoutManager(layoutManager);
            ReimbursementAdapter adapter = new ReimbursementAdapter(lstReimbursements, this);
            reimbursementRecyclerView.setAdapter(adapter);
        }


        fabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddReimbursementActivity.class);
                AddReimbursementActivity.DataHolder.setData(lstReimbursements);
                startActivity(intent);
            }
        });

        accountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_logout);

                Button buttonLogout = dialog.findViewById(R.id.logoutButton);
                ImageView closeIcon = dialog.findViewById(R.id.iconClose);

                buttonLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                closeIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL);
            }
        });

    }


}


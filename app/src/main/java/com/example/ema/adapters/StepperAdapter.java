package com.example.ema.adapters;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import com.example.ema.fragments.StepOneFragment;
import com.example.ema.fragments.StepThreeFragment;
import com.example.ema.fragments.StepTwoFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class StepperAdapter extends AbstractFragmentStepAdapter {
    private static String STEP_POSITION_KEY = "step_position_key";
    public StepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        if(position==0){
            final StepOneFragment step = new StepOneFragment();
            Bundle b = new Bundle();
            b.putInt(STEP_POSITION_KEY, position);
            step.setArguments(b);
            return step;
        }else if(position==1){
            final StepTwoFragment step = new StepTwoFragment();
            Bundle b = new Bundle();
            b.putInt(STEP_POSITION_KEY, position);
            step.setArguments(b);
            return step;
        }
        else if(position==2){
            final StepThreeFragment step = new StepThreeFragment();
            Bundle b = new Bundle();
            b.putInt(STEP_POSITION_KEY, position);
            step.setArguments(b);
            return step;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return new StepViewModel.Builder(context)
                .setTitle("")
                .create();
    }
}

package com.gosuncn.cu.module.other;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gosuncn.cu.R;
import com.gosuncn.cu.databinding.ActivityAutoRecordBinding;

public class AutoRecordActivity extends AppCompatActivity {

    ActivityAutoRecordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_auto_record);
    }

    public void onSaveClick(View view) {
        binding.actvText1.save();
    }

    public void onClearClick(View view) {
        binding.actvText1.clear();
    }
    public void onSave2Click(View view) {
        binding.actvText2.save();
    }

    public void onClear2Click(View view) {
        binding.actvText2.clear();
    }
}

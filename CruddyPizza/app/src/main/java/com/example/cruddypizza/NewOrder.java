package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Calendar;

public class NewOrder extends AppCompatActivity {
    String size;
    RadioButton btn_small, btn_med, btn_lrg, btn_xlrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        btn_small = findViewById(R.id.btn_small);
        btn_med = findViewById(R.id.btn_medium);
        btn_lrg = findViewById(R.id.btn_large);
        btn_xlrg = findViewById(R.id.btn_xlarge);

        btn_small.setOnClickListener(onSizeButtonClicked);
    }

    public View.OnClickListener onSizeButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.btn_small: {
//                    size = "Small";
//                }
//
//                case R.id.btn_medium: {
//
//                }
//
//                case R.id.btn_large: {
//
//                }
//
//                case R.id.btn_xlarge: {
//
//                }
//            }
            size  = ((RadioButton)view).getText().toString();
            btn_small.setChecked(false);
            btn_med.setChecked(false);
            btn_lrg.setChecked(false);
            btn_xlrg.setChecked(false);
            ((RadioButton) view).setChecked(true);
        }
    };
}
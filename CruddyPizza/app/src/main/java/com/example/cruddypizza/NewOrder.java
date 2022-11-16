package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class NewOrder extends AppCompatActivity {
    int size = 0, numToppings = 0;
    RadioButton btn_small, btn_med, btn_lrg, btn_xlrg;
    CheckBox chk_topping1, chk_topping2, chk_topping3, chk_topping4, chk_topping5, chk_topping6, chk_topping7, chk_topping8, chk_topping9, chk_topping10, chk_topping11, chk_topping12, chk_topping13, chk_topping14;
    EditText editText_firstName, editText_lastName, editText_address, editText_phone;
    Button btn_submit;
    TableLayout toppingSection;
    Intent intent;
    ArrayList<String> order = new ArrayList<>();
    ArrayList<ArrayList<String>> table = new ArrayList<>();
    ArrayList<CheckBox> toppings = new ArrayList<>();
    Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        shake = AnimationUtils.loadAnimation(NewOrder.this, R.anim.shake);

        btn_small = findViewById(R.id.btn_small);
        btn_med = findViewById(R.id.btn_medium);
        btn_lrg = findViewById(R.id.btn_large);
        btn_xlrg = findViewById(R.id.btn_xlarge);

        toppingSection = findViewById(R.id.toppingSection);
        chk_topping1 = findViewById(R.id.chk_topping1);
        toppings.add(chk_topping1);
        chk_topping2 = findViewById(R.id.chk_topping2);
        toppings.add(chk_topping2);
        chk_topping3 = findViewById(R.id.chk_topping3);
        toppings.add(chk_topping3);
        chk_topping4 = findViewById(R.id.chk_topping4);
        toppings.add(chk_topping4);
        chk_topping5 = findViewById(R.id.chk_topping5);
        toppings.add(chk_topping5);
        chk_topping6 = findViewById(R.id.chk_topping6);
        toppings.add(chk_topping6);
        chk_topping7 = findViewById(R.id.chk_topping7);
        toppings.add(chk_topping7);
        chk_topping8 = findViewById(R.id.chk_topping8);
        toppings.add(chk_topping8);
        chk_topping9 = findViewById(R.id.chk_topping9);
        toppings.add(chk_topping9);
        chk_topping10 = findViewById(R.id.chk_topping10);
        toppings.add(chk_topping10);
        chk_topping11 = findViewById(R.id.chk_topping11);
        toppings.add(chk_topping11);
        chk_topping12 = findViewById(R.id.chk_topping12);
        toppings.add(chk_topping12);
        chk_topping13 = findViewById(R.id.chk_topping13);
        toppings.add(chk_topping13);
        chk_topping14 = findViewById(R.id.chk_topping14);
        toppings.add(chk_topping14);


        editText_firstName = findViewById(R.id.editTextFirstName);
        editText_lastName = findViewById(R.id.editTextLastName);
        editText_address = findViewById(R.id.editTextAddress);
        editText_phone = findViewById(R.id.editTextPhone);

        btn_submit = findViewById(R.id.btn_submitOrder);

        btn_small.setOnClickListener(onSizeButtonClicked);
        btn_med.setOnClickListener(onSizeButtonClicked);
        btn_lrg.setOnClickListener(onSizeButtonClicked);
        btn_xlrg.setOnClickListener(onSizeButtonClicked);

        for (CheckBox topping: toppings) {
            topping.setOnClickListener(onToppingClicked);
        }

        btn_submit.setOnClickListener(onNewClicked);

        intent = getIntent();
        if (intent != null) {
            table = (ArrayList<ArrayList<String>>) intent.getSerializableExtra("table");
            if (table == null) {
                table = new ArrayList<>();
            }
        }
    }

    public View.OnClickListener onSizeButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            btn_small.setChecked(false);
            btn_med.setChecked(false);
            btn_lrg.setChecked(false);
            btn_xlrg.setChecked(false);
            ((RadioButton) view).setChecked(true);

        }
    };

    public View.OnClickListener onToppingClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (numToppings < 3 && ((CheckBox) view).isChecked()) {
                numToppings += 1;
            } else if (!((CheckBox) view).isChecked()) {
                numToppings -=1;
            } else {
                view.startAnimation(shake);
                ((CheckBox) view).setChecked(false);
            }
        }
    };

    public View.OnClickListener onNewClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean isGood = true;
            if (numToppings == 0) {
                toppingSection.startAnimation(shake);
                isGood = false;
            }
            if (editText_firstName.getText().toString().length() == 0) {
                editText_firstName.startAnimation(shake);
                isGood = false;
            }
            if (editText_lastName.getText().toString().length() == 0) {
                editText_lastName.startAnimation(shake);
                isGood = false;
            }
            if (editText_address.getText().toString().length() == 0) {
                editText_address.startAnimation(shake);
                isGood = false;
            }
            if (editText_phone.getText().toString().length() == 0) {
                editText_phone.startAnimation(shake);
                isGood = false;
            }
            if (btn_small.isChecked()) {
                size = 1;
            } else if (btn_med.isChecked()) {
                size = 2;
            } else if (btn_lrg.isChecked()) {
                size = 3;
            } else if (btn_xlrg.isChecked()) {
                size = 4;
            }
            if (isGood) {
                order.add(editText_firstName.getText().toString());
                order.add(editText_lastName.getText().toString());
                order.add(editText_address.getText().toString());
                order.add(editText_phone.getText().toString());
                order.add(String.valueOf(size));
                for (CheckBox topping: toppings) {
                    if (topping.isChecked()) {
                        order.add(topping.getText().toString());
                    }
                }
                table.add(order);
                Intent i = new Intent(NewOrder.this, MainActivity.class);
                i.putExtra("table", table);
                startActivity(i);
            }
        }
    };
}
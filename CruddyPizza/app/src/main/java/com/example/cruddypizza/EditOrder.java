package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditOrder extends AppCompatActivity {
    int size = 0, numToppings = 0;
    long id;
    Switch swtch_lang;
    RadioButton btn_small, btn_med, btn_lrg, btn_xlrg;
    CheckBox chk_topping1, chk_topping2, chk_topping3, chk_topping4, chk_topping5, chk_topping6, chk_topping7, chk_topping8, chk_topping9, chk_topping10, chk_topping11, chk_topping12, chk_topping13, chk_topping14;
    EditText editText_firstName, editText_lastName, editText_address, editText_phone;
    Button btn_submit;
    TableLayout toppingSection;
    SharedPreferences prefs;
    Boolean isEnglish;
    static final String IS_ENGLISH_KEY = "isEnglish";
    Intent intent;
    DBAdapter db;
    Order order;
    ArrayList<CheckBox> toppings = new ArrayList<>();
    Animation shake;
    ArrayList<Integer> dbToppings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        shake = AnimationUtils.loadAnimation(EditOrder.this, R.anim.shake);
        swtch_lang = findViewById(R.id.sw_lang);

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

        btn_submit = findViewById(R.id.btn_submitEdit);

        btn_small.setOnClickListener(onSizeButtonClicked);
        btn_med.setOnClickListener(onSizeButtonClicked);
        btn_lrg.setOnClickListener(onSizeButtonClicked);
        btn_xlrg.setOnClickListener(onSizeButtonClicked);
        swtch_lang.setOnClickListener(onLanguageClicked);
        btn_submit.setOnClickListener(onSubmitClicked);

        for (CheckBox topping: toppings) {
            topping.setOnClickListener(onToppingClicked);
        }

        prefs = getSharedPreferences("settings", MODE_PRIVATE);
        isEnglish = prefs.getBoolean(IS_ENGLISH_KEY, true);
        swtch_lang.setChecked(isEnglish);
        changeLanguage(isEnglish);

        intent = getIntent();
        id = intent.getLongExtra("id", 0);

        db = new DBAdapter(this);
        db.open();
        order = db.getOrder(id);
        db.close();

        switch (order.size) {
            case (1):
                btn_small.setChecked(true);
                break;
            case (2):
                btn_med.setChecked(true);
                break;
            case (3):
                btn_lrg.setChecked(true);
                break;
            case (4):
                btn_xlrg.setChecked(true);
                break;
        }

        dbToppings.add(order.topping1);
        dbToppings.add(order.topping2);
        dbToppings.add(order.topping3);

        for (int i = 0; i < toppings.size(); i++) {
            if (dbToppings.contains(i + 1)) {
                toppings.get(i).setChecked(true);
                numToppings += 1;
            }
        }

        editText_firstName.setText(order.fName);
        editText_lastName.setText(order.lName);
        editText_address.setText(order.address);
        editText_phone.setText(order.phone);

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

    public View.OnClickListener onSubmitClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean isGood = true;
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
                order.size = size;
                order.topping1 = 0;
                int totalToppings = 0;
                for (CheckBox topping: toppings) {
                    if (topping.isChecked()) {
                        totalToppings += 1;
                        switch (totalToppings) {
                            case (1):
                                order.topping1 = toppings.indexOf(topping) + 1;
                                break;
                            case (2):
                                order.topping2 = toppings.indexOf(topping) + 1;
                                break;
                            case (3):
                                order.topping3 = toppings.indexOf(topping) + 1;
                                break;
                        }

                    }
                }
                order.fName = editText_firstName.getText().toString();
                order.lName = editText_lastName.getText().toString();
                order.address = editText_address.getText().toString();
                order.phone = editText_phone.getText().toString();

                db.open();
                if (db.updateOrder(order)){
                    if (isEnglish) {
                        Toast.makeText(getApplicationContext(), "Successfully Updated Order!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Commande mise à jour avec succès!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (isEnglish) {
                        Toast.makeText(getApplicationContext(), "Order Not Updated, Something Went Wrong!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Commande non mise à jour, quelque chose s'est mal passé!", Toast.LENGTH_SHORT).show();
                    }
                }
                db.close();
                Intent i = new Intent(EditOrder.this, MainActivity.class);
                startActivity(i);
            }
        }
    };

    public View.OnClickListener onLanguageClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            prefs = getSharedPreferences("settings", MODE_PRIVATE);
            isEnglish = swtch_lang.isChecked();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(IS_ENGLISH_KEY, isEnglish);
            editor.commit();
            changeLanguage(isEnglish);
        }
    };

    public void changeLanguage(Boolean isEnglish) {
        String[] stringsArray;
        if (isEnglish) {
            stringsArray = getResources().getStringArray(R.array.english);
        } else {
            stringsArray = getResources().getStringArray(R.array.french);
        }
        ((TextView) findViewById(R.id.txt_editTitle)).setText(stringsArray[34]);
        ((Button) findViewById(R.id.btn_submitEdit)).setText(stringsArray[41]);
        ((TextView) findViewById(R.id.txt_createSize)).setText(stringsArray[5]);
        ((RadioButton) findViewById(R.id.btn_small)).setText(stringsArray[6]);
        ((RadioButton) findViewById(R.id.btn_medium)).setText(stringsArray[7]);
        ((RadioButton) findViewById(R.id.btn_large)).setText(stringsArray[8]);
        ((RadioButton) findViewById(R.id.btn_xlarge)).setText(stringsArray[9]);
        ((TextView) findViewById(R.id.txt_createToppings)).setText(stringsArray[10]);
        ((CheckBox) findViewById(R.id.chk_topping1)).setText(stringsArray[11]);
        ((CheckBox) findViewById(R.id.chk_topping2)).setText(stringsArray[12]);
        ((CheckBox) findViewById(R.id.chk_topping3)).setText(stringsArray[13]);
        ((CheckBox) findViewById(R.id.chk_topping4)).setText(stringsArray[14]);
        ((CheckBox) findViewById(R.id.chk_topping5)).setText(stringsArray[15]);
        ((CheckBox) findViewById(R.id.chk_topping6)).setText(stringsArray[16]);
        ((CheckBox) findViewById(R.id.chk_topping7)).setText(stringsArray[17]);
        ((CheckBox) findViewById(R.id.chk_topping8)).setText(stringsArray[18]);
        ((CheckBox) findViewById(R.id.chk_topping9)).setText(stringsArray[19]);
        ((CheckBox) findViewById(R.id.chk_topping10)).setText(stringsArray[20]);
        ((CheckBox) findViewById(R.id.chk_topping11)).setText(stringsArray[21]);
        ((CheckBox) findViewById(R.id.chk_topping12)).setText(stringsArray[22]);
        ((CheckBox) findViewById(R.id.chk_topping13)).setText(stringsArray[23]);
        ((CheckBox) findViewById(R.id.chk_topping14)).setText(stringsArray[24]);
        ((TextView) findViewById(R.id.txt_createCustInfo)).setText(stringsArray[32]);
        ((EditText) findViewById(R.id.editTextFirstName)).setHint(stringsArray[28]);
        ((EditText) findViewById(R.id.editTextLastName)).setHint(stringsArray[29]);
        ((EditText) findViewById(R.id.editTextAddress)).setHint(stringsArray[30]);
        ((EditText) findViewById(R.id.editTextPhone)).setHint(stringsArray[21]);
    }
}
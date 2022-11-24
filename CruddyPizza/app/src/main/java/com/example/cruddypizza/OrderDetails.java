package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {
    long id;
    Switch swtch_lang;
    String[] stringsArray;
    Button btn_edit, btn_delete, btn_back;
    Intent intent;
    Order order;
    ListView orderDetails;
    ArrayAdapter<String> adapter;
    SharedPreferences prefs;
    Boolean isEnglish;
    static final String IS_ENGLISH_KEY = "isEnglish";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        intent = getIntent();

        swtch_lang = findViewById(R.id.sw_lang);
        btn_edit = findViewById(R.id.btn_editOrder);
        btn_delete = findViewById(R.id.btn_deleteOrder);
        btn_back = findViewById(R.id.btn_back);

        orderDetails = findViewById(R.id.orderDetails);

        btn_edit.setOnClickListener(onEditClicked);
        btn_delete.setOnClickListener(onDeleteClicked);
        btn_back.setOnClickListener(onBackClicked);
        swtch_lang.setOnClickListener(onLanguageClicked);

        id = intent.getLongExtra("id", 0);

        prefs = getSharedPreferences("settings", MODE_PRIVATE);
        isEnglish = prefs.getBoolean(IS_ENGLISH_KEY, true);
        swtch_lang.setChecked(isEnglish);

        DBAdapter db = new DBAdapter(this);
        db.open();
        order = db.getOrder(id);
        db.close();

        changeLanguage(isEnglish);
    }

    public View.OnClickListener onEditClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(OrderDetails.this, EditOrder.class);
            i.putExtra("id", id);
            startActivity(i);
        }
    };

    public View.OnClickListener onDeleteClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(OrderDetails.this, DeleteOrder.class);
            i.putExtra("id", id);
            startActivity(i);
        }
    };

    public View.OnClickListener onBackClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(OrderDetails.this, MainActivity.class);
            startActivity(i);
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
        if (isEnglish) {
            stringsArray = getResources().getStringArray(R.array.english);
        } else {
            stringsArray = getResources().getStringArray(R.array.french);
        }
        ((TextView) findViewById(R.id.txt_detailsTitle)).setText(stringsArray[33]);
        ((Button) findViewById(R.id.btn_editOrder)).setText(stringsArray[34]);
        ((Button) findViewById(R.id.btn_deleteOrder)).setText(stringsArray[35]);
        ((Button) findViewById(R.id.btn_back)).setText(stringsArray[36]);

        //Populate the ListView inside this method so I can easily change the language. Somewhat last minute fix.
        ArrayList<String> listData = new ArrayList<>();

        ArrayList<String> toppings = new ArrayList<>();
        ArrayList<Integer> dbToppings = new ArrayList<>();
        dbToppings.add(order.topping1);
        dbToppings.add(order.topping2);
        dbToppings.add(order.topping3);

        for (int topping : dbToppings) {
            switch (topping){
                case (1):
                    toppings.add(stringsArray[11]);
                    break;
                case (2):
                    toppings.add(stringsArray[12]);
                    break;
                case (3):
                    toppings.add(stringsArray[13]);
                    break;
                case (4):
                    toppings.add(stringsArray[14]);
                    break;
                case (5):
                    toppings.add(stringsArray[15]);
                    break;
                case (6):
                    toppings.add(stringsArray[16]);
                    break;
                case (7):
                    toppings.add(stringsArray[17]);
                    break;
                case (8):
                    toppings.add(stringsArray[18]);
                    break;
                case (9):
                    toppings.add(stringsArray[19]);
                    break;
                case (10):
                    toppings.add(stringsArray[20]);
                    break;
                case (11):
                    toppings.add(stringsArray[21]);
                    break;
                case (12):
                    toppings.add(stringsArray[22]);
                    break;
            }
        }


        listData.add(stringsArray[42] + ": " + order.id);
        listData.add(stringsArray[5] + ": " + order.size);
        listData.add(stringsArray[10] + ": ");
        for (String topping: toppings) {
            listData.add(" - " + topping);
        }
        listData.add(stringsArray[25] + ": ");
        listData.add(order.orderDateTime.toString());
        listData.add(stringsArray[43] + ": ");
        listData.add(order.fName + " " + order.lName);
        listData.add(stringsArray[44] + ": ");
        listData.add(order.address);
        listData.add(stringsArray[45] + ": ");
        listData.add(order.phone);

        adapter = new ArrayAdapter<>(this, R.layout.custom_textview_details, listData);
        orderDetails.setAdapter(adapter);
    }
}
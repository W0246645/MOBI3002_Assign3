package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn_newOrder;
    Switch swtch_lang;
    TextView txt_success;
    Intent intent;
    ArrayList<ArrayList<String>> table = new ArrayList<>();
    ArrayList<String> row = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayList<String> listData = new ArrayList<>();
    ListView orderList;
    SharedPreferences prefs;
    Boolean isEnglish;
    static final String IS_ENGLISH_KEY = "isEnglish";
    int itemLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_newOrder = findViewById(R.id.btn_newOrder);
        txt_success = findViewById(R.id.txt_success);
        txt_success.setText("");
        swtch_lang = findViewById(R.id.sw_lang);
        orderList = findViewById(R.id.orderList);
        orderList.setOnItemClickListener(this::onItemClicked);
        adapter = new ArrayAdapter<>(this, R.layout.custom_textview, listData);
        orderList.setAdapter(adapter);

        btn_newOrder.setOnClickListener(onNewClicked);
        swtch_lang.setOnClickListener(onLanguageClicked);

        prefs = getSharedPreferences("settings", MODE_PRIVATE);
        isEnglish = prefs.getBoolean(IS_ENGLISH_KEY, true);
        swtch_lang.setChecked(isEnglish);
        changeLanguage(isEnglish);

        intent = getIntent();
        if (intent != null) {
            table = (ArrayList<ArrayList<String>>) intent.getSerializableExtra("table");
            int orderNum = 0;
            if (table != null) {
                for (ArrayList<String> order : table) {
                    String orderDetails = order.get(0) + " " + order.get(1);
                    listData.add(orderDetails);
                }
                adapter.notifyDataSetChanged();
            } else {
                table = new ArrayList<>();
            }
        }
    }

    protected void onResume() {
        super.onResume();
        prefs = getSharedPreferences("settings", MODE_PRIVATE);
        isEnglish = prefs.getBoolean(IS_ENGLISH_KEY, true);
        changeLanguage(isEnglish);
    }

    public View.OnClickListener onNewClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, NewOrder.class);
            i.putExtra("table", table);
            startActivity(i);
        }
    };

    public void onItemClicked(AdapterView<?> l, View v, int position, long id) {
        Intent i = new Intent(MainActivity.this, OrderDetails.class);
        row = table.get(position);
        i.putExtra("table", table);
        i.putExtra("row", row);
        i.putExtra("id", id);
        startActivity(i);
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
        ((TextView) findViewById(R.id.txt_title)).setText(stringsArray[1]);
        ((Button) findViewById(R.id.btn_newOrder)).setText(stringsArray[2]);
    }
}
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

public class DeleteOrder extends AppCompatActivity {
    long id;
    Switch swtch_lang;
    Button btn_yesDelete, btn_dontDelete;
    Intent intent;
    ArrayList<ArrayList<String>> table = new ArrayList<>();
    ArrayList<String> row = new ArrayList<>();
    ArrayList<String> listData = new ArrayList<>();
    SharedPreferences prefs;
    Boolean isEnglish;
    static final String IS_ENGLISH_KEY = "isEnglish";
    ListView orderDetails;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_order);

        intent = getIntent();

        swtch_lang = findViewById(R.id.sw_lang);
        btn_yesDelete = findViewById(R.id.btn_yesDelete);
        btn_dontDelete = findViewById(R.id.btn_dontDelete);

        orderDetails = findViewById(R.id.orderDetails);

        btn_yesDelete.setOnClickListener(onYesClicked);
        btn_dontDelete.setOnClickListener(onNoClicked);
        swtch_lang.setOnClickListener(onLanguageClicked);

        table = (ArrayList<ArrayList<String>>) intent.getSerializableExtra("table");
        row = (ArrayList<String>) intent.getSerializableExtra("row");
        id = intent.getLongExtra("id", 0);

        prefs = getSharedPreferences("settings", MODE_PRIVATE);
        isEnglish = prefs.getBoolean(IS_ENGLISH_KEY, true);
        swtch_lang.setChecked(isEnglish);
        changeLanguage(isEnglish);

        adapter = new ArrayAdapter<>(this, R.layout.custom_textview, listData);
        orderDetails.setAdapter(adapter);

        for (String item: row) {
            listData.add(item);
        }
        adapter.notifyDataSetChanged();
    }

    public View.OnClickListener onYesClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            table.remove((int) id);
            Intent i = new Intent(DeleteOrder.this, MainActivity.class);
            i.putExtra("table", table);
            startActivity(i);
        }
    };

    public View.OnClickListener onNoClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(DeleteOrder.this, OrderDetails.class);
            i.putExtra("table", table);
            i.putExtra("row", row);
            i.putExtra("id", id);
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
        String[] stringsArray;
        if (isEnglish) {
            stringsArray = getResources().getStringArray(R.array.english);
        } else {
            stringsArray = getResources().getStringArray(R.array.french);
        }
        ((TextView) findViewById(R.id.txt_deleteTitle)).setText(stringsArray[37]);
        ((Button) findViewById(R.id.btn_yesDelete)).setText(stringsArray[38]);
        ((Button) findViewById(R.id.btn_dontDelete)).setText(stringsArray[39]);
    }
}
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
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteOrder extends AppCompatActivity {
    long id;
    Switch swtch_lang;
    Button btn_yesDelete, btn_dontDelete;
    Intent intent;
    Order order;
    DBAdapter db;
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

        id = intent.getLongExtra("id", 0);

        prefs = getSharedPreferences("settings", MODE_PRIVATE);
        isEnglish = prefs.getBoolean(IS_ENGLISH_KEY, true);
        swtch_lang.setChecked(isEnglish);

        db = new DBAdapter(this);
        db.open();
        order = db.getOrder(id);
        db.close();

        changeLanguage(isEnglish);
    }

    public View.OnClickListener onYesClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            db.open();
            if (db.deleteOrder(order)){
                if (isEnglish) {
                    Toast.makeText(getApplicationContext(), "Successfully Deleted Order!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Commande supprimée avec succès!", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (isEnglish) {
                    Toast.makeText(getApplicationContext(), "Order Not Deleted, Something Went Wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Commande non supprimée, quelque chose s'est mal passé!", Toast.LENGTH_SHORT).show();
                }
            }
            db.close();
            Intent i = new Intent(DeleteOrder.this, MainActivity.class);
            startActivity(i);
        }
    };

    public View.OnClickListener onNoClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(DeleteOrder.this, OrderDetails.class);
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

        //Populate the ListView inside this method so I can easily change the language. Somewhat last minute fix.
        ArrayList<String> listData = new ArrayList<>();

        listData.add(stringsArray[42] + ": " + order.id);
        listData.add(stringsArray[5] + ": " + order.size);
        listData.add(stringsArray[10] + ": " + order.topping1);
        listData.add(stringsArray[25] + ": ");
        listData.add(order.orderDateTime.toString());
        listData.add(stringsArray[43] + ": ");
        listData.add(order.fName + " " + order.lName);
        listData.add(stringsArray[44] + ": ");
        listData.add(order.address);
        listData.add(stringsArray[45] + ": ");
        listData.add(order.phone);

        adapter = new ArrayAdapter<>(this, R.layout.custom_textview, listData);
        orderDetails.setAdapter(adapter);
    }
}
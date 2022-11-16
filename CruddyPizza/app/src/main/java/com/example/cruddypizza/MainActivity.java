package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    ArrayAdapter<String> adapter;
    ArrayList<String> listData = new ArrayList<>();
    ListView orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_newOrder = findViewById(R.id.btn_newOrder);
        txt_success = findViewById(R.id.txt_success);
        txt_success.setText("");
        swtch_lang = findViewById(R.id.sw_lang);
        orderList = findViewById(R.id.orderList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listData);
        orderList.setAdapter(adapter);

        btn_newOrder.setOnClickListener(onNewClicked);

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
            }
        }
    }

    public View.OnClickListener onNewClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, NewOrder.class);
            i.putExtra("table", table);
            startActivity(i);
        }
    };
}
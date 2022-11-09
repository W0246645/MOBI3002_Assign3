package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn_newOrder;
    Switch swtch_lang;
    TextView txt_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_newOrder = findViewById(R.id.btn_newOrder);
        txt_success = findViewById(R.id.txt_success);
        swtch_lang = findViewById(R.id.sw_lang);
        txt_success.setText("");

        btn_newOrder.setOnClickListener(onNewClicked);
    }

    public View.OnClickListener onNewClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, NewOrder.class);
            startActivity(i);
        }
    };
}
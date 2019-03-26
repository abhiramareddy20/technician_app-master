package com.example.rahul.technicial_side_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Payment extends AppCompatActivity {
    TextView money;
    Button submit;
    Bundle bundle;
    String senderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        money = findViewById(R.id.text);
        submit= findViewById(R.id.completed);

        bundle = getIntent().getExtras();
        if (bundle != null)
        {
            money.setText(" "+bundle.getString("data"));
            senderId = bundle.getString("data");
        }



    }
}

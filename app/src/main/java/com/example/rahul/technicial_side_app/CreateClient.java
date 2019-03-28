package com.example.rahul.technicial_side_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.technicial_side_app.DataTypes.Customer;

public class CreateClient extends AppCompatActivity {

    private Button bill;
    TextView textView,txt2,txt3;
    Customer customer;
    EditText miss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client);

        bill = findViewById(R.id.bill);
        textView = findViewById(R.id.name);
        txt2    = findViewById(R.id.address);
        txt3    = findViewById(R.id.descreption);
        miss    = findViewById(R.id.miss);

    }

    public void billing(View view) {

        String missingData = miss.getText().toString();
        if(TextUtils.isEmpty(missingData))
        {
            Toast.makeText(this, "Please enter missing data ", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(CreateClient.this,SpinnerData.class);
        startActivity(i);
    }
}

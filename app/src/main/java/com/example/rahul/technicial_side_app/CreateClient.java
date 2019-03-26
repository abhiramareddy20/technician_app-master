package com.example.rahul.technicial_side_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CreateClient extends AppCompatActivity {

    private Button bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client);

        bill = findViewById(R.id.bill);



    }

    public void billing(View view) {
        Intent i = new Intent(CreateClient.this,SpinnerData.class);
        startActivity(i);
    }
}

package com.example.rahul.technicial_side_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SpinnerData extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner product,type;
    Button submit;
    String currentStatus;
    String[] productDetails = {"With Amc","Without Amc"};
    String[] selectType     = {"UV","Ro","Ro1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_data);

        product = findViewById(R.id.productDetails);
        type    = findViewById(R.id.amcDetails);
        submit  = findViewById(R.id.Submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SpinnerData.this,Feedbacks.class));
            }
        });

        product.setOnItemSelectedListener(this);
        type.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,productDetails);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(aa);

        ArrayAdapter bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item,selectType);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product.setAdapter(bb);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentStatus = productDetails[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void finishing(View view) {
        if (currentStatus == "With Amc") {
            Intent i = new Intent(SpinnerData.this,HomePage.class);
            //i.putExtra("data",currentStatus);
            startActivity(i);
            finish();
            Toast.makeText(this, "Please collect 6000 ", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(SpinnerData.this,HomePage.class);
            startActivity(i);
            finish();
            Toast.makeText(this, "PLease collect 5000", Toast.LENGTH_SHORT).show();

        }
    }
}

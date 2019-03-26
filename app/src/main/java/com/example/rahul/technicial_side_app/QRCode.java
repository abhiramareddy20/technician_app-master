package com.example.rahul.technicial_side_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.technicial_side_app.DataTypes.Customer;
import com.example.rahul.technicial_side_app.DataTypes.Technician_service_center_firebase_support;
import com.example.rahul.technicial_side_app.DataTypes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;
import android.preference.PreferenceManager;
import android.preference.PreferenceManager;

public class QRCode extends AppCompatActivity implements View.OnClickListener {

    private Button buttonScan;
    private TextView textViewName, textViewAddress,result;

    //qr code scanner object
    private IntentIntegrator qrScan;
    Customer cust;
    User user;
    Button submit;
    String res;
    private String senderId,currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewName = findViewById(R.id.textViewName);

        user = new Gson().fromJson(getSharedPreferences("technician_side_app", MODE_PRIVATE).getString("jsonUser", ""), User.class);

        //intializing scan object
        qrScan = new IntentIntegrator(this);
        submit = findViewById(R.id.submit);
        //attaching onclick listener
        buttonScan.setOnClickListener(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateFirebaseStatus();

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());


                    //setting values to textviews
/*
                    textViewName.setText(obj.getString("name"));
                    textViewAddress.setText(obj.getString("address"));*/
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                   textViewName.setText(result.getContents());
                    res = result.getContents();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        submit.setVisibility(View.VISIBLE);
        buttonScan.setVisibility(View.INVISIBLE);

        qrScan.initiateScan();

    }

    public void updateFirebaseStatus()
    {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("technician");
        QrResults qrResults = new QrResults(res);
        myRef.child(user.getId()).setValue(qrResults);

    }



}

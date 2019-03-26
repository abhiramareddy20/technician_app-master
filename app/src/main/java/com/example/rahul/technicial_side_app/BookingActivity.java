package com.example.rahul.technicial_side_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BookingActivity extends AppCompatActivity {
    TextView desc;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        desc = findViewById(R.id.intent);
        submit = findViewById(R.id.submit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            desc.setText(" "+bundle.getString("data"));
        }

    }

    public void ConfirmBooking(View view) {

        Toast.makeText(this, "Its working", Toast.LENGTH_SHORT).show();
    }
}

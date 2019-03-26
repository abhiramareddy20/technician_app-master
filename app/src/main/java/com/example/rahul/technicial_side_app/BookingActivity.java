package com.example.rahul.technicial_side_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.technicial_side_app.DataTypes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class BookingActivity extends AppCompatActivity {
    TextView desc;
    Button submit;
    User user;
    private String SenderId,currentState,reciverId;
    private DatabaseReference myRef,bookRequest;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        user = new Gson().fromJson(getSharedPreferences("technician_side_app", MODE_PRIVATE).getString("jsonUser", ""), User.class);

        bookRequest = FirebaseDatabase.getInstance ().getReference ().child ("Booking Requests");

        desc = findViewById(R.id.intent);
        submit = findViewById(R.id.submit);


        bundle = getIntent().getExtras();
        if (bundle != null)
        {
           desc.setText(" "+bundle.getString("data"));
           SenderId = bundle.getString("data");
        }
    }

    public void ConfirmBooking(View view) {

        if(SenderId.equals (SenderId))
        {
            SendBookingRequest();
        }

        Toast.makeText(this, "Its working", Toast.LENGTH_SHORT).show();
    }

    private void SendBookingRequest() {
        bookRequest.child(user.getId()).child(SenderId).child ("Request Type").setValue ("sent")
                .addOnCompleteListener (new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful ())
                        {
                            startActivity (new Intent (BookingActivity.this,HomePage.class));
                            finish ();
                            Toast.makeText (BookingActivity.this, "Booking Confirmed", Toast.LENGTH_SHORT).show ();
                        }
                        else
                        {
                            Toast.makeText (BookingActivity.this, "Payment Failed", Toast.LENGTH_SHORT).show ();
                        }

                    }
                });
    }

}

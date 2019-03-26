package com.example.rahul.technicial_side_app;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;

public class TechniciansRegister extends AppCompatActivity {
    DatePickerDialog picker;
    EditText eText;
    private ArrayList<String> mtext1 = new ArrayList<>();
    private ArrayList<String> mtext2 = new ArrayList<String>();
    private ArrayList<String>mtext3 = new ArrayList<>();
    private ArrayList<String> mtext4 = new ArrayList<>();
    private ArrayList<String> mtext5 = new ArrayList<>();
    private ArrayList<String> mtext6 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technicians_register);

        eText=(EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(TechniciansRegister.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        getcontent();
    }


    private void  getcontent()
    {

        mtext1.add("21315");
        mtext2.add(" Rs 250");
        mtext3.add("252");
        mtext4.add("5000");
        mtext5.add("6000");
        mtext6.add("Half day");

        mtext1.add("31254");
        mtext2.add(" Rs 500");
        mtext3.add("352");
        mtext4.add("8000");
        mtext5.add("7000");
        mtext6.add("Full day");

        mtext1.add("21315");
        mtext2.add(" Rs 250");
        mtext3.add("252");
        mtext4.add("5000");
        mtext5.add("6000");
        mtext6.add("Half day");

        mtext1.add("21315");
        mtext2.add(" Rs 250");
        mtext3.add("252");
        mtext4.add("5000");
        mtext5.add("6000");
        mtext6.add("Half day");

        mtext1.add("31254");
        mtext2.add(" Rs 500");
        mtext3.add("352");
        mtext4.add("8000");
        mtext5.add("7000");
        mtext6.add("Full day");

        mtext1.add("21315");
        mtext2.add(" Rs 250");
        mtext3.add("252");
        mtext4.add("5000");
        mtext5.add("6000");
        mtext6.add("Half day");

        mtext1.add("21315");
        mtext2.add(" Rs 250");
        mtext3.add("252");
        mtext4.add("5000");
        mtext5.add("6000");
        mtext6.add("Half day");

        mtext1.add("31254");
        mtext2.add(" Rs 500");
        mtext3.add("352");
        mtext4.add("8000");
        mtext5.add("7000");
        mtext6.add("Full day");

        initRecyclerview();
    }

    private void initRecyclerview() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.agent);
        recyclerView.setLayoutManager(layoutManager);
        TechnicianRegisterAdapter adapter = new TechnicianRegisterAdapter(this,mtext1,mtext2,mtext3,mtext4,mtext5,mtext6);
        recyclerView.setAdapter(adapter);
    }
}

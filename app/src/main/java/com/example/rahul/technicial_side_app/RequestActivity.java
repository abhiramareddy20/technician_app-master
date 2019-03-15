package com.example.rahul.technicial_side_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity implements  Spinner.OnItemSelectedListener{

    private Spinner spinner;

    //An ArrayList for Spinner Items
    private ArrayList<String> students;

    //JSON Array
    private JSONArray result;

    Button search;
    EditText userEnteredCode;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        students = new ArrayList<String>();

        spinner = (Spinner) findViewById(R.id.spinner);
        userEnteredCode = (EditText) findViewById(R.id.code);
        search = (Button) findViewById(R.id.search);

        spinner.setOnItemSelectedListener(this);
        getData();


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = userEnteredCode.getText().toString().trim();

                if(TextUtils.isEmpty(code))
                {
                    Toast.makeText(RequestActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(RequestActivity.this, "Submitted", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(dataForSpinner.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(dataForSpinner.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getStudents(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getStudents(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                students.add(json.getString(dataForSpinner.TAG_USERNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(RequestActivity.this, android.R.layout.simple_spinner_dropdown_item, students));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

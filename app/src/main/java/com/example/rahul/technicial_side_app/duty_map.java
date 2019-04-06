package com.example.rahul.technicial_side_app;

import android.Manifest;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rahul.technicial_side_app.DataTypes.Customer;
import com.example.rahul.technicial_side_app.DataTypes.Technician_service_center_firebase_support;
import com.example.rahul.technicial_side_app.DataTypes.User;
import com.example.rahul.technicial_side_app.Database.uploadCustomersNewLocation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.http.HttpConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class duty_map extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    //dummy url https://maps.googleapis.com/maps/api/directions/json?origin=12.95483,77.61265&destination=12.99483,77.91265&key=AIzaSyCz3WgmlPmHucYVfVD6_nP-UAGD_UfvMHw
    //widgets
    TextView name, street, homeno, phone, problem;
    Customer cust;
    ImageView img;
    Button call;
    LatLng customerLocation;
    static GoogleMap googleMap;
    MapLocationHelper mapHelper;
    LocationManager locationManager;
    User user;
    LatLng mypos;
    Context context;

    AudioManager audioManager;
    Recoreder recorder;
    private SensorManager mSensorManager;
    private Sensor myLightSensor;
    private boolean CallState;
    private float sensorState;
    Recoreder recoreder;
    String sdpath,sd1path,usbdiskpath,sd0path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_map);

        cust = (Customer) getIntent().getSerializableExtra("customer");
        user = new Gson().fromJson(getSharedPreferences("technician_side_app", MODE_PRIVATE).getString("jsonUser", ""), User.class);
        customerLocation = new LatLng(cust.getLat(), cust.getLng());
        mapHelper = new MapLocationHelper(this);
        widgetInitialization();
        updateFirebaseStatus();
        myLocationFetcherInitialization();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(duty_map.this, HomePage.class);
                startActivity(i);
                finish();
            }
        });
    }

    final int MY_PERMISSIONS_REQUEST_LOCATION = 3213;

    void widgetInitialization() {

        name = findViewById(R.id.username);
        street = findViewById(R.id.street);
        homeno = findViewById(R.id.homeno);
        img = findViewById(R.id.imageView4);

        name.setText(cust.getName());
        street.setText(cust.getStreet());
        homeno.setText("Home no:" + cust.getHomeno());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void makeCall(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        //intent.setData(Uri.parse("tel:" + cust.getPhone()));
        startActivity(intent);
    }

    public void startService(View view) {
//        String url = "http://35.237.12.7/uploadAudioService/";

        new uploadCustomersNewLocation(mypos, cust.getId());
        //Intent i=new Intent(this,start_service.class);
        File sdcard = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//Get the text file
        File file = new File(sdcard,"myfile");
        uploadFile(file,cust.getId());

        Intent i = new Intent(this, otp.class);
        i.putExtra("customer", cust);
        startActivity(i);
    }

    String url="http://35.237.12.7/uploadAudioService/";
    private  void uploadFile(File file_url, String bookingId)
    {
        File f=new File(String.valueOf(file_url));
        try {
            MultipartUploadRequest obb=new MultipartUploadRequest(getApplication(),"123",url);

            obb.addParameter("booking_id",bookingId);
            obb.addFileToUpload(f.getPath(),"myfile");
            String a= obb.startUpload();

            Toast.makeText(getApplicationContext(),"File successfully uploaded to "+a,Toast.LENGTH_LONG).show();

            new DownloadData().execute(new String[]{url});

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    class DownloadData extends AsyncTask<String , Void ,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            DownloadUrl download=new DownloadUrl();
            try {
                Log.e("datadownload","started download process");
                String text=download.readUrl(strings[0]);
                return text;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Some exception occured";

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("datadownloader","downloading complete");
            Log.e("datadownloader","downloaded data is "+s);
            downloadComplete(s);

        }
    }

    void downloadComplete(String text)
    {
        //JsonDecoder decoder=new JsonDecoder();
        //imageDescription=decoder.decode(text);
        //output.setText(text);
        //say(imageDescription);
        Log.e("download return",text+" is the output returned");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        googleMap.addMarker(new MarkerOptions().position(customerLocation)
                .title("Marker in Sydney"));
        Log.e("Map", "Loading data to map");
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(customerLocation, 15));
    }

    int location_triggers = 0;

    //THIS FUNCTION ENSURED USERS CURRENT POSITION IS FETCHED AND THE PERMISSION ARE MANAGED OVER HERE
    void myLocationFetcherInitialization() {
        location_triggers++;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!statusOfGPS) {
            Intent gpsOptionsIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsOptionsIntent);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 100, this);
        Log.e("Location trigger", "location trigger occured" + location_triggers);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                Intent gpsOptionsIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(gpsOptionsIntent);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        }

    }

    //FUNCTIONS RELATED TO FETCHING OF USERS CURRENT LOCATIOIN STARTS OVER HERE
    @Override
    public void onLocationChanged(Location location) {
        mypos=new LatLng(location.getLatitude(),location.getLongitude());
        LatLng dest=new LatLng(cust.getLat(),cust.getLng());
        if(mapHelper.fromLocation==null)
        {
            mapHelper.createMap(this.googleMap,mypos,dest);
            mapHelper.displayRoute();

        }
        else
        {

            // mapHelper.updateMarkerPosition(mapHelper.source,pos);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    //FUNCTIONS RELATED TO FETCHING OF USERS CURRENT LOCATION ENDS OVER HERE

    public void updateFirebaseStatus()
    {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("technician");
        Technician_service_center_firebase_support tech_service=new Technician_service_center_firebase_support();
        tech_service.setInProgressStatus(cust.getId());
        Log.e("Customer","id is"+cust.getId());
        myRef.child(user.getId()).setValue(tech_service);
    }

    public void reRoute(View view)
    {
        LatLng dest=new LatLng(cust.getLat(),cust.getLng());
        mapHelper.createMap(this.googleMap,mypos,dest);
        mapHelper.displayRoute();
    }

    public void FeedBack(View view) {

        startActivity(new Intent(duty_map.this,Feedbacks.class));
    }


}





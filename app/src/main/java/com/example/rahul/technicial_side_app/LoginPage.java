package com.example.rahul.technicial_side_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rahul.technicial_side_app.DataTypes.LoadingDialog;
import com.example.rahul.technicial_side_app.DataTypes.User;
import com.example.rahul.technicial_side_app.Database.AsyncLoginResponse;
import com.example.rahul.technicial_side_app.Database.AsyncResponse;
import com.example.rahul.technicial_side_app.Database.DatabaseAdapter;
import com.example.rahul.technicial_side_app.Database.DatabaseOptions;
import com.example.rahul.technicial_side_app.Database.HttpReqeust;
import com.example.rahul.technicial_side_app.Database.Login;
import com.example.rahul.technicial_side_app.alerts.error_dialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class LoginPage extends AppCompatActivity implements AsyncLoginResponse{

    //WIDGETS
    EditText username,password;
    CheckBox rememberme;
    LoadingDialog loader;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

        initializeWidgets();
        loader=new LoadingDialog(this);
    }


    private void initializeWidgets()
    {username=findViewById(R.id.username);
     password=findViewById(R.id.password);
     rememberme=findViewById(R.id.rememberme);
    }

    //CALLED FROM LOGIN BUTTON
    public void login(View view)
    {

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!= null && networkInfo.isConnected()) {

            String user = username.getText().toString();
            String pass = password.getText().toString();
            if (usernameVerification(user)) {
                if (passwordVerification(pass)) {
                    new Login(this, user, pass);
                    loader.show();
                    Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "Invalid details", Toast.LENGTH_SHORT).show();
            }
        }

        else{
            informuser("No Internet Connection");

             }
    }

    private void informuser(String message)
    {
        Snackbar.make(findViewById(R.id.login),message,Snackbar.LENGTH_SHORT).show();
    }

    private boolean usernameVerification(String user)
    {
        if(user.length()==10)
            return true;
        return false;
    }

    private boolean passwordVerification(String password)
    {return true;}

    private void showError(String error_message)
    {
        new error_dialog(this).setMessage(error_message).show();

    }

    @Override
    public void userLoginSuccessful(User user) {
        loader.dismiss();
        SharedPreferences.Editor editor=getSharedPreferences("technician_side_app",MODE_PRIVATE).edit();
        Gson gson=new Gson();
        String json=gson.toJson(user,User.class);
        editor.putString("jsonUser",json);
        editor.apply();
        Intent i=new Intent(this,HomePage.class);
        i.putExtra("user",user);
        startActivity(i);
        }

    @Override
    public void userLoginUnsuccessful() {
        loader.dismiss();

        showError("Server error");
    }
}

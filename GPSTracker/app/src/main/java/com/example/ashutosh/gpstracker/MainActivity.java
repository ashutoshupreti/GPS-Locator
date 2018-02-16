package com.example.ashutosh.gpstracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.UnicodeSetSpanner;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameInput, numberInput;
    TextView trustedContactName;
    MyDBHandler dbHandler;
    LocationManager locationManager;
    LocationListener locationListener;

    SmsManager smsManager = SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SmsManager smsManager = SmsManager.getDefault();
       // smsManager.sendTextMessage("+919623226678", null, "hi",null,null);
        Toast.makeText(getApplicationContext(),"sent successfully", Toast.LENGTH_LONG).show();
        nameInput = (EditText) findViewById(R.id.nameInput);
        numberInput = (EditText) findViewById(R.id.numberInput);
        trustedContactName = (TextView) findViewById(R.id.contactDisplay);
        dbHandler = new MyDBHandler(this, null, null, 1);
        printDatabase();

        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                if (messageText.contains("LocatePhone")) {
                    //smsManager.sendTextMessage("+919834788913", null, "Hey!", null, null);
                }
            }
        });
    }


    // Add contact to the database
    public void addButtonClicked(View view){
        Contacts contact = new Contacts(numberInput.getText().toString(), nameInput.getText().toString());
        dbHandler.addContact(contact);
        printDatabase();
    }

    // Delete contact from database
    public void deleteButtonClicked(View view){
        String name = nameInput.getText().toString();
        dbHandler.deleteContact(name);
        printDatabase();
    }

    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        trustedContactName.setText(dbString);
        nameInput.setText("");
        numberInput.setText("");
    }
}

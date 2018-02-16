package com.example.ashutosh.gpstracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.ArrayList;

public class SmsReceiver extends BroadcastReceiver {

    // Interface
    private static SmsListener mListener;

    private MyDBHandler dbHandler;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();

        ArrayList<String> contactList = dbHandler.contactNumberList();
        // This is based on the assumption that only ONE trusted contact is stored
        String contactNumber = contactList.get(0);

        // PDU: “protocol data unit”, the industry format for an SMS message
        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0; i<pdus.length; i++){

            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();
            // We can use this sender to filter messages

            if (sender.equals(contactNumber))
            {
                String messageBody = smsMessage.getMessageBody();
                // Pass the message text to interface
                mListener.messageReceived(messageBody);
            }
        }
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
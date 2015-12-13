package com.mwyn.amresh.mwyn_chat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Amresh on 12/13/2015.
 */
public class LaunchApplication extends Activity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText phoneNumeber;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.own_number);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor=sharedPreferences.edit();
        phone = sharedPreferences.getString("phone", null);
        Button btnLaunch = (Button) findViewById(R.id.startApplication);
        phoneNumeber = (EditText) findViewById(R.id.myNumber);

        if (phone!= null) {
            Intent intent = new Intent(LaunchApplication.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumeber.getText().toString().length() == 10 && PhoneNumberUtils.isGlobalPhoneNumber(phoneNumeber.getText().toString())) {
                    editor.putString("phone","+91"+phoneNumeber.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(LaunchApplication.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LaunchApplication.this, "Please enter a correct number", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}

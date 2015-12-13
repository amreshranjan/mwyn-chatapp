package com.mwyn.amresh.mwyn_chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amresh on 12/11/2015.
 * This class is used for binding all the contact list in list using custom adapter.
 */

public class MainActivity extends AppCompatActivity {

    List<String> contactName = new ArrayList<>(); // list of contact names from phone contact
    List<String> contactNumber = new ArrayList<>(); // list of contact number from phone contact
    ListView lvContact;
    ListAdapterClass l1; // Object of custom adapter class
    SharedPreferences sharedPreferences;
    String selfNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvContact = (ListView) findViewById(R.id.lvContact);

        /* Cursor will have all contacts(Mobile Number only) from phone contact list */
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        selfNumber = sharedPreferences.getString("phone", null);
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        //Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.Contacts.HAS_PHONE_NUMBER + " = 1", null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            contactName.add(name);
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneNumber=phoneNumber.replace(" ","");
            contactNumber.add(phoneNumber);
        }

        l1 = new ListAdapterClass(contactName, contactNumber, this);
        lvContact.setAdapter(l1);

        /* OnItemClickListener for each contact from list*/

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, ChatPage.class);
                intent.putExtra("name", contactName.get(position));
                intent.putExtra("number", contactNumber.get(position).trim());
                intent.putExtra("self_number",selfNumber);
                startActivity(intent);
            }
        });

    }


}

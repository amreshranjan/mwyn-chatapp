package com.mwyn.amresh.mwyn_chat;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amresh on 12/13/2015.
 * This Class is used for showing content of chat for a particular contact using Firebase.
 */
public class ChatPage extends AppCompatActivity {


    String phoneNumber;
    String selfNumber;
    Button btnSend;
    EditText messegeText;
    ListView lvHistory;
    ProgressBar progressBarNew;
    String fireBasePath = "https://sweltering-torch-2262.firebaseio.com/Chat/";
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    LinearLayout msgPage;
    CheckConnectivity isConnected;
    FirebaseListAdapter mListAdapter;
    ChatListAdapter mChatListAdapter;
    List<ListOfChat> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page);
        isConnected = new CheckConnectivity(this);
        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase(fireBasePath);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar_id);
        progressBarNew = (ProgressBar) findViewById(R.id.progressBarNew);
        lvHistory = (ListView) findViewById(R.id.messageHistoryList);
        msgPage = (LinearLayout) findViewById(R.id.msgPageLayout);

        setSupportActionBar(toolbar);
        phoneNumber = getIntent().getStringExtra("number");
        selfNumber = getIntent().getStringExtra("self_number");

        if (selfNumber.length()==10) {
            selfNumber = "+91"+selfNumber;
        }
        /*Append contact name in Actionbar as a title*/
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        messegeText = (EditText) findViewById(R.id.messageText);
        btnSend = (Button) findViewById(R.id.sendMessageButton);
        mChatListAdapter = new ChatListAdapter(list, this);
        lvHistory.setAdapter(mChatListAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (messegeText.getText().toString().length() > 0) {
                    ListOfChat chatData = new ListOfChat();
                    if (messegeText.getText().toString().trim().contains(" ")) {
                        chatData.setMessage('"' + messegeText.getText().toString() + '"');
                    } else {
                        chatData.setMessage(messegeText.getText().toString());
                    }

                    if (phoneNumber.length() > 10) {
                        chatData.setRecieverNumber(phoneNumber.trim());
                    } else {
                        chatData.setRecieverNumber("+91" + phoneNumber.trim());
                    }

                    chatData.setSenderNumber(selfNumber);
                    chatData.setTimeStamp("today");
                    mFirebaseRef.push().setValue(chatData);
                    messegeText.setText("");
                    getData();
                }
            }
        });
    }

    private void getData() {
        if (isConnected.isConnectingToInternet()) {
            progressBarNew.setVisibility(View.VISIBLE);
            lvHistory.setVisibility(View.GONE);
            mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean connected = (Boolean) dataSnapshot.getValue();
                    if (connected) {
                        progressBarNew.setVisibility(View.GONE);
                        lvHistory.setVisibility(View.VISIBLE);
                        msgPage.setVisibility(View.VISIBLE);
                        mFirebaseRef.limitToFirst(20).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                list.clear();
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Gson gson = new Gson();
                                    ListOfChat listOfChat = gson.fromJson(child.getValue().toString(), ListOfChat.class);
                                    if (listOfChat.getRecieverNumber() != null || listOfChat.getSenderNumber() != null) {
                                        if (listOfChat.getSenderNumber().equals(phoneNumber) || listOfChat.getRecieverNumber().equals(phoneNumber)) {
                                            list.add(listOfChat);
                                        }
                                    }
                                }
                                mChatListAdapter.notifyDataSetChanged();
                                progressBarNew.setVisibility(View.GONE);
                                lvHistory.setVisibility(View.VISIBLE);
                            }


                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                progressBarNew.setVisibility(View.GONE);
                                lvHistory.setVisibility(View.VISIBLE);
                            }
                        });

                        // Toast.makeText(ChatPage.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    // No-op
                    progressBarNew.setVisibility(View.GONE);
                    lvHistory.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Toast.makeText(ChatPage.this, "Please Connect to internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }
}

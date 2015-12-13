package com.mwyn.amresh.mwyn_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amresh on 12/11/2015.
 */
public class ListAdapterClass extends BaseAdapter {

    List<String> contactName;
    List<String> contactNumber;
    Context context;
    public  ListAdapterClass( List<String> lname, List<String> lnumber, Context context)
    {
        this.context= context;
        this.contactName=lname;
        this.contactNumber=lnumber;
    }

    @Override
    public int getCount() {
        return contactName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)context.getSystemService(inflater);
            v = li.inflate(R.layout.contact_list, null);


            TextView NameView = (TextView)v.findViewById(R.id.txtName);
            TextView NumberView = (TextView)v.findViewById(R.id.txtNumber);
            NameView.setText(contactName.get(position));
            NumberView.setText(contactNumber.get(position));
            return v;
    }
}

package com.mwyn.amresh.mwyn_chat;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import java.util.List;


public class ChatListAdapter extends BaseAdapter
{
    List<ListOfChat> chatList;
    Context context;
    public  ChatListAdapter(List<ListOfChat> chatList, Context context)
    {
        this.chatList=chatList;
        this.context=context;

    }

    @Override
    public int getCount() {
        return chatList.size();
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
        v = li.inflate(R.layout.listofchat, null);
        TextView userName = (TextView)v.findViewById(R.id.textUser);
        TextView messegetext = (TextView)v.findViewById(R.id.textMessage);
        userName.setText(chatList.get(position).getSenderNumber());
        messegetext.setText(chatList.get(position).getMessage());
        return v;
    }
}
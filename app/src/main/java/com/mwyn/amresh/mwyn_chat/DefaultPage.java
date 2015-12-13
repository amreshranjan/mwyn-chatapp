package com.mwyn.amresh.mwyn_chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Amresh on 12/12/2015.
 */
public class DefaultPage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_page);
        Thread timer=new Thread(){
            public void run()
            {
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent openMain=new Intent(DefaultPage.this,LaunchApplication.class);
                    startActivity(openMain);
                    finish();
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        System.exit(0);
    }

}

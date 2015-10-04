package com.zj.example.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Random;


/**
 * HandlerThread Demo
 * Created by zhengjiong on 15/10/3.
 */
public class MainActivity extends AppCompatActivity {
    private Handler mMainThreadHandler = new Handler();
    private Handler mAsyncHandler;
    private HandlerThread mHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final TextView txtContent = (TextView) findViewById(R.id.txt_content);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


        mHandlerThread = new HandlerThread("handler-Thread");
        mHandlerThread.start();
        mAsyncHandler = new Handler(mHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                try {
                    //当前是在子线程(handler-Thread)中执行
                    Thread.sleep(2000);

                    mMainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtContent.setText(String.valueOf(new Random().nextInt()));
                        }
                    });

                    //发送消息, 通知更新
                    mAsyncHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        //发送消息, 通知更新
        mAsyncHandler.sendEmptyMessage(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.a24915.design_competition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by 24915 on 2019/3/21.
 */

public class wifiActivity extends AppCompatActivity{
    private Button b1,b3;
    private EditText edit;
    private ListView listView;
    private String[] mListStr={"192.168.0.1","192.168.43.1","192.168.1.1",""};

    Socket socket = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_activity);

        listView=(ListView)findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mListStr));

        b1= (Button)findViewById(R.id.button);
        b3= (Button)findViewById(R.id.button3);

        edit=(EditText) findViewById(R.id.edittext);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edit.setText(mListStr[i]);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(wifiActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(socket==null){
                    if(!edit.getText().toString().trim().equals(" ")){
                        Thread_connect thread_connect = new Thread_connect();
                        thread_connect.start();
                        b3.setText("断开");
                    }
                }else{
                    try {
                        socket.close();
                        socket=null;
                        b3.setText("连接");
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    class Thread_connect extends Thread{
        public void run(){
            try{
                if(socket == null){
                    String ipname = edit.getText().toString();
                    InetAddress ip = InetAddress.getByName(ipname);
                    int port = Integer.valueOf("6666");
                    socket=new Socket(ip,port);
                    ((MySocket)getApplication()).setSocket(socket);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(wifiActivity.this,"连接成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}


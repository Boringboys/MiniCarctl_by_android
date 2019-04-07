package com.example.a24915.design_competition;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Socket socket;

    Handler mHandler;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    private int code,speed;
    private int[] a={0,0};
    static boolean thiscode;

    private SeekBar seekBar;
    private TextView textView1,textView2,textView3;
    private ImageButton imageButton1,imageButton2,imageButton3,imageButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("执行控制界面的onCreate()");


        socket = ((MySocket)getApplication()).getSocket();
        code = 0;
        speed = 0;
        thiscode = true;

        seekBar=(SeekBar)findViewById(R.id.seekBar);
        textView1=(TextView)findViewById(R.id.textView2);
        textView2=(TextView)findViewById(R.id.textView4);
        textView3=(TextView)findViewById(R.id.textView5);
        imageButton1=(ImageButton)findViewById(R.id.imageButton);
        imageButton2=(ImageButton)findViewById(R.id.imageButton2);
        imageButton3=(ImageButton)findViewById(R.id.imageButton3);
        imageButton4=(ImageButton)findViewById(R.id.imageButton4);

        textView1.setText(Integer.toString(speed));
        seekBar.setProgress(0);

        if(!socket.isConnected()){
            Toast.makeText(MainActivity.this,"未连接",Toast.LENGTH_SHORT).show();
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView1.setText(Integer.toString(i));
                speed=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //停止滑动，在这里发送
            }
        });

        imageButton1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        code+=1;
                        break;
                    case MotionEvent.ACTION_UP:
                        code-=1;
                        break;
                }
                a[0]=code;
                a[1]=speed;
                new MyAsyncTask1().execute(a);
                return true;
            }
        });

        imageButton2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        code+=2;
                        break;
                    case MotionEvent.ACTION_UP:
                        code-=2;
                        break;
                }
                a[0]=code;
                a[1]=speed;
                new MyAsyncTask1().execute(a);
                return true;
            }
        });

        imageButton3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        code+=4;
                        break;
                    case MotionEvent.ACTION_UP:
                        code-=4;
                        break;
                }
                a[0]=code;
                a[1]=speed;
                new MyAsyncTask1().execute(a);
                return true;
            }
        });

        imageButton4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        code+=8;
                        break;
                    case MotionEvent.ACTION_UP:
                        code-=8;
                        break;
                }
                a[0]=code;
                a[1]=speed;
                new MyAsyncTask1().execute(a);
                return true;
            }
        });

//        mHandler=new Handler();
//        Runnable r=new Runnable() {
//            @Override
//            public void run() {
//                new MyAsyncTask2().execute("go");
//            }
//        };
//        mHandler.postDelayed(r,2000);

        Thread_Receive thread_receive = new Thread_Receive();
        thread_receive.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("执行控制界面的onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("执行控制界面的onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("执行控制界面的onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("执行控制界面的onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("执行控制界面的onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("执行控制界面的onDestroy()");
    }

    private class MyAsyncTask1 extends AsyncTask<int[],Integer,String>{
        @Override
        protected String doInBackground(int[]... params){
            String sendStr = "";
            try{
                //计算并发送
                int codetmp = params[0][0];
                int speedtmp = params[0][1];
                int sendcode = 0;
                if(codetmp==0)
                {
                    sendcode=0;
                }
                else if(codetmp==1)
                {
                    sendcode=1;
                }
                else if(codetmp==2)
                {
                    sendcode=2;
                }
                else if(codetmp==5)
                {
                    sendcode=3;
                }
                else if(codetmp==9)
                {
                    sendcode=4;
                }
                else if(codetmp==6)
                {
                    sendcode=5;
                }
                else if(codetmp==10)
                {
                    sendcode=6;
                }
                else if(codetmp==4)
                {
                    sendcode=7;
                }
                else if(codetmp==8)
                {
                    sendcode=8;
                }else
                {
                    return null;
                }

                sendStr = Integer.toString(sendcode)+"#"+Integer.toString(speedtmp);
                outputStream=socket.getOutputStream();
                outputStream.write(sendStr.getBytes());
            }catch (Exception e){
                e.printStackTrace();
            }
            return sendStr;
        }
        @Override
        protected void onPostExecute(String s){

        }
    }
//
//    private class MyAsyncTask2 extends AsyncTask<String,String,String>{
//        @Override
//        protected String doInBackground(String... params){
//            String getdata="250#250";
//            try {
//                while (true) {
//                    final byte[] buffer = new byte[1024];
//                    inputStream = socket.getInputStream();
//                    final int len = inputStream.read(buffer);
//                    getdata = new String(buffer,0,len);
//                    publishProgress(getdata);
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            return getdata;
//        }
//        @Override
//        protected void onProgressUpdate(String... values){
//            textView3.setText(values[0]);
//        }
//        @Override
//        protected void onPostExecute(String s){
//            textView3.setText(s);
//        }
//    }

    class Thread_Receive extends Thread{
        public void run(){
            try{
                while(true) {
                    final byte[] buffer = new byte[1024];
                    inputStream = socket.getInputStream();
                    final int len = inputStream.read(buffer);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String getstr = new String(buffer, 0, len);
                            String front = getstr.split("#")[0];
                            String after = getstr.split("#")[1];
                            textView2.setText(front+"cm");
                            textView3.setText(after+"cm");
                            String noticestr = "";
                            int frontint = Integer.parseInt(front);
                            int afterint = Integer.parseInt(after);
                            if(frontint<50 || afterint<50){
                                boolean isready = false;
                                if(frontint<50 && (code==1 || code==3 || code==4)){
                                    isready = true;
                                    noticestr = "前方距离过近，已停车";
                                }
                                else if(afterint<50 && (code==2 || code==5 || code==6)){
                                    isready = true;
                                    noticestr = "后方距离过近，已停车";
                                }
                                if(thiscode && isready) {
                                    thiscode=false;
                                    Toast toast = new Toast(MainActivity.this);
                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.toastview, (ViewGroup) findViewById(R.id.toast_layout));
                                    TextView texttoast = (TextView) layout.findViewById(R.id.textView3);
                                    texttoast.setText(noticestr);
                                    toast.setView(layout);
                                    toast.setGravity(Gravity.CENTER,0,0);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                            else
                            {
                                thiscode = true;
                            }
                        }
                    });
                }

            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}

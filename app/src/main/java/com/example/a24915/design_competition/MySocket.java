package com.example.a24915.design_competition;

import android.app.Application;

import java.net.Socket;

/**
 * Created by 24915 on 2019/3/21.
 */

public class MySocket extends Application {
    Socket socket = null;
    @Override
    public void onCreate(){
        super.onCreate();
        setSocket(new Socket());
    }
    public Socket getSocket(){
        return socket;
    }
    public void setSocket(Socket socket){
        this.socket=socket;
    }
}

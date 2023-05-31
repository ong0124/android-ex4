package com.example.a2025123051ex4;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
    private int count = 0;
    private boolean quit = false;
    private MyServiceBinder myServiceBinder = new MyServiceBinder();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("service is bind");
        return myServiceBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service is created");
        new Thread() {
            @Override
            public void run() {
                while (!quit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    count++;
                }
            }
        }.start();
    }
    // 创建新Thread，如果service 启动每秒钟count++，如果quit 为真则退出

    public class MyServiceBinder extends Binder {
        public int getCount() {
            return count;
        }
    }

    public boolean onUnbind(Intent intent) {
        System.out.println("Service is Unbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        quit = true;
    }
}
package com.example.a2025123051ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnBindService,btnUnbindService,btnGetStatus;
    TextView tvServiceStatus;
    MyService.MyServiceBinder serviceBinder;
    boolean isServiceBind=false;
    ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("--Service Connected--");
            serviceBinder = (MyService.MyServiceBinder) service;
//返回Service 的Binder 对象
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("--Service Disconnected--");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBindService=findViewById(R.id.btn_main_activity_bind_service);
        btnUnbindService=findViewById(R.id.btn_main_activity_unbind_service);
        btnGetStatus=findViewById(R.id.btn_main_activity_get_status);
        tvServiceStatus=findViewById(R.id.tv_main_activity_service_status);
        btnBindService.setOnClickListener(this);
        btnUnbindService.setOnClickListener(this);
        btnGetStatus.setOnClickListener(this);
    }


    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this,MyService.class);
        switch (v.getId()){
            case R.id.btn_main_activity_bind_service:
                tvServiceStatus.setText("Service created");
                bindService(intent,conn, Service.BIND_AUTO_CREATE);
//如果service 尚未绑定就绑定，如果已经绑定则忽略
                break;
            case R.id.btn_main_activity_get_status:
                tvServiceStatus.setText(serviceBinder.getCount());
//如果service 已经绑定，获取service 的count 计数并显示在截面上。
                break;
            case R.id.btn_main_activity_unbind_service:
//如果service 已经绑定，则可以解绑，否则忽略
                tvServiceStatus.setText("Service Unbind");
                unbindService(conn);
                break;
        }
    }
}

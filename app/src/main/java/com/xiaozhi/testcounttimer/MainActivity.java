package com.xiaozhi.testcounttimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_get;
    private Button button_commit;
    private EditText et_input_phone;
    private EditText et_input_number;
    private EventHandler eventHandler;
    private MyCountTimer myCountTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SMSSDK.initSDK(this, "1385d5daeb280", "99f81e0f2635dbd44536895bbdc1b5af");
        initView();
        registeSMS();
    }

    private void initView(){
        et_input_phone = (EditText) findViewById(R.id.et_input_phone);
        et_input_number = (EditText) findViewById(R.id.et_input_number);
        button_get = (Button) findViewById(R.id.button_get);
        button_commit = (Button) findViewById(R.id.button_commit);
        myCountTimer = new MyCountTimer(button_get, "获取验证码");
        button_get.setOnClickListener(this);
        button_commit.setOnClickListener(this);
    }

    private void registeSMS() {
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        MyApplication.getInstance().showToast("验证码获取成功!");
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        MyApplication.getInstance().showToast("验证码校验成功!");
                    }
                } else {
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        final String des = object.optString("detail");
                        int status = object.optInt("status");
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            MyApplication.getInstance().showToast("验证码出现错误:" + des);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myCountTimer.cancel();
        myCountTimer.onFinish();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_get:{
                myCountTimer.start();
                SMSSDK.getVerificationCode("86", et_input_phone.getText().toString());
                break;
            }
            case R.id.button_commit:{
                SMSSDK.submitVerificationCode("86", et_input_phone.getText().toString(), et_input_number.getText().toString());
                break;
            }

        }
    }
}

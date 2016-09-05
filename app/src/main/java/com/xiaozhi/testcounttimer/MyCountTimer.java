package com.xiaozhi.testcounttimer;

import android.os.CountDownTimer;
import android.widget.TextView;

public class MyCountTimer extends CountDownTimer {
    private String endString;
    private TextView tvSendCode;
    public int normalColor = 0xfff30008;
    public int timingColor = 0xff969696;
    public long TOTAL_TIME = 60000;
    public long EVERY_TIME = 1000;

    public MyCountTimer(TextView tvSendCode, int normalColor, int timingColor, String endString, long TOTAL_TIME, long EVERY_TIME) {
        super(TOTAL_TIME, EVERY_TIME);
        this.tvSendCode = tvSendCode;
        this.normalColor = normalColor;
        this.timingColor = timingColor;
        this.endString = endString;
        this.TOTAL_TIME =  TOTAL_TIME;
        this.EVERY_TIME = EVERY_TIME;
    }

    public MyCountTimer(TextView tvSendCode, String endString, long TOTAL_TIME, long EVERY_TIME) {
        super(TOTAL_TIME, EVERY_TIME);
        this.tvSendCode = tvSendCode;
        this.endString = endString;
        this.TOTAL_TIME =  TOTAL_TIME;
        this.EVERY_TIME = EVERY_TIME;
    }

    public MyCountTimer(TextView tvSendCode, String endString, long TOTAL_TIME) {
        super(TOTAL_TIME, 1000);
        this.tvSendCode = tvSendCode;
        this.endString = endString;
        this.TOTAL_TIME =  TOTAL_TIME;
    }

    public MyCountTimer(TextView tvSendCode, String endString) {
        super(60000, 1000);
        this.tvSendCode = tvSendCode;
        this.endString = endString;
    }

    // 计时完毕时触发
    @Override
    public void onFinish() {
        if (normalColor > 0) {
            tvSendCode.setTextColor(normalColor);
        }
        tvSendCode.setText(endString);
        tvSendCode.setEnabled(true);
    }

    // 计时过程显示
    @Override
    public void onTick(long millisUntilFinished) {
        if (timingColor > 0) {
            tvSendCode.setTextColor(timingColor);
        }
        tvSendCode.setEnabled(false);
        tvSendCode.setText(millisUntilFinished / 1000 + "s");
    }
}
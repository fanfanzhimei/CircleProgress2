package com.zhi.www.circleprogress2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhi.www.circleprogress2.weight.CircleProgress;


public class MainActivity extends Activity implements View.OnClickListener {
    private int progress = 0;

    private Button mBtnStart;
    private Button mBtnClear;
    private CircleProgress mCp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCp1 = (CircleProgress) findViewById(R.id.cp_1);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnClear = (Button) findViewById(R.id.btn_clear);
        mBtnStart.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                getStart();
                break;
            case R.id.btn_clear:
                progress = 0;
                mCp1.setProgress(0);
                break;
        }
    }

    private void getStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress <= 100) {
                    progress = progress + 3;
                    mCp1.setProgress(progress);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

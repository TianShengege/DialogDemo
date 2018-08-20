package com.example.user.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.test.Constant.Constant;
import com.example.user.test.CreateImea.Imea;
import com.example.user.test.CustomDialog.SexDialog;
import com.example.user.test.Rt.Retrofits;
import com.example.user.test.Handler.XYHandler;


public class MainActivity extends Activity implements View.OnClickListener, XYHandler.HandleMsgListener {

    private Button bt;
    private TextView tv;

    private Context context;
    private SexDialog sexDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        init();
    }

    public void init() {
        bt = findViewById(R.id.button);
        tv = findViewById(R.id.textView2);
        bt.setOnClickListener(this);
        XYHandler.getInstance().setHandleMsgListener(this);
        new Imea().getImea(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                if (sexDialog == null) {
                    sexDialog = new SexDialog(context);
                }
                sexDialog.show();

                sexDialog.setOnSexClick(new SexDialog.onSexClick() {
                    @Override
                    public void setOnsex(String sex) {
                        if (sexDialog != null) sexDialog.dismiss();
                        new Retrofits().initRetrofit();
                    }
                });
                break;
        }
    }


    @Override
    public void handleMsg(Message msg) {
        final String data = (String) msg.obj;
        switch (msg.what) {
            case Constant.REQUEST_SUCCESS:
                tv.setText("请求成功"+data);
                break;
        }

    }
}
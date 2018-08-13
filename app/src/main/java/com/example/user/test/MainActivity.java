package com.example.user.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.test.Rt.Retrofits;
import com.example.user.test.Rt.XYHandler;


public class MainActivity extends Activity implements View.OnClickListener,XYHandler.HandleMsgListener {

    private Button bt;
    private TextView tv;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        bt = (Button) findViewById(R.id.button);
       tv=(TextView)findViewById(R.id.textView2) ;
         bt.setOnClickListener(this);
        XYHandler.getInstance().setHandleMsgListener(this);
    }

    SexDialog sexDialog;
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                if(sexDialog==null){
                    sexDialog = new SexDialog(context);
                }
                sexDialog.show();
                sexDialog.setOnSexClick(new SexDialog.onSexClick() {
                    @Override
                    public void setOnsex(String sex) {
                        if(sexDialog!=null)   sexDialog.dismiss();
                        Retrofits.getInstance().initRetrofit(tv);
                    }
                });
                break;
        }
    }


    @Override
    public void handleMsg(Message msg) {
        final String data = (String) msg.obj;
        switch (msg.what) {
            case 1:
                break;
        }

    }
}
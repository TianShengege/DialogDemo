package com.example.user.test.CustomDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.user.test.R;


public class NetworkReconnection extends AlertDialog {

    private Context context;
    private View view;


    public NetworkReconnection(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_net_reconnection, null);//加载自定义布局
        setContentView(view);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(false); // 点击屏幕Dialog以外的地方是否消失

    }


    private onSexClick onSexClick;

    /**
     * 通过接口监听事件
     */
    public void setOnSexClick(onSexClick sexClick) {
        this.onSexClick = sexClick;
    }


    /**
     * 定义回调接口
     */
    public interface onSexClick {
        void setOnsex(String sex);
    }


}

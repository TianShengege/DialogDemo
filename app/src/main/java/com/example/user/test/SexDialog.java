package com.example.user.test;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class SexDialog  extends Dialog {

    private Button btRetry;
    private View view;



    public  SexDialog(Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog, null);//加载自定义布局
        btRetry=(Button)view.findViewById(R.id.button2);
        setContentView(view);

        btRetry.setBackgroundColor(Color.RED);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setCanceledOnTouchOutside(false); // 点击屏幕Dialog以外的地方是否消失
        setBtLinstener();
    }

    /**
     * Button监听
     */
    public void  setBtLinstener() {
        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSexClick.setOnsex("调试成功");
            }
        });
    }

    private onSexClick onSexClick;
    /**
     * 通过接口监听事件
     */
    public void setOnSexClick(onSexClick sexClick) {
        this.onSexClick = sexClick;
    }

    /**
     *   定义回调接口
     */
    public interface onSexClick {
        void setOnsex(String sex);
    }


}

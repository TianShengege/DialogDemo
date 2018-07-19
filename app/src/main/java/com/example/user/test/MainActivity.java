package com.example.user.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(this);
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
                        Toast.makeText(context, "网络连接重试成功" + sex, Toast.LENGTH_LONG).show();
                        if(sexDialog!=null)   sexDialog.dismiss();
                    }
                });
                break;
        }
    }

}
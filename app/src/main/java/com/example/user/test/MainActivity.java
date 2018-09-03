package com.example.user.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.test.Constant.Constant;
import com.example.user.test.CreateImea.Imea;
import com.example.user.test.CustomDialog.SexDialog;
import com.example.user.test.Rt.GetLogo;
import com.example.user.test.Rt.Retrofits;
import com.example.user.test.Handler.XYHandler;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import static android.content.ContentValues.TAG;


public class MainActivity extends Activity implements View.OnClickListener, XYHandler.HandleMsgListener {

    private static final int RC_SIGN_IN =10003;
    private Button bt;
    private TextView tv;

    private Context context;
    private SexDialog sexDialog;
    private SignInButton btGoogle;
    public GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private Button btLogo;

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
        btGoogle = findViewById(R.id.google_login);
        btLogo=findViewById(R.id.bt_logo);
        btGoogle.setSize(SignInButton.SIZE_STANDARD);
        bt.setOnClickListener(this);
        btGoogle.setOnClickListener(this);
        btLogo.setOnClickListener(this);
        XYHandler.getInstance().setHandleMsgListener(this);
        new Imea().getImea(this);
        //google登陆初始化
         gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
         mGoogleSignInClient=GoogleSignIn.getClient(this,gso);

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
                
            case R.id.google_login:
                googleLogin();
                break;
            case R.id.bt_logo:
                new GetLogo(this).initRetrofit();
                break;
        }
    }

    /**
     * 登陆
     */
    private void googleLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void handleMsg(Message msg) {
        final String data = (String) msg.obj;
        switch (msg.what) {
            case Constant.REQUEST_SUCCESS:
                tv.setText("请求成功"+data);
                break;
            case Constant.BT_GOOGLELOGIN_SUCCESS:
                btGoogle.setVisibility(View.INVISIBLE);
                break;
            case Constant.BT_GOOGLELOGIN_FAIL:
                btGoogle.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    protected void onStart() {
      if(GoogleSignIn.getLastSignedInAccount(this)!=null){
          //用户已经登陆,隐藏这个登陆按钮
          XYHandler.getInstance().sendEmptyMessage(Constant.BT_GOOGLELOGIN_SUCCESS);
      }
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 从GoogleSignInClient.getSignInIntent（...）启动Intent返回的结果
        if (requestCode == RC_SIGN_IN) {
        // GoogleSignInAccount对象包含有关已登录用户的信息，例如用户的名称
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // 登陆成功显示ui.
            XYHandler.getInstance().sendEmptyMessage(Constant.BT_GOOGLELOGIN_SUCCESS);
        } catch (ApiException e) {
            //登陆失败
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            XYHandler.getInstance().sendEmptyMessage(Constant.BT_GOOGLELOGIN_FAIL);
        }
    }
}


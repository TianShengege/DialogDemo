package com.xysdk.commonlibrary.WebView;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;


@SuppressLint("HandlerLeak")
public class XYHandler extends Handler {

    private HandleMsgListener listener;

    //使用单例模式创建GlobalHandler
    private XYHandler() {
    }

    private static class Holder {
        private static final XYHandler HANDLER = new XYHandler();
    }

    public static XYHandler getInstance() {
        return Holder.HANDLER;
    }

    @Override
    public void handleMessage(Message msg) {
        listener.handleMsg(msg);
    }

    public interface HandleMsgListener {
        void handleMsg(Message msg);
    }

    public void setHandleMsgListener(HandleMsgListener listener) {
        this.listener = listener;
    }

}

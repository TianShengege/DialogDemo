package com.example.user.test.CreateImea;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.user.test.Util.Utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetIp {

    private  Context context;

    public GetIp(Context context){
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getIp(){
        String ip;
        ConnectivityManager conMann = (ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = conMann.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetworkInfo = conMann.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo ethernet = conMann.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);

        if (mobileNetworkInfo.isConnected()) {
            ip = getLocalIpAddress();
            Log.i("XYSDK",ip);
        }else if(wifiNetworkInfo.isConnected())
        {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            ip = Utils.intToIp(ipAddress);
            Log.i("XYSDK",ip);
        }
    }

    private String getLocalIpAddress() {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ip= inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return ip;
    }
}

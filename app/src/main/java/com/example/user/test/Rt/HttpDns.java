package com.example.user.test.Rt;

import android.util.Log;

import com.qiniu.android.dns.DnsManager;
import com.qiniu.android.dns.IResolver;
import com.qiniu.android.dns.NetworkInfo;
import com.qiniu.android.dns.local.Resolver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

import static java.net.InetAddress.getAllByName;
import static java.net.InetAddress.getByName;

public class HttpDns implements Dns {

    private DnsManager dnsManager;

    public HttpDns() {
        IResolver[] resolvers = new IResolver[1];
        try {
            resolvers[0] = new Resolver(getByName("172.20.28.136"));
            dnsManager = new DnsManager(NetworkInfo.normal, resolvers);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        if (dnsManager == null)  //当构造失败时使用默认解析方式
            return Dns.SYSTEM.lookup(hostname);

        try {
            Log.i("XYSDK","1222222222222");
            String[] ips = dnsManager.query(hostname);  //获取HttpDNS解析结果
            Log.i("XYSDK",Arrays.toString(ips)+"1222222222222");
            if (ips == null || ips.length == 0) {
                Log.i("XYSDK","3333333333=");
                return Dns.SYSTEM.lookup(hostname);
            }
            Log.i("XYSDK","1================");
            List<InetAddress> result = new ArrayList<>();
            for (String ip : ips) {  //将ip地址数组转换成所需要的对象列表
                result.addAll(Arrays.asList(getAllByName(ip)));
            }
            Log.i("XYSDK",result+"");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //当有异常发生时，使用默认解析
        return Dns.SYSTEM.lookup(hostname);
    }
}

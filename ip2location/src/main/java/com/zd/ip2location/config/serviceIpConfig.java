package com.zd.ip2location.config;

import com.zd.ip2location.bean.LatAndLongitude;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class serviceIpConfig {
    //服务器经纬度写在这里
    public static final List<GlobalCoordinates> DEFAULT_METHODS ;
    static {
        //先纬度 后经度  121.482049,31.238554 人民广场服务器
        List<GlobalCoordinates > serviceIpAllList = new ArrayList<GlobalCoordinates >(4);
        serviceIpAllList.add(new GlobalCoordinates (31.238554,121.482049));
        serviceIpAllList.add(new GlobalCoordinates (22.15,114.15));
        serviceIpAllList.add(new GlobalCoordinates (39.4 ,115.7));
        serviceIpAllList.add(new GlobalCoordinates (1.18 ,103.51));
        DEFAULT_METHODS = Collections.unmodifiableList(serviceIpAllList);
    }

    public static final List<String> DEFAULT_IP=new ArrayList<String >()  ;
    static {
        DEFAULT_IP.add("192.168.1.154");
        DEFAULT_IP.add("123.111.111.111");
        DEFAULT_IP.add("123.111.111.121");
        DEFAULT_IP.add("123.111.111.131");
    }

    public static final Map<String,String> IP_Map=new HashMap<>() ;
    static {
        IP_Map.put("BJ","11.11.11.001");
        IP_Map.put("SH","11.11.11.002");
        IP_Map.put("HK","11.11.11.003");
        IP_Map.put("SG","11.11.11.004");
    }
}

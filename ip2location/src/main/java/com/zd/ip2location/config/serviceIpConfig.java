package com.zd.ip2location.config;

import com.zd.ip2location.bean.LatAndLongitude;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static final List<String> DEFAULT_IP ;
    static {
        //IP集合
        List<String > IpAllList = new ArrayList<String >(4);
        IpAllList.add("123.111.111.111");
        IpAllList.add("123.111.111.111");
        IpAllList.add("123.111.111.111");
        IpAllList.add("123.111.111.111");
        DEFAULT_IP = Collections.unmodifiableList(IpAllList);
    }
}

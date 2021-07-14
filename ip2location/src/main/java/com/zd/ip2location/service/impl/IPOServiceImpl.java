package com.zd.ip2location.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zd.ip2location.bean.LatAndLongitude;
import com.zd.ip2location.pojo.IPLocation;
import com.zd.ip2location.service.IPOService;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MulticastSocket;
import java.net.URL;
import java.util.*;

@Service
public class IPOServiceImpl implements IPOService {

    @Resource
    private RestTemplate restTemplate;

    public final static double Ea = 6378137;     //   赤道半径

    public final static double Eb = 6356725;     //   极半径

    @Override
    public String getRealIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String netIp;
        if (request.getHeader("x-forwarded-for") == null) {
            netIp= request.getRemoteAddr();
        }
        else {
            netIp= request.getHeader("x-forwarded-for");
        }
        return netIp;
    }

    @Override
    public Integer getTTL() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        return socket.getTimeToLive();
    }

    @Override
    public List<Double> getDistance(GlobalCoordinates  curLocation, List<GlobalCoordinates > target) {
        List<Double> AllDistance=new ArrayList<>();
        for(GlobalCoordinates l1:target){
            AllDistance.add(getDistanceMeter(curLocation,l1,Ellipsoid.Sphere));
        }
        return AllDistance;
    }


    @Override
    public Integer getserverIp(HashMap<Integer,Double> map) {
        if(map==null){
            return -1;
        }
        List<Map.Entry<Integer, Double>> list = new ArrayList(map.entrySet());
        list.sort(Comparator.comparingDouble(Map.Entry::getValue));
        return list.get(0).getKey();
    }


    public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid){

        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }



}

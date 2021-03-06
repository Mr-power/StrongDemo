package com.zd.ip2location.service;

import com.zd.ip2location.bean.LatAndLongitude;
import com.zd.ip2location.pojo.IPLocation;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPOService {
    String getRealIp();
    Integer getTTL() throws  IOException;
    List<Double> getDistance(GlobalCoordinates curLocation, List<GlobalCoordinates > list);
    Integer getserverIp(HashMap<Integer, Double> map);
    String getWhichOneServer(String region);
    String oneCanUse(String[] ipCode);
}

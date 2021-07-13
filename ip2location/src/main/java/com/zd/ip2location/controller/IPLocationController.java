package com.zd.ip2location.controller;

import com.zd.ip2location.bean.LatAndLongitude;
import com.zd.ip2location.bean.OsName;
import com.zd.ip2location.config.KindsOs;
import com.zd.ip2location.config.serviceIpConfig;
import com.zd.ip2location.pojo.IPLocation;
import com.zd.ip2location.service.IPLocationService;
import com.zd.ip2location.service.impl.IPOServiceImpl;
import com.zd.ip2location.util.SocketUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class IPLocationController {

    @Resource
    private IPLocationService ipLocationService;

    @Autowired
    private IPOServiceImpl ipoService;



    @ApiOperation(value = "ip获取 地理位置", notes = "ip获取地理位置")
    @RequestMapping(value = "/ipoLocation",method = RequestMethod.POST)
    @ResponseBody
    public IPLocation getIPLocation(@RequestBody String ip){
        return ipLocationService.getIPLocation(ip);
    }




    @ApiOperation(value = "获取网络延迟", notes = "获取此台服务器网络延迟")
    @RequestMapping(value = "/getHowMuchTime",method = RequestMethod.POST)
    @ResponseBody
    public Integer getMuchTime(@RequestBody String ip) throws IOException {
        /**
         *
         *
         *
         */
        // 1 表 上海 2 表 北京  3 表 香港 4 表 新加坡
        HashMap<Integer,Integer> delay=new HashMap<>();
        String realIp=ipoService.getRealIp();
        //初始TTL的值 简单点，直接设置255的话，会导致距离因素影响巨小。
        int oriTTL;
        String os = System.getProperty("os.name");
        if(KindsOs.UNIX.getMsg().equals(os)){
            oriTTL=KindsOs.UNIX.getTtl();
        }
        else oriTTL=KindsOs.LINUX.getTtl();

        //当前TTL的值
        int TTL=oriTTL-ipoService.getTTL();
        //100千米 对应 1ms 距离+TTL = delay
        //直达坐标
        List<Double> distance = ipoService.getDistance(new GlobalCoordinates(31.21345,121.568812), serviceIpConfig.DEFAULT_METHODS);
        int index=1;
        for(Double l1 : distance){
            delay.put(index++,l1.intValue()/100000 + TTL);
        }
        //先写ip
        //IPLocation thisLocation= ipLocationService.getIPLocation(ip);
        //当前服务器延迟
        int cur_delay;
        Double oneDistance = ipoService.getCurDistance(new GlobalCoordinates(31.21345,121.568812), serviceIpConfig.DEFAULT_METHODS.get(1));
        cur_delay= oneDistance.intValue()/100000 + TTL;
        return cur_delay;
    }

}

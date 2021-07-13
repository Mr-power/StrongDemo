package com.zd.ip2location.controller;

import com.zd.ip2location.bean.LatAndLongitude;
import com.zd.ip2location.bean.OsName;
import com.zd.ip2location.bean.Result;
import com.zd.ip2location.config.KindsOs;
import com.zd.ip2location.config.ResultEnum;
import com.zd.ip2location.config.serviceIpConfig;
import com.zd.ip2location.pojo.IPLocation;
import com.zd.ip2location.service.IPLocationService;
import com.zd.ip2location.service.impl.IPOServiceImpl;
import com.zd.ip2location.util.ResultUtil;
import com.zd.ip2location.util.SocketUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class IPLocationController {

    @Resource
    private IPLocationService ipLocationService;

    @Autowired
    private IPOServiceImpl ipoService;

    @Resource
    private RestTemplate restTemplate;



    @ApiOperation(value = "ip获取 地理位置", notes = "ip获取地理位置")
    @RequestMapping(value = "/ipoLocation",method = RequestMethod.POST)
    @ResponseBody
    public IPLocation getIPLocation(@RequestBody String ip){
        return ipLocationService.getIPLocation(ip);
    }

    @ApiOperation(value = "ip运维员控制故障ip", notes = "ip运维员控制故障ip")
    @RequestMapping(value = "/selectRemoveIp",method = RequestMethod.POST)
    @ResponseBody
    public Result selectRemoveIp (@RequestBody String RemoveIp) {
        serviceIpConfig.DEFAULT_IP.remove(RemoveIp);
        return ResultUtil.success();
    }

    @ApiOperation(value = "ip运维员控制故障ip恢复", notes = "ip运维员控制故障ip恢复")
    @RequestMapping(value = "/selectIp",method = RequestMethod.POST)
    @ResponseBody
    public Result selectResetIp (@RequestBody String ResetIp) {
        if(serviceIpConfig.DEFAULT_IP.contains(ResetIp)){
            return ResultUtil.error(403,"无需恢复，服务器已经存在");
        }
        serviceIpConfig.DEFAULT_IP.add(ResetIp);
        return ResultUtil.success();
    }

    @ApiOperation(value = "估算每个服务器网络延迟，并绑定服务器ip", notes = "估算每个服务器网络延迟，并绑定服务器ip")
    @RequestMapping(value = "/getHowMuchTime",method = RequestMethod.POST)
    @ResponseBody
    public Result getMuchTime(@RequestBody String ip) throws IOException {
        /**
         *
         */
        IPLocation Location=ipLocationService.getIPLocation(ip);
        //GlobalCoordinates CurCoordinates=ipoService.getCoordinates(Location);
        // 服务器 延迟 估算 map
        HashMap<Integer,Integer> delay=new HashMap<>();
      //     String realIp=ipoService.getRealIp();
      //     //初始TTL的值 简单点，直接设置255的话，会导致距离因素影响巨小。
      //     int oriTTL;
      //     String os = System.getProperty("os.name");
      //     if(KindsOs.UNIX.getMsg().equals(os)){
      //         oriTTL=KindsOs.UNIX.getTtl();
      //     }
      //     else oriTTL=KindsOs.LINUX.getTtl();

      //     //当前TTL的值
      //     int TTL=oriTTL-ipoService.getTTL();
        //100千米 对应 1ms 距离+TTL = delay
        //直达坐标
        List<Double> distance = ipoService.getDistance(new GlobalCoordinates(31.21345,121.568812), serviceIpConfig.DEFAULT_METHODS);
        int index=1;
        for(Double l1 : distance){
            delay.put(index++,l1.intValue()/100000);
        }
        int key =ipoService.getserverIp(delay);
        if(key==-1){
            return ResultUtil.error(404,"无服务器可用");
        }
       // String BestIp= delay.get(key);
        return ResultUtil.success();
    }

}

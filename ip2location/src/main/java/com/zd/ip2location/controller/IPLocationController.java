package com.zd.ip2location.controller;


import com.zd.ip2location.bean.Result;
import com.zd.ip2location.config.ServerCode;
import com.zd.ip2location.config.serviceIpConfig;
import com.zd.ip2location.pojo.IPLocation;
import com.zd.ip2location.service.IPLocationService;
import com.zd.ip2location.service.impl.IPOServiceImpl;
import com.zd.ip2location.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

@RestController
public class IPLocationController {

    @Resource
    private IPLocationService ipLocationService;

    @Autowired
    private IPOServiceImpl ipoService;

    @Resource
    private RestTemplate restTemplate;

    @ApiOperation(value = "选择最优服务器", notes = "选择最优服务器")
    @RequestMapping(value = "/getWhichOne",method = RequestMethod.POST)
    @ResponseBody
    public Result getWhichOne(@RequestBody String ip) throws IOException {
        IPLocation curLocationMessage=ipLocationService.getIPLocation(ip);
        String ServerCode;
        String[] ServerPriority;
        String CanUse;
        if(curLocationMessage.getCountryCode().equals("CN")) {
             ServerCode = ipoService.getWhichOneServer(curLocationMessage.getRegionName());

             ServerPriority = ServerCode.split(",");
            //判断哪台服务器能用。
             CanUse = ipoService.oneCanUse(ServerPriority);
             return ResultUtil.success(CanUse);
        }
        ServerCode = ipoService.getWhichOneServer(curLocationMessage.getCountryCode());
        ServerPriority = ServerCode.split(",");
        //判断哪台服务器能用。
        CanUse = ipoService.oneCanUse(ServerPriority);
        return ResultUtil.success(CanUse);
    }

    @ApiOperation(value = "ip获取 地理位置", notes = "ip获取地理位置")
    @RequestMapping(value = "/ipoLocation",method = RequestMethod.POST)
    @ResponseBody
    public Result getIPLocation(@RequestBody String ip){

        return ResultUtil.success(ipLocationService.getIPLocation(ip));
    }

    @ApiOperation(value = "ip运维员控制故障ip——Code", notes = "ip运维员控制故障ip——Code【如HK，BJ...】")
    @RequestMapping(value = "/selectRemoveIp",method = RequestMethod.POST)
    @ResponseBody
    public Result selectRemoveIp (@RequestBody String RemoveIp) {
        serviceIpConfig.IP_Map.remove(RemoveIp);
        return ResultUtil.success();
    }

    @ApiOperation(value = "ip运维员控制故障ip恢复[code,IP][HK,111.11.11.11]", notes = "ip运维员控制故障ip恢复[code,IP][HK,111.11.11.11]")
    @RequestMapping(value = "/selectIp",method = RequestMethod.POST)
    @ResponseBody
    public Result selectResetIp (@RequestBody String ResetIp) {
        String [] insertIp=ResetIp.split(",");
        try {
            Enum.valueOf(ServerCode.class, insertIp[0]);
        }catch (IllegalArgumentException e){
            return ResultUtil.error(403,"区域代码输入错误");
        }
        if(serviceIpConfig.IP_Map.containsKey(insertIp[0])){
            return ResultUtil.error(403,"无需恢复，该区域服务器已经存在");
        }
        serviceIpConfig.IP_Map.put(insertIp[0],insertIp[1]);
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
        HashMap<Integer,Double> delay=new HashMap<>();
        //List<Double> distance = ipoService.getDistance(CurCoordinates, serviceIpConfig.DEFAULT_METHODS);
    //      int index=1;
    //      for(Double l1 : distance){
    //          delay.put(index++,l1);
    //      }
    //      int key =ipoService.getserverIp(delay);
    //      if(key==-1){
    //          return ResultUtil.error(404,"无服务器可用");
    //      }
    //     // String BestIp= delay.get(key);
        return ResultUtil.success();
    }

}

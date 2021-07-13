package com.zd.ip2location.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zd.ip2location.pojo.IPLocation;

public interface IPLocationService extends IService<IPLocation> {

    IPLocation getIPLocation(String ip);



}

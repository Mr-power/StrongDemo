package com.zd.ip2location.config;

import com.zd.ip2location.mapper.IPLocationMapper;
import com.zd.ip2location.pojo.IPLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class IPLocationConfig {

    @Resource
    private IPLocationMapper ipLocationMapper;

    private static final int IP_DATA_SIZE = 1048575;   // ip_location 表共 1048575 条记录

    public static List<IPLocation> ipLocationList = new ArrayList<>(IP_DATA_SIZE);

    // 在项目启动时将 ip_location 表的数据加载到内存
    @PostConstruct
    public void loadIPLocationList(){
        ipLocationList = ipLocationMapper.selectList(null);
        log.info("ip数据加载完成，共{}条记录", ipLocationList.size());
    }


}

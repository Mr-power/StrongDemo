package com.zd.ip2location.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zd.ip2location.config.IPLocationConfig;
import com.zd.ip2location.mapper.IPLocationMapper;
import com.zd.ip2location.pojo.IPLocation;
import com.zd.ip2location.service.IPLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class IPLocationServiceImpl extends ServiceImpl<IPLocationMapper, IPLocation> implements IPLocationService {

    @Override
    public IPLocation getIPLocation(String ip) {
        // 计算 ipNumber， 310643969 = 18.132.13.1 = 18*(256*256*256) + 132*(256*256) + 13*256 + 1
        // 注意：局域网 192 开头，计算出的 ipNumber 不在数据库内，直接抛出异常
        String[] ips = ip.split("\\.");
        Long ipNumber = (Long.parseLong(ips[0]) << 24) + (Long.parseLong(ips[1]) << 16) + (Long.parseLong(ips[2]) << 8) + (Long.parseLong(ips[3]));
        int ipTo = binarySearch(IPLocationConfig.ipLocationList, ipNumber);
        IPLocation ipLocation = IPLocationConfig.ipLocationList.get(ipTo);
        log.info("ip = {}, ipNumber = {}", ip, ipNumber);
        log.info("ipTo = {}", ipTo);
        log.info("ipLocation = {}", ipLocation);
        return ipLocation;
    }

    // 二分查找 ip_to 字段：若查找成功，返回下标；若查找失败，返回区间右边界
    private int binarySearch(List<IPLocation> ipLocationList, Long target) {
        int low = 0;
        int high = ipLocationList.size() - 1;
        int mid;
        if(target < ipLocationList.get(low).getIpTo() || target > ipLocationList.get(high).getIpTo() || low > high){
            throw new IllegalArgumentException("ip不存在或ip数据加载出错，如果您使用的是局域网ip，请使用广域网ip");
        }
        while(low <= high){
            mid = (low + high) >> 1;
            if(target < ipLocationList.get(mid).getIpTo()){
                high = mid - 1;
            }else if(target > ipLocationList.get(mid).getIpTo()){
                low = mid + 1;
            }else{
                return mid;
            }
        }
        return low;
    }

}

package com.zd.ip2location.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "ip_location")
public class IPLocation {

    private Long ipFrom;

    private Long ipTo;

    private String countryCode;

    private String countryName;

    private String regionName;

    private String cityName;
}

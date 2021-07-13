package com.zd.ip2location;

import com.zd.ip2location.service.IPLocationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class Ip2locationApplicationTests {

    @Resource
    private IPLocationService ipLocationService;

    @Test
    void contextLoads() {
        System.out.println(ipLocationService.getIPLocation("18.132.13.1"));
    }

}
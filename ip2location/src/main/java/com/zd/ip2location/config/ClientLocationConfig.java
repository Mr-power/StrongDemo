package com.zd.ip2location.config;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@PropertySource(value = {"classpath:/application.yml"}, encoding = "utf-8")
@ConfigurationProperties(prefix = "server")
public class ClientLocationConfig {

    private  Map<String, String> map = new HashMap<>();

}
package com.zd.ip2location.bean;

import lombok.Data;

@Data
public class LatAndLongitude {
    private double LAT;
    private double LON;

    public LatAndLongitude(double LAT, double LON) {
        this.LAT = LAT;
        this.LON = LON;
    }
}

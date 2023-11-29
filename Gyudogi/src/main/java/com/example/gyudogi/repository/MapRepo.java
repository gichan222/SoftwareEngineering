package com.example.gyudogi.repository;

import java.util.List;

public class MapRepo {
    private String[][] mapData;
    private List<String> objectSpot;
    private List<String> hazardSpot;
    private String startSpot;
    private String startDirection;
    private int n, m;

    public MapRepo(String[][] mapData, String startSpot, String startDirection, List<String> objectSpot, List<String> hazardSpot) {
        this.mapData = mapData;
        this.startSpot = startSpot;
        this.startDirection = startDirection;
        this.objectSpot = objectSpot;
        this.hazardSpot = hazardSpot;
        this.n = mapData.length;
        this.m = mapData[0].length;
    }

    public String[][] getMapData() {
        return mapData;
    }

    public String getStartSpot() {
        return startSpot;
    }

    public String getStartDirection() {
        return startDirection;
    }

    public List<String> getObjectSpot() {
        return objectSpot;
    }


    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }
}

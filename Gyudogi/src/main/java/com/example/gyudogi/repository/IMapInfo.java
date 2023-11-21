package com.example.gyudogi.repository;

import java.util.List;

public interface IMapInfo {
    void setHiddenData(List<String> hazard, List<String> cb);
    List<String> getHazardH();
    List<String> getCbH();
    int getMapSize();
    String getStartSpot();
    void getBackObjectSpot(List<String> list);
    List<String> getObjectSpot();
    List<String> getHazardSpot();
    void removeHiddenHSpot(String point);
    void removeHiddenCBSpot(String point);

    String[][] getMap();
}
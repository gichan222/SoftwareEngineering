package com.example.gyudogi.repository;

import java.util.ArrayList;
import java.util.List;

public class PathRepo{ // 경로에 대한 정보를 저장하는 클래스
    private List<String> path;
    private List<String> pathInfo;
    private List<List<String>> finalInfo;


    public PathRepo() {
        this.pathInfo = new ArrayList<>();
        this.finalInfo = new ArrayList<>(); // 리스트 초기화
    }

    public List<String> getPathInfo() {
        return this.path;
    }

    public void setPathInfo(List<String> newPath) {
        this.path = newPath;
    }
    public List<List<String>> getFinalInfo() {
        return finalInfo;
    }

    public void addFinalInfo(List<String> info) {
        this.finalInfo.add(info);
    }
}


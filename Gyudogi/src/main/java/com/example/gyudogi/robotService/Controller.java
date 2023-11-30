package com.example.gyudogi.robotService;

import com.example.gyudogi.repository.MapRepo;
import com.example.gyudogi.repository.PathRepo;


import java.util.ArrayList;
import java.util.List;

public class Controller { // 로봇의 동작을 담당하는 클래스
    private MapRepo mapRepo;
    private PathRepo pathRepo;
    private PathFinder pathFinder;
    private SIM sim;

    public Controller(MapRepo mapRepo, PathRepo pathRepo, PathFinder pathFinder, SIM sim) {
        this.mapRepo = mapRepo;
        this.pathRepo = pathRepo;
        this.pathFinder = pathFinder;
        String[] startSpotArr = mapRepo.getStartSpot().split(", ");
        this.sim = new SIM(mapRepo.getMapData(), Integer.parseInt(startSpotArr[0]), Integer.parseInt(startSpotArr[1]), mapRepo.getStartDirection());
    }

    public String getCurrentSpot() {
        return sim.getX() + ", " + sim.getY();
    }

    public String actSensor() { // 센서 가동 시키는 함수(현재위치,현재 방향, 컬러블럽, 해저드 탐색)
        StringBuilder sb = new StringBuilder();
        sb.append(sim.getX() + ", " + sim.getY() + ", " + sim.getDirection() + " ");
        sb.append(sim.colorBlobSensor() + " ");
        sb.append(sim.hazardSensor() + " ");
        return sb.toString();
    }

    private String getDirection(int targetX, int targetY) { // 목적지에 대한 방향을 설정하는 함수(시계방향 90도 회전)
        if (targetX > sim.getX()) {
            return "E";
        } else if (targetX < sim.getX()) {
            return "W";
        } else if (targetY > sim.getY()) {
            return "N";
        } else if (targetY < sim.getY()) {
            return "S";
        }

        return "";
    }

    public void moveAndAct() { // 움직이면서 동작하게 해주는 함수(경로 가져오고, 경로 따라 이동하면서 오작동, 해저드 탐색 시 경로 재탐색)
        List<String> pathInfo = pathRepo.getPathInfo();
        saveFinalInfo();
        for (int i = 0; i < pathInfo.size(); i++) {
            String spot = pathInfo.get(i);

            if (!getCurrentSpot().equals(spot)) {
                String[] splitSpot = spot.split(", ");
                int targetX = Integer.parseInt(splitSpot[0]);
                int targetY = Integer.parseInt(splitSpot[1]);

                while (true) {
                    String currentDirection = getDirection(targetX, targetY);
                    if (sim.getDirection().equals(currentDirection)) {
                        break;
                    } else {
                        sim.turn();
                        actSensor();
                        saveFinalInfo();
                    }
                }

                int moveResult = sim.move();
                actSensor();
                saveFinalInfo();
                if (moveResult == 1) {
                    actSensor();
                    String hazardSpot = sim.hazardSensor();
                    if (!hazardSpot.equals("")) {
                        this.recalculatePath();
                        pathInfo = pathRepo.getPathInfo();
                        i = -1;
                    }
                } else if (moveResult == 2){
                    this.recalculatePath();
                } else {
                    actSensor();
                    this.recalculatePath();
                    pathInfo = pathRepo.getPathInfo();
                    i = -1;
                }
            }

        }
    }

    public void recalculatePath() { //경로 재계산 호출 함수
        List<String> newPath = new ArrayList<>();
        pathFinder.findPath(sim.getX(), sim.getY(), mapRepo.getObjectSpot(), newPath);
        pathRepo.setPathInfo(newPath);
    }


    private void saveFinalInfo() { // 최종 경로 저장시키는 함수(x,y,방향,color,hazard,목적지도착여부)
        List<String> info = new ArrayList<>();
        info.add(String.valueOf(sim.getX()));
        info.add(String.valueOf(sim.getY()));
        info.add(sim.getDirection());
        info.add(sim.colorBlobSensor());
        info.add(sim.hazardSensor());
        String currentSpot = getCurrentSpot();
        if (mapRepo.getObjectSpot().contains(getCurrentSpot())) {
            info.add("Yes");
            mapRepo.getObjectSpot().remove(currentSpot);
        } else {
            info.add("No");
        }

        pathRepo.addFinalInfo(info);
    }

}

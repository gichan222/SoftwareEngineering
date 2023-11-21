package com.example.gyudogi.robotService;

import com.example.gyudogi.repository.MapRepo;
import com.example.gyudogi.repository.PathRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class Controller {
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

    public String actSensor() {
        StringBuilder sb = new StringBuilder();
        sb.append("현재위치:" + sim.getX() + ", " + sim.getY() + ", " + sim.getDirection() + " ");
        sb.append(sim.colorBlobSensor() + " ");
        sb.append(sim.hazardSensor() + " ");
        return sb.toString();
    }

    public void moveAndAct() {
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

    private String getDirection(int targetX, int targetY) {
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

    public void recalculatePath() {
        List<String> newPath = new ArrayList<>();
        pathFinder.findPath(sim.getX(), sim.getY(), mapRepo.getObjectSpot(), newPath);
        pathRepo.setPathInfo(newPath);
    }


    private void saveFinalInfo() {
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

    public String getCurrentSpot() {
        return sim.getX() + ", " + sim.getY();
    }
}

package com.example.gyudogi;

import org.junit.jupiter.api.Test;
import com.example.gyudogi.repository.MapRepo;
import com.example.gyudogi.repository.PathRepo;
import com.example.gyudogi.robotService.Controller;
import com.example.gyudogi.robotService.PathFinder;
import com.example.gyudogi.robotService.SIM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LastTest {
    @Test
    public void testRobotMovement() {

        // 지도 데이터 생성
        String[][] mapData = new String[6][5];
        for (String[] row : mapData) {
            Arrays.fill(row, "0");
        }
        mapData[5][1] = "-1"; // 장애물
        mapData[3][3] = "-1"; // 장애물
        mapData[4][3] = "2";  // colorBlob
        mapData[2][2] = "2";  // colorBlob
        mapData[3][4] = "1";  // 목적지
        mapData[0][1] = "1";  // 목적지

        // MapRepo 객체 생성
        MapRepo mapRepo = new MapRepo(mapData, "3, 5", "N", Arrays.asList("1, 2", "0, 1"), Arrays.asList("5, 1", "3, 3"));

        // PathRepo 객체 생성
        PathRepo pathRepo = new PathRepo();

        // PathFinder 객체 생성
        PathFinder pathFinder = new PathFinder(mapRepo, pathRepo);

        // 경로 계산
        pathFinder.findPath(3, 1, Arrays.asList("1, 2", "0, 1"), new ArrayList<>());

        // SIM 객체 생성
        SIM sim = new SIM(mapData, 3, 1, mapRepo.getStartDirection());

        // Controller 객체 생성
        Controller controller = new Controller(mapRepo, pathRepo, pathFinder, sim);

        // 로봇 이동 및 정보 출력
        controller.moveAndAct();


        // finalInfo 출력
        List<List<String>> finalInfo = pathRepo.getFinalInfo();
        for (List<String> info : finalInfo) {
            System.out.println("X: " + info.get(0) + ", Y: " + info.get(1) + ", Direction: " + info.get(2) + ", ColorBlobSensor: " + info.get(3) + ", HazardSensor: " + info.get(4) + ", Arrived: " + info.get(5));
        }
    }
}

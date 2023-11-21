package com.example.gyudogi;

import org.junit.jupiter.api.Test;
import com.example.gyudogi.repository.MapRepo;
import com.example.gyudogi.repository.PathRepo;
import com.example.gyudogi.robotService.Controller;
import com.example.gyudogi.robotService.PathFinder;
import com.example.gyudogi.robotService.SIM;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RobotMovementSensorTest {

    @Test
    public void testRobotMovementAndSensor() {
        // 초기 데이터 설정
        String[][] mapData = new String[6][5];
        for (String[] row : mapData) {
            Arrays.fill(row, "0");
        }
        mapData[5][1] = "-1";
        mapData[3][3] = "-1";
        mapData[4][3] = "2";

        String startSpot = "3, 1";
        List<String> objectSpot = Arrays.asList("3, 4", "0, 1");
        List<String> hazardSpot = Arrays.asList("5, 1", "3, 3");

        // MapRepo, PathRepo, PathFinder, SIM, Controller 초기화
        MapRepo mapRepo = new MapRepo(mapData, "3, 1", "N", Arrays.asList("3, 4", "0, 1"), Arrays.asList("5, 1", "3, 3"));
        PathRepo pathRepo = new PathRepo();
        String[] startSpotArr = startSpot.split(", ");
        SIM sim = new SIM(mapData, Integer.parseInt(startSpotArr[0]), Integer.parseInt(startSpotArr[1]), "N");
        PathFinder pathFinder = new PathFinder(mapRepo, pathRepo);
        Controller controller = new Controller(mapRepo, pathRepo, pathFinder, sim);

        // 초기 경로 계산
        pathFinder.findPath(sim.getX(), sim.getY(), objectSpot, pathRepo.getPathInfo());
        List<String> pathResult = pathRepo.getPathInfo();

        // 목적지까지 이동하며 센서 동작과 경로 재계산
        for (String spot : pathResult) {
            String sensorBeforeMovement = controller.actSensor(); // 이동 전 센서 상태

            String actionResult = controller.moveAndAct(); // 이동 및 센서 동작
            String sensorAfterMovement = controller.actSensor(); // 이동 후 센서 상태

            // 테스트 - 이동 전, 이동 후의 센서 결과 확인
            System.out.println("이동 전 센서 상태: " + sensorBeforeMovement);
            System.out.println("이동 결과: " + actionResult);
            System.out.println("이동 후 센서 상태: " + sensorAfterMovement);

            // 이동 후 센서 결과가 변경되었는지 확인하는 예시
            assertTrue(!sensorBeforeMovement.equals(sensorAfterMovement));

            // 목적지 도착 여부 확인
            if (controller.getCurrentSpot().equals(objectSpot.get(objectSpot.size() - 1))) {
                System.out.println("목적지 도착!");
                break;
            }

            // 재탐색 및 새로운 경로 계산
            controller.recalculatePath();
            pathResult = pathRepo.getPathInfo();
        }
    }
}

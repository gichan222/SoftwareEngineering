//package com.example.gyudogi;
//
//import org.junit.jupiter.api.Test;
//import repository.MapRepo;
//import repository.PathRepo;
//import robotService.Controller;
//import robotService.PathFinder;
//import robotService.SIM;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class RobotMoveAndSensorTest {
//    @Test
//    public void testMoveAndSensor() {
//        // 지도 크기 설정
//        int n = 6;
//        int m = 5;
//
//        // 지도 데이터 초기화
//        String[][] mapData = new String[n][m];
//        for (String[] row : mapData) {
//            Arrays.fill(row, "0");
//        }
//
//        // 출발지 설정
//        String startSpot = "3, 1";
//
//        // 장애물 위치 설정
//        List<String> hazardSpot = Arrays.asList("5, 1", "3, 3");
//        for (String hazard : hazardSpot) {
//            String[] splitHazard = hazard.split(", ");
//            mapData[Integer.parseInt(splitHazard[0])][Integer.parseInt(splitHazard[1])] = "-1";
//        }
//
//        // 목적지 위치 설정
//        List<String> objectSpot = Arrays.asList("3, 4", "0, 1");
//
//        // MapRepo, PathRepo 객체 생성
//        MapRepo mapRepo = new MapRepo(mapData, startSpot, objectSpot, hazardSpot);
//        PathRepo pathRepo = new PathRepo();
//
//        // PathFinder 객체 생성
//        PathFinder pathFinder = new PathFinder(mapRepo, pathRepo);
//
//        // 경로 탐색
//        String[] splitStartSpot = startSpot.split(", ");
//        int startX = Integer.parseInt(splitStartSpot[0]);
//        int startY = Integer.parseInt(splitStartSpot[1]);
//        List<String> pathResult = new ArrayList<>();
//        pathFinder.findPath(startX, startY, objectSpot, pathResult);
//
//        // SIM 객체 생성
//        SIM sim = new SIM(mapData, startX, startY, "N");
//
//        // Controller 객체 생성
//        Controller controller = new Controller(mapRepo, pathRepo, pathFinder, sim);
//
//        // 경로를 따라 이동하면서 센서 작동 테스트
//        for (String spot : pathResult) {
//            System.out.println("이동 전 위치: " + controller.getCurrentSpot());
//            System.out.println("이동 전 센서 상태: " + controller.actSensor());
//
//            String actionResult = controller.moveAndAct();
//            System.out.println("이동 결과: " + actionResult);
//
//            String currentSpot = controller.getCurrentSpot();
//            System.out.println("이동 후 위치: " + currentSpot);
//            System.out.println("이동 후 센서 상태: " + controller.actSensor());
//
//            System.out.println("------------------");
//        }
//    }
//}
//

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
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class getCurrentspotTest {
//    @Test
//    public void testController() {
//        // 지도의 행(n)과 열(m)을 설정
//        int n = 6;
//        int m = 5;
//
//        // 지도 데이터 초기화
//        String[][] mapData = new String[n][m];
//        for (String[] row : mapData) {
//            Arrays.fill(row, "0");
//        }
//
//        // 출발지의 행과 열을 설정
//        String startSpot = "3, 1";
//
//        // 장애물의 위치를 설정
//        List<String> hazardSpot = Arrays.asList("5, 1", "3, 3");
//        for (String hazard : hazardSpot) {
//            String[] splitHazard = hazard.split(", ");
//            mapData[Integer.parseInt(splitHazard[0])][Integer.parseInt(splitHazard[1])] = "-1";
//        }
//
//        // 목적지의 위치를 설정
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
//        List<String> pathResult = new ArrayList<>(); // 경로를 저장할 리스트 생성
//        pathFinder.findPath(startX, startY, objectSpot, pathResult); // 경로 탐색 메소드에 pathResult 리스트 전달
//
//        // SIM 객체 생성
//        SIM sim = new SIM(mapData, startX, startY, "N");
//
//        // Controller 객체 생성
//        Controller controller = new Controller(mapRepo, pathRepo, pathFinder, sim);
//
//        // 경로를 따라 이동하면서 getCurrentSpot() 메서드가 제대로 동작하는지 출력
//        System.out.println("Moving along the path:");
//        for (String spot : pathResult) {
//            System.out.println("Expected spot: " + spot);
//            System.out.println("Actual spot: " + controller.getCurrentSpot());
//            controller.moveAndAct();
//        }
//    }
//}
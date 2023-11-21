//package com.example.gyudogi;
//import robotService.Controller;
//import robotService.PathFinder;
//import robotService.SIM;
//import repository.MapRepo;
//import repository.PathRepo;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//public class RunRobot {
//    @Test
//    public void testPathFinder() {
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
//        // 시작점부터 목적지까지의 경로 출력 및 장애물 탐지 및 경로 재탐색
//        System.out.println("Path from start to destination:");
//        int destinationCount = 1;
//        for (int i = 0; i < pathResult.size(); i++) {
//            String spot = pathResult.get(i);
//            boolean reachedDestination = spot.equals(objectSpot.get(destinationCount - 1));
//
//            // 장애물 탐지 및 경로 재탐색
//            boolean hazardDetected = false;
//            if (i > 0) {
//                String prevSpot = pathResult.get(i - 1);
//                String[] splitPrevSpot = prevSpot.split(", ");
//                int prevX = Integer.parseInt(splitPrevSpot[0]);
//                int prevY = Integer.parseInt(splitPrevSpot[1]);
//                String[] splitSpot = spot.split(", ");
//                int x = Integer.parseInt(splitSpot[0]);
//                int y = Integer.parseInt(splitSpot[1]);
//
//                if (Math.abs(prevX - x) > 1 || Math.abs(prevY - y) > 1) {
//                    hazardDetected = true;
//                    System.out.println("Hazard detected at " + spot);
//                }
//            }
//
//            System.out.println("Current spot: " + spot);
//
//            if (hazardDetected) {
//                // 장애물이 감지되면 경로 재탐색
//                System.out.println("Re-calculating path due to hazard...");
//                pathResult.clear(); // 경로 초기화
//                pathFinder.findPath(Integer.parseInt(splitStartSpot[0]), Integer.parseInt(splitStartSpot[1]), objectSpot, pathResult); // 경로 재탐색
//                i = -1; // 경로 재탐색 후 첫 번째 위치로 이동
//            }
//
//            if (reachedDestination) {
//                System.out.println(destinationCount + "번째 목적지에 도착했습니다.");
//                destinationCount++;
//            }
//        }
//    }
//}
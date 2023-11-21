//package com.example.gyudogi;
//
//import org.junit.jupiter.api.Test;
//import repository.MapRepo;
//import repository.PathRepo;
//import robotService.Controller;
//import robotService.PathFinder;
//import robotService.SIM;
//
//import java.util.Arrays;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ControllerTest {
//    @Test
//    public void testRobotMovement() {
//        String[][] mapData = new String[6][5];
//        for (String[] row : mapData) {
//            Arrays.fill(row, "0");
//        }
//        mapData[5][1] = "-1";
//        mapData[3][3] = "-1";
//        mapData[4][3] = "2";
//
//        String startSpot = "3, 1";
//        List<String> objectSpot = Arrays.asList("3, 4", "0, 1");
//        List<String> hazardSpot = Arrays.asList("5, 1", "3, 3");
//
//        MapRepo mapRepo = new MapRepo(mapData, startSpot, objectSpot, hazardSpot);
//        PathRepo pathRepo = new PathRepo();
//        String[] startSpotArr = startSpot.split(", ");
//        SIM sim = new SIM(mapData, Integer.parseInt(startSpotArr[0]), Integer.parseInt(startSpotArr[1]), "N");
//        PathFinder pathFinder = new PathFinder(mapRepo, pathRepo);
//        Controller controller = new Controller(mapRepo, pathRepo, pathFinder, sim);
//
//        pathFinder.findPath(sim.getX(), sim.getY(), objectSpot, pathRepo.getPathInfo()); // 초기 경로 계산
//        System.out.println("초기 경로: " + pathRepo.getPathInfo());
//
//        // 이동과 센서 동작 결과를 반환받아 테스트
//        List<String> result = controller.moveAndAct();
//        // 출력
//        System.out.println(result);
//    }
//}
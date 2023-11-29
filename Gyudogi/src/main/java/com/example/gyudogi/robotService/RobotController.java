package com.example.gyudogi.robotService;

import com.example.gyudogi.repository.MapRepo;
import com.example.gyudogi.repository.PathRepo;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RestController
public class RobotController {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }



    @PostMapping("/robot/move")
    public ResponseEntity<Map<String, List<List<String>>>> moveRobot(@RequestBody Map<String, Object> request) {
        // 요청 데이터 파싱
        int n = (int) request.get("n");
        int m = (int) request.get("m");
        String startSpot = (String) request.get("startSpot");
        String startDirection = (String) request.get("startDirection");
        List<String> obstacles = (List<String>) request.get("hazards");
        List<String> colorBlobs = (List<String>) request.get("colorBlobs");
        List<String> endSpot = (List<String>) request.get("endSpot");

        // MapRepo 초기화
        String[][] mapData = new String[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mapData[i][j] = "0"; // Set initial value
            }
        }
        for (String obstacle : obstacles) {
            String[] splitObstacle = obstacle.split(", ");
            int obstacleX = Integer.parseInt(splitObstacle[0]);
            int obstacleY = Integer.parseInt(splitObstacle[1]);
            mapData[obstacleX][obstacleY] = "-1";
        }
        for (String colorBlob : colorBlobs) {
            String[] splitColorBlob = colorBlob.split(", ");
            int colorBlobX = Integer.parseInt(splitColorBlob[0]);
            int colorBlobY = Integer.parseInt(splitColorBlob[1]);
            mapData[colorBlobX][colorBlobY] = "2";
        }

        MapRepo mapRepo = new MapRepo(mapData, startSpot, startDirection, endSpot, obstacles);
        PathRepo pathRepo = new PathRepo();
        PathFinder pathFinder = new PathFinder(mapRepo, pathRepo);
        String[] startSpotArr = startSpot.split(", ");
        int startX = Integer.parseInt(startSpotArr[0]);
        int startY = Integer.parseInt(startSpotArr[1]);
        SIM sim = new SIM(mapData, Integer.parseInt(startSpotArr[0]), Integer.parseInt(startSpotArr[1]), startDirection);

        List<String> pathResult = new ArrayList<>();
        pathFinder.findPath(startX, startY, endSpot, pathResult);
        Controller controller = new Controller(mapRepo, pathRepo, pathFinder, sim);

        // 경로 계산 및 이동
        controller.moveAndAct();

        // 결과 데이터 반환
        Map<String, List<List<String>>> response = new HashMap<>();
        response.put("finalInfo", pathRepo.getFinalInfo());

        return ResponseEntity.ok(response);
    }
}
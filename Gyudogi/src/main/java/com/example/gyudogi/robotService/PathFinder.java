package com.example.gyudogi.robotService;

import com.example.gyudogi.repository.MapRepo;
import com.example.gyudogi.repository.PathRepo;

import java.util.*;

public class PathFinder { // 경로 탐색에 대한 클래스
    private MapRepo mapRepo;
    private PathRepo pathRepo;
    private int[][] distance;
    private int[][][] prev;
    private static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int INF = Integer.MAX_VALUE;

    public PathFinder(MapRepo mapRepo, PathRepo pathRepo) {
        this.mapRepo = mapRepo;
        this.pathRepo = pathRepo;
        this.distance = new int[mapRepo.getN()][mapRepo.getM()];
        this.prev = new int[mapRepo.getN()][mapRepo.getM()][2];
    }

    private List<String> getPath(int startX, int startY, int targetX, int targetY) { // 저장된 경로를 가져오는 함수
        LinkedList<String> path = new LinkedList<>();
        while (targetX != startX || targetY != startY) {
            path.addFirst(targetX + ", " + targetY);
            int[] p = prev[targetX][targetY];
            targetX = p[0];
            targetY = p[1];
        }
        path.addFirst(startX + ", " + startY);
        return path;
    }

    public void findPath(int startX, int startY, List<String> targets, List<String> pathResult) {

        // 출발지점으로부터의 거리를 기준으로 목적지 정렬
        final int finalStartX = startX; // 람다식 때문에 출발지의 x좌표 복사
        final int finalStartY = startY; // 람다식 때문에 출발지의 y좌표 복사

        targets.sort((target1, target2) -> {
            String[] splitTarget1 = target1.split(", ");
            int target1X = Integer.parseInt(splitTarget1[0]);
            int target1Y = Integer.parseInt(splitTarget1[1]);

            String[] splitTarget2 = target2.split(", ");
            int target2X = Integer.parseInt(splitTarget2[0]);
            int target2Y = Integer.parseInt(splitTarget2[1]);

            double distance1 = Math.sqrt(Math.pow(target1X - finalStartX, 2) + Math.pow(target1Y - finalStartY, 2));
            double distance2 = Math.sqrt(Math.pow(target2X - finalStartX, 2) + Math.pow(target2Y - finalStartY, 2));

            return Double.compare(distance1, distance2);
        });


        for (String target : targets) {
            String[] splitTarget = target.split(", ");
            int targetX = Integer.parseInt(splitTarget[0]);
            int targetY = Integer.parseInt(splitTarget[1]);
            dijkstra(startX, startY);
            List<String> path = getPath(startX, startY, targetX, targetY);
            pathResult.addAll(path);
            startX = targetX;
            startY = targetY;
        }
        pathRepo.setPathInfo(pathResult);
    }



    private void dijkstra(int startX, int startY) { // 다익스트라 알고리즘을 통해서 최단거리 탐색 진행
        for (int[] row : distance) {
            Arrays.fill(row, INF);
        }
        distance[startX][startY] = 0;

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node[2]));
        queue.offer(new int[]{startX, startY, 0});

        while (!queue.isEmpty()) {
            int[] node = queue.poll();
            int x = node[0];
            int y = node[1];

            if (mapRepo.getMapData()[x][y].equals("-1")) {
                continue;
            }
            for (int[] dir : DIRS) {
                int nextX = x + dir[0];
                int nextY = y + dir[1];
                if (nextX >= 0 && nextX < mapRepo.getN() && nextY >= 0 && nextY < mapRepo.getM()) {
                    int newDist = distance[x][y] + 1;
                    if (newDist < distance[nextX][nextY]) {
                        distance[nextX][nextY] = newDist;
                        prev[nextX][nextY] = new int[]{x, y};
                        queue.offer(new int[]{nextX, nextY, newDist});
                    }
                }
            }
        }
    }

}

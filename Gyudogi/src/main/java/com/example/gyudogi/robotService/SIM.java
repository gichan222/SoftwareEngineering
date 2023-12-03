package com.example.gyudogi.robotService;

import java.util.Random;

public class SIM { // 로봇의 동작에 대한 기본을 담당하는 함수
    private String[][] map;
    private int x;
    private int y;
    private String direction;

    public SIM(String[][] map, int x, int y, String direction) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getDirection() {
        return direction;
    }

    public void turn() { // 로봇을 회전 시키는 함수
        if (direction.equals("N")) {
            direction = "E";
        } else if (direction.equals("E")) {
            direction = "S";
        } else if (direction.equals("S")) {
            direction = "W";
        } else {
            direction = "N";
        }
    }


    public String colorBlobSensor() { // 컬러블럽을 탐색하는 센서
        StringBuilder sb = new StringBuilder();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 북, 남, 서, 동

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (newX >= 0 && newX < map.length && newY >= 0 && newY < map[0].length && map[newX][newY].equals("2")) {
                sb.append(newX).append(",").append(newY).append(", ");
            }
        }

        if (sb.length() > 0) { // 색상 블롭을 찾았으면 마지막의 쉼표와 공백 제거
            sb.setLength(sb.length() - 2);
        }


            return sb.toString();
    }


    public String hazardSensor() { // 해저드를 탐색하는 함수
        int newX = x;
        int newY = y;

        if (direction.equals("N")) {
            newY += 1;
        } else if (direction.equals("E")) {
            newX += 1;
        } else if (direction.equals("S")) {
            newY -= 1;
        } else {
            newX -= 1;
        }

        if (newX >= 0 && newX < map.length && newY >= 0 && newY < map[0].length && map[newX][newY].equals("-1")) {
            return newX + "," + newY;
        } else {
            return "";
        }
    }



    public int move() { // 로봇을 움직이는 함수(5%확률로 0칸이동, 5% 확률로 2칸 이동->리턴값을 전부 다르게 해서 경로 재탐색 가능)
        Random random = new Random();
        int chance = random.nextInt(100);
        int tempX = getX();
        int tempY = getY();

        if (chance < 5) {
            // 이동 실패
            return 0;
        } else if (chance < 10) {
            // 이동 성공하지만 2칸 이동
            if (direction.equals("N")) {
                tempY += 2;
            } else if (direction.equals("E")) {
                tempX += 2;
            } else if (direction.equals("S")) {
                tempY -= 2;
            } else {
                tempX -= 2;
            }

            if (tempX < 0 || tempY < 0 || tempX >= map.length || tempY >= map[0].length || map[tempX][tempY].equals("-1")) {
                return 1;
            }

            setX(tempX);
            setY(tempY);
            return 2;
        } else {
            // 이동 성공
            if (direction.equals("N")) {
                tempY += 1;
            } else if (direction.equals("E")) {
                tempX += 1;
            } else if (direction.equals("S")) {
                tempY -= 1;
            } else {
                tempX -= 1;
            }

            if (tempX < 0 || tempY < 0 || tempX >= map.length || tempY >= map[0].length || map[tempX][tempY].equals("-1")) {
                return 0;
            }

            setX(tempX);
            setY(tempY);
            return 1;
        }

    }

}
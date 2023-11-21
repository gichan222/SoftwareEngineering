package com.example.gyudogi.robotService;

import java.util.Random;

public class SIM {
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

    public void turn() {
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


    public String colorBlobSensor() {
        StringBuilder sb = new StringBuilder();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 북, 남, 서, 동

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (newX >= 0 && newX < map.length && newY >= 0 && newY < map[0].length && map[newX][newY].equals("2")) {
                sb.append("colorBlob: " + newX + "," + newY + " O ");
            } else {
                sb.append("colorBlob: " + newX + "," + newY + " X ");
            }
        }
        return sb.toString();
    }


    public String hazardSensor() {
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



    public int move() {
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
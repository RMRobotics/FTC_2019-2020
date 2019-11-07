package org.firstinspires.ftc.teamcode;

public class Map {
    private boolean[][] CoordsArr;

    public Map() {
        CoordsArr = new boolean[24][24];

    }
    private void AddValues() {
        for (int x = 0; x < 24; x++) {
            for(int y = 0; y <24; y++){
                CoordsArr[x][y] = false;
            }


        }
    }
}

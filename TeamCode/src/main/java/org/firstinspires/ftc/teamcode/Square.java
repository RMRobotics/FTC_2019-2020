package org.firstinspires.ftc.teamcode;

public class Square {

    private int x;
    private int y;

    public Square(int input_x, int input_y, Obstruction inputObs) {
        x = input_x;
        y = input_y;
        obsType = inputObs;
    }


    public enum Obstruction {
        STONE, OBSTRUCTION, NO_OBSTRUCTION
    }

    public int returnX() {
        return x;
    }

    public void setX(int input_x) {
        this.x = input_x;
    }

    public static void main(String[] args) {
        Square square1 = new Square(2, 2, Obstruction.STONE);
        Square square2 = new Square(-1, 2, Obstruction.OBSTRUCTION);
        square1.setX(1);
        System.out.print(square1.returnX());
    }

    public void setSquare(x, y) {

    }

}
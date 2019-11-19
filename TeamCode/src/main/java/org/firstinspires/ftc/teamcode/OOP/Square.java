package org.firstinspires.ftc.teamcode.OOP;

public class Square {

    private int x;
    private int y;
    private Obstruction obstruction;

    public Square(int x, int y, Obstruction obstruction) {
        this.x = x;
        this.y = y;
        this.obstruction = obstruction;

    }


    public Square(){

    }


    public int returnX() {
        return x;
    }

    public void setX(int input_x) {
        this.x = input_x;
    }

    public static void main(String[] args) {
        Square square1 = new Square(2, 2, Obstruction.SKY_STONE);
        Square square2 = new Square(-1, 2, Obstruction.FOUNDATION);
        square1.setX(1);
        System.out.print(square1.returnX());
    }


}
package org.firstinspires.ftc.teamcode.OOP;

public class Pose {
    private int x;
    private int y;
    private double theta;

    public Pose(){

    }

    public Pose(int x,int y, double theta){
       this.x = x;
       this.y = y;
       this.theta = theta;
    }

    public Pose(int x, int y){
        this.x = x;
        this.y = y;
    }


    public double getTheta(){
        return theta;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void setTheta(double theta) {
        this.theta = theta;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

package org.firstinspires.ftc.teamcode.OOP;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Drivetrain {

    //Constants - TODO - Verify these values
    protected final float MM_PER_INCH = 25.4f;
    protected final float ROBOT_SIZE = 18 * MM_PER_INCH;
    protected final float FTC_FIELD_SIZE_MM = (12 * 12 - 2) * MM_PER_INCH;
    protected final double CPI = 86.93063762;

    //Motors
    public DcMotor FL;
    public DcMotor FR;
    public DcMotor BL;
    public DcMotor BR;

    //Sensors
    protected DcMotor odometryX;
    protected DcMotor odometryLeft;
    protected DcMotor odometryRight;

    //Other variables
    protected ElapsedTime timer;

    //Map and Positioning
    protected OdometryMap map;
    protected Pose robotPosition;

    public Drivetrain(HardwareMap hardwareMap){
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        odometryLeft = hardwareMap.dcMotor.get("odometryLeft");
        odometryRight = hardwareMap.dcMotor.get("odometryRight");
        odometryX = hardwareMap.dcMotor.get("odometryX");
        setupMotors();
        timer = new ElapsedTime();
    }

    //public void moveTo(Position pos){

    //}

    protected void setupMotors(){
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        odometryLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        odometryRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        odometryX.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //Manual Movement Functions
    protected void setDrive(double p) {
        FL.setPower(p);
        FR.setPower(p);
        BL.setPower(p);
        BR.setPower(p);
    }


    //other methods
    protected void holdUp(double num) {
        timer.reset();
        while (timer.seconds()<num){}
    }


    public DcMotor getOdometryLeft() {
        return odometryLeft;
    }

    public DcMotor getOdometryRight() {
        return odometryRight;
    }

    public DcMotor getOdometryX() {
        return odometryX;
    }

    public double getCPI() {
        return CPI;
    }
}

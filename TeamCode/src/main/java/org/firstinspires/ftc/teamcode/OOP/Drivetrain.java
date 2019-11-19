package org.firstinspires.ftc.teamcode.OOP;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivetrain {

    //Constants
    public static final float MM_PER_INCH = 25.4f;
    public static final float ROBOT_SIZE = 18 * MM_PER_INCH;
    public static final float FTC_FIELD_SIZE_MM = (12 * 12 - 2) * MM_PER_INCH;
    public static final float CPI = 0;

    //Motors
    private DcMotor FL;
    private DcMotor FR;
    private DcMotor BL;
    private DcMotor BR;

    //Sensors
    private DcMotor odometryY;
    private DcMotor odometryLeft;
    private DcMotor odometryRight;

    //Map and Positioning
    private OdometryMap map;
    private Pose robotPosition;

    public Drivetrain(HardwareMap hardwareMap){
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        odometryLeft = hardwareMap.dcMotor.get("odometryLeft");
        odometryRight = hardwareMap.dcMotor.get("odometryRight");
        odometryY = hardwareMap.dcMotor.get("odometryY");
        setupMotors();

    }

    //public void moveTo(Position pos){

    //}

    private void setupMotors(){
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        odometryLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        odometryRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        odometryY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //Manual Movement Functions



}

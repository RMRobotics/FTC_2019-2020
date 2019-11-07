package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveTrain {

    //Motors
    private DcMotor FL;
    private DcMotor FR;
    private DcMotor BL;
    private DcMotor BR;

    //Sensors
    private DcMotor odometryY;
    private DcMotor odometryLeft;
    private DcMotor odometryRight;


    public DriveTrain(HardwareMap hardwareMap){
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
    


}

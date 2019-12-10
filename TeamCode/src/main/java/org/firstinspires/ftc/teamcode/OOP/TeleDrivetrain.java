package org.firstinspires.ftc.teamcode.OOP;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class TeleDrivetrain extends Drivetrain {



    public TeleDrivetrain(HardwareMap hardwareMap){
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        setupMotors(hardwareMap);
        timer = new ElapsedTime();

    }

    @Override
    protected void setupMotors(HardwareMap hardwareMap) {
        setDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPowerAll(double fl, double fr, double bl, double br){
        FL.setPower(fl);
        FR.setPower(fr);
        BL.setPower(bl);
        BR.setPower(br);
    }

}

package org.firstinspires.ftc.teamcode.OOP;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class Robot {
    public enum Mode{
        TELEOP,AUTO
    }



    protected DcMotor intakeLeft,intakeRight; //These will operate tje same just diff directions
    //private Servo pivotLeft, pivotRight; //Must move at same time(one is negated rotation)
    //private Servo ramp, clamp;

    private final double PIVOT_POS = 0.5;
    private double defaultPosition;


    public void setupAttachments(HardwareMap hardwareMap){
        //lift = hardwareMap.dcMotor.get("lift");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //pivotLeft = hardwareMap.servo.get("pivotLeft");
        //pivotRight = hardwareMap.servo.get("pivotRight");
        // ramp = hardwareMap.servo.get("ramp");
        //clamp = hardwareMap.servo.get("clamp");
    }


    public abstract void setPower(double fl,double fr,double bl,double br);

    public abstract void intake();

    public abstract void pivot();


/*
    public void pivotButtons(){
        //Reset
        //Swing out
        //swing back
        //COnstant speed

        if(gamepad2.x){ //Default Position
            pivotLeft.setPosition(defaultPosition);
            pivotRight.setPosition(defaultPosition);
        }else if(gamepad2.a) {
            pivotLeft.setPosition(defaultPosition-(0.5(1-defaultPosition)));
            pivotRight.setPosition();
        }

    }
*/
}

package org.firstinspires.ftc.teamcode.OOP.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class Robot {
    public enum Mode{
        TELEOP,AUTO
    }



    protected DcMotor intakeLeft,intakeRight; //These will operate tje same just diff directions
    protected Servo swivel;
    //private Servo pivotLeft, pivotRight; //Must move at same time(one is negated rotation)
    //private Servo ramp, clamp;
    protected Gamepad gamepad1;
    protected Gamepad gamepad2;
    private final double PIVOT_POS = 0.5;
    private int swivelPos;



    public void setupAttachments(HardwareMap hardwareMap){
        //lift = hardwareMap.dcMotor.get("lift");
        //intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        //intakeRight = hardwareMap.dcMotor.get("intakeRight");
        //intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        swivel = hardwareMap.servo.get("swivel");

        //pivotLeft = hardwareMap.servo.get("pivotLeft");
        //pivotRight = hardwareMap.servo.get("pivotRight");
        // ramp = hardwareMap.servo.get("ramp");
        //clamp = hardwareMap.servo.get("clamp");

    }


    public abstract void setPower(double fl,double fr,double bl,double br);

    public abstract void intake();

    public abstract void pivot();


    public void toggleSwivel(){
        if(swivelPos ==0){
            swivel.setPosition(90);
            swivelPos = 1;
        }else{
            swivel.setPosition(-90);
            swivelPos = 0;
        }

    }

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

package org.firstinspires.ftc.teamcode.OOP.robot;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.OOP.drivetrain.TeleDrivetrain;

public class TeleBot extends Robot {
    TeleDrivetrain drivetrain;

    private DcMotor lift;
    private CRServo pivotLeft,pivotRight;
    private Servo clamp;
    private boolean isClamped;



    public TeleBot(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2){
        this.drivetrain = new TeleDrivetrain(hardwareMap);
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        setupAttachments(hardwareMap);
    }

    @Override
    public void setupAttachments(HardwareMap hardwareMap) {
        super.setupAttachments(hardwareMap);
        lift = hardwareMap.dcMotor.get("lift");
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        pivotLeft = hardwareMap.crservo.get("pivotLeft");
        pivotRight = hardwareMap.crservo.get("pivotRight");
        clamp = hardwareMap.servo.get("clamp");
    }



    @Override
    public void setPower(double fl, double fr, double bl, double br) {
        drivetrain.setPowerAll(fl,fr,bl,br);
    }

    @Override
    public void intake() {
        //In this order, only one direction can be applied at a time, and running intake.
        if(gamepad1.right_bumper){
            intakeRight.setPower(1);
            intakeLeft.setPower(1);
        }else if (gamepad1.left_bumper){
            intakeRight.setPower(-1);
            intakeLeft.setPower(-1);
        }else{
            intakeRight.setPower(0);
            intakeLeft.setPower(0);
        }
    }

    /**
     * Note that lift motor direction is reversed so positive power is associated with going up and vice versa
     */
    public void lift(){
        if(gamepad2.left_trigger > 0){
            lift.setPower(0.5);
        }else if (gamepad2.right_trigger > 0){
            lift.setPower(-0.5);
        }else{
            lift.setPower(0);
        }
    }


    public void clamp(){
        if(gamepad2.a){
            if(isClamped){
                clamp.setPosition(0);
                isClamped = false;
            }else{
                clamp.setPosition(1);
                isClamped = true;
            }
        }
    }

    @Override
    public void pivot() {
        //Reset
        //Swing out
        //swing back
        //COnstant speed

        if(gamepad2.left_bumper){ //turn forward
            pivotLeft.setPower(1);
            pivotRight.setPower(-1);
        }else if(gamepad2.right_bumper) { //turn backward
            pivotLeft.setPower(-1);
            pivotRight.setPower(1);
        }else{
            pivotRight.setPower(0);
            pivotLeft.setPower(0);
        }
    }



}

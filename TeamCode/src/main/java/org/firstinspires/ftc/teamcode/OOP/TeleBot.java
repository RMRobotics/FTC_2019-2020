package org.firstinspires.ftc.teamcode.OOP;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TeleBot extends Robot {
    TeleDrivetrain drivetrain;
    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private DcMotor lift;



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
        if(gamepad1.left_trigger > 0){
            lift.setPower(1);
        }else if (gamepad1.right_trigger > 0){
            lift.setPower(-1);
        }else{
            lift.setPower(0);
        }
    }

    @Override //This is temporary to not create errors
    public void pivot() {

    }
    /*
    @Override
    public void pivot() {
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

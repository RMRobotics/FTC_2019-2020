package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class RobotDemo extends OpMode {

    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;
    DcMotor lift;

    @Override

    public void init(){
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        lift = hardwareMap.dcMotor.get("lift");

        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);

        // set mode
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void loop(){
        if (gamepad1.left_trigger > 0) {
            lift.setPower(0.25);
        } else if (gamepad1.right_trigger > 0) {
            lift.setPower(-0.25);
        } else {
            lift.setPower(0);
        }
    }

    public void forward() {
        if (gamepad1.dpad_up) {
            FL.setPower(1);
            FR.setPower(1);
            BL.setPower(1);
            BR.setPower(1);
        } else {
            FL.setPower(0);
            FR.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
        }
    }

    public void backwards() {
        if (gamepad1.dpad_down){
            FL.setPower(-1);
            FR.setPower(-1);
            BL.setPower(-1);
            BR.setPower(-1);
        } else {
            FL.setPower(0);
            FR.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
        }
    }

    public void left() {
        if (gamepad1.dpad_left){
            FL.setPower(-1);
            FR.setPower(1);
            BL.setPower(1);
            BR.setPower(-1);
        } else {
            FL.setPower(0);
            FR.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
        }
    }

    public void right() {
        if (gamepad1.dpad_right){
            FL.setPower(1);
            FR.setPower(-1);
            BL.setPower(-1);
            BR.setPower(1);
        } else {
            FL.setPower(0);
            FR.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
        }
    }

}
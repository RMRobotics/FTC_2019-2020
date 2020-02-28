package org.firstinspires.ftc.teamcode.States;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OOP.robot.AutoBot;

public class imuTest extends LinearOpMode {
    private AutoBot robot;


    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        robot.turnDegrees(90,0.5);

    }


    public void initialize(){
        robot = new AutoBot(hardwareMap,gamepad1,gamepad2);
        robot.setTelemetry(telemetry);
        robot.setDrivePath();

    }
}

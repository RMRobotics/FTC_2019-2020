package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OOP.robot.AutoBot;

@Autonomous(name = "AutoTestBot", group = "auto")

public class recruitAutoTesting extends LinearOpMode {
    private AutoBot robot;




    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        //Robot


        waitForStart();
        robot.moveDistance(50,0.3);
        robot.toggleSwivel();
        robot.toggleSwivel();
        //robot.strafeForDistance(50.75,true);


    }

    public void initialize(){

        robot = new AutoBot(hardwareMap,gamepad1,gamepad2);
        robot.setTelemetry(telemetry);
        //robot.setDrivePath();

    }


}

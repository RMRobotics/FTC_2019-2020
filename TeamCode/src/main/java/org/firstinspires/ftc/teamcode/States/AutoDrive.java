package org.firstinspires.ftc.teamcode.States;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OOP.robot.AutoBot;
@Autonomous(name = "Auto-STATES", group = "auto")
public class AutoDrive extends LinearOpMode {
    /**
     * ======================================This has drivepath settings=======================================
     */
    private AutoBot robot;
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        robot.detectBlock();
        //robot.
        robot.moveDistance(10,0.5);
        //robot.strafeForDistance(50.75,true);
        robot.turnDegrees(90,0.5);


    }

    public void initialize(){

        robot = new AutoBot(hardwareMap);
        robot.setTelemetry(telemetry);
        robot.setDrivePath();

    }
}

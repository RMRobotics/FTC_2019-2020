package org.firstinspires.ftc.teamcode.Qualifier;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OOP.robot.AutoBot;

@Autonomous(name = "Auto-Red-Foundation", group = "auto")
public class AutoDriveTestRed extends LinearOpMode {

    private AutoBot robot;
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        //robot.strafeForDistance(50.75,false);
        robot.strafeForDuration(0.66*2,false);


    }

    public void initialize(){

        robot = new AutoBot(hardwareMap);
        robot.setTelemetry(telemetry);
    }
}

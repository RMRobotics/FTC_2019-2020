package org.firstinspires.ftc.teamcode.Qualifier;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OOP.robot.AutoBot;
@Autonomous(name = "Auto-Blue-Foundation", group = "auto")
public class AutoDriveTestBlue extends LinearOpMode {
    /**
     * ======================================This has drivepath settings=======================================
     */
    private AutoBot robot;
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        //robot.strafeForDistance(50.75,true);
        robot.strafeForDuration(0.66*2,true);


    }

    public void initialize(){

        robot = new AutoBot(hardwareMap);
        robot.setTelemetry(telemetry);
        robot.setDrivePath();

    }
}

package org.firstinspires.ftc.teamcode.OOP;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(group = "auto",name = "AutoDriveTest")
public class AutoDriveTest extends LinearOpMode {
    private AutoBot robot;
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        robot.strafeDistance(5,0.5);
        robot.moveDistance(10,0.5);
        robot.turnDegrees(90,0.5);
    }

    public void initialize(){

        robot = new AutoBot(hardwareMap);
    }
}


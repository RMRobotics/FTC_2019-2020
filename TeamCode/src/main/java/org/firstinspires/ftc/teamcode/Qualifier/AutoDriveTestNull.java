package org.firstinspires.ftc.teamcode.Qualifier;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OOP.robot.AutoBot;

@Autonomous(name = "Auto-Blue-NULL", group = "auto")
public class AutoDriveTestNull extends LinearOpMode {

    private AutoBot robot;


    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        //Robot


        waitForStart();
        while(true){
            telemetry.addLine().addData("hello","world");
            telemetry.update();
        }


        //robot.strafeForDistance(50.75,true);


    }

    public void initialize(){

        robot = new AutoBot(hardwareMap,gamepad1,gamepad2);
        robot.setTelemetry(telemetry);

        //robot.setDrivePath();


    }
}

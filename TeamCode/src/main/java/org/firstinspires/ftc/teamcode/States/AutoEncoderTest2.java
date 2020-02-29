package org.firstinspires.ftc.teamcode.States;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.DriveDirection;

@Autonomous(name = "States-Acceleration-Test", group = "auto")
public class AutoEncoderTest2 extends AutoSuper {
    /**
     * ======================================This has drivepath settings=======================================
     */

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        acceleratedMove(10, DriveDirection.FORWARD);
        stop();
    }

    //public void initialize(){
    // i
    // setDrivePath();

    //}
}

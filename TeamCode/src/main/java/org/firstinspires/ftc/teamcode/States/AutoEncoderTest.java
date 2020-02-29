package org.firstinspires.ftc.teamcode.States;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "States-Encoder-Test", group = "auto")
public class AutoEncoderTest extends AutoSuper {
    /**
     * ======================================This has drivepath settings=======================================
     */

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        moveDistance(10,0.5);
        stop();
    }

    //public void initialize(){
    // i
    // setDrivePath();

    //}
}

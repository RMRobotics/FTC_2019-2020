package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(group = "auto",name = "AutoDrive2.0")
public class AutoDriveTest extends armisticeAutoSuper {

    @Override
    public void runOpMode() throws InterruptedException {
        initialize(true);
        //moveEncodersCount(5000,1);
        moveEncoders(200,1.5);
    }
}


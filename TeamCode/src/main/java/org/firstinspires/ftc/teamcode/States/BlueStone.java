package org.firstinspires.ftc.teamcode.States;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Stone", group = "auto")
public class BlueStone extends AutoSuper {
    /**
     * ======================================This has drivepath settings=======================================
     */

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        strafeDistance(12, .6);    //move so that the robot is about 18 inches away from the blocks

        //ALIGN THE ROBOT WITH THE CLOSES SKYSTONE, MOVING FORWARDS/BACKWARDS --- MEASURE HOW FAR UP/DOWN IT GOES
        double distanceMovedToAlign = 0;

        strafeDistance(16.5, .6);    //move so that the robot is about 1.5 inches away from the blocks

        //CODE TO GRAB THE STONE

        strafeDistance(-4, .6);        //move so that the robot doesn't crash into the bridge

        //MOVE FORWARDS 90 ish INCHES + distanceMovedToAlign

        //DROP STONE

        //MOVE BACKWARDS ^ THAT AMOUNT

        //START MOVING BACKWARDS SLOWLY UNTIL YOU SEE THE STONE

        //ALIGN THE ROBOT WITH THE CLOSEST SKYSTONE, MOVING FORWARDS/BACKWARDS --- MEASURE HOW FAR UP/DOWN IT GOES
        distanceMovedToAlign = 0;

        //CODE TO GRAB THE STONE

        strafeDistance(-4, .6);        //move so that the robot doesn't crash into the bridge

        //MOVE FORWARDS 90 ish INCHES + distanceMovedToAlign

        //MOVE BACKWARDS until you are under the bridge

        stop();
    }
}

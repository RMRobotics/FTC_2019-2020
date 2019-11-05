package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Neal on 12/6/2018.
 */

@Autonomous(name = "AutoDepot", group = "auto")
public class AutoDepot extends armisticeAutoSuper{
// NOTE: MECHANICS WILL ADD AN ARM TO DEPOSIT TEAM MARKER, NAVIGATING TO THE DEPOT WILL NOT BE NECESSARY
    public void runOpMode(){

        //initialization
        initialize(true);
//        waitForStart();
//        telemetry.addData("range", String.format("%.01f cm", sensorRange.getDistance(DistanceUnit.CM)));
//        telemetry.addData("range", String.format("%.01f in", sensorRange.getDistance(DistanceUnit.INCH)));
//        telemetry.update();

                                                            //Get off lander
//        lift.setPower(0.3);
//        holdUp(3);
//        lift.setPower(0);

                                                            //slide a bit to the left lower lift and slide back
        strafeEncoders(1,0.5);
        lift.setPower(-0.3);
        holdUp(3);
        lift.setPower(0);
        strafeEncoders(1,-0.5);


                                                            //See Qube


                                                            //Vars
        int count = 0;
        int change = 0;
        int direction = 1;
        double totalDistance = 0;
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

//        if (detector.getAligned().equals(Direction.CENTER)){
//            double distance = sensorRange.getDistance(DistanceUnit.INCH);
//            moveEncoders(distance);
//            //push mineral w/ arm
//        }
//        else
//        else
//        {
//            moveEncoders(2 * -1);
//        }



//        while (detector.isFound() == false && timer.seconds() < 5){
//            strafeEncoders(3,direction, .25);
//            count++;
//
//            if (change%2 == 1) {
//                totalDistance--;
//            }
//            else {
//                totalDistance++;
//            }
//
//            if (count > 5) {
//                strafeEncoders(14.5, -direction, .25);
//                count = 0;
//                totalDistance = 0;
//                direction = -direction;
//                change++;
//            }
//        }
//        if (detector.isFound() == true){
//            position = detector.getAligned();
//            strafeEncoders(20, 1, .25);
//        }
//        else
//        {
//            if (totalDistance < 0) {
//                totalDistance = -totalDistance + 20;
//            }
//            strafeEncoders(totalDistance, 1, .25);
//        }


                                                            //knock off yellow mineral
        moveEncoders(5* 1,0.4);

                                                            //go back to initial pos and turn
        moveEncoders(5* -1,0.4);
        imuTurn(90, 0.4,true);

                                                            //move to turn point and turn
        moveEncoders(55* 1,0.4);
        imuTurn(135,0.4,true);

                                                            //move to home depot and drop flag
        moveEncoders(60*1,0.4);

                                                            //turn arouuuuuuund every now and then i get a little bit lonely
        imuTurn(180, 0.4,true);

                                                            //travel from depot to crater
        moveEncoders(69* 1,0.4);

                                                            //drop arm in crater
//        arm.setPower(0.3);
//        holdUp(2);
//        arm.setPower(0);
    }
}

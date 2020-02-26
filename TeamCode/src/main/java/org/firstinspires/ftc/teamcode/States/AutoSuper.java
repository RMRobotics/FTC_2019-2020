package org.firstinspires.ftc.teamcode.States;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DanCV.Detection.MineralDetector;
import org.firstinspires.ftc.teamcode.DanCV.UI.CVViewActivity;
import org.firstinspires.ftc.teamcode.OOP.drivetrain.AutoDrivetrain;

/**
 * Created by Daniel on 02/26/2020.
 */
//The LinearOpMode class provides various methods that simplify writing code for the Auto program
//Armistice Auto Super is ABSTRACT. This acts as a helper class that abstracts out common robot functions,
//and allows for one to focus just on robot movement logic.
public abstract class AutoSuper extends LinearOpMode {


    //==================================DEFINITION SECTION====================================\\
    //All of the sensors and attachments needed during the Opmode is defined here. This includes motors,Servos and various sensors.

    //Drivetrain Motors - Naming Scheme is FRONT/BACK followed by LEFT/RIGHT
    protected AutoDrivetrain drivetrain;

    //CRSERVO - (Continuous Rotation) Servo - Can rotate a full 360 degrees
    //SERVO - This servo can rotate only 180 degrees


    //Timer object is created so that the current time that has elapsed during a opMode can be referred to.
    protected ElapsedTime timer = new ElapsedTime();

    //Detector for OpenCV object detection
    protected MineralDetector detector;

    //TODO THINK: what does CPI stand for, and what is the significance of the numbers used in its definition?
    public static double CPI = (1120.0 * 0.66666)/(4.0 * Math.PI);

    //Pretty self explanatory - On initialize state
    public void initialize (Boolean i) {

        drivetrain = new AutoDrivetrain(hardwareMap);
        //drivetrain.
        //This is the object detector from DanCV. It detects objects based on color.
        detector = new MineralDetector();
        detector.init(hardwareMap.appContext, CVViewActivity.getInstance(),1);
        //Always save activation(Turning the camera on and starting the detection) for the beginning of the opmode
        //detector.activate();

    }

    //=================================UTILITY METHOD SECTION=====================================\\

    /**
     * Sets all drivetrain motors to run at the same RunMode.
     */
    protected void setMode(DcMotor.RunMode r) {
        //drivetrain.
    }

    /**
     * Sets all drivetrain motors' zero power behavior to the same value
     */
    protected void setZeroMode(DcMotor.ZeroPowerBehavior z) {

    }

    /**
     * Sets all drivetrain motors' to move at the same given power
     */
    protected void setDrive(double p) {

    }


    /**
     * Causes the robot to strafe by applying the same power value but negated to the back left and front right motors
     */
    protected void setStrafe(double pwr)
    {

    }


    /**
     * Easy way of setting robot telemetry
     */
    protected void print(String message, double time)
    {
        telemetry.addData(message,"");
        telemetry.update();
        holdUp(time);
    }

    /**
     * Pause for a specificed amount of time
     * NOTE: This does not pause any motors
     */
    protected void holdUp(double num)
    {
        /*
        Resetting the timer mid opMode seems like it could be problematic,specifically
        causing the robot to do some action longer than expected. For example, imagine a while loop in the opmode,
        where the robot is to move forward till 5 seconds have elapsed. If holdup is called during this while loop,
        and given a value of 10 seconds, It will reset the timer object, wait (and move) for 10 seconds, before
        immediately breaking out of the while loop at the end of the current loop. Since the timer would be at 10,
        after holdup was done, and the while loop uses the timer object to track time elapsed.
        */
        timer.reset();

        while (timer.seconds()<num)
        {}
    }


/*
    protected void moveEncoders(double distanceInches, double power){
        // Reset encoders
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Get current position of each motor
        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();
        //distanceTics is num of tics it needs to travel
        int distanceTics = (int)(distanceInches * CPI);

        // Prepare to drive to target position
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set target position and speed
        FL.setTargetPosition(currentPos1 + distanceTics);
        FR.setTargetPosition(currentPos2 + distanceTics);
        BL.setTargetPosition(currentPos3 + distanceTics);
        BR.setTargetPosition(currentPos4 + distanceTics);

        FL.setPower(power);
        FR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);

        // Loop while we approach the target.  Display position as we go
        while(FR.isBusy() && FL.isBusy() && BL.isBusy() && BR.isBusy()) {
            telemetry.addData("FL Encoder", FL.getCurrentPosition());
            telemetry.addData("BL Encoder", BL.getCurrentPosition());
            telemetry.addData("FR Encoder", FR.getCurrentPosition());
            telemetry.addData("BR Encoder", BR.getCurrentPosition());
            telemetry.update();
        }

        // We are done, turn motors off and switch back to normal driving mode.
        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);

        //TODO Never set back to normal driving mode
    }

*/


}
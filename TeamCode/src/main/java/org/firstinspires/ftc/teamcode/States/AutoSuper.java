package org.firstinspires.ftc.teamcode.States;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DanCV.Detection.MineralDetector;
import org.firstinspires.ftc.teamcode.DanCV.Enums.RelativePosition;
import org.firstinspires.ftc.teamcode.DanCV.UI.CVViewActivity;
import org.firstinspires.ftc.teamcode.OOP.drivetrain.AutoDrivetrain;
import org.firstinspires.ftc.teamcode.OOP.robot.AutoSettings;

/**
 * Created by Daniel on 02/26/2020.
 */
//The LinearOpMode class provides various methods that simplify writing code for the Auto program
//Armistice Auto Super is ABSTRACT. This acts as a helper class that abstracts out common robot functions,
//and allows for one to focus just on robot movement logic.
public abstract class AutoSuper extends LinearOpMode {
    //==================================DEFINITION SECTION====================================\\
    //All of the sensors and attachments needed during the Opmode is defined here. This includes
    // motors,Servos and various sensors.


    protected AutoDrivetrain drivetrain;

    //Timer object is created so that the current time that has elapsed during a opMode can be referred to.
    protected ElapsedTime timer = new ElapsedTime();

    //Detector for OpenCV object detection
    protected MineralDetector detector;
    private AutoSettings autoSettings;



    //Pretty self explanatory - On initialize state
    public void initialize () {

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
     * Method used to add powers to the motors all at once.
     * @param fl - Front Left
     * @param fr - Front Right
     * @param bl - Back Left
     * @param br - Back Right
     */
    public void setPower(double fl, double fr, double bl, double br) {
        drivetrain.setPowerAll(fl,fr,bl,br);
    }

    public void setPowerAll(double power){
        drivetrain.setPowerAll(power,power,power,power);
    }

    public void setStrafe(double power){
        drivetrain.setStrafe(power);
    }

    /**
     * Sleep for a duration of time and then continue executing code.
     * @param seconds
     */
    public void waitForDuration(double seconds){
        drivetrain.wait(seconds);
    }

    /**
     * Easy way of setting robot telemetry
     */
    protected void print(String caption, String data)
    {
        drivetrain.getTelemetry().addData(caption,data);
    }

    public void setTelemetry(Telemetry t){
        drivetrain.setTelemetry(t);
    }
    //========================================ENCODER-BASED===================================\\
    /**
     * Move a specified number of inches and then stop.
     * @param distanceInches - Distance in inches to move.
     * @param power - speed at which to move.
     */
    public void moveDistance(double distanceInches, double power){
        drivetrain.moveDistance(distanceInches,power);
    }

    /**
     * Strafe for a specified number of inches and then stop.
     * @param distanceInches - Number of inches through which to strafe.
     * @param power - speed at which to strafe .
     */
    public void strafeDistance(double distanceInches, double power){
        drivetrain.strafeDistance(distanceInches,power);
    }

    /**
     * THIS IS AN IMU-BASED MODE. Turn a specified number of degrees and then stop. (USES IMU)
     * @param degreesToTurn - number of degrees to turn the robot.
     * @param speed - speed at which to make the turn.
     */
    public void turnDegrees(int degreesToTurn, double speed){
        drivetrain.turnDegrees(degreesToTurn,speed);
    }



    public void detectBlock(){
        detector.activate();
        while(!detector.isReady()){ }
        while (!detector.isCentered()) {
            //This method returns a value from the Relative Position enum - see Enum Package folder in DanCV for more info
            //Can yield LEFT,RIGHT,CENTER, or UNKNOWN (not visible)
            print("Detected Position", detector.getRelativePos().name());
            //Yields whether object is centered or not
            print("Centered? ", "" +detector.isCentered());
            //Whether its visible (may have problems, but should work)
            print("Visible", "" +detector.isVisible());
            if(detector.getRelativePos() == RelativePosition.RIGHT){
                setPowerAll(0.3);
            }else if(detector.getRelativePos() == RelativePosition.UNKNOWN){
                strafeForDistance(3,true);
            }else{
                setPowerAll(-0.3);
            }
        }
        setPowerAll(0);
        grabBlock();
    }


    private void grabBlock(){
        strafeDistance(10,0.7);
        //LIFT MECHANISM TO PICKUP BLOCK
    }


/**
 * ======================================TIME-BASED METHODS=======================================
 */
    /**
     * Will move motors for a set amount of time.
     * @param seconds - number of seconds to run the motors at FULL SPEED for.
     * @param isForward - Specifies whether direction of motion is Forward or Backward(REPLACE WITH ENUMERATION)
     */
    public void moveForDuration(double seconds, boolean isForward){
        //340 rpm gear down by 2 max speed per minute. 160 rpm/60
        //34 inches/second
        drivetrain.moveForDuration(seconds,isForward);
    }

    /**
     * Will move motors for a set amount of time to achieve a certain distance.
     * @param inches - number of inches to move
     * @param isForward - Specifies whether direction of motion is Forward or Backward(REPLACE WITH ENUMERATION)
     */
    public void moveForDistance(double inches,boolean isForward){
        drivetrain.moveDistanceByInch(inches,isForward);
    }

    /**
     * Will move motors for a set time to achieve a certain distance.
     * @param inches
     */
    public void strafeForDistance(double inches,boolean isLeft){
        drivetrain.strafeDistanceByInch(inches,isLeft);
    }

    /**
     * Will move motors for a set time to strafe a certain distance.
     * @param seconds - number of seconds to strafe for
     * @param isLeft - Specifies whether direction of motion is Forward or Backward(REPLACE WITH ENUMERATION)
     */
    public void strafeForDuration(double seconds,boolean isLeft){
        drivetrain.strafeForDuration(seconds,isLeft);
    }

    /**
     * ================================UNFINISHED METHODS SECTION===================================
     */

    public void setDrivePath(){
        boolean optionChosen = false;
        AutoSettings settings = new AutoSettings();
        print("Settings","");
        print("Q1: Blue or Red","Press A/B");
        do {
            if(gamepad1.a || gamepad2.b){
                if(gamepad1.a){
                    settings.setTeamColor(AutoSettings.Settings.BLUE);
                }else if(gamepad1.b){
                    settings.setTeamColor(AutoSettings.Settings.RED);
                }
                optionChosen = true;
            }
        }while(!optionChosen);
        optionChosen = false;
        print("Q2: Foundation or Skystone", "Press A/B");
        do {
            if(gamepad1.a || gamepad2.b){
                if(gamepad1.a){
                    settings.setFieldSide(AutoSettings.Settings.FOUNDATION);
                }else if(gamepad1.b){
                    settings.setFieldSide(AutoSettings.Settings.SKYSTONE);
                }
                optionChosen = true;
            }
        }while(!optionChosen);
        optionChosen = false;
        print("Q3: Additional Run Options","Press the corresponding button");
        print("\tA:","Simple");
        print("\tB:","Advanced");
        do {
            if(gamepad1.a || gamepad2.b){
                if(gamepad1.a){
                    settings.setComplexity(AutoSettings.Settings.SIMPLE);
                }else if(gamepad1.b){
                    settings.setComplexity(AutoSettings.Settings.ADVANCED);
                }
                optionChosen = true;
            }
        }while(!optionChosen);
        autoSettings = settings;
        print("Settings are complete.","Ready To Start");
    }
}
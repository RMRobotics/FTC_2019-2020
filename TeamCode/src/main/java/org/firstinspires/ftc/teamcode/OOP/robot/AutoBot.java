package org.firstinspires.ftc.teamcode.OOP.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OOP.drivetrain.AutoDrivetrain;

public class AutoBot extends Robot {
    private AutoDrivetrain drivetrain;
    private AutoSettings autoSettings;


    public AutoBot(HardwareMap hardwareMap){
        drivetrain = new AutoDrivetrain(hardwareMap);
        setupAttachments(hardwareMap);
    }

    /**
     * ================================MUTATORS AND ACCESSORS===================================
     */

    public void setTelemetry(Telemetry t){
        drivetrain.setTelemetry(t);
    }


    public void addTelemetry(String caption, String data){
        drivetrain.getTelemetry().addData(caption,data);
    }

    public void setDrivetrain(AutoDrivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public AutoDrivetrain getDrivetrain(){
        return drivetrain;
    }


    /**
     * ======================================MAIN METHODS=======================================
     */
    //========================================ENCODER-BASED===================================\\
    /**
     * THIS IS AN ENCODER-BASED MODE. Move a specified number of inches and then stop.
     * @param distanceInches - Distance in inches to move.
     * @param power - speed at which to move.
     */
    public void moveDistance(double distanceInches, double power){
        drivetrain.moveDistance(distanceInches,power);
    }

    /**
     * THIS IS AN ENCODER-BASED MODE. Strafe for a specified number of inches and then stop.
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

    }

    //========================================TIME-BASED===================================\\
    /**
     * THIS IS A TIME-BASED MODE. Will move motors for a set amount of time.
     * @param seconds - number of seconds to run the motors at FULL SPEED for.
     * @param isForward - Specifies whether direction of motion is Forward or Backward(REPLACE WITH ENUMERATION)
     */
    public void moveForDuration(double seconds, boolean isForward){
        //340 rpm gear down by 2 max speed per minute. 160 rpm/60
        //34 inches/second
        drivetrain.moveForDuration(seconds,isForward);
    }

    /**
     * THIS IS A TIME-BASED MODE. Will move motors for a set amount of time to achieve a certain distance.
     * @param inches - number of inches to move
     * @param isForward - Specifies whether direction of motion is Forward or Backward(REPLACE WITH ENUMERATION)
     */
    public void moveForDistance(double inches,boolean isForward){
        drivetrain.moveDistanceByInch(inches,isForward);
    }

    /**
     * THIS A TIME-BASED MODE. Will move motors for a set time to achieve a certain distance.
     * @param inches
     */
    public void strafeForDistance(double inches,boolean isForward){
        drivetrain.strafeDistanceByInch(inches,isForward);
    }

    /**
     * THIS IS AN TIME-BASED MODE. Will move motors for a set time to strafe a certain distance.
     * @param seconds - number of seconds to strafe for
     * @param isForward - Specifies whether direction of motion is Forward or Backward(REPLACE WITH ENUMERATION)
     */
    public void strafeForDuration(double seconds,boolean isForward){
        drivetrain.strafeForDuration(seconds,isForward);
    }

    /**
     * Method used to add powers to the motors all at once.
     * @param fl - Front Left
     * @param fr - Front Right
     * @param bl - Back Left
     * @param br - Back Right
     */
    @Override
    public void setPower(double fl, double fr, double bl, double br) {
        drivetrain.setPowerAll(fl,fr,bl,br);
    }

    /**
     * Sleep for a duration of time and then continue executing code.
     * @param seconds
     */
    public void waitForDuration(double seconds){
        drivetrain.wait(seconds);
    }


    /**
     * ================================UNFINISHED METHODS SECTION===================================
     */
    //this needs to take in a time to run for
    @Override
    public void intake() {

    }

    @Override
    public void pivot() {

    }



    public void setDrivePath(){
        boolean optionChosen = false;
        AutoSettings settings = new AutoSettings();
        addTelemetry("Settings","");
        addTelemetry("Q1: Blue or Red","Press A/B");
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
        addTelemetry("Q2: Foundation or Skystone", "Press A/B");
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
        addTelemetry("Q3: Additional Run Options","Press the corresponding button");
        addTelemetry("\tA:","Simple");
        addTelemetry("\tB:","Advanced");
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
        addTelemetry("Settings are complete.","Ready To Start");
    }
    /*

    private void waitForControllerInput(){
        do {
            if(gamepad1.a || gamepad2.b){
                if(gamepad1.a){
                    settings.setO(AutoSettings.Settings.BLUE);
                }else if(gamepad1.b){
                    settings.setTeamColor(AutoSettings.Settings.RED);
                }
                optionChosen = true;
            }
        }while(!optionChosen);
    }
    */
}

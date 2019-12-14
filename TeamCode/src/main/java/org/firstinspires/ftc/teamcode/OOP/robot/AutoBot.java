package org.firstinspires.ftc.teamcode.OOP.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OOP.drivetrain.AutoDrivetrain;

public class AutoBot extends Robot {
    private AutoDrivetrain drivetrain;


    public AutoBot(HardwareMap hardwareMap){
        drivetrain = new AutoDrivetrain(hardwareMap,false);
        setupAttachments(hardwareMap);

    }

    public void setDrivetrain(AutoDrivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public AutoDrivetrain getDrivetrain(){
        return drivetrain;
    }

    public void moveDistance(double distanceInches, double power){
        drivetrain.moveDistance(distanceInches,power);
    }

    public void turnDegrees(int degreesToTurn, double speed){
        drivetrain.turnDegrees(degreesToTurn,speed);
    }

    public void strafeDistance(double distanceInches, double power){
        drivetrain.strafeDistance(distanceInches,power);
    }

    @Override
    public void setPower(double fl, double fr, double bl, double br) {
        drivetrain.setPowerAll(fl,fr,bl,br);
    }
    //this needs to take in a time to run for
    @Override
    public void intake() {

    }
    public void setTelemetry(Telemetry t){
        drivetrain.setTelemetry(t);
    }

    @Override
    public void pivot() {

    }
//340 rpm gear down by 2 max speed per minute. 160 rpm/60
    //34 inches/second
    public void moveForDuration(double seconds, boolean isForward){

        drivetrain.moveForDuration(seconds,isForward);


    }

    /**
     * THIS IS A TIME-BASED MODE. Will move motors for a set time to achieve a certain distance.
     * @param inches
     */
    public void moveForDistance(double inches,boolean isForward){
        drivetrain.moveDistanceByInch(inches,isForward);
    }

    /**
     * THIS A TIME_BASED MODE. Will move motors for a set time to achieve a certain distance.
     * @param inches
     */
    public void strafeForDistance(double inches,boolean isForward){
        drivetrain.strafeDistanceByInch(inches,isForward);
    }


    public void strafeForDuration(double seconds,boolean isForward){
        drivetrain.strafeForDuration(seconds,isForward);
    }

    public void waitForDuration(double seconds){
        drivetrain.wait(seconds);
    }


}

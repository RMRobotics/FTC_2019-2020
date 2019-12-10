package org.firstinspires.ftc.teamcode.OOP;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class AutoBot extends Robot {
    private AutoDrivetrain drivetrain;


    public AutoBot(HardwareMap hardwareMap){
        drivetrain = new AutoDrivetrain(hardwareMap,false);
        setupAttachments(hardwareMap);

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

    @Override
    public void pivot() {

    }
}

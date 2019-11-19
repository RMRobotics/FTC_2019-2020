package org.firstinspires.ftc.teamcode.OOP;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Direction;
import org.firstinspires.ftc.teamcode.DriveDirection;

public class AutoDrivetrain extends Drivetrain {

    //constants
    public static final double MAX_JERK = 0;                //power / second^2
    public static final double MAX_ACCELERATION = 0;        //power / second
    public static final double MAX_VELOCITY = 0;            //power


    //constructors
    public AutoDrivetrain(HardwareMap hardwareMap){
        super(hardwareMap);
    }


    public void acceleratedMoveStraight(double maxJerk, double maxAcceleration, double maxVelocity, double moveDistance, DriveDirection direction){

    }

    public void acceleratedMoveStraight(double moveDistance, DriveDirection direction){
        acceleratedMoveStraight(MAX_JERK, MAX_ACCELERATION, MAX_VELOCITY, moveDistance, direction);
    }






}

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


    //methods

    //maxJerk in power / second^2, maxAcceleration in power / second, maxVelocity in power, moveDistance in inches
    public void acceleratedMoveStraight(double maxJerk, double maxAcceleration, double maxVelocity, double moveDistance, DriveDirection direction){
        double power = 0;

        double tickDistance = CPI * moveDistance;
        double halfTickDistance = tickDistance / 2;
        double quarterTickDistance = tickDistance / 4;
        double halfMaxVelocity = maxVelocity / 2;

        double acceleration = 0;
        double velocity = 0;
        double ticks = 0;

        boolean halfMaxVelocityReached = false;

        double previousTicks = ticks;
        double elapsedTicks = 0;

        while(! halfMaxVelocityReached){               //find a way to update ticks!!
            elapsedTicks = ticks - previousTicks;
            previousTicks = ticks;

            elapsedTicks = timer.seconds();
            timer.reset();

            if(acceleration <= maxAcceleration){
                if(ticks <= quarterTickDistance){
                    acceleration += elapsedTicks * maxJerk;
                }
                else{
                    if(acceleration <= 0){
                        acceleration = 0;
                    }
                    else{
                        acceleration -= elapsedTicks * maxJerk;
                    }
                }
            }
            if(velocity <= maxVelocity){
                if(ticks <= quarterTickDistance){
                    velocity += elapsedTicks * acceleration;
                }
            }

            setDrive(velocity);

            if(velocity >= halfMaxVelocity){
                halfMaxVelocityReached = true;
            }
        }
    }

    public void acceleratedMoveStraight(double moveDistance, DriveDirection direction){
        acceleratedMoveStraight(MAX_JERK, MAX_ACCELERATION, MAX_VELOCITY, moveDistance, direction);
    }






}

package org.firstinspires.ftc.teamcode.OOP;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DriveDirection;
import org.firstinspires.ftc.teamcode.RevIMU;

public class AutoDrivetrain extends Drivetrain {

    //constants
    public final double MAX_JERK = 0;                //power / second^2
    public final double MAX_ACCELERATION = 0;        //power / second
    public final double MAX_VELOCITY = 0;            //power
    protected RevIMU imu;
    private BNO055IMU rev;
    private ElapsedTime timer;

    //constructors
    public AutoDrivetrain(HardwareMap hardwareMap){
        super(hardwareMap);
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setOdometryMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setOdometryMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setOdometryZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rev = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new RevIMU(rev);
        timer = null;

    }



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

        boolean reachedHalfMaxVelocity = false;
        boolean reachedMaxAcceleration = false;
        boolean reachedHalfTicks = false;
        boolean reachedMaxVelocity = false;

        double ticksAtMaxAcceleration = 0;

        double dTicks = 1;

        double previousTicks = 0;

        double halfVelocityTicks = 0;


        setOdometryMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //get to half velocity
        while(! reachedHalfMaxVelocity && ! reachedHalfTicks){
            ticks = odometryRight.getCurrentPosition();

            if((ticks - previousTicks) > 1){
                dTicks = ticks - previousTicks;
            }
            else{
                dTicks = 1;
            }

            previousTicks = ticks;


            if(acceleration < maxAcceleration){
                acceleration += maxJerk * dTicks;
            }
            else{
                if(! reachedMaxAcceleration) {
                    ticksAtMaxAcceleration = ticks;
                    reachedMaxAcceleration = true;
                }
            }

            velocity += acceleration * dTicks;

            setDrive(velocity);

            if(velocity >= halfMaxVelocity){
                reachedHalfMaxVelocity = true;
            }
            if(ticks >= halfTickDistance){
                reachedHalfTicks = true;
            }
        }

        ticksAtMaxAcceleration = ticks - ticksAtMaxAcceleration;
        halfVelocityTicks = ticks;

        //get to half the distance
        while(! reachedHalfTicks){
            ticks = odometryRight.getCurrentPosition();

            if((ticks - previousTicks) > 1){
                dTicks = ticks - previousTicks;
            }
            else{
                dTicks = 1;
            }

            previousTicks = ticks;

            if(! reachedMaxVelocity){
                if(reachedMaxAcceleration){
                    if(odometryRight.getCurrentPosition() <= halfVelocityTicks + ticksAtMaxAcceleration){
                        velocity += acceleration * dTicks;
                        setDrive(velocity);
                    }
                    else{
                    reachedMaxAcceleration = false;
                }
                }
                else{
                    acceleration
                }
            }












            if(ticks >= halfTickDistance){
                reachedHalfTicks = true;
            }
        }

    }

    public void acceleratedMoveStraight(double moveDistance, DriveDirection direction){
        acceleratedMoveStraight(MAX_JERK, MAX_ACCELERATION, MAX_VELOCITY, moveDistance, direction);
    }


    protected void setDriveMode(DcMotor.RunMode r) {
        FL.setMode(r);
        FR.setMode(r);
        BL.setMode(r);
        BR.setMode(r);
    }

    protected void setZeroBehavior(DcMotor.ZeroPowerBehavior z) {
        FL.setZeroPowerBehavior(z);
        FR.setZeroPowerBehavior(z);
        BL.setZeroPowerBehavior(z);
        BR.setZeroPowerBehavior(z);
    }

    protected void setOdometryMode(DcMotor.RunMode r){
        odometryLeft.setMode(r);
        odometryRight.setMode(r);
        odometryX.setMode(r);
    }

    protected void setOdometryZeroBehavior(DcMotor.ZeroPowerBehavior z){
        odometryLeft.setZeroPowerBehavior(z);
        odometryRight.setZeroPowerBehavior(z);
        odometryX.setZeroPowerBehavior(z);
    }



    /**
     * Gets the orientation of the robot using the REV IMU
     * @return the angle of the robot
     */
    public double getZAngle(){
        return imu.getZAngle();
    }

    /**
     * Sets power to all four drive motors
     * @param fl power for right front motor
     * @param fr power for right back motor
     * @param bl power for left front motor
     * @param br power for left back motor
     */
    public void setPowerAll(double fl, double fr, double bl, double br){
        FL.setPower(fl);
        FR.setPower(fr);
        BL.setPower(bl);
        BR.setPower(br);
    }

    public void setTimer(ElapsedTime timer) {
        this.timer = timer;
    }
}

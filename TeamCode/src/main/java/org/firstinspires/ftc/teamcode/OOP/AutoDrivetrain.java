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

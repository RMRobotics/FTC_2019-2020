package org.firstinspires.ftc.teamcode.OOP.drivetrain;

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
    public final double MAX_VELOCITY = 0;
    public final double INCHES_PER_SECOND = 34;
    //power
    protected RevIMU imu;
    private BNO055IMU rev;
    private ElapsedTime timer;
    private final double HALF_SPEED = 0.5;
    private final double FULL_SPEED = 1;
    private boolean odometryActive;

    //Sensors
    protected DcMotor odometryX;
    protected DcMotor odometryLeft;
    protected DcMotor odometryRight;

    //constructors
    public AutoDrivetrain(HardwareMap hardwareMap, boolean odometryActive){
        this.odometryActive = odometryActive;
        setupMotors(hardwareMap);
        rev = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new RevIMU(rev);
        timer = new ElapsedTime();
    }

    @Override
    protected void setupMotors(HardwareMap hardwareMap) {
        super.setupMotors(hardwareMap);
        if(isOdometryActive()){
            odometryLeft = hardwareMap.dcMotor.get("odometryLeft");
            odometryRight = hardwareMap.dcMotor.get("odometryRight");
            odometryX = hardwareMap.dcMotor.get("odometryX");
            setDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            setOdometryMode(DcMotor.RunMode.RUN_USING_ENCODER);
            setOdometryMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }else{
            setDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
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
                    if(acceleration >= 0){
                        acceleration -= maxJerk * dTicks;
                    }
                    else{
                        acceleration = 0;
                    }
                }
            }

            if(ticks >= halfTickDistance){
                reachedHalfTicks = true;
            }
        }

    }

    public boolean isOdometryActive() {
        return odometryActive;
    }

    public void acceleratedMoveStraight(double moveDistance, DriveDirection direction){
        acceleratedMoveStraight(MAX_JERK, MAX_ACCELERATION, MAX_VELOCITY, moveDistance, direction);
    }

    protected void setOdometryMode(DcMotor.RunMode r){
        if(odometryActive){
            odometryLeft.setMode(r);
            odometryRight.setMode(r);
            odometryX.setMode(r);
        }

    }

    protected void setOdometryZeroBehavior(DcMotor.ZeroPowerBehavior z){
        if(odometryActive){
            odometryLeft.setZeroPowerBehavior(z);
            odometryRight.setZeroPowerBehavior(z);
            odometryX.setZeroPowerBehavior(z);
        }

    }


    /**
     *  *Method borrowed from last year*
     * @param distanceInches
     */
    public void moveDistance(double distanceInches, double power){
        if(!odometryActive){
            setSpecialMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setSpecialMode(DcMotor.RunMode.RUN_TO_POSITION);
             //Stop and reset encoder
            int distanceTics = (int)(distanceInches * CPI);
            int currentPos1 = FL.getCurrentPosition();
            //int currentPos2 = FR.getCurrentPosition();
            int currentPos3 = BL.getCurrentPosition();
            int currentPos4 = BR.getCurrentPosition();
            int startPos1 = currentPos1;
            //int startPos2 = currentPos2;
            int startPos3 = currentPos3;
            int startPos4 = currentPos4;


            int targetPos1 = currentPos1+distanceTics;
            //int targetPos2 = currentPos2+distanceTics;
            int targetPos3 = currentPos3+distanceTics;
            int targetPos4 = currentPos4+distanceTics;

            setTargetPosition(targetPos1,targetPos3,targetPos4);

            setPowerAll(power,power,power,power);

            // Loop while we approach the target.  Display position as we go
            while(FL.isBusy() && BL.isBusy() && BR.isBusy()) {
                telemetry.addData("FL Encoder", FL.getCurrentPosition());
                telemetry.addData("BL Encoder", BL.getCurrentPosition());
                telemetry.addData("BR Encoder", BR.getCurrentPosition());

                telemetry.addData("FL Start", startPos1 + ", Target: " +targetPos1);
                telemetry.addData("BL Start", startPos3+ ", Target: " +targetPos3);
                telemetry.addData("BR Start", startPos4+ ", Target: " +targetPos4);

                telemetry.addData("Power FR - ", FR.getPower());
                telemetry.addData("Power FL - ", FL.getPower());
                telemetry.addData("Power BL - ", BL.getPower());
                telemetry.addData("Power BR - ", BR.getPower());
                telemetry.update();
            }

            // We are done, turn motors off and switch back to normal driving mode.
            FL.setPower(0);
            FR.setPower(0);
            BL.setPower(0);
            BR.setPower(0);


        }

    }

    /**
     *
     * @param degreesToTurn - Number of degrees to turn the robot.
     * @param speed - Speed at which to rotate
     */
    public void turnDegrees(int degreesToTurn, double speed){
        while(getZAngle() < degreesToTurn){
            setPowerAll(speed, -speed, speed, -speed);
            if(getZAngle() < degreesToTurn/4) {
                setPowerAll(-speed/2, -speed/2, speed/2, speed/2);
            }
            telemetry.addData("IMU Angle", getZAngle());
            telemetry.update();
        }

        //Stop the robot
        setPowerAll(0, 0, 0, 0);
        timer.reset();
    }

    /**
     * A positive value for strafe, results in a strafe to the robot's right, negative for strafing left
     * @param distanceInches
     */
    public void strafeDistance(double distanceInches, double power){

        setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //Stop and reset encoder
        int distanceTics = (int)(distanceInches * CPI);
        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();

        setDriveMode(DcMotor.RunMode.RUN_TO_POSITION);
        int targetPos1 = currentPos1+distanceTics;
        int targetPos2 = currentPos2-distanceTics;
        int targetPos3 = currentPos3-distanceTics;
        int targetPos4 = currentPos4+distanceTics;

        //setTargetPosition(targetPos1,targetPos2,targetPos3,targetPos4); -THIS WILL BREAK THE CODE

        setPowerAll(power,-power,-power,power);

        // Loop while we approach the target.  Display position as we go
        while(FL.isBusy() && BL.isBusy() && BR.isBusy()) {
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



    }

    /**
     * Moves for a specified duration
     * @param seconds
     */
    public void moveForDuration(double seconds, boolean isForward){
        timer.reset();
        setDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if(isForward){
            setDrive(1);
        }else{
            setDrive(-1);
        }

        while(timer.seconds() < seconds){
            telemetry.addData("Moving straight for duration: ", seconds + ", currently at "+ timer.seconds());
            telemetry.update();
        }
        setDrive(0);
    }

    public void moveDistanceByInch(double inches, boolean isForward){
        double secondsToMove = INCHES_PER_SECOND/inches;
        moveForDuration(secondsToMove,isForward);
    }


    public void strafeForDuration(double seconds,boolean isLeft){
        timer.reset();
        setDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if(isLeft){
            setStrafe(1);
        }else{
            setStrafe(-1);
        }

        while(timer.seconds() < seconds){
            telemetry.addData("Strafing for duration: ", seconds + ", currently at "+ timer.seconds());
            telemetry.update();
        }
        setStrafe(0);
    }

    public void strafeDistanceByInch(double inches, boolean isLeft){
        double secondsToMove = INCHES_PER_SECOND/inches;
        strafeForDuration(secondsToMove,isLeft);
    }

    public void setStrafe(double power){
        setPowerAll(-power,-power,power,power);
    }

    /**
     * Gets the orientation of the robot using the REV IMU
     * @return the angle of the robot
     */
    public double getZAngle(){
        return imu.getZAngle();
    }


    public void setTargetPosition(int fl, int bl, int br){
        FL.setTargetPosition(fl);
        //FR.setTargetPosition(fr);
        BL.setTargetPosition(bl);
        BR.setTargetPosition(br);

    }

    public void setTimer(ElapsedTime timer) {
        this.timer = timer;
    }

    public DcMotor getOdometryLeft() {
        return odometryLeft;
    }

    public DcMotor getOdometryRight() {
        return odometryRight;
    }

    public DcMotor getOdometryX() {
        return odometryX;
    }


    public void wait(double seconds){
        timer.reset();
        while(timer.seconds() < seconds){
            telemetry.addData("Waiting...", "Current: "+ timer.seconds() + ", Target: " +seconds);
        }
    }
}
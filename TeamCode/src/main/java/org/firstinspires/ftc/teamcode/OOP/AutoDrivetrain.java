package org.firstinspires.ftc.teamcode.OOP;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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
    private final double HALF_SPEED = 0.5;
    private final double FULL_SPEED = 1;
    private Telemetry telemetry;

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

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }


    protected void setDriveMode(DcMotor.RunMode r) {
        FL.setMode(r);
        FR.setMode(r);
        BL.setMode(r);
        BR.setMode(r);
    }


    public Telemetry getTelemetry() {
        return telemetry;
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
     *  *Method borrowed from last year*
     * @param distanceInches
     */
    public void moveDistance(double distanceInches, double power){

        setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //Stop and reset encoder
        int distanceTics = (int)(distanceInches * CPI);
        int currentPos1 = FL.getCurrentPosition();
        int currentPos2 = FR.getCurrentPosition();
        int currentPos3 = BL.getCurrentPosition();
        int currentPos4 = BR.getCurrentPosition();

        setDriveMode(DcMotor.RunMode.RUN_TO_POSITION);
        int targetPos1 = currentPos1+distanceTics;
        int targetPos2 = currentPos2+distanceTics;
        int targetPos3 = currentPos3+distanceTics;
        int targetPos4 = currentPos4+distanceTics;

        setTargetPosition(targetPos1,targetPos2,targetPos3,targetPos4);

        setPowerAll(power,power,power,power);

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

        setTargetPosition(targetPos1,targetPos2,targetPos3,targetPos4);

        setPowerAll(power,-power,-power,power);

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

    public void setTargetPosition(int fl, int fr, int bl, int br){
        FL.setTargetPosition(fl);
        FR.setTargetPosition(fr);
        BL.setTargetPosition(bl);
        BR.setTargetPosition(br);

    }

    public void setTimer(ElapsedTime timer) {
        this.timer = timer;
    }
}

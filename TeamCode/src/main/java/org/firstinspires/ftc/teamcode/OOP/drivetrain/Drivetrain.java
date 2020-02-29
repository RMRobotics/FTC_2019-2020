package org.firstinspires.ftc.teamcode.OOP.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class Drivetrain {

    //Constants - TODO - Verify these values
    protected final float MM_PER_INCH = 25.4f;
    protected final float ROBOT_SIZE = 18 * MM_PER_INCH;
    protected final float FTC_FIELD_SIZE_MM = (12 * 12 - 2) * MM_PER_INCH;



    static final double COUNTS_PER_MOTOR_REV = 537.6;    // NeveRest Orbital 20 Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.143;     // debateable
    static final double WHEEL_DIAMETER_INCHES = 3.93701;     // for circumference
    static final double CPI = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    protected Telemetry telemetry;

    //Motors
    //Drivetrain Motors - Naming convention is FRONT(F)/BACK(B) followed by LEFT(L)/RIGHT(R)
    public DcMotor FL;
    public DcMotor FR;
    public DcMotor BL;
    public DcMotor BR;

    //Other variables
    protected ElapsedTime timer;
    protected boolean opModeIsActive;

    //Manual Movement Functions
    protected void setDrive(double p) {
        FL.setPower(p);
        FR.setPower(p);
        BL.setPower(p);
        BR.setPower(p);
    }

    //Manual Movement Functions
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

    protected void setupMotors(HardwareMap hardwareMap){
        //The hardwareMap is essentially the access point of the hardware on the robot
        //Here the motors are accessed through the the hardwareMap by the name set in the FTC-app configs.
        FL = hardwareMap.get(DcMotor.class, "FL");
        FR = hardwareMap.get(DcMotor.class, "FR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");

        FL.setDirection(DcMotor.Direction.FORWARD);
        BL.setDirection(DcMotor.Direction.FORWARD);
        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);

        //  foundation = hardwareMap.get(Servo.class, "foundation");
        //  parking = hardwareMap.get(CRServo.class, "parking");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();


        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Path", "Starting at %7d :%7d :%7d :%7d",
                FL.getCurrentPosition(),
                FR.getCurrentPosition(),
                BL.getCurrentPosition(),
                BR.getCurrentPosition());
        telemetry.update();

    }


    public double getCPI() {
        return CPI;
    }

    protected void setZeroBehavior(DcMotor.ZeroPowerBehavior z) {
        FL.setZeroPowerBehavior(z);
        FR.setZeroPowerBehavior(z);
        BL.setZeroPowerBehavior(z);
        BR.setZeroPowerBehavior(z);
    }
    protected void setDriveMode(DcMotor.RunMode r) {
        FL.setMode(r);
        FR.setMode(r);
        BL.setMode(r);
        BR.setMode(r);
    }



    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }
    public Telemetry getTelemetry() {
        return telemetry;
    }

    public boolean isOpModeIsActive() {
        return opModeIsActive;
    }

    public void setOpModeIsActive(boolean opModeIsActive) {
        this.opModeIsActive = opModeIsActive;
    }
}

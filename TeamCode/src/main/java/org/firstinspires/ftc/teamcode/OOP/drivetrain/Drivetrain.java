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
    protected final double CPI = 86.93063762;
    protected Telemetry telemetry;

    //Motors
    //Drivetrain Motors - Naming convention is FRONT(F)/BACK(B) followed by LEFT(L)/RIGHT(R)
    public DcMotor FL;
    public DcMotor FR;
    public DcMotor BL;
    public DcMotor BR;

    //Other variables
    protected ElapsedTime timer;
    protected boolean isOpModeActive;

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
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

    public boolean isOpModeActive() {
        return isOpModeActive;
    }

    public void setOpModeActive(boolean opModeActive) {
        isOpModeActive = opModeActive;
    }
}

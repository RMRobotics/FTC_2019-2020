package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.OOP.AutoDrivetrain;

import java.io.File;
@Autonomous(group = "auto",name = "OdometryCalibration")
public class PositionCalibration extends LinearOpMode {


    AutoDrivetrain drivetrain;
    DcMotor odometryLeft;
    DcMotor odometryRight;
    DcMotor odometryX;


    ElapsedTime timer = new ElapsedTime();
    double PIVOT_SPEED = 0.5;
    double horizontalTickOffset = 0;

    //Text files to write the values to. The files are stored in the robot controller under Internal Storage\FIRST\settings
    File wheelBaseSeparationFile = AppUtil.getInstance().getSettingsFile("wheelBaseSeparation.txt");
    File horizontalTickOffsetFile = AppUtil.getInstance().getSettingsFile("horizontalTickOffset.txt");

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        while(drivetrain.getZAngle() < 90 && opModeIsActive()){
            drivetrain.setPowerAll(-PIVOT_SPEED, -PIVOT_SPEED, PIVOT_SPEED, PIVOT_SPEED);
            if(drivetrain.getZAngle() < 60) {
                drivetrain.setPowerAll(-PIVOT_SPEED/2, -PIVOT_SPEED/2, PIVOT_SPEED/2, PIVOT_SPEED/2);
            }
            telemetry.addData("IMU Angle", drivetrain.getZAngle());
            telemetry.update();
        }

        //Stop the robot
        drivetrain.setPowerAll(0, 0, 0, 0);
        timer.reset();
        while(timer.milliseconds() < 1000 && opModeIsActive()){
            telemetry.addData("IMU Angle", drivetrain.getZAngle());
            telemetry.update();
        }

        //Record IMU and encoder values to calculate the constants for the global position algorithm
        double angle = drivetrain.getZAngle();

        /*
        Encoder Difference is calculated by the formula (leftEncoder - rightEncoder)
        Since the left encoder is also mapped to a drive motor, the encoder value needs to be reversed with the negative sign in front
        THIS MAY NEED TO BE CHANGED FOR EACH ROBOT
       */
        double encoderDifference = Math.abs(odometryLeft.getCurrentPosition()) + (Math.abs(odometryRight.getCurrentPosition()));

        double verticalEncoderTickOffsetPerDegree = encoderDifference/angle;

        double wheelBaseSeparation = (2*90*verticalEncoderTickOffsetPerDegree)/(Math.PI*drivetrain.getCPI());

        horizontalTickOffset = odometryX.getCurrentPosition()/Math.toRadians(drivetrain.getZAngle());

        //Write the constants to text files
        ReadWriteFile.writeFile(wheelBaseSeparationFile, String.valueOf(wheelBaseSeparation));
        ReadWriteFile.writeFile(horizontalTickOffsetFile, String.valueOf(horizontalTickOffset));

        while(opModeIsActive()){
            telemetry.addData("Odometry System Calibration Status", "Calibration Complete");
            //Display calculated constants
            telemetry.addData("Wheel Base Separation", wheelBaseSeparation);
            telemetry.addData("Horizontal Encoder Offset", horizontalTickOffset);

            //Display raw values
            telemetry.addData("IMU Angle", drivetrain.getZAngle());
            telemetry.addData("Vertical Left Position", -odometryLeft.getCurrentPosition());
            telemetry.addData("Vertical Right Position", odometryRight.getCurrentPosition());
            telemetry.addData("Horizontal Position", odometryX.getCurrentPosition());
            telemetry.addData("Vertical Encoder Offset", verticalEncoderTickOffsetPerDegree);

            //Update values
            telemetry.update();
        }

    }


    public void initialize(){
        drivetrain = new AutoDrivetrain(hardwareMap,true);
        odometryX = drivetrain.getOdometryX();
        odometryRight = drivetrain.getOdometryRight();
        odometryLeft = drivetrain.getOdometryLeft();
    }



}

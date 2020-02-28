package org.firstinspires.ftc.teamcode.Vuforia;

/*
Created on 2/27/20 by Neal Machado
Test aligning with the skystone(s) using vuforia
 */

//Z DISPLACEMENT STILL NEEDS TO BE ACCOUNTED FOR

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.States.AutoSuper;

import java.util.ArrayList;
import java.util.List;


@Autonomous(name = "Skystone Alignment Test", group = "Vuforia")
public class SkyStoneAlignTest extends AutoSuper {

    private static final float mmPerInch = 25.4f;
    private static final float robotSize = 18 * mmPerInch;
    private static final float mmFTCFieldWidth = (12 * 12 - 2) * mmPerInch;
    private static final float mmTargetHeight = 6 * mmPerInch;

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

//    // Constants for the center support targets
//    private static final float bridgeZ = 6.42f * mmPerInch;
//    private static final float bridgeY = 23 * mmPerInch;
//    private static final float bridgeX = 5.18f * mmPerInch;
//    private static final float bridgeRotY = 59;                                 // Units are degrees
//    private static final float bridgeRotZ = 180;
//
//    // Constants for perimeter targets
//    private static final float halfField = 72 * mmPerInch;
//    private static final float quadField = 36 * mmPerInch;


    @Override
    public void runOpMode() throws InterruptedException {

        double tX = 0, tY = 0, tZ = 0, rX = 0, rY = 0, rZ = 0;
        double drivePower = 0;
        double maxDrivePower = 1;

        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT; //uses the back phone camera
        params.vuforiaLicenseKey = "AW2QuFH/////AAABmWaIPHGonUtBiAByunLrGxcyyeTFYpDBVTYsP/A5yrSSQ7PX+/+pCet8bFzd5AWw983mUAycCFdAz/tNDXFvp5BJeqH2b5ZGPFwi08UznmQ9zrq+k3GiKBUSJj37HaPMGeOuE04icbwblA5FgZEThDkSAUyiUqL+tMPv/zkXNzpVWKJkjObucLS2gdYNljJm4calEVnr9JOLbmbcP0IU3hy53CJtkxFc65LSF7n+CcajbEEB2PVfTCS3JLwCHcSKYkoR/FrHO06YFyESC0f5itieL2hKKleOwqOFwiqpV77u5WlMj4y3UncYn0uiCob7f3uXTR//dCCqPAp9P2y5cowPQ5/G6jKyWmv3B+qyegux";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES; //show the xyz axis on the target (can be a teapot, building, axis, or none)

        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(params); //create the vuforia object with the above params
        VuforiaTrackables images = vuforia.loadTrackablesFromAsset("Skystone"); //access the .xml file with all the images

        //set location for the phone
        OpenGLMatrix phoneOnRobot = OpenGLMatrix
                .translation(0, robotSize / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 0, -90, 0));

        //set names for images
        VuforiaTrackable stoneTarget = images.get(0);
        stoneTarget.setName("Stone Target");

        List<VuforiaTrackable> imageTrackables = new ArrayList<VuforiaTrackable>();
        imageTrackables.addAll(images);

        //define matricices for the locations of the images
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, -90)));


        //pass phone orientation to listeners
        for (VuforiaTrackable trackable : images) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);
        }

        waitForStart();

        images.activate();

        OpenGLMatrix lastLocation = null; //stores last known location of robot

        images.activate();

        while (opModeIsActive()) {

            while (!isStopRequested()) {

                for (VuforiaTrackable i : images) {
                    OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) (i.getListener())).getPose();

                    if (pose != null) { //if the image is found

                        //finds data about the image
                        VectorF translation = pose.getTranslation();
                        Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                        OpenGLMatrix robotLocationOnField = ((VuforiaTrackableDefaultListener) (i).getListener()).getUpdatedRobotLocation();
                        lastLocation = robotLocationOnField;

                        if (lastLocation != null) {
                            telemetry.addData(i.getName(), " is visible");
//                        telemetry.addData("Pos ", format(lastLocation));

                            VectorF location = lastLocation.getTranslation();
                            Orientation locationRot = Orientation.getOrientation(lastLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                            tY = VuforiaUtil.round(location.get(0) / mmPerInch, 2);
                            tX = VuforiaUtil.round(location.get(1) / mmPerInch, 2);
                            rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle + 180), 2);

                            //find the rotational x and y components of the robot relative to the target, finds the z rotation of the robot relative to the field
                            if(i.getName().equals("Stone Target")){

                                telemetry.addData("\nImage: ", i.getName());
                                telemetry.addData("\n(Translation to Target) X: " + tX + ", Y: " + tY + ", Z: " + tZ, "");
                                telemetry.addData("\nZ-Rotation: ", rZ);

                                if(tX > .25){
                                    drivePower = -maxDrivePower + (1/(.5 * tX));
                                    if(drivePower > -.2){
                                        drivePower = -.2;
                                    }
                                }
                                else if (-.25 <= tX && tX <= .25){
                                    drivePower = 0;
                                }
                                else{
                                    drivePower = maxDrivePower - (1/(.5* tX));
                                    if(drivePower < .2){
                                        drivePower = .2;
                                    }
                                }
                                setPower(drivePower, drivePower, drivePower, drivePower);
                            }
                        }

                    }

                }
                telemetry.update();
            }
            images.deactivate();
        }
    }

    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }

}
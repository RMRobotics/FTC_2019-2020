package org.firstinspires.ftc.teamcode.Vuforia;

/*
Created on 10/15/19 by Neal Machado
Test Vuforia class with actual locations for each image
 */

//Z DISPLACEMENT STILL NEEDS TO BE ACCOUNTED FOR

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.ArrayList;
import java.util.List;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;


@Autonomous(name = "Skystone Test", group = "Vuforia")
public class SkyStoneRecogTest extends LinearOpMode {

    private static final float mmPerInch = 25.4f;
    private static final float robotSize = 18 * mmPerInch;
    private static final float mmFTCFieldWidth = (12 * 12 - 2) * mmPerInch;
    private static final float mmTargetHeight = 6 * mmPerInch;

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField = 36 * mmPerInch;


    @Override
    public void runOpMode() throws InterruptedException {
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

        VuforiaTrackable blueRearBridge = images.get(1);
        blueRearBridge.setName("Blue Rear Bridge");

        VuforiaTrackable redRearBridge = images.get(2);
        redRearBridge.setName("Red Rear Bridge");

        VuforiaTrackable redFrontBridge = images.get(3);
        redFrontBridge.setName("Red Front Bridge");

        VuforiaTrackable blueFrontBridge = images.get(4);
        blueFrontBridge.setName("Blue Front Bridge");

        VuforiaTrackable red1 = images.get(5);
        red1.setName("Red Perimeter 1");

        VuforiaTrackable red2 = images.get(6);
        red2.setName("Red Perimeter 2");

        VuforiaTrackable front1 = images.get(7);
        front1.setName("Front Perimeter 1");

        VuforiaTrackable front2 = images.get(8);
        front2.setName("Front Perimeter 2");

        VuforiaTrackable blue1 = images.get(9);
        blue1.setName("Blue Perimeter 1");

        VuforiaTrackable blue2 = images.get(10);
        blue2.setName("Blue Perimeter 2");

        VuforiaTrackable rear1 = images.get(11);
        rear1.setName("Rear Perimeter 1");

        VuforiaTrackable rear2 = images.get(12);
        rear2.setName("Rear Perimeter 2");

        List<VuforiaTrackable> imageTrackables = new ArrayList<VuforiaTrackable>();
        imageTrackables.addAll(images);

        //define matricices for the locations of the images
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 0, bridgeRotY, 0)));

        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90, 0, -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
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

                            double tX = VuforiaUtil.round(location.get(0) / mmPerInch, 2);
                            double tY = VuforiaUtil.round(location.get(1) / mmPerInch, 2);
                            double tZ = VuforiaUtil.round(location.get(2) / mmPerInch, 2);

                            double rX, rY, rZ;

                            //find the rotational x and y components of the robot relative to the target, finds the z rotation of the robot relative to the field

//                       -------REDEFINE FOR SKYSTONE YEAR------
//                        switch(i.getName()){
//                            case "BluePerimeter":
//                                rX = -1 * VuforiaUtil.round(rot.secondAngle, 2);
//                                rY = VuforiaUtil.round(rot.firstAngle, 2);
//                                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle + 180), 2);
//                                break;
//                            case "RedPerimeter":
//                                rX = VuforiaUtil.round(rot.secondAngle, 2);
//                                rY = -1 * VuforiaUtil.round(rot.firstAngle, 2);
//                                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle), 2);
//                                break;
//                            case "FrontPerimeter":
//                                rX = -1 * VuforiaUtil.round(rot.firstAngle, 2);
//                                rY = -1 * VuforiaUtil.round(rot.secondAngle, 2);
//                                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle + 270), 2);
//                                break;
//                            case "BackPerimeter":
//                                rX = VuforiaUtil.round(rot.firstAngle, 2);
//                                rY = VuforiaUtil.round(rot.secondAngle, 2);
//                                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle + 90), 2);
//                                break;
//                            default:
//                                rX = 0;
//                                rY = 0;
//                                rZ = 0;
//                        }
                            if(i.getName().equals("Stone Target")){
                                tY = VuforiaUtil.round(location.get(0) / mmPerInch, 2);
                                tX = VuforiaUtil.round(location.get(1) / mmPerInch, 2);
                                rZ = VuforiaUtil.round(VuforiaUtil.to180(-1 * rot.secondAngle + 180), 2);
                                telemetry.addData("\nImage: ", i.getName());
                                telemetry.addData("\n(Translation to Target) X: " + tX + ", Y: " + tY + ", Z: " + tZ, "");
                                telemetry.addData("\nZ-Rotation: ", rZ);
                            }
                            else{
                                telemetry.addData("\nImage: ", i.getName());
                                telemetry.addData("\n(Translations) X: " + VuforiaUtil.round(translation.get(0) / mmPerInch, 2) + ", Y: " + VuforiaUtil.round(translation.get(1) / mmPerInch, 2) + ", Z: " + VuforiaUtil.round(translation.get(2) / mmPerInch, 2), "");
                                telemetry.addData("(Rotations) X: " + VuforiaUtil.round(rot.firstAngle, 0) + ", Y: " + VuforiaUtil.round(rot.firstAngle, 1) + ", Z: " + VuforiaUtil.round(rot.firstAngle, 2), "");
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
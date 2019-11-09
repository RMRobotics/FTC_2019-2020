package org.firstinspires.ftc.teamcode.vuforia;

/*
Created on 10/24/19 by Neal Machado
Vuforia Util Class v3
 */

/*change for this year's challenges*/

/**********IMPORTS**********/

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
import java.util.ArrayList;

public class VuforiaUtil {

    /**********INSTANCE VARIABLES**********/
    public static final float MM_PER_INCH = 25.4f;
    public static final float robotSize = 18 * MM_PER_INCH;
    public static final float MM_FTC_FIELD_WIDTH = (12*12 - 2) * MM_PER_INCH;

    private VuforiaLocalizer.Parameters params;
    private VuforiaLocalizer vuforia;
    private VuforiaTrackables images;

    private float degreesX;
    private float degreesY;
    private float degreesZ;

    private OpenGLMatrix phoneOnRobot;
    private OpenGLMatrix blueImageOnField;
    private OpenGLMatrix redImageOnField;
    private OpenGLMatrix frontImageOnField;
    private OpenGLMatrix backImageOnField;

    private VuforiaTrackable currentImage;

    private OpenGLMatrix pose;
    private VectorF translation;
    private Orientation rot;
    private OpenGLMatrix robotLocationOnField;
    private OpenGLMatrix lastLocation;

    private VectorF location;
    private Orientation locationRot;

    private int i;
    private VuforiaLocalizer.CameraDirection cameraDirection;

    private Position robotToImage;
    private Position robotToField;

    /**********CONSTRUCTORS**********/
    //default Vuforia parameters set to front camera, no visual feedback
    public VuforiaUtil(){
        this(false,  VuforiaLocalizer.CameraDirection.FRONT);
    }

    //constructor with defaulted visual feedback as the axes
    public VuforiaUtil(boolean visualFeedback, VuforiaLocalizer.CameraDirection cameraDirection){
        this(visualFeedback, cameraDirection, VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, 90, 0, 0);
    }

    //constructor
    public VuforiaUtil(boolean visualFeedback, VuforiaLocalizer.CameraDirection cameraDirection, VuforiaLocalizer.Parameters.CameraMonitorFeedback feedback, float degreesX, float degreesY, float degreesZ){
        this.degreesX = degreesX;
        this.degreesY = degreesY;
        this.degreesZ = degreesZ;

        if(visualFeedback){
            params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
            params.cameraMonitorFeedback = feedback;
        }
        else{
            params = new VuforiaLocalizer.Parameters();
        }
        this.cameraDirection = cameraDirection;
        params.cameraDirection = cameraDirection;
        params.vuforiaLicenseKey = "AW2QuFH/////AAABmWaIPHGonUtBiAByunLrGxcyyeTFYpDBVTYsP/A5yrSSQ7PX+/+pCet8bFzd5AWw983mUAycCFdAz/tNDXFvp5BJeqH2b5ZGPFwi08UznmQ9zrq+k3GiKBUSJj37HaPMGeOuE04icbwblA5FgZEThDkSAUyiUqL+tMPv/zkXNzpVWKJkjObucLS2gdYNljJm4calEVnr9JOLbmbcP0IU3hy53CJtkxFc65LSF7n+CcajbEEB2PVfTCS3JLwCHcSKYkoR/FrHO06YFyESC0f5itieL2hKKleOwqOFwiqpV77u5WlMj4y3UncYn0uiCob7f3uXTR//dCCqPAp9P2y5cowPQ5/G6jKyWmv3B+qyegux";

        vuforia = ClassFactory.getInstance().createVuforia(params);


        images = vuforia.loadTrackablesFromAsset("RoverRuckus");

        images.get(0).setName("BluePerimeter");
        images.get(1).setName("RedPerimeter");
        images.get(2).setName("FrontPerimeter");
        images.get(3).setName("BackPerimeter");

        OpenGLMatrix phoneOnRobot = OpenGLMatrix
                .translation(0, robotSize / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, degreesX, degreesY, degreesZ));

        OpenGLMatrix blueImageOnField = OpenGLMatrix
                .translation(-MM_FTC_FIELD_WIDTH / 2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 90));
        images.get(0).setLocation(blueImageOnField);
        ((VuforiaTrackableDefaultListener)images.get(0).getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);

        OpenGLMatrix redImageOnField = OpenGLMatrix
                .translation(MM_FTC_FIELD_WIDTH / 2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 270));
        images.get(1).setLocation(redImageOnField);
        ((VuforiaTrackableDefaultListener)images.get(1).getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);

        OpenGLMatrix frontImageOnField = OpenGLMatrix
                .translation(0, -MM_FTC_FIELD_WIDTH / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 180, 0));
        images.get(2).setLocation(frontImageOnField);
        ((VuforiaTrackableDefaultListener)images.get(2).getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);

        OpenGLMatrix backImageOnField = OpenGLMatrix
                .translation(0, MM_FTC_FIELD_WIDTH / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 0));
        images.get(3).setLocation(backImageOnField);
        ((VuforiaTrackableDefaultListener)images.get(3).getListener()).setPhoneInformation(phoneOnRobot, params.cameraDirection);

        images.activate();

        pose = null;

        robotLocationOnField = null;
        lastLocation = null;

        location = null;
        locationRot = null;
        translation = null;
        rot = null;

        i = 0;

        robotToField = new Position();
        robotToImage = new Position();
    }

    /**********METHODS**********/
    public void start(){

    }

    public void stillRobotInformation(){
        ArrayList<Position> robotToImages = new ArrayList();
        ArrayList<Position> robotToFields = new ArrayList();
        int count = 0;
        while(robotToImages.size() < 6 && count < 25){
            robotInformation();
            if(robotToField != null && robotToImage != null){
                robotToImages.add(new Position(robotToImage));
                robotToFields.add(new Position(robotToField));
            }
            count++;
        }
        if(robotToImages.size() < 6){
            robotToImage.settX(-1);
            robotToImage.settY(-1);
            robotToImage.settZ(-1);
            robotToImage.setrX(-1);
            robotToImage.setrY(-1);
            robotToImage.setrZ(-1);
            robotToField.settX(-1);
            robotToField.settY(-1);
            robotToField.settZ(-1);
            robotToField.setrX(-1);
            robotToField.setrY(-1);
            robotToField.setrZ(-1);
        }
        else{
            robotToImage = new Position();
            robotToField = new Position();
            for(int l = 0; l < 6; l++){
                robotToImage.settX(robotToImage.gettX() + robotToImages.get(l).gettX());
                robotToImage.settY(robotToImage.gettY() + robotToImages.get(l).gettY());
                robotToImage.settZ(robotToImage.gettZ() + robotToImages.get(l).gettZ());
                robotToImage.setrX(robotToImage.getrX() + robotToImages.get(l).getrX());
                robotToImage.setrY(robotToImage.getrY() + robotToImages.get(l).getrY());
                robotToImage.setrZ(robotToImage.getrZ() + robotToImages.get(l).getrZ());
                robotToField.settX(robotToField.gettX() + robotToFields.get(l).gettX());
                robotToField.settY(robotToField.gettY() + robotToFields.get(l).gettY());
                robotToField.settZ(robotToField.gettZ() + robotToFields.get(l).gettZ());
                robotToField.setrX(robotToField.getrX() + robotToFields.get(l).getrX());
                robotToField.setrY(robotToField.getrY() + robotToFields.get(l).getrY());
                robotToField.setrZ(robotToField.getrZ() + robotToFields.get(l).getrZ());
            }
            robotToImage.settX(robotToImage.gettX() / 6);
            robotToImage.settY(robotToImage.gettY() / 6);
            robotToImage.settZ(robotToImage.gettZ() / 6);
            robotToImage.setrX(robotToImage.getrX() / 6);
            robotToImage.setrY(robotToImage.getrY() / 6);
            robotToImage.setrZ(robotToImage.getrZ() / 6);
            robotToField.settX(robotToField.gettX() / 6);
            robotToField.settY(robotToField.gettY() / 6);
            robotToField.settZ(robotToField.gettZ() / 6);
            robotToField.setrX(robotToField.getrX() / 6);
            robotToField.setrY(robotToField.getrY() / 6);
            robotToField.setrZ(robotToField.getrZ() / 6);
        }

    }

    public void robotInformation(){

        for(i = 0; i < 4; i++){
            pose = ((VuforiaTrackableDefaultListener)(images.get(i).getListener())).getPose();

            if(pose != null){
                currentImage = images.get(i);

                robotToImage.settX(5);
                retrieveData();
                retrieveRobotToImage();
                if(lastLocation != null){
                    location = lastLocation.getTranslation();
                    locationRot = Orientation.getOrientation(lastLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                    retrieveRobotToField();
                }
            }
        }

    }

    public void stop(){

    }

    private void retrieveData(){

        translation = pose.getTranslation();
        rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

        robotLocationOnField = ((VuforiaTrackableDefaultListener)(images.get(i)).getListener()).getUpdatedRobotLocation();
        lastLocation = robotLocationOnField;
    }

    private void retrieveRobotToImage(){
            if(degreesY == 0){
                robotToImage.settX(round(translation.get(0) / MM_PER_INCH, 2));
                robotToImage.settY(round(translation.get(1) / MM_PER_INCH, 2));
                robotToImage.settZ(-1 * round(translation.get(2) / MM_PER_INCH, 2));
                robotToImage.setrX(to180(round(rot.firstAngle, 2)));
                robotToImage.setrY(to180(round(rot.secondAngle, 2)));
                robotToImage.setrZ(to180(round(rot.thirdAngle, 2)));
            }
            else if(degreesY == -90){
                    robotToImage.settX(-1 * round(translation.get(1) / MM_PER_INCH, 2));
                    robotToImage.settY(round(translation.get(0) / MM_PER_INCH, 2));
                    robotToImage.settZ(-1 * round(translation.get(2) / MM_PER_INCH, 2));
                    robotToImage.setrX(-1 * to180(round(rot.firstAngle, 2)));
                    robotToImage.setrY(-1 * to180(round(rot.secondAngle, 2)));
                    robotToImage.setrZ(round(to180(rot.thirdAngle - 90), 2));
            }
//            else if(cameraDirection == VuforiaLocalizer.CameraDirection.BACK){
//                robotToImage.settX(round(translation.get(0) / MM_PER_INCH, 2));
//                robotToImage.settY(round(translation.get(1) / MM_PER_INCH, 2));
//                robotToImage.settZ(-1 * round(translation.get(2) / MM_PER_INCH, 2));
//                robotToImage.setrX(to180(round(rot.firstAngle, 2)));
//                robotToImage.setrY(to180(round(rot.secondAngle, 2)));
//                robotToImage.setrZ(to180(round(rot.thirdAngle, 2)));
//            }
    }

    private void retrieveRobotToField(){
            robotToField.settX(VuforiaUtil.round(location.get(0) / MM_PER_INCH, 2));
            robotToField.settY(VuforiaUtil.round(location.get(1) / MM_PER_INCH, 2));
            robotToField.settZ(VuforiaUtil.round(location.get(2) / MM_PER_INCH, 2));
            switch (currentImage.getName()) {
                case "BluePerimeter":
                    robotToField.setrX(-1 * round(rot.secondAngle, 2));
                    robotToField.setrY(round(rot.firstAngle, 2));
                    robotToField.setrZ(round(to180(-1 * rot.secondAngle + 180), 2));
                    break;
                case "RedPerimeter":
                    robotToField.setrX(round(rot.secondAngle, 2));
                    robotToField.setrY(-1 * round(rot.firstAngle, 2));
                    robotToField.setrZ(round(to180(-1 * rot.secondAngle), 2));
                    break;
                case "FrontPerimeter":
                    robotToField.setrX(-1 * round(rot.firstAngle, 2));
                    robotToField.setrY(-1 * round(rot.secondAngle, 2));
                    robotToField.setrZ(round(to180(-1 * rot.secondAngle + 270), 2));
                    break;
                case "BackPerimeter":
                    robotToField.setrX(round(rot.firstAngle, 2));
                    robotToField.setrY(round(rot.secondAngle, 2));
                    robotToField.setrZ(round(VuforiaUtil.to180(-1 * rot.secondAngle + 90), 2));
                    break;
                default:
                    robotToField.setrX(0);
                    robotToField.setrY(0);
                    robotToField.setrZ(0);
                    break;
            }
    }

    /**********ACCESSORS**********/
    public VuforiaTrackable getCurrentImage(){ return currentImage; }
    public String getCurrentImageName(){
        if(currentImage != null){
            return currentImage.getName();
        }
        else{
            return "No Current Image";
        }
    }
    public Position getRobotToImage(){ return robotToImage; }
    public Position getRobotToField(){ return robotToField; }
    public double getRobotToImagetX(){ return robotToImage.gettX(); }
    public double getRobotToImagetY(){ return robotToImage.gettY(); }
    public double getRobotToImagetZ(){ return robotToImage.gettZ(); }
    public double getRobotToImagerX(){ return robotToImage.getrX(); }
    public double getRobotToImagerY(){ return robotToImage.getrY(); }
    public double getRobotToImagerZ(){ return robotToImage.getrZ(); }
    public double getRobotToFieldtX(){ return robotToField.gettX(); }
    public double getRobotToFieldtY(){ return robotToField.gettY(); }
    public double getRobotToFieldtZ(){ return robotToField.gettZ(); }
    public double getRobotToFieldrX(){ return robotToField.getrX(); }
    public double getRobotToFieldrY(){ return robotToField.getrY(); }
    public double getRobotToFieldrZ(){ return robotToField.getrZ(); }


    /**********STATIC UTILITY METHODS**********/
    //gives the general location of the image on the field given a number
    public static String imageLocation(int num){
        switch(num){
            case -1:
                return "No Image In Sight";
            case 0:
                return "BluePerimeter";
            case 1:
                return "RedPerimeter";
            case 2:
                return "FrontPerimeter";
            case 3:
                return "BackPerimeter";
            case 7:
                return "TEST";
            default:
                return "Invalid input";

        }
    }

    //takes a value with angle measurements of 0 to 360 and converts it to -180 to 180
    public static double to180(double angle){
        while(angle > 180) {
            angle -= 360;
        }
        while(angle <= -180){
            angle += 360;
        }
        return angle;
    }

    //rounds a double (num) to decPlaces decimal places
    public static double round(double num, int decPlaces){
        double roundedNum = 0;
        double numMult = num * Math.pow(10, decPlaces);
        numMult = Math.round(numMult);
        roundedNum = numMult / Math.pow(10, decPlaces);
        return roundedNum;
    }

}

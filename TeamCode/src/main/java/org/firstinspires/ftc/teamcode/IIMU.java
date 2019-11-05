package org.firstinspires.ftc.teamcode;

/**
 * Created by Angela on 11/24/2018.
 */

public interface IIMU {
    double getXAngle();  //Gets angle on x-axis
    double getYAngle();  //Gets angle on y-axis
    double getZAngle();  //Gets angle on z-axis
    double getZAngle(double desiredAngle); //get z angle with the discontinuity being opposite of the desired angle
    double getXAcc();    //Gets acceleration on x-axis
    double getYAcc();    //Gets acceleration on y-axis
    double getZAcc();    //Gets acceleration on z-axis
    double getXVelo();    //Gets velocity on x-axis
    double getYVelo();    //Gets velocity on y-axis
    double getZVelo();    //Gets velocity on z-axis
    void calibrate();    //Calibrates sensor
    void setOffset(double offset);   //sets offset
    void setAsZero();    //Set the current position as zero
    void initialize();
}
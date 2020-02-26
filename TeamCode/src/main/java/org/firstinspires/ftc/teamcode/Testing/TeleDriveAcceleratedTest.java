package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OOP.robot.TeleBot;

@TeleOp(name = "AccelerationTest", group = "tele")
public class TeleDriveAcceleratedTest extends OpMode {
    protected int max;
    protected TeleBot robot;

    @Override
    public void init() {

        robot = new TeleBot(hardwareMap, gamepad1, gamepad2);
    }

    double fl, fr, bl, br;
    double forward, strafe, rotate;
    double accelerationMultiplier = 0;
    final double jerk = 0.005;
    boolean accelerating = false;

    public void loop() {

        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        if(forward == 0 && strafe == 0 && rotate == 0){
            accelerating = false;
            accelerationMultiplier = 0;
        }
        else{
            if (accelerating){
                if(accelerationMultiplier <= 1){
                    accelerationMultiplier += jerk;
                }
                else{
                    accelerationMultiplier = 1;
                }
            }
            else{
                accelerating = true;
            }
        }

        //toggles to slowmode
        if (gamepad1.a) {
            if (max == 1) {
                max = 2;
                telemetry.addData("half speed", "");
            } else {
                max = 1;
                telemetry.addData("normal", "");
            }
            telemetry.update();
        }

        fl = (forward - strafe + rotate) / max;
        fr = (forward - strafe - rotate) / max;
        bl = (forward + strafe + rotate) / max;
        br = (forward + strafe - rotate) / max;

        robot.setPower(fl, fr, bl, br);
        robot.intake();
        robot.lift();
    }
}

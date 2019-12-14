package org.firstinspires.ftc.teamcode.Qualifier;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OOP.robot.TeleBot;

@TeleOp(name = "QualiferTele", group = "tele")
public class TeleDriveTest extends OpMode {
    protected int max;
    protected TeleBot robot;

    @Override
    public void init() {
        robot = new TeleBot(hardwareMap, gamepad1, gamepad2);
    }

    double fl, fr, bl, br;

    public void loop() {

        double forward, strafe, rotate;
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

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
        robot.pivot();
        robot.clamp();
    }
}

package org.firstinspires.ftc.teamcode.OOP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Working2.0",group = "tele")
public class TeleDriveTest extends OpMode {
    protected int max;
    protected TeleBot robot;

    @Override
    public void init() {
        robot = new TeleBot(hardwareMap,gamepad1,gamepad2);
    }

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

        double fl = (forward - strafe + rotate) / max;
        double fr = (forward - strafe - rotate) / max;
        double bl = (forward + strafe + rotate) / max;
        double br = (forward + strafe - rotate) / max;

        robot.setPower(fl,fr,bl,br);
        robot.intake();
        robot.lift();
    }
}

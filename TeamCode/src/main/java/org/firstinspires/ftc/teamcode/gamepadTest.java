

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;



@TeleOp(name="gamepad test", group="Robot")
//@Disabled
public class gamepadTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        /* Wait for the game driver to press play */
        waitForStart();
        /* Run until the driver presses stop */
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            telemetry.addData("LEFT STICK Y", y);
            telemetry.addData("LEFT STICK X", x);
            telemetry.addData("RIGHT STICK X", rx);
            telemetry.update();
        }
    }
}
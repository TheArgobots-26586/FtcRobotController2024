
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

/* Code to test servos. */

@TeleOp(name="Servo Test", group="Linear OpMode")
public class ServoTest extends LinearOpMode {
    private Servo wrist_servo = null;
    private CRServo intake_servo = null;

    @Override
    public void runOpMode() {
        wrist_servo = hardwareMap.get(Servo.class,"wrist");
        intake_servo = hardwareMap.get(CRServo.class,"intake");
        wrist_servo.setDirection(Servo.Direction.REVERSE);
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("current position:",wrist_servo.getPosition());
            if (gamepad1.cross) {
                wrist_servo.setPosition(1.0);
            }
            else if (gamepad1.circle) {
                wrist_servo.setPosition(0.0);

            }
            else if (gamepad1.triangle) {
                wrist_servo.setPosition(0.5);
            }
            if (gamepad1.dpad_up) {
                intake_servo.setPower(1.0);
            }
            else if (gamepad1.dpad_down) {
                intake_servo.setPower(0.0);
            }
            else if (gamepad1.dpad_left) {
                intake_servo.setPower(-1.0);
            }
            telemetry.update();
        }
    }
}


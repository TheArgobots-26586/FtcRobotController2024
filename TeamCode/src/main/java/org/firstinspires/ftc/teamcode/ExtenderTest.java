package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Extender Test", group="Linear OpMode")
public class ExtenderTest extends LinearOpMode {
    public static double extended = -1200.0;
    public static double unextended = 0.0;
    private DcMotor extender = null;

    public void runOpMode() {

        extender = hardwareMap.get(DcMotor.class, "extender");
        extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        while (opModeIsActive()) {

            if ((gamepad1.triangle) & (extender.getCurrentPosition() < unextended)) {
                ((DcMotorEx) extender).setVelocity(500.0);
            }
            else if ((gamepad1.cross) & (extender.getCurrentPosition() > extended))  {
                ((DcMotorEx) extender).setVelocity(-500.0);
            }
            else {
                ((DcMotorEx) extender).setVelocity(0.0);
            }

            telemetry.addData("Current Ticks:", extender.getCurrentPosition());
            telemetry.update();


        }

    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp(name="Extender PID Test", group="Linear OpMode")
public class ExtenderPIDTest extends LinearOpMode {
    public static double extended = -1200;
    public static double unextended = 0;
    private DcMotor extender = null;
    public void runOpMode() {

        extender = hardwareMap.get(DcMotor.class, "extender");
        extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.triangle) {
                ((DcMotorEx) extender).setVelocity(500.0);
                extender.setTargetPosition((int)unextended);
                extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else if (gamepad1.cross)  {
                ((DcMotorEx) extender).setVelocity(-500.0);
                extender.setTargetPosition((int)extended);
                extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            PIDFCoefficients coefficients = ((DcMotorEx) extender).getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION);

            telemetry.addData("Current Ticks:", extender.getCurrentPosition());
            telemetry.addData("P", coefficients.p);
            telemetry.addData("I:", coefficients.i);
            telemetry.addData("D:", coefficients.d);
            telemetry.addData("F:", coefficients.f);

            telemetry.addData("Velocity:", ((DcMotorEx) extender).getVelocity());
            telemetry.addData("Power: ", extender.getPower());
            telemetry.update();




        }

    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Lift Test", group="Linear OpMode")
public class LiftTest extends LinearOpMode {
    private DcMotor liftMotor = null;
    static final int kDeposit = 2800;
    static final int kPickup = 190;


    public void runOpMode() {

        liftMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.triangle) {
                ((DcMotorEx) liftMotor).setVelocity(1000.0);
                liftMotor.setTargetPosition(kDeposit);
                liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else if (gamepad1.cross) {
                ((DcMotorEx) liftMotor).setVelocity(1000.0);
                liftMotor.setTargetPosition(kPickup);
                liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            telemetry.addData("Current Ticks:", liftMotor.getCurrentPosition());
            telemetry.update();
        }

    }
}


package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name="Extender Test", group="teleop")
public class ExtenderCheck extends LinearOpMode{
    private DcMotor extender = null;
    private DcMotor armMotor = null;
    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0; // we want ticks per degree, not per rotation
    //The end result of these calculations is 19.7924893141
    @Override
    public void runOpMode() {
        extender = hardwareMap.get(DcMotor.class, "extender");
        extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        /*
        armMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setTargetPosition((int)ARM_TICKS_PER_DEGREE*20);
        armMotor.setPower(0.2);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(armMotor.isBusy()) {
            telemetry.addData("TARGET: ", armMotor.getTargetPosition());
            telemetry.addData("POSITION: ", armMotor.getCurrentPosition());
        }
        armMotor.setPower(0);
        */
        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("EXTENDER TICKS", extender.getCurrentPosition());
            telemetry.update();
        }
    }

}

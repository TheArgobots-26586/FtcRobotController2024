package org.firstinspires.ftc.teamcode.drive;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
public class ArmLift {
    public DcMotor armMotor;
    public Servo wrist;
    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0; // we want ticks per degree, not per rotation
    public ArmLift(HardwareMap hardwareMap) {
        armMotor = hardwareMap.get(DcMotorEx.class, "ArmMotor");
        wrist = hardwareMap.get(Servo.class, "wrist");
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void liftArm(Telemetry telemetry) {
        telemetry.addLine("LIFTING ARM");

        armMotor.setTargetPosition((int)(ARM_TICKS_PER_DEGREE*20));
        ((DcMotorEx) armMotor).setVelocity(2100);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wrist.setPosition(0.85);
        telemetry.addLine("EXITING LIFTARM");
        telemetry.update();
    }

}

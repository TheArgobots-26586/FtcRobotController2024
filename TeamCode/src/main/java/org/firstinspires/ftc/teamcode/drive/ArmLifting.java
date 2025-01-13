package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmLifting {
    DcMotor armMotor = null;
    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0;
    final double ARM_COLLAPSED_INTO_ROBOT  = 0;
    final double ARM_COLLECT               = 0 * ARM_TICKS_PER_DEGREE;
    final double ARM_CLEAR_BARRIER         = 25 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SAMPLE_IN_LOW   = 100 * ARM_TICKS_PER_DEGREE;
    public ArmLifting(HardwareMap hardwareMap) {
        armMotor = hardwareMap.get(DcMotorEx.class, "ArmMotor");
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition((int)ARM_COLLAPSED_INTO_ROBOT);
        ((DcMotorEx) armMotor).setVelocity(2100);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void armCollect() {
        armMotor.setTargetPosition((int)ARM_COLLECT);
        ((DcMotorEx) armMotor).setVelocity(2100);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void armClearBarrier() {
        armMotor.setTargetPosition((int)ARM_CLEAR_BARRIER);
        ((DcMotorEx) armMotor).setVelocity(2100);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void armScoreSample() {
        armMotor.setTargetPosition((int)ARM_SCORE_SAMPLE_IN_LOW);
        ((DcMotorEx) armMotor).setVelocity(2100);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}

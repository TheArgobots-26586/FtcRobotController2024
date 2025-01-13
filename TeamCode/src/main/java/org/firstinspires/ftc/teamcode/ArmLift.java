package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmLift {
    private DcMotor lift = null;
    private final double ARM_TICKS_PER_RADIAN =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0
                    * 1 / (Math.PI/180);

    public ArmLift (HardwareMap hardwareMap)  {
        lift = hardwareMap.get(DcMotor.class, "ArmMotor");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public boolean checkTarget(int target) {
        if (Math.abs(lift.getCurrentPosition()-target) < 50) {
            return true;
        }
        return false;
    }

    public boolean clearBarrier() {
        int target = (int)(Math.toRadians(25) * ARM_TICKS_PER_RADIAN);
        ((DcMotorEx) lift).setVelocity(1000.0);
        lift.setTargetPosition(target);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        return checkTarget(target);
    }

    public boolean raise() {
        ((DcMotorEx) lift).setVelocity(1000.0);
        int target = (int)(Math.toRadians(100) * ARM_TICKS_PER_RADIAN);
        lift.setTargetPosition(target);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        return checkTarget(target);
    }

    public boolean lower() {
        ((DcMotorEx) lift).setVelocity(1000.0);
        int target = 0;
        lift.setTargetPosition(target);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        return checkTarget(target);
    }




}

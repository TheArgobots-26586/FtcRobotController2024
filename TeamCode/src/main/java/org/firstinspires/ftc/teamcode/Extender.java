package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Extender {
    private DcMotor extender = null;
    public static int extended_ticks = -1300;
    public static int retracted_ticks = 0;
    public Extender (HardwareMap hardwareMap)  {
        extender = hardwareMap.get(DcMotor.class, "extender");
        extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public boolean extend() {
        ((DcMotorEx) extender).setVelocity(500.0);
        extender.setTargetPosition(extended_ticks);
        ((DcMotorEx) extender).setTargetPositionTolerance(25);
        extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (Math.abs(extender.getCurrentPosition() - extended_ticks) < 50) {
            return true;
        }
        return false;
    }
    public boolean retract() {
        ((DcMotorEx) extender).setVelocity(500.0);
        extender.setTargetPosition(retracted_ticks);
        ((DcMotorEx) extender).setTargetPositionTolerance(25);
        extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (Math.abs(extender.getCurrentPosition() - retracted_ticks) < 50) {
            return true;
        }
        return false;
    }


}

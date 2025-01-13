package org.firstinspires.ftc.teamcode.drive;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
public class ExtenderLift {
    DcMotor liftMotor = null;
    final double LIFT_TICKS_PER_MM = (111132.0 / 289.0) / 120.0;
    final double LIFT_SCORING_IN_LOW_BASKET = 0 * LIFT_TICKS_PER_MM;
    final double LIFT_SCORING_IN_HIGH_BASKET = 480 * LIFT_TICKS_PER_MM;

    public ExtenderLift(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.get(DcMotorEx.class, "extender");
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setTargetPosition((int)(LIFT_SCORING_IN_LOW_BASKET));
        ((DcMotorEx) liftMotor).setVelocity(1000);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void extend() {
        liftMotor.setTargetPosition((int)(LIFT_SCORING_IN_HIGH_BASKET));
        ((DcMotorEx) liftMotor).setVelocity(1000);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void unextend() {
        liftMotor.setTargetPosition((int)(LIFT_SCORING_IN_LOW_BASKET));
        ((DcMotorEx) liftMotor).setVelocity(1000);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

}

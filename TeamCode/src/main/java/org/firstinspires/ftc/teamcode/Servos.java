package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Servos {
    private Servo wrist = null;
    private CRServo intake = null;
    private final double ARM_TICKS_PER_RADIAN =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0
                    * 1 / (Math.PI/180);

    public Servos (HardwareMap hardwareMap)  {
        wrist = hardwareMap.get(Servo.class, "wrist");
        intake = hardwareMap.get(CRServo.class, "intake");
    }

    public void intakeIn() {
        intake.setPower(-1.0);
    }

    public void intakeOut() {
        intake.setPower(1.0);
    }

    public void intakeOff() {
        intake.setPower(0.0);
    }

    public void wristOut() {
        wrist.setPosition(0.5);
    }
    public void wristIn() {
        wrist.setPosition(0.0);
    }


}

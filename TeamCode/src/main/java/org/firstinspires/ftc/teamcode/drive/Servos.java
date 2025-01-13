package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Servos {
    public CRServo intake      = null; //the active intake servo
    public Servo wrist       = null;
    final double INTAKE_COLLECT    = -1.0;
    final double INTAKE_OFF        =  0.0;
    final double INTAKE_DEPOSIT    =  0.5;
    final double WRIST_FOLDED_IN   = 0.85;
    final double WRIST_FOLDED_OUT  = 0.5;
    public Servos(HardwareMap hardwareMap) {
        intake = hardwareMap.get(CRServo.class, "intake");
        wrist  = hardwareMap.get(Servo.class, "wrist");
        intake.setPower(INTAKE_OFF);
        wrist.setPosition(WRIST_FOLDED_IN);
    }
    public void foldOut() {
        wrist.setPosition(WRIST_FOLDED_OUT);
    }
    public void collect() {
        intake.setPower(INTAKE_COLLECT);
    }
    public void deposit() {
        intake.setPower(INTAKE_DEPOSIT);
    }
}

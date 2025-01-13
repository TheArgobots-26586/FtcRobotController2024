package org.firstinspires.ftc.teamcode.drive;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class ArmMovements {
    ArmLifting armlifting = new ArmLifting(hardwareMap);
    ExtenderLift extenderLift = new ExtenderLift(hardwareMap);
    Servos servos = new Servos(hardwareMap);
    public ArmMovements(HardwareMap hardwareMap) {
        armlifting.armClearBarrier();
        extenderLift.unextend();
        servos.foldOut();
        servos.collect();
    }
    public void arm_collect() {
        armlifting.armCollect();
        servos.collect();
        extenderLift.unextend();
    }
    public void arm_score() {
        armlifting.armScoreSample();
        extenderLift.extend();

    }
    public void arm_clear() {

    }
}


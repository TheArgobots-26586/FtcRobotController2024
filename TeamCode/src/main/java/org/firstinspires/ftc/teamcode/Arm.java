package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    ArmLift lift = null;
    Extender extender = null;
    Servos servos = null;
    public Arm(HardwareMap hardwareMap) {
        lift = new ArmLift(hardwareMap);
        extender = new Extender(hardwareMap);
        servos = new Servos(hardwareMap);
    }

    public boolean clearBarrier() {
        if (extender.retract()) {
            return lift.clearBarrier();
        }
        return false;
    }

    public boolean collect() {
        servos.intakeIn();
        return lift.lower();
    }

    public boolean deposit() {
        if (lift.raise()) {
            if (extender.extend()) {
                servos.intakeOut();
                return true;
            }
        }
        return false;
    }
}

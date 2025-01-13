package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.ArmLift;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/*
 * This is a simple routine to test turning capabilities.
 */
@Config
@Autonomous(group = "drive")
public class TurnTest extends LinearOpMode {
    public static double ANGLE = 180; // deg

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        ArmLift armLift = new ArmLift(hardwareMap);
        waitForStart();

        if (isStopRequested()) return;
        while(!isStopRequested()) {
            if(gamepad1.a) {
                drive.turn(Math.toRadians(ANGLE));
            }
            armLift.liftArm(telemetry);
        }
    }
}

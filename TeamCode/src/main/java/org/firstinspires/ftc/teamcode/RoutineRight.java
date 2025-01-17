package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="Routine Right")
public class RoutineRight extends LinearOpMode {
    @Override
    public void runOpMode() {
        Pose2d pose = new Pose2d(-58, -8, Math.toRadians(0));
        Servos servos = new Servos(hardwareMap);
        ArmLift lift = new ArmLift(hardwareMap);
        Extender extender = new Extender(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(pose);

        TrajectorySequence RightRoutine = drive.trajectorySequenceBuilder(pose)
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    lift.hook();
                    servos.wristOut();
                })
                .waitSeconds(1)
                .lineToConstantHeading(new Vector2d(-31, 1))
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    lift.clearBarrier();
                    servos.intakeIn();
                })
                .waitSeconds(2)
                .back(10)
                .waitSeconds(1)
                .addTemporalMarker(() -> {
                    servos.intakeOut();
                })
                .addTemporalMarker(() -> {
                    lift.clearBarrier();
                })

                .back(5)
                .waitSeconds(2)
                .strafeRight(65)
                .back(8)
                .build();

        waitForStart();
        if(isStopRequested()) return;

        drive.followTrajectorySequence(RightRoutine);
    } }

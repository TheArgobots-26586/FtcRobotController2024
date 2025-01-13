package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name="Routine Left")
public class RoutineLeft extends LinearOpMode {


    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(-58, 14, Math.toRadians(0));
        Arm arm = new Arm(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Trajectory Trajectory1 = drive.trajectoryBuilder(startPose)
                .addTemporalMarker(0, () -> {})
                .splineTo(new Vector2d(-20, 48), Math.toRadians(150.0))
                .addTemporalMarker(3, arm::collect)
                .strafeLeft(3)
                .addSpatialMarker(new Vector2d(-23, -48), arm::clearBarrier)
                .splineTo(new Vector2d(-48, -48), Math.toRadians(150.0))
                .addSpatialMarker(new Vector2d(-48, -48), arm::deposit)
                .addSpatialMarker(new Vector2d(-48, -48), arm::clearBarrier)
                .build();

        waitForStart();
        if(isStopRequested()) return;
        drive.followTrajectory(Trajectory1);


} }

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@Autonomous(name="BasicTrajectory")
public class BasicTrajectory extends LinearOpMode {

    @Override
    public void runOpMode() {

        Pose2d pose = new Pose2d(-58, 14, Math.toRadians(0));
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        ArmLift arm = new ArmLift(hardwareMap);

        Trajectory Trajectory1 = drive.trajectoryBuilder(pose)
                .splineTo(new Vector2d(-48, 48), Math.toRadians(135.0))
                .build();

        waitForStart();
        while (!isStopRequested()) {
            if (arm.clearBarrier()) {
                drive.followTrajectory(Trajectory1);
                break;
            }
        }

        }
    }



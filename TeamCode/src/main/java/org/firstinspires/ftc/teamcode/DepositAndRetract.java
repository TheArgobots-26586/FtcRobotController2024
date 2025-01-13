package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@Autonomous(name="Deposit and Retract")
public class DepositAndRetract extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    enum State {
        START,
        ClEAR_BARRIER,
        GO_TO_COLLECT,
        COLLECT,
        GO_TO_BASKET,
        GO_TO_DEPOSIT,
        DEPOSIT,
        WAIT_TO_COLLECT
    }

    @Override
    public void runOpMode() {
        State state = State.START;
        Pose2d pose = new Pose2d(-58, 14, Math.toRadians(0));
        Arm arm = new Arm(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        int curr_traj = 0;
        Trajectory TrajectoryToDeposit = drive.trajectoryBuilder(pose)
                .splineTo(new Vector2d(-48, 48), Math.toRadians(135.0))
                .build();

        Trajectory Trajectory1ToCollect = drive.trajectoryBuilder(pose)
                .splineTo(new Vector2d(-24, 48), Math.toRadians(90.0)) // 48, -24
                .build();
        Trajectory Trajectory2ToCollect = drive.trajectoryBuilder(pose)
                .splineTo(new Vector2d(-24, 60), Math.toRadians(90.0)) // 48, -24
                .build();
        Trajectory Trajectory3ToCollect = drive.trajectoryBuilder(pose)
                .splineTo(new Vector2d(-24, 72), Math.toRadians(90.0)) // 48, -24
                .build();

        waitForStart();

        while (!isStopRequested()) {
            if (state == State.START) {
                if (arm.clearBarrier()) {
                    state = State.ClEAR_BARRIER;
                    runtime.reset();
                }
            } else if (state == State.ClEAR_BARRIER) {
                if (runtime.seconds() > 2) {
                    if (curr_traj == 0) {
                        state = State.GO_TO_DEPOSIT;
                    }
                    else {
                        state = State.GO_TO_COLLECT;
                        runtime.reset();
                    }
                }

            } else if (state == State.GO_TO_COLLECT) {
                /*
                if (curr_traj == 1) {
                    drive.followTrajectory(Trajectory1ToCollect);
                }
                else if (curr_traj == 2){
                    drive.followTrajectory(Trajectory2ToCollect);
                }
                else if (curr_traj == 3){
                    drive.followTrajectory(Trajectory3ToCollect);
                }

                else {
                    break;
                }
                state = State.COLLECT
                */

            } else if (state == State.COLLECT) {
                if (arm.collect() & runtime.seconds() > 5) {
                    state = State.GO_TO_BASKET;
                }
            } else if (state == State.GO_TO_BASKET) {
                if (arm.clearBarrier()) {
                    state = State.GO_TO_DEPOSIT;
                }
            } else if (state == State.GO_TO_DEPOSIT) {
                drive.followTrajectory(TrajectoryToDeposit);
            } else if (state == State.DEPOSIT) {
                    if (arm.deposit()) {
                        runtime.reset();
                        state = State.WAIT_TO_COLLECT;
                    }
                } else if (state == State.WAIT_TO_COLLECT & runtime.seconds() > 3) {
                    state = State.START;
                    curr_traj += 1;
                }
                telemetry.addData("State:", state);
                telemetry.update();
            }
        }
    }


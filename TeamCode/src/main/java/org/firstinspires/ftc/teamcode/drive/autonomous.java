package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "autonomous", group = "Robot")
public class autonomous extends LinearOpMode {
    public DcMotor armMotor = null;
    public CRServo intake      = null; //the active intake servo
    public Servo wrist       = null; //the wrist servo
    private ElapsedTime runtime = new ElapsedTime();


    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0;
    final double ARM_COLLAPSED_INTO_ROBOT  = 0;
    final double ARM_COLLECT               = 0 * ARM_TICKS_PER_DEGREE;
    final double ARM_CLEAR_BARRIER         = 25 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SPECIMEN        = 90 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SAMPLE_IN_LOW   = 95 * ARM_TICKS_PER_DEGREE;
    final double ARM_ATTACH_HANGING_HOOK   = 110 * ARM_TICKS_PER_DEGREE;
    final double ARM_WINCH_ROBOT           = 10  * ARM_TICKS_PER_DEGREE;

    /* Variables to store the speed the intake servo should be set at to intake, and deposit game elements. */
    final double INTAKE_COLLECT    = -1.0;
    final double INTAKE_OFF        =  0.0;
    final double INTAKE_DEPOSIT    =  0.5;

    /* Variables to store the positions that the wrist should be set to when folding in, or folding out. */
    final double WRIST_FOLDED_IN   = 0.85;
    final double WRIST_FOLDED_OUT  = 0.5;


    @Override
    public void runOpMode() {

        /* Define and initialize servos.*/
        intake = hardwareMap.get(CRServo.class, "intake");
        wrist  = hardwareMap.get(Servo.class, "wrist");


        /* Make sure that the intake is off, and the wrist is folded in. */
        intake.setPower(INTAKE_OFF);
        wrist.setDirection(Servo.Direction.REVERSE);
        wrist.setPosition(WRIST_FOLDED_IN);

        wrist.setPosition(WRIST_FOLDED_IN);
        armMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // armMotor.setTargetPosition((int)(160*ARM_TICKS_PER_DEGREE));
        // armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


        Pose2d startPose = new Pose2d(0, 0, 0);
        drive.setPoseEstimate(startPose);
        //BLUE TRAJECTORY 1 is for pushing 3 of the samples into observation
        TrajectorySequence BLUE_TRAJECTORY_1 = drive.trajectorySequenceBuilder(startPose)
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_CLEAR_BARRIER);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .forward(24)
                .lineToSplineHeading(new Pose2d(12, 60, Math.toRadians(135)))
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_SCORE_SAMPLE_IN_LOW);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .addTemporalMarker(() -> wrist.setPosition(WRIST_FOLDED_OUT))
                .waitSeconds(1.5)
                .addTemporalMarker(() -> intake.setPower(INTAKE_DEPOSIT))
                .waitSeconds(1)
                .addTemporalMarker(() -> intake.setPower(INTAKE_OFF))
                .addTemporalMarker(() ->{
                    armMotor.setTargetPosition((int)(ARM_CLEAR_BARRIER));
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .splineTo(new Vector2d(46, -42), Math.toRadians(-90))
                .turn(Math.toRadians(-90))
                .lineTo(new Vector2d(9, -62))
                .back(12)
                .turn(Math.toRadians(-180))
                .lineToConstantHeading(new Vector2d(48, -50))
                .strafeLeft(12)
                .turn(Math.toRadians(-180))
                .lineToConstantHeading(new Vector2d(9, -62))
                .back(10)
                .lineToConstantHeading(new Vector2d(46, -42))
                .lineToConstantHeading(new Vector2d(72, -62))
                .lineToConstantHeading(new Vector2d(9, -62))
                .build();


        runtime.reset();
        waitForStart();

        if (!isStopRequested()) {
            /*
            armMotor.setTargetPosition((int) ARM_SCORE_SAMPLE_IN_LOW);
            ((DcMotorEx) armMotor).setVelocity(2100);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            */
            drive.followTrajectorySequence(BLUE_TRAJECTORY_1);
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }
}
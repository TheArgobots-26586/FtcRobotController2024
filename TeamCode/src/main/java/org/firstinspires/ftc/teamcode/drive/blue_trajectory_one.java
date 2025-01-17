package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "BLUE TRAJECTORY 1", group = "Robot")
public class blue_trajectory_one extends LinearOpMode {
    public DcMotor armMotor = null;
    public DcMotor liftMotor = null;
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
    final double ARM_SCORE_SAMPLE_IN_LOW   = 105 * ARM_TICKS_PER_DEGREE;
    final double ARM_ATTACH_HANGING_HOOK   = 110 * ARM_TICKS_PER_DEGREE;
    final double ARM_WINCH_ROBOT           = 10  * ARM_TICKS_PER_DEGREE;

    /* Variables to store the speed the intake servo should be set at to intake, and deposit game elements. */
    final double INTAKE_COLLECT    = -1.0;
    final double INTAKE_OFF        =  0.0;
    final double INTAKE_DEPOSIT    =  0.5;

    /* Variables to store the positions that the wrist should be set to when folding in, or folding out. */
    final double WRIST_FOLDED_IN   = 0.85;
    final double WRIST_FOLDED_OUT  = 0.5;
    final double LIFT_TICKS_PER_MM = (111132.0 / 289.0) / 120.0;
    final double LIFT_COLLAPSED = 0 * LIFT_TICKS_PER_MM;
    final double LIFT_SCORING_IN_LOW_BASKET = 0 * LIFT_TICKS_PER_MM;
    final double LIFT_SCORING_IN_HIGH_BASKET = 1350;


    @Override
    public void runOpMode() {

        /* Define and initialize servos.*/
        intake = hardwareMap.get(CRServo.class, "intake");
        wrist  = hardwareMap.get(Servo.class, "wrist");


        /* Make sure that the intake is off, and the wrist is folded in. */
        intake.setPower(INTAKE_OFF);
        wrist.setPosition(WRIST_FOLDED_IN);

        armMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftMotor = hardwareMap.dcMotor.get("extender");
        ((DcMotorEx) liftMotor).setTargetPositionTolerance(35);
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setTargetPosition(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // armMotor.setTargetPosition((int)(160*ARM_TICKS_PER_DEGREE));
        // armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


        Pose2d startPose = new Pose2d(-58, 14, 0);
        drive.setPoseEstimate(startPose);
        //BLUE TRAJECTORY 1 is for pushing 3 of the samples into observation
        TrajectorySequence BLUE_TRAJECTORY_1 = drive.trajectorySequenceBuilder(startPose)
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_CLEAR_BARRIER);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .addTemporalMarker(() -> wrist.setPosition(WRIST_FOLDED_OUT))
                .lineToSplineHeading(new Pose2d(-50, 48, Math.toRadians(135)))
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_SCORE_SAMPLE_IN_LOW);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .forward(8)
                .waitSeconds(0.5)
                .addTemporalMarker(() ->{
                    liftMotor.setTargetPosition((int)(LIFT_SCORING_IN_HIGH_BASKET));
                    ((DcMotorEx) liftMotor).setVelocity(2100);
                    liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .addTemporalMarker(() -> wrist.setPosition(WRIST_FOLDED_OUT))
                .waitSeconds(1.5)
                .addTemporalMarker(() -> intake.setPower(INTAKE_DEPOSIT))
                .waitSeconds(0.5)
                .addTemporalMarker(() ->{
                    liftMotor.setTargetPosition((int)(LIFT_COLLAPSED));
                    ((DcMotorEx) liftMotor).setVelocity(2100);
                    liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .waitSeconds(0.5)
                .back(5)
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_CLEAR_BARRIER);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .waitSeconds(0.5)
                .lineToSplineHeading(new Pose2d(-20.5, 35, Math.toRadians(90)))
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_COLLECT);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> intake.setPower(INTAKE_COLLECT))
                .forward(4)
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_CLEAR_BARRIER);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .lineToSplineHeading(new Pose2d(-50, 48, Math.toRadians(135)))
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_SCORE_SAMPLE_IN_LOW);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .waitSeconds(0.5)
                .forward(8)
                .waitSeconds(0.5)
                .addTemporalMarker(() ->{
                    liftMotor.setTargetPosition((int)(LIFT_SCORING_IN_HIGH_BASKET));
                    ((DcMotorEx) liftMotor).setVelocity(2100);
                    liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .addTemporalMarker(() -> wrist.setPosition(WRIST_FOLDED_OUT))
                .waitSeconds(1.5)
                .addTemporalMarker(() -> intake.setPower(INTAKE_DEPOSIT))
                .waitSeconds(0.5)
                .addTemporalMarker(() ->{
                    liftMotor.setTargetPosition((int)(LIFT_COLLAPSED));
                    ((DcMotorEx) liftMotor).setVelocity(2100);
                    liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .waitSeconds(0.5)
                .back(5)
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_CLEAR_BARRIER);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .waitSeconds(0.5)
                .lineToSplineHeading(new Pose2d(-20, 45, Math.toRadians(90)))
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_COLLECT);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> intake.setPower(INTAKE_COLLECT))
                .forward(4)
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_CLEAR_BARRIER);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .lineToSplineHeading(new Pose2d(-50, 48, Math.toRadians(135)))
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_SCORE_SAMPLE_IN_LOW);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .waitSeconds(0.5)
                .forward(8)
                .waitSeconds(0.5)
                .addTemporalMarker(() ->{
                    liftMotor.setTargetPosition((int)(LIFT_SCORING_IN_HIGH_BASKET));
                    ((DcMotorEx) liftMotor).setVelocity(2100);
                    liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .addTemporalMarker(() -> wrist.setPosition(WRIST_FOLDED_OUT))
                .waitSeconds(1.5)
                .addTemporalMarker(() -> intake.setPower(INTAKE_DEPOSIT))
                .waitSeconds(0.5)
                .addTemporalMarker(() ->{
                    liftMotor.setTargetPosition((int)(LIFT_COLLAPSED));
                    ((DcMotorEx) liftMotor).setVelocity(2100);
                    liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .waitSeconds(0.5)
                .back(5)
                .addTemporalMarker(() -> {
                    armMotor.setTargetPosition((int) ARM_CLEAR_BARRIER);
                    ((DcMotorEx) armMotor).setVelocity(2100);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .build();

        runtime.reset();
        waitForStart();

        if (!isStopRequested()) {
            drive.followTrajectorySequence(BLUE_TRAJECTORY_1);
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }
}
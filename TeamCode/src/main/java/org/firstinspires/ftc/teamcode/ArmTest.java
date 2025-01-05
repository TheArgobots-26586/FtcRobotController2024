
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name="Arm Test", group="TeleOp")

public class ArmTest extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private DcMotor armMotor = null;


    // This tells us the ticks per degree
    //each motor has an individual amount of ticks per revolution

    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0; // we want ticks per degree, not per rotation
    //The end result of these calculations is 19.7924893141


    //Arm collapsed into robot is how it looks when it starts
    //It should be the "0 ticks" for the motor
    final double ARM_COLLAPSED_INTO_ROBOT  = 0;
    //Arm collect is where it goes when the lift(button y) is pressed
    final double ARM_COLLECT               = 50 * ARM_TICKS_PER_DEGREE;


    //The arms current position should start as collapsed into the robot aka 0 ticks
    double armPosition = (int)ARM_COLLAPSED_INTO_ROBOT;
    @Override

    public void runOpMode() {
        //map armMotor to the configured motor on driver hub
        armMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        /*This sets the maximum current that the control hub will apply to the arm before throwing a flag */
        ((DcMotorEx) armMotor).setCurrentAlert(5, CurrentUnit.AMPS);

        //reset encoder ticks to 0
        //armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        // When the driver presses start this telemtry will show
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // while (opModeIsActive()) {

            //ARM CODE begins

            telemetry.addData("Button Pressed", "NONE");
            //if y is pressed the arm should rise

            if(gamepad1.y){
                armPosition = ARM_COLLECT;
                telemetry.addData("Button Pressed", "LIFT");
                //armMotor.setTargetPosition((int) (armPosition));
                armMotor.setTargetPosition((int)armPosition);

                // armMotor.setTargetPosition((int) (armPosition));

                ((DcMotorEx) armMotor).setVelocity(2100);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                //If the motor exceeds curent limit of 5 amps

                if (((DcMotorEx) armMotor).isOverCurrent()) {
                    telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!");
                }


                telemetry.update();
            }

            //if x is pressed the arm should rest
            else if(gamepad1.x) {
                armPosition = ARM_COLLAPSED_INTO_ROBOT;
                telemetry.addData("Button Pressed", "REST");
                armMotor.setTargetPosition((int) (armPosition));
            }
            telemetry.addData("Arm Current Position", armMotor.getCurrentPosition());
            telemetry.addData("Arm Target Position", armPosition);
            telemetry.update();

        }
    }}
